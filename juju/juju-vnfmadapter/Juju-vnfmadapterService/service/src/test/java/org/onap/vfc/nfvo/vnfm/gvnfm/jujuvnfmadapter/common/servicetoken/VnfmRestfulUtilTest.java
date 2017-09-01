/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class VnfmRestfulUtilTest {

    @Test
    public void testGetRestResByDefaultByNull() {
        RestfulResponse result = VnfmRestfulUtil.getRestResByDefault("path", "methodNames", new JSONObject());
        assertNull(result);
    }
    @Test
    public void testGetRestResByDefaultByGet() {
        RestfulResponse result = VnfmRestfulUtil.getRestResByDefault("path", "get", new JSONObject());
        assertNotNull(result);
    }

    @Test
    public void testGetRestResByDefaultByPut() {
        RestfulResponse result = VnfmRestfulUtil.getRestResByDefault("path", "put", new JSONObject());
        assertNotNull(result);
    }

    ////
    @Test
    public void testSendReqToAppByFail() {
        JSONObject result = VnfmRestfulUtil.sendReqToApp("path", "put", new JSONObject());
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }

    @Test
    public void testSendReqToAppByVnfmInfoPut() {
        JSONObject paraJson = new JSONObject();
        JSONObject vnfmObj = new JSONObject();
        vnfmObj.put("id", "id");
        paraJson.put("vnfmInfo", vnfmObj);
        JSONObject result = VnfmRestfulUtil.sendReqToApp("path", "put", paraJson);
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }

    @Test
    public void testSendReqToAppByVnfmInfoPutNormal() {
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                JSONObject obj = new JSONObject();
                obj.put("retCode", 1);
                obj.put("data", new JSONObject());
                resp.setResponseJson(obj.toString());
                return resp;
            }
        };
        JSONObject paraJson = new JSONObject();
        JSONObject vnfmObj = new JSONObject();
        vnfmObj.put("id", "id");
        JSONObject result = VnfmRestfulUtil.sendReqToApp("path", "put", paraJson);
        assertEquals(Constant.REST_SUCCESS, result.get("retCode"));
    }
    @Test
    public void testSendReqToAppByVnfmInfoPutNormal2() {
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                JSONObject obj = new JSONObject();
                obj.put("retCode", -1);
                obj.put("data", new JSONObject());
                resp.setResponseJson(obj.toString());
                return resp;
            }
        };
        JSONObject paraJson = new JSONObject();
        JSONObject vnfmObj = new JSONObject();
        vnfmObj.put("id", "id");
        JSONObject result = VnfmRestfulUtil.sendReqToApp("path", "put", paraJson);
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }

    @Test
    public void testGenerateParamsMap() {
        Map<String, String> result = VnfmRestfulUtil.generateParamsMap("url", "methodType", "path","auth");
        Map<String, String> paramsMap = new HashMap<String, String>(6);
        paramsMap.put("url", "url");
        paramsMap.put("methodType", "methodType");
        paramsMap.put("path", "path");
        paramsMap.put("authMode", "auth");
        assertEquals(paramsMap, result);
    }
    @Test
    public void getRemoteResponseTestInvalidGet(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "/test/abc");
        paramsMap.put("url", "http://localhost:8080");
        paramsMap.put("methodType","get");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestInvalidPut(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "/test/abc");
        paramsMap.put("url", "http://localhost:8080");
        paramsMap.put("methodType","put");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestInvalidPost(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "/openoapi/test");
        paramsMap.put("url", "http://localhost:8080");
        paramsMap.put("methodType","post");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test123");
        assertTrue(resp == null);
    }
    @Test
    public void getRemoteResponseTestInvalidDelete(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "/openoapi/test");
        paramsMap.put("url", "http://localhost:8080");
        paramsMap.put("methodType","delete");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponse2TestInvalidGet(){
        String url = "http://localhost:8080";
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(url, "get", "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponse2TestInvalidPut(){
        String url = "http://localhost:8080";
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(url, "put", "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponse2TestInvalidPost(){
        String url = "http://localhost:8080";
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(url, "post", "test123");
        assertTrue(resp == null);
    }
    @Test
    public void getRemoteResponse2TestInvalidDelete(){

        String url = "http://localhost:8080";

        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(url, "delete", "test123");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestFalseNfvoApp(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "/openoapi/test");
        paramsMap.put("url", "http://localhost:8080");
        paramsMap.put("methodType","delete");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test123");
        assertTrue(resp == null);
    }

   /* @Test
    public void sentEvtByRestTest(){
        VnfmRestfulUtil.sentEvtByRest("http://localhost:8080", "get", null);
        assertTrue(true);
    }
    @Test
    public void sentEvtByRestTest2(){
        JSONObject bodyParam = new JSONObject();
        VnfmRestfulUtil.sentEvtByRest("http://localhost:8080", "get",bodyParam);
        assertTrue(true);
    }*/
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = VnfmRestfulUtil.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
