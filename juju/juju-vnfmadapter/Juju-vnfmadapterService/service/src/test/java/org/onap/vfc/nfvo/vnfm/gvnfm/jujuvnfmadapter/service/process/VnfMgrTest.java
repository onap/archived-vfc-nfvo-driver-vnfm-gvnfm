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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.VnfmUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.inf.IResourceManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfo;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process.VnfMgr;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class VnfMgrTest {

    VnfMgr mgr;
    @Before
    public void setUp() {
        mgr = new VnfMgr();
        mgr.getJujuVnfmInfoMapper();
        mgr.getResourceManager();

    }

    @Test
    public void addVnfTestNullJson() {
        JSONObject vnfObject = new JSONObject();
        String vnfmId = "1234";
        JSONObject resp = mgr.addVnf(vnfObject, vnfmId);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void addVnfTestOk() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject vnfmObject = new JSONObject();
                vnfmObject.put("url", "http://localhost:8080");
                return vnfmObject;
            }
        };
        new MockUp<VnfmRestfulUtil>() {

            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params,
                    String domainTokens) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                return resp;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        vnfObject.put("vnfPackageId", "123");
        String vnfmId = "1234";
        JSONObject resp = mgr.addVnf(vnfObject, vnfmId);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void addVnfTestNull() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject vnfmObject = new JSONObject();
                return vnfmObject;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        String vnfmId = "1234";
        JSONObject resp = mgr.addVnf(vnfObject, vnfmId);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void addVnfTestNullRes() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject vnfmObject = new JSONObject();
                vnfmObject.put("url", "http://localhost:8080");
                return vnfmObject;
            }
        };
        new MockUp<VnfmRestfulUtil>() {

            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params,
                    String domainTokens) {

                return null;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        String vnfmId = "1234";
        JSONObject resp = mgr.addVnf(vnfObject, vnfmId);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void deleteVnfTestNullJson() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                return null;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        String vnfmId = "1234";
        JSONObject resp = mgr.deleteVnf("vnfId", "vnfmId", vnfObject);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void deleteVnfTestValidJson() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject obj = new JSONObject();
                obj.put("url", "http://localhost:8080");
                return obj;
            }
        };
        new MockUp<VnfMgr>(){
            @Mock
            private JujuVnfmInfo findByVnfId(String vnfId){
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setVnfmId("1234");
                return info;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        String vnfmId = "1234";
        JSONObject resp = mgr.deleteVnf("vnfId", "vnfmId", vnfObject);
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void deleteVnf2TestNormal() {

        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject obj = new JSONObject();
                obj.put("url", "http://localhost:8080");
                return obj;
            }
        };
        new MockUp<VnfMgr>(){
            @Mock
            private JujuVnfmInfo findByVnfId(String vnfId){
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setVnfmId("1234");
                return info;
            }
            @Mock
            private void delJujuVnfmInfo(String vnfId){
                return;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(204);
                return resp;
            }
        };
        JSONObject vnfObject = new JSONObject();
        vnfObject.put("vnfInstanceName", "test123");
        String vnfmId = "1234";
        JSONObject resp = mgr.deleteVnf("vnfId", "vnfmId", vnfObject);
        assertEquals(resp.get("retCode"), 1);
    }

    @Test
    public void getVnfTestNullResp() {
        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                return null;
            }
        };
        JSONObject resp = mgr.getVnf("vnfId", "vnfmId");
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void getVnfTestValidJson() {
        new MockUp<VnfmUtil>() {

            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject obj = new JSONObject();
                obj.put("url", "http://localhost:8080");
                return obj;
            }
        };
        new MockUp<VnfMgr>(){
            @Mock
            private JujuVnfmInfo findByVnfId(String vnfId){
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setVnfmId("1234");
                return info;
            }
            @Mock
            private void delJujuVnfmInfo(String vnfId){
                return;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(201);
                JSONObject obj = new JSONObject();
                obj.put("data", new JSONObject());
                resp.setResponseJson(obj.toString());
                return resp;
            }
        };
        JSONObject resp = mgr.getVnf("vnfId", "vnfmId");
        assertEquals(resp.get("retCode"), -1);
    }

    @Test
    public void getJobTestNullResp(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                return null;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }

    @Test
    public void getJobTestNullHttpResp(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }
    @Test
    public void getJobTestSuccessWithNullData(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse res = new RestfulResponse();
                res.setStatus(201);

                return res;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }
    @Test
    public void getJobTestOkWithNullData(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse res = new RestfulResponse();
                res.setStatus(200);

                return res;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }
    @Test
    public void getJobTestInternalError(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse res = new RestfulResponse();
                res.setStatus(500);

                return res;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }
    @Test
    public void getJobTestNormal(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse res = new RestfulResponse();
                JSONObject jsonData = new JSONObject();
                jsonData.put("data", new JSONObject());
                res.setStatus(200);
                res.setResponseJson(jsonData.toString());
                return res;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"), null);
    }
    @Test
    public void getJobTestNullData(){
        new MockUp<VnfmUtil>() {
            @Mock
            public JSONObject getVnfmById(String vnfmId) {
                JSONObject ret = new JSONObject();
                ret.put("url", "http://localhost:8080");
                return ret;
            }
        };
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params, String domainTokens) {
                RestfulResponse res = new RestfulResponse();
                JSONObject jsonData = new JSONObject();
                jsonData.put("data", null);
                res.setStatus(200);
                res.setResponseJson(jsonData.toString());
                return res;
            }
        };
        JSONObject resp = mgr.getJob("jobId", "vnfmId");
        assertEquals(resp.get("retCode"),null);
    }

}
