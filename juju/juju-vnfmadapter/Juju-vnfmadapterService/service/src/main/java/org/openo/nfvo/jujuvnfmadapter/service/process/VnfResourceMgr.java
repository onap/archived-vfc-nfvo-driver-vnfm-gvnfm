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

package org.openo.nfvo.jujuvnfmadapter.service.process;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.nfvo.jujuvnfmadapter.common.SwitchController;
import org.openo.nfvo.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.openo.nfvo.jujuvnfmadapter.service.constant.Constant;
import org.openo.nfvo.jujuvnfmadapter.service.constant.UrlConstant;
import org.openo.nfvo.jujuvnfmadapter.service.entity.JujuVnfmInfo;
import org.openo.nfvo.jujuvnfmadapter.service.entity.JujuVnfmInfoExample;
import org.openo.nfvo.jujuvnfmadapter.service.mapper.JujuVnfmInfoMapper;
import org.python.jline.internal.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Provide function of resource for VNFM.
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Aug 25, 2016
 */
public class VnfResourceMgr {

    private static final Logger LOG = LoggerFactory.getLogger(VnfResourceMgr.class);

    private JujuVnfmInfoMapper jujuVnfmInfoMapper;

    /**
     * @return Returns the jujuVnfmInfoMapper.
     */
    public JujuVnfmInfoMapper getJujuVnfmInfoMapper() {
        return jujuVnfmInfoMapper;
    }

    /**
     * @param jujuVnfmInfoMapper The jujuVnfmInfoMapper to set.
     */
    public void setJujuVnfmInfoMapper(JujuVnfmInfoMapper jujuVnfmInfoMapper) {
        this.jujuVnfmInfoMapper = jujuVnfmInfoMapper;
    }

    /**
     * Provide function of grant resource for VNFM.
     * <br/>
     *
     * @param vnfId
     * @return
     * @since NFVO 0.5
     */
    public JSONObject grantVnfResource(JSONObject compute, String vnfId) {
        LOG.warn("function=grantVnfResource, msg=call LCM to grant vnf resource, params: {}", compute);
        JSONObject resultJson = new JSONObject();
        resultJson.put("retCode", Constant.REST_FAIL);
        try {
            if(SwitchController.isDebugModel()){
                resultJson.put("retCode", Constant.REST_SUCCESS); 
                resultJson.put("data", "{'mock_result':true}"); 
                return resultJson;
            }
            JujuVnfmInfo vnfmInfo = findByVnfId(vnfId);
            String vimId = getVimId(vnfmInfo);
            JSONObject reqParams = this.buildGrantReq(vimId, vnfId,compute);
            
            resultJson = VnfmRestfulUtil.sendReqToApp(UrlConstant.RES_VNF, Constant.POST, reqParams);
        } catch(JSONException e) {
            LOG.error("function=grantVnfResource, msg=parse params occoured JSONException e={}.", e);
            resultJson.put("errorMsg", "params parse exception");
        }

        return resultJson;
    }
    
    /**
     * {
    "vnfInstanceId": 1,
    "addResource": [
        {
            "resourceDefinitionId": "11111",
            "resourceTemplate": {
                "VirtualComputeDescriptor": {
                    "virtualCpu": {
                        "numVirtualCpu": 1
                    },
                    "virtualMemory": {
                        "virtualMemSize": 1//mem in MB
                    }
                },
                "VirtualStorageDescriptor": {
                    "typeOfStorage": "",
                    "sizeOfStorage": 111, //disk in GB
                    "swImageDescriptor": ""
                }
            },
            "type": "vdu",
            "vdu": "vdu_name"
        }
    ],
    "vimId":"",
    "removeResource": [],
    "additionalParam": {}
}
     * 
     * <br/>
     * 
     * @return
     * @since  NFVO 0.5
     */
    private JSONObject buildGrantReq(String vimId, String vnfInstanceId,JSONObject compute){
        JSONObject obj = new JSONObject();
        obj.put("vnfInstanceId", vnfInstanceId);
        obj.put("vimId", vimId);
        obj.put("additionalParam", new JSONObject());
        JSONArray array = new JSONArray();
        JSONObject resource = new JSONObject();
        resource.put("resourceDefinitionId", UUID.randomUUID().toString());
        resource.put("type", "vdu");
        resource.put("vdu", "vdu_name");//vdu_name?
        JSONObject resourceTemplate = new JSONObject();
        JSONObject virtualComputeDescriptor = new JSONObject();
        JSONObject virtualCpu = new JSONObject();
        JSONObject virtualMemory = new JSONObject();
        virtualCpu.put("numVirtualCpu", compute.get("cpu") !=null?compute.get("cpu"):0);
        virtualMemory.put("virtualMemSize", compute.get("mem") !=null?compute.get("mem"):0);
        virtualComputeDescriptor.put("virtualCpu", virtualCpu);
        virtualComputeDescriptor.put("virtualMemory", virtualMemory);
        JSONObject virtualStorageDescriptor = new JSONObject();
        virtualStorageDescriptor.put("typeOfStorage", "");
        virtualStorageDescriptor.put("swImageDescriptor", "");
        virtualStorageDescriptor.put("sizeOfStorage", compute.get("disk") !=null?compute.get("disk"):0);
        resourceTemplate.put("VirtualComputeDescriptor", virtualComputeDescriptor);
        resourceTemplate.put("VirtualStorageDescriptor", virtualStorageDescriptor);
        resource.put("resourceTemplate", resourceTemplate);
        array.add(resource);
        if("addResource".equals(compute.getString("action"))){
            obj.put("addResource", array);
            obj.put("removeResource", new JSONArray());
        }else{
            obj.put("removeResource", array); 
            obj.put("addResource", new JSONArray());
        }
        Log.info("buildGrantReq->result="+obj);
        return obj;
    }

    /**
     * {
     * “vnfInstanceName”:”vFW”,
     * “vnfPackageId”:”1”,
     * “vnfDescriptorId”:”1”,
     * “vnfInstanceDescription”:”vFW_1”,
     * “extVirtualLinks”:[
     * {
     * ”vlInstanceId”:”1”,
     * “resourceId”:”1246”,
     * ” cpdId”:”11111”,
     * ”vim”:
     * {
     * “vimInfoId”:”1”,
     * “vimid”:”1”,
     * “interfaceInfo”:{
     * "vimType":”vim”,
     * "apiVersion":”v2”,
     * "protocolType":”http”
     * }
     * “accessInfo”:{
     * "tenant":”tenant_vCPE”,
     * "username":”vCPE”,
     * "password":”vCPE_321”
     * }
     * “interfaceEndpoint”:”http://10.43.21.105:80/”
     * }
     * }
     * ]
     * “additionalParam”:{
     * ……
     * }
     * }
     * <br/>
     * 
     * @return
     * @since NFVO 0.5
     */
    private String getVimId(JujuVnfmInfo vnfmInfo) {
        try {
            if(vnfmInfo != null && StringUtils.isNotBlank(vnfmInfo.getExtend())){
                JSONObject json =  JSONObject.fromObject(vnfmInfo.getExtend());
                JSONObject extVirtualLinkLink = json.getJSONArray("extVirtualLinks").getJSONObject(0);
                String vimId = extVirtualLinkLink.getJSONObject("vim").getString("vimid");
                return vimId;
            }
        } catch(Exception e) {
            LOG.error("vnfmInfo.getExtend() format error!please check it",e);
        }
       return null;
    }

    /**
     * findByVnfId from db
     * <br/>
     * 
     * @param vnfId
     * @return
     * @since NFVO 0.5
     */
    private JujuVnfmInfo findByVnfId(String vnfId) {
        JujuVnfmInfoExample example = new JujuVnfmInfoExample();
        example.createCriteria().andVnfIdEqualTo(vnfId);
        List<JujuVnfmInfo> list = jujuVnfmInfoMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(list) && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
