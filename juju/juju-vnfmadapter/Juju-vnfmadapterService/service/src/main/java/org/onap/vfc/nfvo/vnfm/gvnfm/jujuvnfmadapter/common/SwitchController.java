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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

/**
 * <br/>
 * <p>
 *   to assist debug
 * </p>
 * 
 * @author        
 * @version     NFVO 0.5  Sep 13, 2016
 */
public class SwitchController {
    
    /**
     * turn debug model
     */
    private static boolean debugModel = false;
    private static String vnfmServiceUrl = null;

    public SwitchController() { //NOSONAR
        //Constructor
    }

    /**
     * @return Returns the debugModel.
     */
    public static boolean isDebugModel() {
        return debugModel;
    }

    
    /**
     * @param debugModel The debugModel to set.
     */
    public static void setDebugModel(boolean debugModel) {
        SwitchController.debugModel = debugModel;
    }
    
    public static void setVnfmServiceUrl(String inVnfmServiceUrl) {
        SwitchController.vnfmServiceUrl = inVnfmServiceUrl;
    }
    public static String getVnfmServiceUrl() {
	return SwitchController.vnfmServiceUrl;
    }
}
