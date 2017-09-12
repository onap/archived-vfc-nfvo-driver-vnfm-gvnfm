/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.EntityUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.JujuConfigUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.StringUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl.JujuClientManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.inf.IJujuClientManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process.VnfMgr;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author 
 * @version NFVO 0.5 Aug 18, 2016
 */
@Path("/v1/vnfms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JujuClientRoa {

    private static final Logger LOG = LoggerFactory.getLogger(JujuClientRoa.class);

    private IJujuClientManager jujuClientManager;
    private VnfMgr vnfMgr;

    /**
     * @return Returns the jujuClientManager.
     */
    public IJujuClientManager getJujuClientManager() {
        return jujuClientManager;
    }

    /**
     * @param jujuClientManager The jujuClientManager to set.
     */
    public void setJujuClientManager(IJujuClientManager jujuClientManager) {
        this.jujuClientManager = jujuClientManager;
    }

    /**
     *
     * @return
     */
    public VnfMgr getVnfMgr() {
        return vnfMgr;
    }

    /**
     *
     * @param vnfMgr
     */
    public void setVnfMgr(VnfMgr vnfMgr) {
        this.vnfMgr = vnfMgr;
    }

    /**
     * Set Charm url for juju deployment
     * <br/>
     * 
     * @param resp
     * @param context
     *            parameter : charmUrl
     * @return "{"charmUrl":"http://dld_url"}
     * @since NFVO 0.5
     */
    @POST
    @Path("/setCharmUrl")
    public String setCharmUrl(@Context HttpServletRequest context, @Context HttpServletResponse resp)
            throws ServiceException {
        JSONObject result = new JSONObject();
        result.put("retCode", Constant.REST_FAIL);
        JSONObject reqJsonObject = StringUtil.getJsonFromContexts(context);
        
        LOG.debug(reqJsonObject + ":");
        return result.toString();
    }

    /**
     * Get VNF status
     * parameter: vnfInstanceId
     * <br/>
     * 
     * @param modelName
     * @param resp
     * @param context
     * @return Depends on juju's return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @GET
    @Path("/status")
    public String getVnfStatus(@QueryParam("modelName") String modelName, @Context HttpServletRequest context,
            @Context HttpServletResponse resp) throws ServiceException {
        String appName = processAppName(modelName);
        JSONObject result = jujuClientManager.getStatus(appName);
        LOG.debug("status json str:"+result.toString());
        return result.toString();


    }

    /**
     * Instance VNF to juju-client
     * <br/>
     * deployParam: depend on juju require
     * 
     * @param resp
     * @param context
     * @return status: deplay result <br>
     *         the return data must be include "{
     *         app_info:{"vnfId":123344}
     *         }"
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @POST
    @Path("/deploy")
    public String deploySerivce(@Context HttpServletRequest context, @Context HttpServletResponse resp)
            throws ServiceException {
        JSONObject result = new JSONObject();
        String msg = null;
        try {
            result.put(EntityUtils.RESULT_CODE_KEY, EntityUtils.ExeRes.FAILURE);
            JSONObject reqJsonObject = StringUtil.getJsonFromContexts(context);
            LOG.info("deploySerivce request data-->"+reqJsonObject);
            if(reqJsonObject == null || reqJsonObject.get(Constant.APP_NAME) == null){
                result.put(EntityUtils.MSG_KEY, "the param 'appName' can't be null");
                resp.setStatus(Constant.HTTP_INNERERROR); 
                return result.toString();
            }
            String csarId = (String)reqJsonObject.get("csarId");
          
            String appName = reqJsonObject.getString(Constant.APP_NAME);
            appName = processAppName(appName);
            //1、download the catalog,unzip file and get the charmPath  
            String charmPath = vnfMgr.getCharmPath(csarId);
            if(StringUtils.isBlank(charmPath)) {
                    charmPath = JujuConfigUtil.getValue("charmPath");
            }
            String vnfId = UUID.randomUUID().toString();
            //2、grant resource
            boolean grantRes = jujuClientManager.grantResource(charmPath, appName, JujuClientManager.ADDRESOURCE, vnfId);
            LOG.info("grantResource result:"+grantRes);
            //3、deploy service
            if(grantRes){
                result = jujuClientManager.deploy(charmPath,appName);
                if(result.getInt(EntityUtils.RESULT_CODE_KEY) == EntityUtils.ExeRes.SUCCESS){
                    resp.setStatus(Constant.HTTP_CREATED); 
                }
                result.put("vnfId", vnfId);//return vnfId
                return result.toString();
            }else{
                msg = "Grant resource fail:"+vnfId;
            }
        } catch(Exception e) {
            msg = e.getMessage();
           LOG.error("deploy fail in method deployService",e);
        }
        resp.setStatus(Constant.HTTP_INNERERROR); 
        result.put(EntityUtils.MSG_KEY, msg);
        return result.toString();
    }

    /**
     * <br/>
     * here appName equals modelName
     * @param resp
     * @param context
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @POST
    @Path("/destroy")
    public String destroySerivce(@Context HttpServletRequest context, @Context HttpServletResponse resp)
            throws ServiceException {
        JSONObject result = new JSONObject();
        result.put(EntityUtils.RESULT_CODE_KEY, EntityUtils.ExeRes.FAILURE);
        String msg;
        try {
            JSONObject reqJsonObject = StringUtil.getJsonFromContexts(context);
            if(reqJsonObject == null || reqJsonObject.get(Constant.APP_NAME) == null){
                result.put(EntityUtils.MSG_KEY, "the param 'appName' can't be null");
                resp.setStatus(Constant.HTTP_INNERERROR); 
                return result.toString();
            }
            String appName = reqJsonObject.getString(Constant.APP_NAME);
            appName = processAppName(appName);
            String vnfId="";
            if(reqJsonObject.containsKey("vnfId")) {
                vnfId = reqJsonObject.getString("vnfId");
            }
            result = jujuClientManager.destroy(appName);
            resp.setStatus(Constant.UNREG_SUCCESS);
            LOG.info("destroy service success!!!"+appName+vnfId);
            return result.toString();
        } catch(Exception e) {
            msg = e.getMessage();
            LOG.error("destory fail in method destroyService",e);
         
        }
        resp.setStatus(Constant.HTTP_INNERERROR); 
        result.put(EntityUtils.MSG_KEY, msg);
        return result.toString();
    }

    private static String processAppName(String appName){
        if(appName != null && appName.indexOf(".yaml") > 0){//remove zte's attach
            return appName.substring(0,appName.indexOf(".yaml"))+".yaml";
        }
        return appName;
    }

}
