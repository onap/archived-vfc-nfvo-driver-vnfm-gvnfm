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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.JujuVnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.UrlConstant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * Provide function of getting vnfmInfo
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Aug 25, 2016
 */
public final class VnfmUtil {

    private static final Logger LOG = LoggerFactory.getLogger(VnfmUtil.class);
    private VnfmUtil() {

    }

    /**
     * Get vnfmInfo by ip
     * <br/>
     * 
     * @param vnfmId
     * @return
     * @since  NFVO 0.5
     */
    public static JSONObject getVnfmById(String vnfmId) {
        if(SwitchController.isDebugModel()){
            JSONObject json = new JSONObject();
            json.put("vnfmId", vnfmId);
            json.put("vnfdId","testVnfdId");
            json.put("vnfPackageId", "testPackageId");
            json.put("version", "1");
            json.put("url",JujuConfigUtil.getValue("jujuvnfm_server_url"));
            return json;
        }
        RestfulResponse rsp = VnfmRestfulUtil.getRemoteResponse(String.format(UrlConstant.REST_ESRINFO_GET, vnfmId), JujuVnfmRestfulUtil.GET_TYPE, null);
        if(rsp == null || rsp.getStatus() != Constant.HTTP_OK) {
            return null;
        }
        LOG.error("funtion=getVnfmById, status={}", rsp.getStatus());
        return JSONObject.fromObject(rsp.getResponseContent());
    }

    /**
     * Get vnfmInfo by id
     * <br/>
     * 
     * @param ip
     * @return
     * @since  NFVO 0.5
     */
    public static String getVnfmIdByIp(String ip) {
        RestfulResponse rsp =
                VnfmRestfulUtil.getRemoteResponse(UrlConstant.REST_ESRINFO_GET, VnfmRestfulUtil.TYPE_GET, null);
        if(rsp == null || rsp.getStatus() != Constant.HTTP_OK) {
            return "";
        }

        JSONArray vnfmList = JSONArray.fromObject(rsp.getResponseContent());
        for(int i = 0; i < vnfmList.size(); i++) {
            if(vnfmList.getJSONObject(i).getString("url").contains(ip)) {
                return vnfmList.getJSONObject(i).getString("vnfmId");
            }
        }

        return "";
    }
}
