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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju.config;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju.config.Config;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju.config.ConfigItem;

public class ConfigTest {
    @Test
    public void testsetDefaults() {

        Map<String,ConfigItem> options=new HashMap<String,ConfigItem> ();
        ConfigItem configItem=new ConfigItem();
        configItem.setDescription("");
        options.put("", configItem);
        Config config=new Config(options);
        config.setOptions(options);
        Map map=config.getOptions();
         assertEquals(options, map);
    }

}
