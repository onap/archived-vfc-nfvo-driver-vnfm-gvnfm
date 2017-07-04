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

package org.openo.nfvo.jujuvnfmadapter.common.servicetoken;

import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.roa.util.restclient.HttpRest;
import org.openo.baseservice.roa.util.restclient.Restful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Http rest help class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class HttpRestHelp {

    public static final String PROTO_HTTP = "http";

    private static final Logger LOG = LoggerFactory.getLogger(HttpRestHelp.class);

    private static final Map<String, Restful> INSTANCES = new HashMap<>(2);

    private HttpRestHelp() {
        // constructor
    }

    private static Restful createHttpsRest(String ssloptionfile, String restoptionfile, boolean isHttps) {

        HttpRest rest = new HttpRest();
        setHttpsRestOption(rest, restoptionfile);
        return rest;
    }

    /**
     * 
     * Get rest instance class.<br>
     * 
     * @param ssloptionfile
     * @param restoptionfile
     * @param isHttps
     * @return
     * @since  NFVO 0.5
     */
    public static synchronized Restful getRestInstance(String ssloptionfile, String restoptionfile, boolean isHttps) {
        Restful rest = INSTANCES.get(PROTO_HTTP);
        if(null != rest) {
            return rest;
        }
        rest = createHttpsRest(ssloptionfile, restoptionfile, isHttps);
        INSTANCES.put(PROTO_HTTP, rest);
        return rest;
    }

    private static void setHttpsRestOption(HttpRest httpRest, String restoptfile) {
        LOG.info("setHttpsRestOption");
    }

}
