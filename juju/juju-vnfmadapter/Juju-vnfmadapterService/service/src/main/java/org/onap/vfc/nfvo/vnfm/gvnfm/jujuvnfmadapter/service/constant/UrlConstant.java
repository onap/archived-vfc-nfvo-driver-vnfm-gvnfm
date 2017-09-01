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
 * Url constant class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Sep 10, 2016
 */
public class UrlConstant {

    public static final String REST_DRIVERMGR_REGISTER = "/openoapi/drivermgr/v1/drivers";

    public static final String REST_MSB_REGISTER = "/api/microservices/v1/services";

    public static final String REST_ESRINFO_GET = "/openoapi/extsys/v1/vnfms/%s";

    public static final String REST_VNFDINFO_GET =
            "/openoapi/catalog/v1/csars/%s/files?relativePath=%s&relativePath=%s";

    public static final String REST_MSB_UNREGISTER = "/openoapi/microservices/v1/services/%s/version/%s/nodes/%s/%s";

    public static final String REST_JUJU_CLIENT_DEPLOY = "/openoapi/jujuvnfm/v1/vnfms/deploy";

    public static final String REST_JUJU_CLIENT_DESTORY = "/openoapi/jujuvnfm/v1/vnfms/destroy";

    public static final String REST_JUJU_CLIENT_GET = "/openoapi/jujuvnfm/v1/vnfms/status?modelName=%s";

    public static final String RES_VNF = "/openoapi/nslcm/v1/ns/grantvnf";
    public static final String REST_JUJU_VNFM_GRANT_URL = "/openoapi/jujuvnfm/v1/instances/%s/grant";

    public static final String REST_CSARINFO_GET = "/openoapi/catalog/v1/csars/%s";

    private UrlConstant() {
        // Constructor
    }
}
