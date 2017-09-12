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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.JujuVnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl.JujuAdapter2MSBManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.ServiceException;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.HttpRest;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulParametes;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulResponse;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class JujuAdapter2MSBManagerTest {

	private JujuAdapter2MSBManager adapter2MSBManager;

	private JSONObject jsonObj;

	@Test
	public void registerJujuAdapterTest() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(200);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.registerJujuAdapter(paramsMap, resultObj);
		assertTrue(resp != null);
	}

	@Test
	public void registerJujuAdapterTest2() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(500);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.registerJujuAdapter(paramsMap, resultObj);
		assertTrue(resp != null);
	}

	@Test
	public void registerJujuAdapterTest3() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(415);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.registerJujuAdapter(paramsMap, resultObj);
		assertTrue(resp != null);
	}

	@Test
	public void registerJujuAdapterTest4() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(201);
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

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.registerJujuAdapter(paramsMap, resultObj);
		assertTrue(resp != null);
	}

	@Test
	public void registerJujuAdapterTest1() {
		Map<String, String> paramsMap = new HashMap<>();
		JSONObject resultObj = new JSONObject();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.registerJujuAdapter(null, resultObj);
		assertTrue(resp.get("reason").equals("RestfulResponse is null."));
	}

	@Test
	public void unregisterJujuAdapterTest() {
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp.get("reason").equals("RestfulResponse is null."));
	}

	@Test
	public void unregisterJujuAdapterTest1() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(204);
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

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp != null);
	}
	
	@Test
	public void unregisterJujuAdapterTest2() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(404);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp != null);
	}
	
	@Test
	public void unregisterJujuAdapterTest3() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(415);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp != null);
	}
	
	@Test
	public void unregisterJujuAdapterTest4() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(500);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp != null);
	}
	
	@Test
	public void unregisterJujuAdapterTest5() {
		new MockUp<HttpRest>() {
			@Mock
			RestfulResponse post(String arg0, RestfulParametes arg1) throws ServiceException {
				RestfulResponse rsp = new RestfulResponse();
				rsp.setStatus(200);
				return rsp;
			}
		};
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put("url", "/openoapi/microservices/v1/services");
		paramsMap.put("methodType", "post");

		JujuAdapter2MSBManager mgr = new JujuAdapter2MSBManager();
		JSONObject resp = mgr.unregisterJujuAdapter(paramsMap);
		assertTrue(resp != null);
	}
}
