/*
 * Copyright (c) 2017, Huawei Technologies Co., Ltd.
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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.yaml.snakeyaml.Yaml;

import net.sf.json.JSON;

public class YamlUtilTest {

    YamlUtil yaml;

    @Before
    public void setUp() {
        yaml = new YamlUtil();

    }

    @Test
    public void test() throws ServiceException {
        String yamlName = "src/test/resources/test.yaml";
        String arrayName = "src/test/resources/testArray.yaml";
        JSON json = yaml.yamlToJson(yamlName);
        yaml.yamlToJson(arrayName);
        String S = yaml.loadYaml(yamlName);
        Yaml yaml = new Yaml();
        File file = new File(yamlName);
    }

    @Test
    public void testFileNotFoundException() throws ServiceException {
        yaml.yamlToJson("src/test/resources/abc.yaml");
        yaml.loadYaml("src/test/resources/abc.yaml");
    }

}
