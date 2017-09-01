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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.AsyncCallback;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.VnfmException;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.Restful;
import org.openo.baseservice.roa.util.restclient.RestfulAsyncCallback;
import org.openo.baseservice.roa.util.restclient.RestfulFactory;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * VNF Restful utility class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public final class VNFRestfulUtil {

    public static final String TYPE_GET = "get";

    public static final String TYPE_ADD = "add";

    public static final String TYPE_POST = "post";

    public static final String TYPE_PUT = "put";

    public static final String TYPE_DEL = "delete";

    public static final int ERROR_STATUS_CODE = -1;

    private static final Logger LOG = LoggerFactory.getLogger(VNFRestfulUtil.class);

    private VNFRestfulUtil() {

    }

    /**
     * 
     * within our module, we support a default method to invoke.<br>
     * 
     * @param path, rest service url
     * @param methodNames, [post, delete, put, get, asyncPost, asyncDelete, asyncPut,asyncGet]
     * @param bodyParam, rest body msg
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put(Constant.CONTENT_TYPE, Constant.APPLICATION);
        restParametes.setHeaderMap(headerMap);

        if(Constant.GET.equals(methodNames) || Constant.DELETE.equals(methodNames)) {
            if(null != bodyParam) {
                Map<String, String> vnfparamsMap = new HashMap<>(Constant.DEFAULT_COLLECTION_SIZE);
                if(path.contains("?")) {
                    String[] vnfUtlList = path.split("\\?");
                    String[] vnfParams = vnfUtlList[1].split("&");
                    int paramsSize = vnfParams.length;

                    for(int i = 0; i < paramsSize; i++) {
                        vnfparamsMap.put(vnfParams[i].split("=")[0], vnfParams[i].split("=")[1]);
                    }
                }

                String vnfParamKey;
                Iterator<String> nameItr = bodyParam.keys();
                while(nameItr.hasNext()) {
                    vnfParamKey = nameItr.next();
                    vnfparamsMap.put(vnfParamKey, bodyParam.get(vnfParamKey).toString());

                }
                LOG.warn("method is GET or DEL,and paramsMap = " + vnfparamsMap);
                restParametes.setParamMap(vnfparamsMap);
            }
        } else {
            restParametes.setRawData(bodyParam == null ? null : bodyParam.toString());
        }
        return getRestRes(methodNames, path, restParametes);
    }

    /**
     * send restful notification to other module
     * 
     * @param path
     *            service url
     * @param methodName
     *            [post, delete, put, get, asyncPost, asyncDelete, asyncPut,
     *            asyncGet]
     * @param bodyParam
     *            rest body msg
     */
    public static void sentEvtByRest(String path, String methodName, JSONObject bodyParam) {
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put(Constant.CONTENT_TYPE, Constant.APPLICATION);
        restParametes.setHeaderMap(headerMap);
        restParametes.setRawData(bodyParam == null ? null : bodyParam.toString());
        getRestRes(methodName, path, restParametes, new AsyncCallback());
    }

    /**
     * encapsulate the java reflect exception
     * 
     * @param methodName
     *            Restful's method
     * @param objects
     *            method param array
     * @return
     */
    private static boolean isAnyNull(Object... objects) {
        for(int i = 0; i < objects.length; i++) {
            if(objects[i] == null) {
                return true;
            }
        }
        return false;

    }

    private static Class<?>[] formArray(Object[] objects) {
        Class<?>[] vnfClasses = new Class[objects.length];
        for(int i = 0; i < objects.length; i++) {
            vnfClasses[i] = objects[i].getClass();
        }
        return vnfClasses;

    }

    /**
     * 
     * Get restful resource.<br>
     * 
     * @param methodName
     * @param objects
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getRestRes(String methodName, Object... objects) {
        Restful rest = RestfulFactory.getRestInstance(RestfulFactory.PROTO_HTTP);
        try {
            if(isAnyNull(objects, rest)) {
                return null;
            }

            Class<?>[] vnfClasses = formArray(objects);

            if(methodName.startsWith("async")) {
                vnfClasses[vnfClasses.length - 1] = RestfulAsyncCallback.class;
            }

            Class<?> rtType = methodName.startsWith("async") ? void.class : RestfulResponse.class;
            MethodType mt = MethodType.methodType(rtType, vnfClasses);
            Object reuslt = MethodHandles.lookup().findVirtual(rest.getClass(), methodName, mt).bindTo(rest)
                    .invokeWithArguments(objects);
            if(reuslt != null) {
                return (RestfulResponse)reuslt;
            }
            LOG.warn("function=getRestRes, msg: invoke Restful async {} method which return type is Void.", methodName);
            return null;
        } catch(ReflectiveOperationException e) {
            LOG.error("function=getRestRes, msg=error occurs, e={}.", e);
        } catch(ServiceException e) {

            LOG.error("function=getRestRes, msg=ServiceException occurs, status={}", e.getHttpCode());
            LOG.error("function=getRestRes, msg=ServiceException occurs, reason={}.", e.getCause().getMessage());
            LOG.error("function=getRestRes, msg=ServiceException occurs, e={}.", e);
            RestfulResponse response = new RestfulResponse();
            response.setStatus(e.getHttpCode());
            response.setResponseJson(e.getCause().getMessage());
            return response;

        } catch(Throwable e) {//NOSONAR
            try {
                throw (VnfmException)new VnfmException().initCause(e.getCause());
            } catch(VnfmException e1) {
                LOG.error("function=getRestRes, msg=VnfmException occurs, e={},e1={}.", e1, e);
            }

        }
        return null;
    }

    /**
     * 
     * Send request to application.<br>
     * 
     * @param path
     * @param methodName
     * @param paraJson
     * @return
     * @since  NFVO 0.5
     */
    public static JSONObject sendReqToApp(String path, String methodName, JSONObject paraJson) {
        JSONObject retJson = new JSONObject();
        retJson.put(Constant.RETURN_CODE, Constant.REST_FAIL);
        String abPath = null;
        String vnfmId = null;
        if(paraJson != null && paraJson.containsKey("vnfmInfo")) {
            JSONObject vnfmObj = paraJson.getJSONObject("vnfmInfo");
            vnfmId = vnfmObj.getString("id");
        } else {
            abPath = path;
        }
        LOG.warn("function=sendReqToApp, msg=url to send to app is: " + abPath);

        RestfulResponse restfulResponse = VNFRestfulUtil.getRestResByDefault(path, methodName, paraJson);
        if(restfulResponse == null || abPath == null) {
            LOG.error("function=sendReqToApp, msg=data from app is null");
            retJson.put("data", "get null result");
        } else if(restfulResponse.getStatus() == Constant.HTTP_OK) {
            JSONObject object = JSONObject.fromObject(restfulResponse.getResponseContent());
            if(!abPath.contains("vnfdmgr/v1")) {
                LOG.warn("function=sendReqToApp, msg=result from app is: " + object.toString());
            }
            if(object.getInt(Constant.RETURN_CODE) == Constant.REST_SUCCESS) {
                retJson.put(Constant.RETURN_CODE, Constant.REST_SUCCESS);
                retJson.put("data", withVnfmIdSuffix(vnfmId, object.get("data")));
                return retJson;
            } else {
                retJson.put(Constant.RETURN_CODE, Constant.REST_FAIL);
                if(object.containsKey("msg")) {
                    retJson.put("data", object.getString("msg"));
                    return retJson;
                } else {
                    return object;
                }
            }
        } else {
            LOG.error("function=sendReqToApp, msg=status from app is: " + restfulResponse.getStatus());
            LOG.error("function=sendReqToApp, msg=result from app is: " + restfulResponse.getResponseContent());
            retJson.put("data", "send to app get error status: " + restfulResponse.getStatus());
        }
        return retJson;
    }

    /**
     * append suffix to result with vnfmId
     * 
     * @param vnfmId
     * @param dataJson
     * @return
     */
    private static Object withVnfmIdSuffix(String vnfmId, Object dataJson) {
        Object result = new Object();
        if(vnfmId == null) {
            return dataJson;
        }

        if(dataJson instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)dataJson;
            jsonObject.put("vnfmId", vnfmId);
            result = jsonObject;
        } else if(dataJson instanceof JSONArray) {
            JSONArray dataArray = (JSONArray)dataJson;
            JSONArray resultArray = new JSONArray();

            for(Object obj : dataArray) {
                JSONObject jsonObject = JSONObject.fromObject(obj);
                jsonObject.put("vnfmId", vnfmId);
                resultArray.add(jsonObject);
            }
            result = resultArray;
        }
        return result;
    }

    /**
     * 
     * Get remote response.<br>
     * 
     * @param paramsMap
     * @param params
     * @param domainTokens
     * @param isNfvoApp
     * @return
     * @since  NFVO 0.5
     */
    public static RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens,
            boolean isNfvoApp) {
        String utilUrl = paramsMap.get("url");
        String utilMethodType = paramsMap.get(Constant.METHOD_TYPE);
        String utilPath = paramsMap.get("path");
        String authMode = paramsMap.get(Constant.AUTH_MODE);

        RestfulResponse rsp = null;
        Restful rest = null;
        String sslOptionFile = "";
        try {
            String restClientFile = "restclient.json";

            if(isNfvoApp) {
                sslOptionFile = "ssl.nfvo.properties";
            } else {
                sslOptionFile = "ssl.vcmm.properties";
            }

            LOG.warn("function=getRemoteResponse,AuthenticationMode=" + authMode);

            rest = HttpRestfulHelp.getRestInstance(sslOptionFile, restClientFile);

            RestfulOptions opt = new RestfulOptions();
            String[] strs = utilPath.split("(http(s)?://)|:");

            opt.setHost(strs[1]);
            opt.setPort(Integer.parseInt(strs[2]));

            RestfulParametes restfulParametes = new RestfulParametes();
            Map<String, String> headerMap = new HashMap<>(3);
            headerMap.put(Constant.CONTENT_TYPE, Constant.APPLICATION);
            headerMap.put(Constant.HEADER_AUTH_TOKEN, domainTokens);
            restfulParametes.setHeaderMap(headerMap);
            restfulParametes.setRawData(params);

            if(rest != null) {
                if(TYPE_GET.equalsIgnoreCase(utilMethodType)) {
                    rsp = rest.get(utilUrl, restfulParametes, opt);
                } else if(TYPE_POST.equalsIgnoreCase(utilMethodType)) {
                    rsp = rest.post(utilUrl, restfulParametes, opt);
                } else if(TYPE_PUT.equalsIgnoreCase(utilMethodType)) {
                    rsp = rest.put(utilUrl, restfulParametes, opt);
                } else if(TYPE_DEL.equalsIgnoreCase(utilMethodType)) {
                    rsp = rest.delete(utilUrl, restfulParametes, opt);
                }
            }
        } catch(ServiceException e) {
            LOG.error("function=restfulResponse, get restful response catch exception {}", e);
        }
        return rsp;
    }

    /**
     * 
     * Generate parameters map.<br>
     * 
     * @param url
     * @param methodType
     * @param path
     * @param authMode
     * @return
     * @since  NFVO 0.5
     */
    public static Map<String, String> generateParamsMap(String url, String methodType, String path, String authMode) {
        Map<String, String> utilParamsMap = new HashMap<>(6);
        utilParamsMap.put("url", url);
        utilParamsMap.put("methodType", methodType);
        utilParamsMap.put("path", path);
        utilParamsMap.put("authMode", authMode);
        return utilParamsMap;
    }

    /**
     * 
     * Generate parameters map.<br>
     * 
     * @param url
     * @param methodType
     * @param path
     * @return
     * @since  NFVO 0.5
     */
    public static Map<String, String> generateParamsMap(String url, String methodType, String path) {
        Map<String, String> paramsMap = new HashMap<>(6);
        paramsMap.put("url", url);
        paramsMap.put("methodType", methodType);
        paramsMap.put("path", path);
        paramsMap.put("authMode", "Certificate");
        return paramsMap;
    }

    /**
     * 
     * Get result to vnfm.<br>
     * 
     * @param vnfmInfo
     * @param vnfmId
     * @return
     * @since  NFVO 0.5
     */
    public static JSONObject getResultToVnfm(JSONObject vnfmInfo, String vnfmId) {
        JSONObject retJson = new JSONObject();
        retJson.put(Constant.RETURN_CODE, Constant.REST_FAIL);
        if(vnfmInfo == null) {
            LOG.error("function=getResultToVnfm, msg=data from vnfm is null");
            retJson.put("data", "get null result");
            return retJson;
        }

        if(vnfmInfo.getInt(Constant.RETURN_CODE) == Constant.REST_SUCCESS) {
            retJson.put(Constant.RETURN_CODE, Constant.REST_SUCCESS);
            retJson.put("data", withVnfmIdSuffix(vnfmId, vnfmInfo.get("data")));
            return retJson;
        } else {
            retJson.put(Constant.RETURN_CODE, Constant.REST_FAIL);
            if(vnfmInfo.containsKey("msg")) {
                retJson.put("data", vnfmInfo.getString("msg"));
                return retJson;
            } else {
                return vnfmInfo;
            }
        }
    }
}
