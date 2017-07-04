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

package org.openo.nfvo.jujuvnfmadapter.service.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Constant class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class Constant {

    public static final String VNFM_APP = "VnfmDriver";

    public static final String VNFM_DB = "vnfmdb";

    public static final String VNFM_ID = "vnfmId";

    public static final String POST = "post";

    public static final String PUT = "put";

    public static final String DELETE = "delete";

    public static final String GET = "get";

    public static final String HEAD = "head";

    public static final String ASYNCPOST = "asyncPost";

    public static final String ASYNCGET = "asyncGet";

    public static final String ASYNCPUT = "asyncPut";

    public static final String ASYNCDELETE = "asyncDelete";

    public static final String RESPONSE_CONTENT = "responseContent";

    public static final String STATUS_CODE = "statusCode";

    public static final String RETURN_CODE = "retCode";

    public static final String REASON = "reason";

    public static final int ERROR_STATUS_CODE = -1;

    public static final String ENCODEING = "utf-8";

    public static final String COOKIE = "Cookie";

    public static final String ACCESSSESSION = "bspsession=";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String METHOD_TYPE = "methodType";

    public static final String AUTH_MODE = "authMode";

    public static final String APPLICATION = "application/json";

    public static final String APP_NAME = "appName";

    public static final String HEADER_SUBJECT_TOKEN = "X-Subject-Token";

    public static final String HEADER_AUTH_TOKEN = "X-Auth-Token";

    public static final String CSM = "csm";

    public static final int ERROR_CODE = -1;

    public static final int HTTP_OK = 200;

    public static final int HTTP_CREATED = 201;

    public static final int HTTP_ACCEPTED = 202;

    public static final int UNREG_SUCCESS = 204;

    public static final int HTTP_BAD_REQUEST = 400;

    public static final int HTTP_UNAUTHORIZED = 401;

    public static final int HTTP_NOTFOUND = 404;

    public static final int HTTP_CONFLICT = 409;
    
    public static final int INVALID_PARAMETERS = 415;

    public static final int HTTP_INNERERROR = 500;
    
    public static final int REPEAT_REG_TIME = 60 * 1000;
    
    public static final String JUJUADAPTERINFO = "jujuadapterinfo.json";

    public static final String FILE_SEPARATOR = "file.separator";

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final List<String> AUTHLIST =
            Collections.unmodifiableList(Arrays.asList(AuthenticationMode.ANONYMOUS, AuthenticationMode.CERTIFICATE));

    public static final int INTERNAL_EXCEPTION = 600;

    public static final int TOKEN_HEAD_NULL = 601;

    public static final int TOKEN_USER_NULL = 602;

    public static final int SERVICE_URL_ERROR = 603;

    public static final int ACCESS_OBJ_NULL = 604;

    public static final int CONNECT_NOT_FOUND = 605;

    public static final int VCENTER_PARA_ERROR = 606;

    public static final int TYPE_PARA_ERROR = 607;

    public static final int CONNECT_FAIL = 608;

    public static final int DIS_CONNECT_FAIL = 609;

    public static final int HANDSHAKE_FAIL = 610;

    public static final int MIN_PWD_LENGTH = 6;

    public static final int MAX_PWD_LENGTH = 160;

    public static final int MIN_URL_LENGTH = 7;

    public static final int MAX_VNFM_NAME_LENGTH = 64;

    public static final int MIN_VNFM_NAME_LENGTH = 1;

    public static final int MAX_URL_LENGTH = 256;

    public static final int MAX_SAMPLE_NUM = 1;

    public static final int MAX_VERSION_LENGTH = 160;

    public static final int MIN_VERSION_LENGTH = 1;

    public static final String HANDSHAKE = "handShake";

    public static final String INACTIVE = "inactive";

    public static final String ACTIVE = "active";

    public static final String RESOURCE_PATH = "";

    public static final int REST_SUCCESS = 1;

    public static final int REST_PART_SUCCESS = 0;

    public static final int DEFAULT_COLLECTION_SIZE = 10;

    public static final int REST_FAIL = -1;

    public static final String ROARAND = "?roarand=%s";
    
    public static final long PROCESS_WAIT_MILLIS = 30000;
    
    public static final String CSARINFO="csarinfo.json";
    
    public static final String DOWNLOADCSAR_SUCCESS = "Success";

    public static final String DOWNLOADCSAR_FAIL = "FAIL";

    public static final int UNZIP_SUCCESS = 0;

    public static final int UNZIP_FAIL = -1;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     */
    private Constant() {
        // Private Constructor
    }

    /**
     * Authentication mode.<br>
     * <p>
     * </p>
     * 
     * @author
     * @version     NFVO 0.5  Sep 12, 2016
     */
    public static class AuthenticationMode {

        public static final String ANONYMOUS = "Anonymous";

        public static final String CERTIFICATE = "Certificate";

        private AuthenticationMode() {
            
        }
    }
}
