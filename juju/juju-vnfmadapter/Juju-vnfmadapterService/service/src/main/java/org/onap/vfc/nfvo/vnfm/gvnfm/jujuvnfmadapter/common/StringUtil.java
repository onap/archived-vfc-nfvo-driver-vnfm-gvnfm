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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

/**
 * 
 * String utility class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public final class StringUtil {

    private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);

    private StringUtil() {

    }

    /**
     * 
     * Check whther the string is valid or not.<br>
     * 
     * @param str
     * @return
     * @since  NFVO 0.5
     */
    public static boolean isValidString(String str) {
        return str != null && !"".equals(str.trim());
    }

    /**
     * 
     * Check whether the Url is valid or not.<br>
     * 
     * @param url
     * @return
     * @since  NFVO 0.5
     */
    public static boolean isValidUrl(String url) {
        String reg =
                "http[s]?://(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}:[0-9]{2,5}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    /**
     * 
     * Get the JSON string from input http context.<br>
     * 
     * @param vnfReq
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
        try {
            InputStream vnfInput = vnfReq.getInputStream();
            String vnfJsonStr = IOUtils.toString(vnfInput);
            JSONTokener vnfJsonTokener = new JSONTokener(vnfJsonStr);

            if(vnfJsonTokener.nextClean() == Character.codePointAt("{", 0)) {
                return (T)JSONObject.fromObject(vnfJsonStr);
            }

            vnfJsonTokener.back();

            if(vnfJsonTokener.nextClean() == Character.codePointAt("[", 0)) {
                return (T)JSONArray.fromObject(vnfJsonStr);
            }
        } catch(IOException e) {
            LOG.error("function=getJsonFromContext, msg=IOException occurs, e={}.", e);
        } catch(JSONException e) {
            LOG.error("function=getJsonFromContext, msg=JSONException occurs, e={}.", e);
        }

        return null;
    }

    /**
     * 
     * Translate sites to site array.<br>
     * 
     * @param sites
     * @return
     * @since  NFVO 0.5
     */
    public static JSONArray transSitesToArray(String sites) {
        String[] siteList = sites.split("&");
        int siteSize = siteList.length;
        JSONArray siteArray = new JSONArray();
        for(int i = 0; i < siteSize; i++) {
            siteArray.add(siteList[i]);
        }

        return siteArray;
    }

    /**
     * 
     * Check whether the string is valid or not.<br>
     * 
     * @param fields
     * @return
     * @since  NFVO 0.5
     */
    public static boolean isValidAnyString(String... fields) {
        for(String str : fields) {
            if(!isValidString(str)) {
                return false;
            }
        }
        return true;
    }
}
