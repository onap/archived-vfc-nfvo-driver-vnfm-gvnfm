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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest;

import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.StringUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfo;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process.VnfMgr;
import org.springframework.mock.web.MockHttpServletResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class VnfRoaTest {

    VnfRoa roa = new VnfRoa();

    @Before
    public void setUp() {
        roa.setVnfMgr(new VnfMgr());
    }

    @Test
    public void addVnfTestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.addVnf(context, resp, vnfmId);
        assertNotNull(res);
    }

    @Test
    public void addVnfTest() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                String vnfJsonStr = "{}";
                return (T)JSONObject.fromObject(vnfJsonStr);
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject addVnf(JSONObject vnfObject, String vnfmId) {
                JSONObject result = new JSONObject();
                result.put("retCode", -1);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.addVnf(context, resp, vnfmId);
        assertNotNull(res);
    }

    @Test
    public void addVnfTestSucce() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                String vnfJsonStr = "{}";
                return (T)JSONObject.fromObject(vnfJsonStr);
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject addVnf(JSONObject vnfObject, String vnfmId) {
                JSONObject result = new JSONObject();
                result.put("retCode", 1);
                JSONObject data = new JSONObject();
                data.put("result", "success");
                result.put("data", data);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.addVnf(context, resp, vnfmId);
        assertNotNull(res);
    }

    @Test
    public void delVnfTestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            private JujuVnfmInfo findByVnfId(String vnfId) {
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setVnfmId("1234");
                return info;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.delVnf("vnfmId", resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void delVnf2TestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.delVnf(null, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void delVnf3TestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.delVnf(vnfmId, resp, null, context);
        assertNotNull(res);
    }

    @Test
    public void delVnfSuccess() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject deleteVnf(String vnfId, String vnfmId, JSONObject vnfObject) {
                JSONObject result = new JSONObject();
                result.put("retCode", 1);
                JSONObject data = new JSONObject();
                data.put("result", "success");
                result.put("data", data);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.delVnf(vnfmId, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void delVnfFail() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject deleteVnf(String vnfId, String vnfmId, JSONObject vnfObject) {
                JSONObject result = new JSONObject();
                result.put("retCode", -1);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.delVnf(null, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void getVnf1TestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            private JujuVnfmInfo findByVnfId(String vnfId) {
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setVnfmId("1234");
                return info;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.getVnf(vnfmId, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void getVnfSuccess() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject getVnf(String vnfId, String vnfmId) {
                JSONObject result = new JSONObject();
                result.put("retCode", 1);
                JSONObject data = new JSONObject();
                data.put("result", "success");
                result.put("data", data);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.getVnf(vnfmId, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void getVnfFail() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject getVnf(String vnfId, String vnfmId) {
                JSONObject result = new JSONObject();
                result.put("retCode", -1);
                return result;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.getVnf(vnfmId, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void getVnf2TestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.getVnf(null, resp, "vnfInstanceId", context);
        assertNotNull(res);
    }

    @Test
    public void getVnf3TestNull() throws ServiceException {
        new MockUp<StringUtil>() {

            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfmId = "1234";
        String res = roa.getVnf(vnfmId, resp, null, context);
        assertNotNull(res);
    }

    @Test
    public void getJobTestNull() throws ServiceException {
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.getJob("jobId", "vnfmId", resp, "responseId");
        assertNotNull(res);
    }

    @Test
    public void getJobTest2Null() throws ServiceException {
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.getJob(null, "vnfmId", resp, "responseId");
        assertNotNull(res);
    }

    @Test
    public void getJobTest3Null() throws ServiceException {
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.getJob("jobId", null, resp, "responseId");
        assertNotNull(res);
    }

    @Test
    public void getJobTestNormal() throws ServiceException {
        new MockUp<VnfMgr>() {

            @Mock
            public JSONObject getJob(String jobId, String vnfmId) {
                JSONObject obj = new JSONObject();
                JSONObject dataObj = new JSONObject();
                dataObj.put("id", "1234");
                dataObj.put("status", "Success");
                obj.put("data", dataObj);
                obj.put("retCode", 1);
                return obj;
            }

        };
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.getJob("jobId", "vnfmId", resp, "responseId");
        assertNotNull(res);
    }

}
