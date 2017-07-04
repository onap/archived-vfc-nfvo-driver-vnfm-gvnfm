/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.nfvo.jujuvnfmadapter.service.rest.fullstack;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.nfvo.jujuvnfmadapter.common.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.nfvo.jujuvnfmadapter.common.EntityUtils.ExeRes;
import org.openo.nfvo.jujuvnfmadapter.service.adapter.inf.IResourceManager;
import org.openo.nfvo.jujuvnfmadapter.service.rest.JujuClientRoa;
import org.python.jline.internal.Log;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 *
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Nov 3, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test.xml"})
public class JujuClientRoaTest {

    Logger logger = Logger.getLogger(JujuClientRoaTest.class);

    @Resource
    private JujuClientRoa jujuClientRoa;
    @Resource
    private IResourceManager resourceManager;


    /**
     * <br/>
     *
     * @throws java.lang.Exception
     * @since NFVO 0.5
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Assert.assertNotNull(jujuClientRoa);
    }

    @Test
    public void testSetCharmUrl() {
        HttpServletResponse resp = new MockHttpServletResponse();
        HttpServletRequest request;
        String result = null;
        try {
            request = TestHelper.buildDefaultRequest("setCharmUrl.json");
            result = jujuClientRoa.setCharmUrl(request, resp);

        } catch(Exception e) {
            logger.error("error:",e);

        }
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetVnfStatus() {

        //mock method  EntityUtils#execute
        new MockUp<EntityUtils>(){
            @Mock
            public  ExeRes execute(String dir, List<String> command) {
                ExeRes res = new ExeRes();
                res.setCode(ExeRes.SUCCESS);
                res.setBody(null);
                return res;
            }
        };

        HttpServletResponse resp = new MockHttpServletResponse();
        MockHttpServletRequest request;
        String result = null;
        try {
            request = TestHelper.buildDefaultRequest(null);
            String modelName = "mediawiki.yaml";
            request.setParameter("modelName", modelName);
            result = jujuClientRoa.getVnfStatus(modelName, request, resp);
            Log.info(result);
        } catch(Exception e) {
            logger.error("error:",e);

        }
        JSONObject jr = JSONObject.fromObject(result);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, jr.getInt("retCode"));
    }

  //  @Test
    public void testDeploySerivce() {
        HttpServletResponse resp = new MockHttpServletResponse();
        HttpServletRequest request;
        String result = null;
        try {
            request = TestHelper.buildDefaultRequest("deployService.json");
            result = jujuClientRoa.deploySerivce(request, resp);
            Assert.assertNotNull(result);
            JSONObject json = JSONObject.fromObject(result);
            logger.info(json);
            Assert.assertEquals(json.getInt(EntityUtils.RESULT_CODE_KEY),EntityUtils.ExeRes.SUCCESS);

        } catch(Exception e) {
            logger.error("error:",e);

        }

    }

//    @Test
    public void testDestroySerivce() {
        HttpServletResponse resp = new MockHttpServletResponse();
        HttpServletRequest request;
        String result = null;
        try {
            request = TestHelper.buildDefaultRequest("destroySerivce.json");
            result = jujuClientRoa.setCharmUrl(request, resp);

        } catch(Exception e) {
            logger.error("error:",e);

        }
        Assert.assertNotNull(result);
    }

}
