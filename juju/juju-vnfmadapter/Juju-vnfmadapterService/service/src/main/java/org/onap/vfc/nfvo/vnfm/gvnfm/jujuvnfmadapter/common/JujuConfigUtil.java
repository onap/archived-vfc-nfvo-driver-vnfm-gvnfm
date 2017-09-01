/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import java.util.ResourceBundle;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author        
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class JujuConfigUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("juju-config");
    
    private JujuConfigUtil() {
        // private constructor
    }
    
    /**
     * read the juju-config.properties file
     * <br/>
     * 
     * @param key
     * @return
     * @since  NFVO 0.5
     */
    public static String getValue(String key){
       return bundle.getString(key);
    }

}
