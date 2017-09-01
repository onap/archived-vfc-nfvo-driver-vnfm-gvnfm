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
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VNFRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Aug 10, 2016
 */
public class VNFRestfulUtilTest {

    @Test
    public void testGetRestResByDefaultByNull() {
        RestfulResponse result = VNFRestfulUtil.getRestResByDefault("path", "methodNames", new JSONObject());
        assertNull(result);
    }

    @Test
    public void testGetRestResByDefaultByGet() {
        RestfulResponse result = VNFRestfulUtil.getRestResByDefault("path", "get", new JSONObject());
        assertNotNull(result);
    }

    @Test
    public void testGetRestResByDefaultByPut() {
        RestfulResponse result = VNFRestfulUtil.getRestResByDefault("path", "put", new JSONObject());
        assertNotNull(result);
    }

    /*@Test
    public void testSendReqToApp() {
        new MockUp<VNFRestfulUtil>() {

            @Mock
            public RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setStatus(Constant.HTTP_OK);
                String responseString = "{\"retCode\":1,\"data\":\"success\"}";
                restfulResponse.setResponseJson(responseString);
                return restfulResponse;
            }
        };
        JSONObject result = VNFRestfulUtil.sendReqToApp("path", "put", new JSONObject());
        assertEquals(Constant.REST_SUCCESS, result.get("retCode"));
    }*/

    /*@Test(expected = ExceptionInInitializerError.class)
    public void testSendReqToAppByErrorMsg() {
        new MockUp<VNFRestfulUtil>() {

            @Mock
            public RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setStatus(Constant.HTTP_OK);
                String responseString = "{\"retCode\":-1,\"data\":\"fail\",\"msg\":\"fail\"}";
                restfulResponse.setResponseJson(responseString);
                return restfulResponse;
            }
        };
        JSONObject result = VNFRestfulUtil.sendReqToApp("path", "put", new JSONObject());
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }*/

    /*@Test
    public void testSendReqToAppByError() {
        new MockUp<VNFRestfulUtil>() {

            @Mock
            public RestfulResponse getRestResByDefault(String path, String methodNames, JSONObject bodyParam) {
                RestfulResponse restfulResponse = new RestfulResponse();
                restfulResponse.setStatus(Constant.HTTP_OK);
                String responseString = "{\"retCode\":-1,\"data\":\"fail\"}";
                restfulResponse.setResponseJson(responseString);
                return restfulResponse;
            }
        };
        JSONObject result = VNFRestfulUtil.sendReqToApp("path", "put", new JSONObject());
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }*/

    @Test
    public void testSendReqToAppByFail() {
        JSONObject result = VNFRestfulUtil.sendReqToApp("path", "put", new JSONObject());
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }

    @Test
    public void testSendReqToAppByVnfmInfoPut() {
        JSONObject paraJson = new JSONObject();
        JSONObject vnfmObj = new JSONObject();
        vnfmObj.put("id", "id");
        paraJson.put("vnfmInfo", vnfmObj);
        JSONObject result = VNFRestfulUtil.sendReqToApp("path", "put", paraJson);
        assertEquals(Constant.REST_FAIL, result.get("retCode"));
    }

    @Test
    public void testGenerateParamsMap() {
        Map<String, String> result = VNFRestfulUtil.generateParamsMap("url", "methodType", "path");
        Map<String, String> paramsMap = new HashMap<String, String>(6);
        paramsMap.put("url", "url");
        paramsMap.put("methodType", "methodType");
        paramsMap.put("path", "path");
        paramsMap.put("authMode", "Certificate");
        assertEquals(paramsMap, result);
    }

    @Test
    public void testGenerateParamsMap2() {
        Map<String, String> result = VNFRestfulUtil.generateParamsMap("url", "methodType", "path", "authMode");
        Map<String, String> paramsMap = new HashMap<String, String>(6);
        paramsMap.put("url", "url");
        paramsMap.put("methodType", "methodType");
        paramsMap.put("path", "path");
        paramsMap.put("authMode", "authMode");
        assertEquals(paramsMap, result);
    }

    @Test
    public void testGetResultToVnfmByVnfmInfoNull() {
        JSONObject result = VNFRestfulUtil.getResultToVnfm(null, null);

        JSONObject retJson = new JSONObject();
        retJson.put("retCode", Constant.REST_FAIL);
        retJson.put("data", "get null result");
        assertEquals(retJson, result);
    }

    @Test
    public void testGetResultToVnfmByVnfmInfoErrorMsg() {
        JSONObject vnfmInfo = new JSONObject();
        vnfmInfo.put("retCode", Constant.REST_FAIL);
        vnfmInfo.put("msg", "ErrorMsg");
        JSONObject result = VNFRestfulUtil.getResultToVnfm(vnfmInfo, "vnfmId");

        JSONObject retJson = new JSONObject();
        retJson.put("retCode", Constant.REST_FAIL);
        retJson.put("data", "ErrorMsg");
        assertEquals(retJson, result);
    }

    @Test
    public void testGetResultToVnfmByVnfmInfoError() {
        JSONObject vnfmInfo = new JSONObject();
        vnfmInfo.put("retCode", Constant.REST_FAIL);
        JSONObject result = VNFRestfulUtil.getResultToVnfm(vnfmInfo, "vnfmId");

        JSONObject retJson = new JSONObject();
        retJson.put("retCode", Constant.REST_FAIL);
        assertEquals(retJson, result);
    }
    @Test
    public void getRemoteResponseTestInvalidGet(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("url", "/openoapi/test");
        paramsMap.put("methodType","get");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VNFRestfulUtil.getRemoteResponse(paramsMap, null, "test123", true);
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestInvalidPut(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("url", "/openoapi/test");
        paramsMap.put("methodType","put");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VNFRestfulUtil.getRemoteResponse(paramsMap, null, "test123", true);
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestInvalidPost(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("url", "/openoapi/test");
        paramsMap.put("methodType","post");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VNFRestfulUtil.getRemoteResponse(paramsMap, null, "test123", true);
        assertTrue(resp == null);
    }
    @Test
    public void getRemoteResponseTestInvalidDelete(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("url", "/openoapi/test");
        paramsMap.put("methodType","delete");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VNFRestfulUtil.getRemoteResponse(paramsMap, null, "test123", true);
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestFalseNfvoApp(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("url", "/openoapi/test");
        paramsMap.put("methodType","delete");
        paramsMap.put("authMode","test");
        RestfulResponse resp = VNFRestfulUtil.getRemoteResponse(paramsMap, null, "test123", false);
        assertTrue(resp == null);
    }

    @Test
    public void sentEvtByRestTest(){
        VNFRestfulUtil.sentEvtByRest("http://localhost:8080", "get", null);
        assertTrue(true);
    }
    @Test
    public void sentEvtByRestTest2(){
        JSONObject bodyParam = new JSONObject();
        VNFRestfulUtil.sentEvtByRest("http://localhost:8080", "get",bodyParam);
        assertTrue(true);
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = VNFRestfulUtil.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
