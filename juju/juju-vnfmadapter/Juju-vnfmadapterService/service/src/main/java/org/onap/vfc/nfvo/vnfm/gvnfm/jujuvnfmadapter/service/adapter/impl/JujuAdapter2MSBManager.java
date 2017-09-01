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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl;

import java.util.Map;

import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.JujuVnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.inf.IJujuAdapter2MSBManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 
 * Juju adapter to msb manager class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class JujuAdapter2MSBManager implements IJujuAdapter2MSBManager {

    private static final Logger LOG = LoggerFactory.getLogger(JujuAdapter2MSBManager.class);

    @Override
    public JSONObject registerJujuAdapter(Map<String, String> paramsMap, JSONObject adapterInfo) {
        JSONObject resultObj = new JSONObject();
        //verify url,reserve
        
        RestfulResponse rsp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap,adapterInfo.toString());
        if(null == rsp) {
            LOG.error("function=registerJujuAdapter,  RestfulResponse is null");
            resultObj.put(Constant.REASON, "RestfulResponse is null.");
            resultObj.put(Constant.RETURN_CODE, Constant.ERROR_STATUS_CODE);
            return resultObj;
        }
        String resultCreate = rsp.getResponseContent();

        if(rsp.getStatus() == Constant.HTTP_CREATED) {
            LOG.warn("function=registerJujuAdapter, msg= status={}, result={}.", rsp.getStatus(), resultCreate);
            resultObj = JSONObject.fromObject(resultCreate);
            resultObj.put(Constant.RETURN_CODE, Constant.HTTP_CREATED);
            return resultObj;
        } else if(rsp.getStatus() == Constant.INVALID_PARAMETERS){
            LOG.error("function=registerJujuAdapter, msg=MSB return fail,invalid parameters,status={}, result={}.", rsp.getStatus(),
                    resultCreate);
            resultObj.put(Constant.REASON, "MSB return fail,invalid parameters.");
        } else if(rsp.getStatus() == Constant.HTTP_INNERERROR){
            LOG.error("function=registerJujuAdapter, msg=MSB return fail,internal system error,status={}, result={}.", rsp.getStatus(),
                    resultCreate);
            resultObj.put(Constant.REASON, "MSB return fail,internal system error.");
        }
        resultObj.put(Constant.RETURN_CODE, Constant.ERROR_STATUS_CODE);
        return resultObj;
    }

    @Override
    public JSONObject unregisterJujuAdapter(Map<String, String> paramsMap) {
        JSONObject resultObj = new JSONObject();
        //verify url,reserve
        
        RestfulResponse rsp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap,"");
        if(null == rsp) {
            LOG.error("function=unregisterJujuAdapter,  RestfulResponse is null");
            resultObj.put(Constant.REASON, "RestfulResponse is null.");
            resultObj.put(Constant.RETURN_CODE, Constant.ERROR_STATUS_CODE);
            return resultObj;
        }
        String resultCreate = rsp.getResponseContent();

        if(rsp.getStatus() == Constant.UNREG_SUCCESS) {
            LOG.warn("function=unregisterJujuAdapter, msg= status={}, result={}.", rsp.getStatus(), resultCreate);
            resultObj = JSONObject.fromObject(resultCreate);
            resultObj.put(Constant.RETURN_CODE, Constant.UNREG_SUCCESS);
            return resultObj;
        } else if(rsp.getStatus() == Constant.HTTP_NOTFOUND){
            LOG.error("function=unregisterJujuAdapter, msg=MSB return fail,can't find the service instance.status={}, result={}.", rsp.getStatus(),
                    resultCreate);
            resultObj.put(Constant.REASON, "MSB return fail,can't find the service instance.");
        } else if(rsp.getStatus() == Constant.INVALID_PARAMETERS){
            LOG.error("function=unregisterJujuAdapter, msg=MSB return fail,invalid parameters,status={}, result={}.", rsp.getStatus(),
                    resultCreate);
            resultObj.put(Constant.REASON, "MSB return fail,invalid parameters.");
        } else if(rsp.getStatus() == Constant.HTTP_INNERERROR){
            LOG.error("function=unregisterJujuAdapter, msg=MSB return fail,internal system error,status={}, result={}.", rsp.getStatus(),
                    resultCreate);
            resultObj.put(Constant.REASON, "MSB return fail,internal system error.");
        }
        resultObj.put(Constant.RETURN_CODE, Constant.ERROR_STATUS_CODE);
        return resultObj;
    }
}
