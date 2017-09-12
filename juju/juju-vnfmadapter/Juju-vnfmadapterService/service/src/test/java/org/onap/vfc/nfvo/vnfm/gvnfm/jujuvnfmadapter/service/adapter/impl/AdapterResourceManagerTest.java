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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl.AdapterResourceManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.HttpRest;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulParametes;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class AdapterResourceManagerTest {

	private AdapterResourceManager adapterResManager;

	private JSONObject jsonObj;

	/*
	 * @Test public void testGetJujuVnfmInfo() throws Exception { Map paramsMap
	 * = new HashMap(); JSONObject resultObj = new JSONObject();
	 * resultObj.put("reason", "RestfulResponse is null.");
	 * resultObj.put("retCode", Constant.ERROR_STATUS_CODE);
	 * 
	 * paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
	 * paramsMap.put("methodType","get");
	 * 
	 * new MockUp<JujuVnfmRestfulUtil>(){
	 * 
	 * @Mock public RestfulResponse getRemoteResponse(Map paramsMap,String str)
	 * { return null; } }; adapterResManager = new AdapterResourceManager();
	 * jsonObj = adapterResManager.getJujuVnfmInfo(paramsMap);
	 * assertEquals(resultObj,jsonObj); }
	 */

	/*
	 * @Test public void testGetVnfdInfo() throws Exception { Map paramsMap =
	 * new HashMap(); JSONObject resultObj = new JSONObject();
	 * resultObj.put("reason", "RestfulResponse is null.");
	 * resultObj.put("retCode", Constant.ERROR_STATUS_CODE);
	 * 
	 * paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
	 * paramsMap.put("methodType","get");
	 * 
	 * new MockUp<JujuVnfmRestfulUtil>(){
	 * 
	 * @Mock public RestfulResponse getRemoteResponse(Map paramsMap,String str)
	 * { return null; } }; adapterResManager = new AdapterResourceManager();
	 * jsonObj = adapterResManager.getJujuVnfmInfo(paramsMap);
	 * assertEquals(resultObj,jsonObj); }
	 */

	@Test
	public void getJujuVnfmInfoTest() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse get(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(200);
				return rsp;
			}
		};
		new MockUp<JSONObject>() {
			@Mock
			public JSONObject fromObject(Object object) {
				JSONObject json = new JSONObject();
				return json;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
		jsonObj = adapterResManager.getJujuVnfmInfo(paramsMap);
		assertTrue(jsonObj != null);
	}

	@Test
	public void getJujuVnfmInfoTest1() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse get(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(504);
				return rsp;
			}
		};
		new MockUp<JSONObject>() {
			@Mock
			public JSONObject fromObject(Object object) {
				JSONObject json = new JSONObject();
				return json;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
		jsonObj = adapterResManager.getJujuVnfmInfo(paramsMap);
		assertTrue(jsonObj != null);
	}

	@Test
	public void getJujuVnfmInfoTestFalse() {
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
		jsonObj = adapterResManager.getJujuVnfmInfo(null);
		assertTrue(jsonObj.get("reason").equals("RestfulResponse is null."));
	}

	@Test
	public void getVnfdInfoTest() {
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
//		jsonObj = adapterResManager.getVnfdInfo("1111");
//		assertTrue(jsonObj.get("reason").equals("RestfulResponse is null."));
	}

	@Test
	public void getVnfdInfoTest1() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse get(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(200);
				return rsp;
			}
		};
		new MockUp<JSONObject>() {
			@Mock
			public JSONObject fromObject(Object object) {
				JSONObject json = new JSONObject();
				return json;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
//		jsonObj = adapterResManager.getVnfdInfo("1111");
//		assertTrue(jsonObj != null);
	}

	@Test
	public void getVnfdInfoTest2() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse get(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(504);
				return rsp;
			}
		};

		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();
		resultObj.put("reason", "RestfulResponse is null.");
		resultObj.put("retCode", Constant.ERROR_STATUS_CODE);

		paramsMap.put("url", "/openoapi/extsys/v1/vnfms/11111");
		paramsMap.put("methodType", "get");
		adapterResManager = new AdapterResourceManager();
//		jsonObj = adapterResManager.getVnfdInfo("1111");
//		assertTrue(jsonObj != null);
	}
}
