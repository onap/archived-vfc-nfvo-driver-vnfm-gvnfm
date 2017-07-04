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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.Restful;
import org.openo.baseservice.roa.util.restclient.RestfulFactory;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Juju VNFM restful utility class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public final class JujuVnfmRestfulUtil {

    public static final String GET_TYPE = "get";

    public static final String ADD_TYPE = "add";

    public static final String POST_TYPE = "post";

    public static final String PUT_TYPE = "put";

    public static final String DEL_TYPE = "delete";

    public static final String METHOD_TYPE = "methodType";

    public static final int ERROR_STATUS_CODE = -1;

    public static final String CONTENT_TYPE = "Content-type";

    public static final String APPLICATION = "application/json";

    public static final String HEADER_AUTH_TOKEN = "X-Auth-Token";

    private static final Logger LOG = LoggerFactory.getLogger(JujuVnfmRestfulUtil.class);

    private JujuVnfmRestfulUtil() {
        // constructor
    }

    /**
     * 
     * Get Vim response content.<br>
     * 
     * @param url
     * @param restParametes
     * @param type
     * @return
     * @since  NFVO 0.5
     */
    public static String getVimResponseContent(String url, RestfulParametes restParametes, String type) {
        Map<String, Object> resMap = getVimResponseContent(url, restParametes, null, type);

        return resMap.get("responseContent").toString();

    }

    /**
     * 
     * Get Vim response content.<br>
     * 
     * @param url
     * @param restParametes
     * @param opt
     * @param type
     * @return
     * @since  NFVO 0.5
     */
    public static Map<String, Object> getVimResponseContent(String url, RestfulParametes restParametes,
            RestfulOptions opt, String type) {
        Map<String, Object> resMap = new HashMap<>(2);

        try {
            Restful rest = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);
            RestfulResponse rsp = null;
            if (rest != null) {
                if (GET_TYPE.equals(type)) {
                    rsp = rest.get(url, restParametes, opt);
                } else if (ADD_TYPE.equals(type)) {
                    rsp = rest.post(url, restParametes, opt);
                } else if (PUT_TYPE.equals(type)) {
                    rsp = rest.put(url, restParametes, opt);
                } else if (DEL_TYPE.equals(type)) {
                    rsp = rest.delete(url, restParametes, opt);
                }
                if (null != rsp) {
                    resMap.put("responseContent", rsp.getResponseContent());
                    resMap.put("statusCode", rsp.getStatus());
                }
            }
            LOG.info("get response data success!");
        } catch (ServiceException e) {
            LOG.error("get response data catch exception {}.", e);
        }

        return resMap;
    }

    /**
     * 
     * Get Vim response result.<br>
     * 
     * @param url
     * @param type
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getVimResponseResult(String url, String type) {
        RestfulParametes restParametes = new RestfulParametes();
        return getVimResponseResult(url, restParametes, type);
    }

    /**
     * 
     * Get Vim response result.<br>
     * 
     * @param url
     * @param restParametes
     * @param type
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getVimResponseResult(String url, RestfulParametes restParametes, String type) {
        return vimRestfulResponse(url, restParametes, null, type);
    }

    /**
     * 
     * Get Vim response.<br>
     * 
     * @param url
     * @param restParametes
     * @param opt
     * @param type
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse vimRestfulResponse(String url, RestfulParametes restParametes, RestfulOptions opt,
            String type) {
        RestfulResponse rsp = null;
        try {
            Map<String, String> headerMap = new HashMap<>(2);
            headerMap.put(CONTENT_TYPE, APPLICATION);
            restParametes.setHeaderMap(headerMap);
            Restful rest = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);

            if (rest != null) {
                if (GET_TYPE.equals(type)) {
                    rsp = rest.get(url, restParametes, opt);
                } else if (ADD_TYPE.equals(type)) {
                    rsp = rest.post(url, restParametes, opt);
                } else if (PUT_TYPE.equals(type)) {
                    rsp = rest.put(url, restParametes, opt);
                } else if (DEL_TYPE.equals(type)) {
                    rsp = rest.delete(url, restParametes, opt);
                }
            }
            LOG.info("get response data success!");
        } catch (ServiceException e) {
            LOG.error("get response data catch ServiceException {}.", e);
        }
        return rsp;
    }

    /**
     * 
     * Get remote response.<br>
     * 
     * @param paramsMap
     * @param params
     * @param domainTokens
     * @param isHttps
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens,
            boolean isHttps) {
        String url = paramsMap.get("url");
        String methodType = paramsMap.get(METHOD_TYPE);
        String path = paramsMap.get("path");

        RestfulResponse rsp = null;
        Restful rest = null;
        String sslOptionFile = "";
        try {
            String restClientFile = "restclient.json";

            if (isHttps) {
                sslOptionFile = "ssl.nfvo.properties";

            }

            rest = HttpRestHelp.getRestInstance(sslOptionFile, restClientFile, isHttps);

            RestfulOptions opt = new RestfulOptions();
            String[] strs = path.split("(http(s)?://)|:");

            opt.setHost(strs[1]);
            opt.setPort(Integer.parseInt(strs[2]));

            RestfulParametes restfulParametes = new RestfulParametes();
            Map<String, String> headerMap = new HashMap<>(3);
            headerMap.put(CONTENT_TYPE, APPLICATION);
            headerMap.put(HEADER_AUTH_TOKEN, domainTokens);
            restfulParametes.setHeaderMap(headerMap);
            restfulParametes.setRawData(params);

            if (rest != null) {
                if (GET_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.get(url, restfulParametes, opt);
                } else if (POST_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.post(url, restfulParametes, opt);
                } else if (PUT_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.put(url, restfulParametes, opt);
                } else if (DEL_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.delete(url, restfulParametes, opt);
                }
            }
        } catch (ServiceException e) {
            LOG.error("function=restfulResponse, get restful response catch exception {}", e);
        }
        return rsp;
    }

    /**
     * 
     * Get remote response.<br>
     * 
     * @param paramsMap
     * @param params
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params) {
        if(null == paramsMap){
            return null;
        }
        String url = paramsMap.get("url");
        String methodType = paramsMap.get(METHOD_TYPE);

        RestfulResponse rsp = null;
        Restful rest = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);
        try {

            RestfulParametes restfulParametes = new RestfulParametes();
            Map<String, String> headerMap = new HashMap<>(3);
            headerMap.put(CONTENT_TYPE, APPLICATION);
            restfulParametes.setHeaderMap(headerMap);
            restfulParametes.setRawData(params);

            if (rest != null) {
                if (GET_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.get(url, restfulParametes);
                } else if (POST_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.post(url, restfulParametes);
                } else if (PUT_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.put(url, restfulParametes);
                } else if (DEL_TYPE.equalsIgnoreCase(methodType)) {
                    rsp = rest.delete(url, restfulParametes);
                }
            }
        } catch (ServiceException e) {
            LOG.error("function=getRemoteResponse, get restful response catch exception {}", e);
        }
        return rsp;
    }
    
    /**
     * 
     * Generate parameter map.<br>
     * 
     * @param url
     * @param methodType
     * @param path
     * @param authMode
     * @return
     * @since  NFVO 0.5
     */
    public static Map<String, String> generateParametesMap(String url, String methodType, String path,
            String authMode) {
        Map<String, String> paramsMap = new HashMap<>(6);
        paramsMap.put("url", url);
        paramsMap.put(METHOD_TYPE, methodType);
        paramsMap.put("path", path);
        paramsMap.put("authMode", authMode);
        return paramsMap;
    }
}
