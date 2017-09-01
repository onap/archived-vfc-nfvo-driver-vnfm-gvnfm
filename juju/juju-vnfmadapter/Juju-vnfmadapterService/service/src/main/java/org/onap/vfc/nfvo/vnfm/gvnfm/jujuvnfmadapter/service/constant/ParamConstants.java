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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant;

/**
 * 
 * Parameter constants class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class ParamConstants {

    public static final String GET_TOKENS_V1 = "{\"grantType\": \"password\", \"userName\": \"%s\",\"value\": \"%s\"}";

    public static final String GET_TOKENS_V2 =
            "{\"auth\":{\"identity\": {\"methods\": [\"password\"],\"password\": {\"user\": {\"name\": \"%s\",\"password\": \"%s\"}}}}}";

    public static final String GET_IAM_TOKENS =
            "{\"auth\": {\"identity\": {\"methods\": [\"password\"],\"password\": {\"user\": {\"name\": "
                    + "\"%s\",\"password\": \"%s\",\"domain\": {\"name\": \"%s\"}}}},\"scope\": {\"domain\": {\"name\": \"%s\"}}}}";

    public static final String GET_TOKEN_SUC_RESP =
            "{\"token\": {\"methods\": [\"password\"],\"expires_at\": \"\",\"user\": {\"id\": \"%s\","
                    + "\"name\": \"%s\"},\"roa_rand\": \"%s\"}}";

    public static final String GET_TOKEN_FAIL_RESP = "{\"Information\": \"%s\"}";

    public static final String REST_3RD_CONNECTION = "/rest/plat/smapp/v1/oauth/token";

    public static final String REST_3RD_DISCONNECT = "/rest/plat/smapp/v1/sessions?roarand=%s";

    public static final String REST_3RD_HANDSHAKE = "/rest/plat/ssm/v1/sessions/verify";

    public static final String IAM_AUTH = "/v3/auth/tokens";

    public static final String CSM_AUTH_CONNECT = "/v2/auth/tokens";

    public static final String CSM_AUTH_DISCONNECT = "/v2/auth/tokens/%s/%s";

    public static final String CSM_AUTH_HANDSHAKE = "/v2/nfvo/shakehand?roattr=status";

    public static final String VNFMMED = "/rest/vnfmmed/";

    public static final String CONNECTMGR_CONNECT = "/connectmgr/v1/connect";

    public static final String CONNECTMGR_DISCONNECT = "/connectmgr/v1/disconnect";

    public static final String CONNECTMGR_HANDSHAKE = "/connectmgr/v1/handshake";

    public static final String CREATE_VNF_PERF = "/staticsmgr/v1/vnfperformance";

    public static final String VNFMGR_INSTANCE = "/vnfmgr/v1/instances";

    public static final String VNF_SCALE = "/vnfmgr/v1/instances/%s/scale";

    public static final String VNFD_FLAVOR = "/vnfdmgr/v1/flavor";

    public static final String VNFDGR_INSTALL = "/vnfdmgr/v1/vnfd/%s/install";

    public static final String VNFDGR_DETAIL = "/vnfdmgr/v1/vnfd/%s";

    public static final String VNFDGR_DETAILS = "/vnfdmgr/v1/vnfd";

    public static final String VNFDGR_TOPOLOGY = "/vnfdmgr/v1/topology/%s";

    public static final String UPDATE_RESOURCE = "/rest/v1/resmanage/resuse/updateres";

    public static final String VNFDGR_INSTANCE = "/vnfmmed/v1/vnfdm/";

    public static final String VNF_QUERY = "/resmgr/v1/vnfs";

    public static final String VMS_QUERY = "/resmgr/v1/vms";

    public static final String REST_EVENT_ADD = "/rest/v1/resmanage/vnfm/site";

    public static final String REST_EVENT_DEL = "/rest/v1/resmanage/vnfm/site/%s";

    public static final String VNFMGR_VNFKPI = "/staticsmgr/v1/vnfkpi";

    public static final String RES_VNF = "/rest/v1/resmanage/vappvm";

    public static final String NOTIFY_VNF_PERF = "/rest/v1/resmanage/vappvm";

    public static final String PARAM_MODULE = "VnfmDriver";

    public static final String GET_ALL_SITES = "/rest/v1/resmanage/sites";

    public static final String GET_ALL_SOS = "/rest/sodriver/v1/sos";

    public static final String OPERATION_LOG_PATH = "/rest/plat/audit/v1/logs";

    public static final String SYSTEM_LOG_PATH = "/rest/plat/audit/v1/systemlogs";

    public static final String SECURITY_LOG_PATH = "/rest/plat/audit/v1/seculogs";

    public static final String SCALINGPOLICY_QUERY = "/policymgr/v1/scalingpolicy";

    public static final String SCALINGPOLICY_OPERATE = "/policymgr/v1/scalingpolicy/%s/%s";

    public static final String ACTIVE_POLICY = "/policymgr/v1/activepolicy";

    public static final String DEACTIVE_POLICY = "/policymgr/v1/deactivepolicy";

    public static final String GET_VNFM_VNF = "/rest/v1/resmanage/vapps?vnfmId=%s";

    public static final String GET_RES_NET = "/rest/v1/resmanage/virtualnetworks?id=%s";

    public static final String GET_JOB_STATUS = "/vnfmgr/v1/jobs/%s";

    public static final String CREATE_POLICY = "/policymgr/v1/vnfs/%s/policies";

    public static final String REST_FOR_VNFD_PLANS = "/v2/vapps/templates/%s/plans";

    public static final String REST_GET_VNFDS = "/v2/vapps/templates";

    public static final String REST_FOR_VNFD_BASIC = "/v2/vapps/templates?type=basic&vendor=%s&vnfdID=%s&version=%s";

    public static final String REST_TOPOLOGY = "/v1/vapps/templates/%s?type=templatetopology";

    public static final String VNF_INSTANCE = "/v2/vapps/instances";

    public static final String VNF_INSTANCE_DEL = "/v2/vapps/instances/%s";
    
    private ParamConstants() {
        //Constructor
    }
}
