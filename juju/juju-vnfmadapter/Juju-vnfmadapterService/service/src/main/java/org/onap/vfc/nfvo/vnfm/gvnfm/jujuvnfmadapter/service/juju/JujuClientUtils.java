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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author        
 * @version     NFVO 0.5  Aug 22, 2016
 */
public class JujuClientUtils {

    private static  PyObjectFactory factory ;
    
    
    private JujuClientUtils() {
        // private constructor
    }
    
    /**
     * call this to connect JUJU VNFM
     * <br/>
     * 
     * @param envName
     * @since  NFVO 0.5
     */
    public static void init(String envName){
            factory = PyObjectFactory.build(envName);
    }
    
    /**
     * <br>
     * 
     * @param serviceName
     * @param charmUrl
     * @return pyObject
     */
    public static PyObject setCharm(String serviceName,String charmUrl){
       return factory.execute("set_charm", new PyString(serviceName), new PyString(charmUrl));
    }
    /**
     * <br>
     * 
     * @param vnfInstanceId
     * @param vnfmId
     * @return pyObject
     */
    public static PyObject getVnfStatus(String vnfInstanceId,String vnfmId){
        return null;
     }
    
    /**
     * <br>
     * 
     * @param serviceName
     * @param charmUrl
     * @param numUnits
     * @return pyObject
     */
    public static PyObject deployService(String serviceName,String charmUrl, int numUnits){
        PyObject[] arrys = new PyObject[]{new PyString(serviceName), new PyString(charmUrl), new PyInteger(numUnits)};
        return factory.execute("deploy", arrys);
     }
    /**
     * Destory a service and all of it's units.
     * <br/>
     * 
     * @param serviceName
     * @param charmUrl
     * @param numUnits
     * @return
     * @since  NFVO 0.5
     */
    public static PyObject destoryService(String serviceName){
        return factory.execute("deploy",new PyString(serviceName));
     }
}
