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

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl.JujuClientManager;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class JujuClientManagerTest {

    @Test
    public void testdeploy() {
        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.deploy("charmPath", "appName");
        assertTrue(json != null);
    }

    @Test
    public void testdeploy3() {
        new MockUp<ProcessBuilder>() {
            @Mock
            public Process start() throws IOException {
                Process process = new ProcessMockImpl();
                return process;
            }
        };
        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.deploy("charmPath","appName");
        assertTrue(json != null);
    }

    @Test
    public void testdeploy1() {

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.deploy("charmPath", null);
        assertTrue(json != null);
    }

    @Test
    public void testdeploy2() {

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.deploy(null, null);
        assertTrue(json != null);
    }

    @Test
@Ignore
    public void testdestroy() {

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.destroy("appName");
        assertTrue(json != null);
    }

    @Test
    public void testdestroy1() {
        new MockUp<ProcessBuilder>() {
            @Mock
            public Process start() throws IOException {
                Process process = new ProcessMockImpl();
                return process;
            }
        };
        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.destroy("appName");
        assertTrue(json != null);
    }

    @Test
    public void testgetStatus() {

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.getStatus("appName");
        assertTrue(json != null);
    }

    @Test
    public void testgetStatus1() {

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.getStatus("");
        assertTrue(json != null);
    }

    @Test
    public void testgetStatus2() {
        new MockUp<ProcessBuilder>() {
            @Mock
            public Process start() throws IOException {
                Process process = new ProcessMockImpl();
                return process;
            }
        };

        JujuClientManager jujuClientManager = new JujuClientManager();
        JSONObject json = jujuClientManager.getStatus("");
        assertTrue(json != null);
    }
    @Test
    public void testParseYaml(){
        JujuClientManager jujuClientManager = new JujuClientManager();
        jujuClientManager.parseYaml("abc/efg", "mediawiki.yaml", "addResource");
    }
}
