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

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConfigRoaTest {

    ConfigRoa roa;

    @Before
    public void setUp() {
        roa = new ConfigRoa();
    }

    @Test
    public void initUITest() {
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String res = roa.initUI(context, resp);
        assertNotNull(res);
    }

    @Test
    public void setDebugModelTest() throws ServiceException {
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        boolean res = roa.setDebugModel(1, context, resp);
        assertTrue(res);
    }

    @Test
    public void setDebug2ModelTest() throws ServiceException {
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        boolean res = roa.setDebugModel(2, context, resp);
        assertFalse(res);
    }

    /*
     * @Test
     * public void testmock() throws ServiceException {
     * HttpServletRequest context = null;
     * HttpServletResponse resp = new MockHttpServletResponse();
     * String methodName = "getVnfmById";
     * JSONObject json = new JSONObject();
     * new MockUp<JujuConfigUtil>() {
     * @Mock
     * public String getValue(String key) {
     * return null;
     * }
     * };
     * roa.mock(methodName, context, resp);
     * roa.unmock(methodName, context, resp);
     * }
     */
}
