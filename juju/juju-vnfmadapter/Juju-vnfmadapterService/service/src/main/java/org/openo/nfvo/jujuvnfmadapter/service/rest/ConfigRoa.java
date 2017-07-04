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

package org.openo.nfvo.jujuvnfmadapter.service.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.nfvo.jujuvnfmadapter.common.EntityUtils;
import org.openo.nfvo.jujuvnfmadapter.common.FileUtils;
import org.openo.nfvo.jujuvnfmadapter.common.JujuConfigUtil;
import org.openo.nfvo.jujuvnfmadapter.common.EntityUtils.ExeRes;
import org.openo.nfvo.jujuvnfmadapter.common.servicetoken.JujuVnfmRestfulUtil;
import org.openo.nfvo.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.openo.nfvo.jujuvnfmadapter.service.adapter.impl.AdapterResourceManager;
import org.openo.nfvo.jujuvnfmadapter.service.constant.Constant;
import org.openo.nfvo.jujuvnfmadapter.service.constant.UrlConstant;
import org.openo.nfvo.jujuvnfmadapter.common.SwitchController;
import org.openo.nfvo.jujuvnfmadapter.common.VnfmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Sep 13, 2016
 */
@Path("/v1/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigRoa {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigRoa.class);

    /**
     * <br/>
     * 
     * @param context
     * @param resp
     * @return
     * @since NFVO 0.5
     */
    @GET
    @Path("/")
    public String initUI(@Context HttpServletRequest context, @Context HttpServletResponse resp) {
        return EntityUtils.toString(new SwitchController(), SwitchController.class);
    }

    /**
     * <br/>
     * 
     * @param type
     * @param context
     * @param resp
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @GET
    @Path("/debug/{type}")
    public boolean setDebugModel(@PathParam("type") int type, @Context HttpServletRequest context,
            @Context HttpServletResponse resp) throws ServiceException {
        if(type == 1) {
            SwitchController.setDebugModel(true);
        } else {
            SwitchController.setDebugModel(false);
        }
        LOG.debug("change to debug model:" + SwitchController.isDebugModel());
        return SwitchController.isDebugModel();
    }

    /**
     * <br/>
     * 
     * @param methodName
     * @param context
     * @param resp
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @GET
    @Path("/mock/{methodName}")
    public boolean mock(@PathParam("methodName") String methodName, @Context HttpServletRequest context,
            @Context HttpServletResponse resp) throws ServiceException {
        if("getVnfmById".equals(methodName)) {
            new MockUp<VnfmUtil>() {

                @Mock
                public JSONObject getVnfmById(String vnfmId) {
                    JSONObject json = new JSONObject();
                    json.put("vnfmId", vnfmId);
                    json.put("vnfdId", "testVnfdId");
                    json.put("vnfPackageId", "testPackageId");
                    json.put("version", "1");
                    json.put("url", JujuConfigUtil.getValue("jujuvnfm_server_url"));
                    return json;
                }
            };
        } else if("execute".equals(methodName)) {
            new MockUp<EntityUtils>() {

                @Mock
                public ExeRes execute(String dir, List<String> command) {
                    ExeRes er = new ExeRes();
                    String resContent = null;
                    try {
                        resContent = new String(
                                FileUtils.readFile(new File(JujuConfigUtil.getValue("juju_cmd_res_file")), "UTF-8"));
                    } catch(Exception e) {
                        LOG.error("mock fail",e);
                        resContent = "mock fail";
                    }
                    er.setBody(resContent);
                    return er;
                }
            };
        }else if("fetchDownloadUrlFromCatalog".equals(methodName)) {
            new MockUp<AdapterResourceManager>() {
                @Mock
                public String fetchDownloadUrlFromCatalog(String csarId){
                    return JujuConfigUtil.getValue("catalog_download_url");
                }
            };
        }
        return true;
    }

    /**
     * <br/>
     * 
     * @param methodName
     * @param context
     * @param resp
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    @GET
    @Path("/unmock/{methodName}")
    public boolean unmock(@PathParam("methodName") String methodName, @Context HttpServletRequest context,
            @Context HttpServletResponse resp) throws ServiceException {
        if("getVnfmById".equals(methodName)) {
            new MockUp<VnfmUtil>() {

                @Mock
                public JSONObject getVnfmById(String vnfmId) {
                    RestfulResponse rsp = VnfmRestfulUtil.getRemoteResponse(
                            String.format(UrlConstant.REST_ESRINFO_GET, vnfmId), JujuVnfmRestfulUtil.GET_TYPE, null);
                    if(rsp == null || rsp.getStatus() != Constant.HTTP_OK) {
                        return null;
                    }
                    LOG.error("funtion=getVnfmById, status={}", rsp.getStatus());
                    return JSONObject.fromObject(rsp.getResponseContent());
                }
            };
        }
        return true;
    }
}
