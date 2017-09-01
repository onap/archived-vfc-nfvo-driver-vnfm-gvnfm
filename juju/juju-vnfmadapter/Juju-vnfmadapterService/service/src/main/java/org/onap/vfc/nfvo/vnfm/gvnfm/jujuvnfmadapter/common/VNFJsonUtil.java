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

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.JSONUtils;

/**
 * 
 * Virtual Network Function Json Utility class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 10, 2016
 */
public final class VNFJsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(VNFJsonUtil.class);

    private static final ObjectMapper VNFMAPPER = new ObjectMapper();
    
    private static final String ERROR = "error";
    static {
        VNFMAPPER.setDeserializationConfig(VNFMAPPER.getDeserializationConfig()
                .without(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    private VNFJsonUtil() {
    }

    /**
     * 
     * UnMarshal method.<br>
     * 
     * @param vnfJsonstr
     * @param type
     * @return
     * @throws IOException
     * @since  NFVO 0.5
     */
    public static <T> T unMarshal(String vnfJsonstr, Class<T> type) throws IOException {
        return VNFMAPPER.readValue(vnfJsonstr, type);
    }

    /**
     * 
     * UnMarshal method.<br>
     * 
     * @param vnfJsonstr
     * @param type
     * @return
     * @throws IOException
     * @since  NFVO 0.5
     */
    public static <T> T unMarshal(String vnfJsonstr, TypeReference<T> type) throws IOException {
        return VNFMAPPER.readValue(vnfJsonstr, type);
    }

    /**
     * 
     * Marshal method.<br>
     * 
     * @param srcObj
     * @return
     * @throws IOException
     * @since  NFVO 0.5
     */
    public static String marshal(Object srcObj) throws IOException {
        if(srcObj instanceof JSON) {
            return srcObj.toString();
        }
        return VNFMAPPER.writeValueAsString(srcObj);
    }

    public static ObjectMapper getMapper() {
        return VNFMAPPER;
    }

    /**
     * 
     * Get Json field string.<br>
     * 
     * @param vnfJsonObj
     * @param fieldName
     * @return
     * @since  NFVO 0.5
     */
    public static String getJsonFieldStr(JSONObject vnfJsonObj, String fieldName) {
        if(null == vnfJsonObj || null == vnfJsonObj.get(fieldName) || "null".equals(vnfJsonObj.getString(fieldName))) {
            LOG.warn("getJsonFieldStr: VNFJson object field(" + fieldName + ") is null.");
            return "";
        }

        return vnfJsonObj.getString(fieldName);
    }

    /**
     * 
     * Get Json field integer.<br>
     * 
     * @param vnfJsonObj
     * @param fieldName
     * @return
     * @since  NFVO 0.5
     */
    public static Integer getJsonFieldInt(JSONObject vnfJsonObj, String fieldName) {
        if(null == vnfJsonObj || null == vnfJsonObj.get(fieldName)) {
            LOG.warn("getJsonFieldInt: VNFJson object field(" + fieldName + ") is Null");
            return 0;
        }
        return vnfJsonObj.getInt(fieldName);
    }

    /**
     * 
     * Get Json field long.<br>
     * 
     * @param vnfJsonObj
     * @param fieldName
     * @return
     * @since  NFVO 0.5
     */
    public static Long getJsonFieldLong(JSONObject vnfJsonObj, String fieldName) {
        if(null == vnfJsonObj || null == vnfJsonObj.get(fieldName)) {
            LOG.warn("getJsonFieldLong: VNFJson object field(" + fieldName + ") is null");
            return 0L;
        }
        return vnfJsonObj.getLong(fieldName);
    }

    /**
     * 
     * Parse error information.<br>
     * 
     * @param errorInfo
     * @return
     * @since  NFVO 0.5
     */
    public static String parseErrorInfo(String errorInfo) {
        if((errorInfo != null) && (!errorInfo.isEmpty())) {
            JSONObject errorInfoJst = JSONObject.fromObject(errorInfo);
            if(errorInfoJst.has(ERROR) && errorInfoJst.getJSONObject(ERROR).has("message")) {
                return errorInfoJst.getJSONObject(ERROR).getString("message");
            }
        }
        return "System Error!";
    }

    static {
        VNFMAPPER.setDeserializationConfig(VNFMAPPER.getDeserializationConfig()
                .without(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    /**
     * 
     * Convert VNF Json to objects.<br>
     * 
     * @param vnfJsonString
     * @param pojoCalss
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T vnfJsonToObjects(String vnfJsonString, Class<T> pojoCalss) {
        JSONObject vnfJsonObject = JSONObject.fromObject(vnfJsonString);
        return (T)JSONObject.toBean(vnfJsonObject, pojoCalss);
    }

    /**
     * 
     * Convert VNF Json to objects.<br>
     * 
     * @param vnfJsonString
     * @param vnfJsonConfig
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T vnfJsonToObjects(String vnfJsonString, JsonConfig vnfJsonConfig) {
        JSONObject vnfJsonObject = JSONObject.fromObject(vnfJsonString);
        return (T)JSONObject.toBean(vnfJsonObject, vnfJsonConfig);
    }

    /**
     * 
     * Convert VNF Json to lists.<br>
     * 
     * @param vnfJsonString
     * @param pojoClass
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> vnfJsonToLists(String vnfJsonString, Class<T> pojoClass) {
        JSONArray vnfJsonVNFArray = JSONArray.fromObject(vnfJsonString);
        JSONObject vnfJsonObject;
        List<T> list = new ArrayList<>(20);
        for(int i = 0; i < vnfJsonVNFArray.size(); i++) {
            vnfJsonObject = vnfJsonVNFArray.getJSONObject(i);
            list.add((T)JSONObject.toBean(vnfJsonObject, pojoClass));
        }
        return list;
    }

    /**
     * 
     * Convert VNF Json to list.<br>
     * 
     * @param vnfJsonString
     * @param pojoClass
     * @param dataFormat
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> vnfJsonToList(String vnfJsonString, Class<T> pojoClass, String dataFormat) {
        JsonConfig vnfJsonConfig = configJson(dataFormat);
        JSONArray vnfJsonVNFArray = JSONArray.fromObject(vnfJsonString, vnfJsonConfig);
        JSONObject vnfJsonObject;
        List<T> list = new ArrayList<>(20);
        for(int i = 0; i < vnfJsonVNFArray.size(); i++) {
            vnfJsonObject = vnfJsonVNFArray.getJSONObject(i);
            list.add((T)JSONObject.toBean(vnfJsonObject, pojoClass));
        }
        return list;
    }

    /**
     * 
     * Object to json string.<br>
     * 
     * @param javaObj
     * @return
     * @since  NFVO 0.5
     */
    public static String objectToJsonStr(Object javaObj) {
        JSONObject vnfJson = JSONObject.fromObject(javaObj);
        return vnfJson.toString();
    }

    /**
     * 
     * object to json.<br>
     * 
     * @param javaObj
     * @return
     * @since  NFVO 0.5
     */
    public static JSONObject objectToJson(Object javaObj) {
        return JSONObject.fromObject(javaObj);
    }

    /**
     * 
     * Object to json.<br>
     * 
     * @param javaObj
     * @param vnfJsonConfig
     * @return
     * @since  NFVO 0.5
     */
    public static String objectToJson(Object javaObj, JsonConfig vnfJsonConfig) {
        JSONObject vnfJson = JSONObject.fromObject(javaObj, vnfJsonConfig);
        return vnfJson.toString();
    }

    /**
     * 
     * Object to json.<br>
     * 
     * @param javaObj
     * @param dataFormat
     * @return
     * @since  NFVO 0.5
     */
    public static String objectToJson(Object javaObj, String dataFormat) {
        JsonConfig vnfJsonConfig = configJson(dataFormat);
        JSONObject vnfJson = JSONObject.fromObject(javaObj, vnfJsonConfig);
        return vnfJson.toString();

    }

    /**
     * 
     * List to json.<br>
     * 
     * @param list
     * @return
     * @since  NFVO 0.5
     */
    public static <T> String listToJson(List<T> list) {
        JSONArray vnfJson = JSONArray.fromObject(list);
        return vnfJson.toString();
    }

    /**
     * 
     * List to json.<br>
     * 
     * @param list
     * @param dataFormat
     * @return
     * @since  NFVO 0.5
     */
    public static <T> String listToJson(List<T> list, String dataFormat) {
        JsonConfig vnfJsonConfig = configJson(dataFormat);
        JSONArray vnfJson = JSONArray.fromObject(list, vnfJsonConfig);
        return vnfJson.toString();
    }

    /**
     * 
     * Config json.<br>
     * 
     * @param datePattern
     * @return
     * @since  NFVO 0.5
     */
    public static JsonConfig configJson(final String datePattern) {

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {datePattern}));

        JsonConfig vnfJsonConfig = new JsonConfig();
        vnfJsonConfig.setIgnoreDefaultExcludes(false);
        vnfJsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        vnfJsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            @Override
            public Object processObjectValue(String key, Object value, JsonConfig vnfJsonConfig) {
                if(value instanceof Date) {
                    return new SimpleDateFormat(datePattern).format((Date)value);
                }
                return value == null ? null : value.toString();
            }
            @Override
            public Object processArrayValue(Object value, JsonConfig vnfJsonConfig) {
                String[] vnfObj = {};
                SimpleDateFormat vnfSf = new SimpleDateFormat(datePattern);
                if(value instanceof Date[]) {
                    Date[] dates = (Date[])value;
                    vnfObj = new String[dates.length];
                    for(int i = 0; i < dates.length; i++) {
                        vnfObj[i] = vnfSf.format(dates[i]);
                    }
                }
                return vnfObj;
            }
        });
        return vnfJsonConfig;
    }

    /**
     * @param context the HttpContext
     * @param <T> JSONObject or JSONArray
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getJsonFromContext(HttpServletRequest context) {
        try {
            InputStream input = context.getInputStream();
            String vnfJsonStr = IOUtils.toString(input);
            JSONTokener vnfVnfJsonTokener = new JSONTokener(vnfJsonStr);

            // "{"
            if(vnfVnfJsonTokener.nextClean() == Character.codePointAt("{", 0)) {
                return (T)JSONObject.fromObject(vnfJsonStr);
            }

            vnfVnfJsonTokener.back();

            // "["
            if(vnfVnfJsonTokener.nextClean() == Character.codePointAt("[", 0)) {
                return (T)JSONArray.fromObject(vnfJsonStr);
            }
        } catch(IOException e) {
            LOG.error("function=getJsonFromContext,msg= IOException occurs. exception=" + e);
        } catch(JSONException e) {
            LOG.error("function=getJsonFromContext,msg= JSONException occurs, exception=" + e);
        }
        return null;
    }

}
