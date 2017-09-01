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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju;

import org.apache.commons.lang3.StringUtils;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author		quanzhong@huawei.com
 * @version     NFVO 0.5  Nov 4, 2016
 */
public class JujuHelper {

    /**
     * 1\ toLowerCase
     * 2\remove the extension
     * 3\remove the underline
     * <br/>
     * 
     * @param appName
     * @return
     * @since  NFVO 0.5
     */
     public static String getModelName(String appName){
         if(StringUtils.isBlank(appName)){
             return appName;
         }
        char c = '.';
        String modelName = appName;
        modelName = modelName.toLowerCase();
        if(modelName.indexOf(c) > -1){
            modelName = modelName.substring(0,modelName.indexOf(c)); 
        }
        return modelName.replaceAll("_", "");
         
    }
}
