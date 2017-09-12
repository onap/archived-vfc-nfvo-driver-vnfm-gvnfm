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

package org.openo.nfvo.jujuvnfmadapter.service.rest;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.testmanager.TestManager;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author		quanzhong@huawei.com
 * @version     NFVO 0.5  Sep 29, 2016
 */
public class ITConfigRoaTest extends TestManager {
    private static final String GET_SUCCESS_PATH = "testcase/configroa/setDebugModelTestSuccess.json";
    private static final String GET_FAIL_PATH = "testcase/configroa/setDebugModelTestFail.json";
   
    @Test
    public void setDebugModelTestSuccess() throws ServiceException{
        execTestCase(new File(getClassPath()+File.separator+GET_SUCCESS_PATH), new IChecker(){

            @Override
            public boolean check(HttpResponse paramHttpResponse) {
                int status = paramHttpResponse.getStatus();
                if(status == 200){
                    String data = paramHttpResponse.getData();
                    JSONObject dataObj = JSONObject.fromObject(data);
                    Assert.assertNotNull(dataObj);
                    return true;
                }
               
                return false;
            }
            
        });
    }
    @Test
    public void setDebugModelTestFail() throws ServiceException{
        execTestCase(new File(getClassPath()+File.separator+GET_FAIL_PATH),  new IChecker(){

            @Override
            public boolean check(HttpResponse paramHttpResponse) {
                int status = paramHttpResponse.getStatus();
                if(status != 200){
                    return true;
                }
                return false;
            }
            
        });
    }
    
    public static String getClassPath(){
        String path = ClassLoader.getSystemClassLoader().getResource("./").getPath();
        if(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }
        return path;
    }
}
