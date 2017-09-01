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

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.JujuVnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VNFRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Aug 10, 2016
 */
public class JujuVnfmRestfulUtilTest {


    @Test
    public void testGetRemoteResponseByVnfmInfoNull() {
        RestfulResponse result = JujuVnfmRestfulUtil.getRemoteResponse(new HashMap(), null);
        assertEquals(null, result);
    }

    @Test
    public void testGetRemoteResponseByPost() {
        Map testMap = new HashMap();
        testMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
        testMap.put("methodType","post");
        RestfulResponse result = JujuVnfmRestfulUtil.getRemoteResponse(testMap, "");

        assertEquals(null, result);
    }

    @Test
    public void testGetRemoteResponseByVnfmInfo() {
        Map testMap = new HashMap();
        testMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
        testMap.put("methodType","get");
        RestfulResponse result = JujuVnfmRestfulUtil.getRemoteResponse(testMap, "");

        assertEquals(null, result);
    }

    @Test
    public void testGetRemoteResponseByVnfmInfoPut() {
        Map testMap = new HashMap();
        testMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
        testMap.put("methodType","put");
        RestfulResponse result = JujuVnfmRestfulUtil.getRemoteResponse(testMap, "");

        assertEquals(null, result);
    }

    @Test
    public void testGetRemoteResponseByVnfmInfoDelete() {
        Map testMap = new HashMap();
        testMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
        testMap.put("methodType","delete");
        RestfulResponse result = JujuVnfmRestfulUtil.getRemoteResponse(testMap, "");

        assertEquals(null, result);
    }

    @Test
    public void getVimResponseContentGetInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, Object> result = JujuVnfmRestfulUtil.getVimResponseContent("http://127.0.0.1:8080", restParametes, null, "put");
        assertTrue(result.isEmpty());
    }
    @Test
    public void getVimResponseContentAddInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, Object> result = JujuVnfmRestfulUtil.getVimResponseContent("http://127.0.0.1:8080", restParametes, null, "add");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getVimResponseContentPutInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, Object> result = JujuVnfmRestfulUtil.getVimResponseContent("http://127.0.0.1:8080", restParametes, null, "put");
        assertTrue(result.isEmpty());
    }
    @Test
    public void getVimResponseContentDeleteInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, Object> result = JujuVnfmRestfulUtil.getVimResponseContent("http://127.0.0.1:8080", restParametes, null, "delete");
        assertTrue(result.isEmpty());
    }

    @Test
    public void getVimResponseContentPatchInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        Map<String, Object> result = JujuVnfmRestfulUtil.getVimResponseContent("http://127.0.0.1:8080", restParametes, null, "patch");
        assertTrue(result.isEmpty());
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
    public void vimRestfulResponseTestGetInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse resp = JujuVnfmRestfulUtil.vimRestfulResponse("http://127.0.0.1:8080", restParametes, null, "get");
        assertTrue(resp == null);
    }
    @Test
    public void vimRestfulResponseTestAddInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse resp = JujuVnfmRestfulUtil.vimRestfulResponse("http://127.0.0.1:8080", restParametes, null, "add");
        assertTrue(resp == null);
    }

    @Test
    public void vimRestfulResponseTestPutInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse resp = JujuVnfmRestfulUtil.vimRestfulResponse("http://127.0.0.1:8080", restParametes, null, "put");
        assertTrue(resp == null);
    }

    @Test
    public void vimRestfulResponseTestDeleteInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse resp = JujuVnfmRestfulUtil.vimRestfulResponse("http://127.0.0.1:8080", restParametes, null, "delete");
        assertTrue(resp == null);
    }

    @Test
    public void vimRestfulResponseTestPatchInvalid(){
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse resp = JujuVnfmRestfulUtil.vimRestfulResponse("http://127.0.0.1:8080", restParametes, null, "patch");
        assertTrue(resp == null);
    }

    @Test
    public void getRemoteResponseTestGetInvalid(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("methodType","get");
        RestfulResponse resp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test", false);
        assertNull(resp);

    }

    @Test
    public void getRemoteResponseTestGetHttpsInvalid(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("methodType","get");
        RestfulResponse resp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test", true);
        assertNull(resp);

    }

    @Test
    public void getRemoteResponseTestPostInvalid(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("methodType","post");
        RestfulResponse resp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test", false);
        assertNull(resp);

    }

    @Test
    public void getRemoteResponseTestPutInvalid(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("methodType","put");
        RestfulResponse resp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test", false);
        assertNull(resp);

    }

    @Test
    public void getRemoteResponseTestDeleteInvalid(){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("path", "http://localhost:8080");
        paramsMap.put("methodType","delete");
        RestfulResponse resp = JujuVnfmRestfulUtil.getRemoteResponse(paramsMap, null, "test", false);
        assertNull(resp);

    }
    @Test
    public void generateParametesMapTest(){
        Map<String, String> paramsMap = JujuVnfmRestfulUtil.generateParametesMap("http://localhost:8080", "get", "openoapi/test", "test");
        assertTrue("http://localhost:8080".equals(paramsMap.get("url")) && "get".equals(paramsMap.get("methodType"))
                && "openoapi/test".equals(paramsMap.get("path")) && "test".equals(paramsMap.get("authMode")));
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = JujuVnfmRestfulUtil.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
