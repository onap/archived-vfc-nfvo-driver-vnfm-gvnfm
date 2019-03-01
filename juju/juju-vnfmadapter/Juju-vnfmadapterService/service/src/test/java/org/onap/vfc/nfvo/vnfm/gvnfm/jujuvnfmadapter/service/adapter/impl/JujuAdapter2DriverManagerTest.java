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


package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl;

import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JujuAdapter2DriverManagerTest {



    @Test
    public void testRegisterDriver()
    {
        JujuAdapter2DriverManager jujuAdapter2DriverManager =new JujuAdapter2DriverManager();
        Map<String, String> map = new HashMap<String, String>();
        map.put("a",  "1");
        JSONObject json = new JSONObject();
        json.put("sun","yellow");
        jujuAdapter2DriverManager.registerDriver(map,json);

        jujuAdapter2DriverManager.unregisterDriver(map);

    }
}