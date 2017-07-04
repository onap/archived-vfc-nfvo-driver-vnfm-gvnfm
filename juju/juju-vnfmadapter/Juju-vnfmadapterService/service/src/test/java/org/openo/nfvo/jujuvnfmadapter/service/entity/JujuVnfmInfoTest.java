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

package org.openo.nfvo.jujuvnfmadapter.service.entity;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Date;

public class JujuVnfmInfoTest {

	@Test
	public void testSetId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setId("testSetId");
		String result = jujuVnfmInfo.getId();
		assertEquals("testSetId", result);
	}

	@Test
	public void testGetId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getId();
		assertNull(result);
	}

	@Test
	public void testSetVnfmId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setVnfmId("testSetVnfmId");
		String result = jujuVnfmInfo.getVnfmId();
		assertEquals("testSetVnfmId", result);
	}

	@Test
	public void testGetVnfmId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getVnfmId();
		assertNull(result);
	}

	@Test
	public void testSetVnfId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setVnfId("testSetVnfId");
		String result = jujuVnfmInfo.getVnfId();
		assertEquals("testSetVnfId", result);
	}

	@Test
	public void testGetVnfId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getVnfId();
		assertNull(result);
	}

	@Test
	public void testSetAppName() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setAppName("testSetAppName");
		String result = jujuVnfmInfo.getAppName();
		assertEquals("testSetAppName", result);
	}

	@Test
	public void testGetAppName() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getAppName();
		assertNull(result);
	}

	@Test
	public void testSetJobId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setJobId("testSetJobId");
		String result = jujuVnfmInfo.getJobId();
		assertEquals("testSetJobId", result);
	}

	@Test
	public void testGetJobId() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getJobId();
		assertNull(result);
	}

	@Test
	public void testSetStatus() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setStatus(0);
		Integer result = jujuVnfmInfo.getStatus();
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testGetStatus() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		Integer result = jujuVnfmInfo.getStatus();
		assertNull(result);
	}

	@Test
	public void testSetCreateTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setCreateTime(new Date());
		Date result = jujuVnfmInfo.getCreateTime();
		assertEquals(new Date(), result);
	}

	@Test
	public void testGetCreateTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		Date result = jujuVnfmInfo.getCreateTime();
		assertNull(result);
	}

	@Test
	public void testSetModifyTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setModifyTime(new Date());
		Date result = jujuVnfmInfo.getModifyTime();
		assertEquals(new Date(), result);
	}

	@Test
	public void testGetModifyTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		Date result = jujuVnfmInfo.getModifyTime();
		assertNull(result);
	}

	@Test
	public void testSetDeleteTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setDeleteTime(new Date());
		Date result = jujuVnfmInfo.getDeleteTime();
		assertEquals(new Date(), result);
	}

	@Test
	public void testGetDeleteTime() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		Date result = jujuVnfmInfo.getDeleteTime();
		assertNull(result);
	}

	@Test
	public void testSetExtend() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		jujuVnfmInfo.setExtend("testSetExtend");
		String result = jujuVnfmInfo.getExtend();
		assertEquals("testSetExtend", result);
	}

	@Test
	public void testGetExtend() {
		JujuVnfmInfo jujuVnfmInfo = new JujuVnfmInfo();
		String result = jujuVnfmInfo.getExtend();
		assertNull(result);
	}


}
