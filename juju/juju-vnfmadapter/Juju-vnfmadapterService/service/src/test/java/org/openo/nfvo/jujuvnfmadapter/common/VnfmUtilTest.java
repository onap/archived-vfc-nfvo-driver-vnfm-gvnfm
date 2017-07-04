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
package org.openo.nfvo.jujuvnfmadapter.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.nfvo.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class VnfmUtilTest {
    
    @Test
    public void getVnfmByIdTestNull(){ 
        JSONObject resp = VnfmUtil.getVnfmById("vnfmId");
        assertNull(resp);
    }
    @Test
    public void getVnfmByIdTest(){
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(String url, String methodType, String params) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                JSONObject json = new JSONObject();
                json.put("id", "1234");
                resp.setResponseJson(json.toString());
                return resp;
            }
        };
        JSONObject resp = VnfmUtil.getVnfmById("vnfmId");
        assertNotNull(resp);
    }
    @Test
    public void getVnfmByIdTest2(){
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(String url, String methodType, String params) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(500);
                return resp;
            }
        };
        JSONObject resp = VnfmUtil.getVnfmById("vnfmId");
        assertNull(resp);
    }
    @Test
    public void getVnfmIdByIpNullResp(){
        String resp = VnfmUtil.getVnfmIdByIp("1.1.1.1");
        assertEquals(resp,"");
    }
    @Test
    public void getVnfmIdByIpInternalError(){
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(String url, String methodType, String params) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(500);
                return resp;
            }
        };
        String resp = VnfmUtil.getVnfmIdByIp("1.1.1.1");
        assertEquals(resp,"");
    }
    @Test
    public void getVnfmIdByIp(){
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(String url, String methodType, String params) {
                RestfulResponse resp = new RestfulResponse();
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("url", "1.1.1.1");
                jsonobj.put("vnfmId", "1111");
                jsonArray.add(jsonobj);
                resp.setResponseJson(jsonArray.toString());
                resp.setStatus(200);
                return resp;
            }
        };
        String resp = VnfmUtil.getVnfmIdByIp("1.1.1.1");
        assertEquals(resp,"1111");
    }
    
    @Test
    public void getVnfmIdByIpInvalidIP(){
        new MockUp<VnfmRestfulUtil>(){
            @Mock
            public RestfulResponse getRemoteResponse(String url, String methodType, String params) {
                RestfulResponse resp = new RestfulResponse();
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("url", "1.1.1.1");
                jsonobj.put("vnfmId", "1111");
                jsonArray.add(jsonobj);
                resp.setResponseJson(jsonArray.toString());
                resp.setStatus(200);
                return resp;
            }
        };
        String resp = VnfmUtil.getVnfmIdByIp("1.1.1.2");
        assertEquals(resp,"");
    }

}
