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
package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfo;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample.Criteria;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample.Criterion;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.mapper.JujuVnfmInfoMapper;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.process.VnfResourceMgr;
import org.openo.baseservice.remoteservice.exception.ServiceException;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import net.sf.json.JSONObject;
import static mockit.Deencapsulation.*;


@RunWith(JMockit.class)

public class VnfResourceMgrTest {
    VnfResourceMgr vnfMgr;
    JujuVnfmInfoMapper jujuVnfmInfoMapper;
    JujuVnfmInfoExample jujuexample = new JujuVnfmInfoExample();
    Criteria criteria = jujuexample.createCriteria();

    @Before
    public void setUp() {
        vnfMgr = new VnfResourceMgr();
        vnfMgr.setJujuVnfmInfoMapper(jujuVnfmInfoMapper);
        vnfMgr.getJujuVnfmInfoMapper();
    }

    @Test
    public void grantVnfResourceTest() throws ServiceException {

        String vnfId = "1";
        new Expectations(vnfMgr) {
            {
                invoke(vnfMgr, "findByVnfId", "1");
                JujuVnfmInfo info = new JujuVnfmInfo();
                info.setId("1");
                info.setAppName("Test");
                info.setJobId("1");
                info.setVnfId(vnfId);
                returns(info);
            }
        };
        JSONObject compute = new JSONObject();
        JSONObject res = vnfMgr.grantVnfResource(compute, vnfId);
        assertEquals(res.get("retCode"), -1);
    }

}
