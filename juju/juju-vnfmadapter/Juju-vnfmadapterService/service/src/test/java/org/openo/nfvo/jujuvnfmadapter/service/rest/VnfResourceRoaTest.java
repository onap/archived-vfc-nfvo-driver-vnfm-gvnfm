/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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
package org.openo.nfvo.jujuvnfmadapter.service.rest;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.nfvo.jujuvnfmadapter.common.StringUtil;
import org.openo.nfvo.jujuvnfmadapter.service.process.VnfResourceMgr;
import org.springframework.mock.web.MockHttpServletResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class VnfResourceRoaTest {

    VnfResourceRoa roa = new VnfResourceRoa();

    @Before
    public void setUp(){
        roa.setVnfResourceMgr(new VnfResourceMgr());
    }

    @Test
    public void grantVnfResTest() throws ServiceException{
        new MockUp<StringUtil>(){
            @Mock
            public <T> T getJsonFromContexts(HttpServletRequest context) {
                String reqJsonObject = "{}";
                return (T)JSONObject.fromObject(reqJsonObject);
            }
        };
        HttpServletRequest context = null;
        HttpServletResponse resp = new MockHttpServletResponse();
        String vnfId = "1234";
        String res = roa.grantVnfRes(context, vnfId);
        assertNotNull(res);
    }

}