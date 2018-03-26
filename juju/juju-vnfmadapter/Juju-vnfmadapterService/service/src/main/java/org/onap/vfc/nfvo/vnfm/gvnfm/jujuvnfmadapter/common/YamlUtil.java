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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.parser.ParserException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Oct 25, 2016
 */
public class YamlUtil {

    private static Logger log = LoggerFactory.getLogger(YamlUtil.class);

    /**
     * <br/>
     * 
     * @param yamlName
     * @return
     * @since NFVO 0.5
     */
    public static JSON yamlToJson(String yamlName) {
        Object res = parseYaml(yamlName);
        if(res instanceof ArrayList) {
            return JSONArray.fromObject(res);
        }
        return JSONObject.fromObject(res);
    }

    /**
     * <br/>
     * 
     * @param yamlName
     * @return
     * @since NFVO 0.5
     */
    public static String loadYaml(String yamlName) {
        String res = null;
        try {
            Yaml yaml = new Yaml();
            File file = new File(yamlName);

            Object obj = yaml.load(new FileInputStream(file));
            if(obj != null) {
                res = obj.toString();
            }
            log.debug("yaml-> " + res);
        } catch(ParserException e) {
            log.error("error format:", e);
        } catch(FileNotFoundException e) {
            log.error("the yaml file not exist {}", yamlName, e);
        }
        return res;
    }

    /**
     * <br/>
     * 
     * @param yamlName
     * @return
     * @since NFVO 0.5
     */
    public static Object parseYaml(String yamlName) {
        Object obj = null;
        try {
            File file = new File(yamlName);
            obj = new Yaml().load(new FileInputStream(file));
        } catch(ParserException e) {
            log.error("error format:", e);

        } catch(FileNotFoundException e) {
            log.error("the yaml file not exist {}", yamlName, e);
        }
        return obj;
    }

}
