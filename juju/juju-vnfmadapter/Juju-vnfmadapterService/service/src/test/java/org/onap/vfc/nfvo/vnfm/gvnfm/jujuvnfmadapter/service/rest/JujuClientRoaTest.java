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
package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest;

import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.StringUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl.JujuClientManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process.VnfMgr;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest.JujuClientRoa;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.springframework.mock.web.MockHttpServletResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class JujuClientRoaTest {

    JujuClientRoa roa;


    @Before
    public void setUp(){
        roa = new JujuClientRoa();
        roa.setJujuClientManager(new JujuClientManager());
        roa.getJujuClientManager();

    }

    @Test
    public void setCharmUrlTest() throws ServiceException {
       new MockUp<StringUtil>(){
           @Mock
           public <T> T getJsonFromContexts(HttpServletRequest context) {
               String reqJsonObject = "{}";
               return (T)JSONObject.fromObject(reqJsonObject);
           }
       };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.setCharmUrl(context, resp);
        assertNotNull(res);
    }
    @Test
    public void getVnfStatusTest() throws ServiceException {
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.getVnfStatus("appName", context,resp);
        assertNotNull(res);
    }
    @Test
    public void deploySerivceTestFail() throws ServiceException {
        new MockUp<StringUtil>(){
            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                return null;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.deploySerivce(context,resp);
        assertNotNull(res);
    }
    @Test
    public void deploySerivceTest() throws ServiceException {
        new MockUp<StringUtil>(){
            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                JSONObject reqJsonObject = new JSONObject();
                reqJsonObject.put("charmPath", "/abc/xyz");
                reqJsonObject.put("mem", "100");
                reqJsonObject.put("appName", "test");
                return (T)reqJsonObject;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.deploySerivce(context,resp);
        assertNotNull(res);
    }

    @Test
    public void destroySerivceTestFail() throws ServiceException {
        new MockUp<StringUtil>(){
            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
                JSONObject reqJsonObject = new JSONObject();
                reqJsonObject.put("charmPath", "/abc/xyz");
                reqJsonObject.put("mem", "100");
                reqJsonObject.put("appName", "test");
                return (T)reqJsonObject;
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.destroySerivce(context,resp);
        assertNotNull(res);
    }
//    @Test
//    public void destroySerivce2TestFail() throws ServiceException {
//        new MockUp<StringUtil>(){
//            @Mock
//            public <T> T getJsonFromContexts(HttpServletRequest vnfReq) {
//                JSONObject reqJsonObject = new JSONObject();
//                reqJsonObject.put("appName", "test");
//                return (T)reqJsonObject;
//            }
//        };
//        HttpServletRequest context = null;
//        HttpServletResponse resp = new MockHttpServletResponse();
//        String res = roa.destroySerivce(context,resp);
//        assertNotNull(res);
//    }
}
