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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.nfvo.jujuvnfmadapter.common.CryptUtil;
import org.openo.nfvo.jujuvnfmadapter.service.entity.JujuVnfm;
import org.openo.nfvo.jujuvnfmadapter.service.entity.VnfmOpResult;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class JujuVnfmTest {

    @Test
    public void testGetId() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getId();
        assertNull(result);
    }

    @Test
    public void testSetId() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setId("testId");
        String result = jujuvnfm.getId();
        assertEquals("testId", result);
    }

    @Test
    public void testGetType() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getType();
        assertNull(result);
    }

    @Test
    public void testSetType() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setType("testType");
        String result = jujuvnfm.getType();
        assertEquals("testType", result);
    }

    @Test
    public void testGetName() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getName();
        assertNull(result);
    }

    @Test
    public void testSetName() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setName("testName");
        String result = jujuvnfm.getName();
        assertEquals("testName", result);
    }

    @Test
    public void testGetVersion() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getVersion();
        assertNull(result);
    }

    @Test
    public void testSetVersion() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setVersion("testVersion");
        String result = jujuvnfm.getVersion();
        assertEquals("testVersion", result);
    }

    @Test
    public void testGetUserName() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getUserName();
        assertNull(result);
    }

    @Test
    public void testSetUserName() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setUserName("testUserName");
        String result = jujuvnfm.getUserName();
        assertEquals("testUserName", result);
    }

    @Test
    public void testGetUrl() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getUrl();
        assertNull(result);
    }

    @Test
    public void testSetUrl() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setUrl("testUrl");
        String result = jujuvnfm.getUrl();
        assertEquals("testUrl", result);
    }

    @Test
    public void testGetPwd() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getPwd();
        assertNull(result);
    }

    @Test
    public void testSetPwd() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setPwd("testPwd");
        String result = jujuvnfm.getPwd();
        assertEquals("testPwd", result);
    }

    @Test
    public void testGetVender() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getVendor();
        assertNull(result);
    }

    @Test
    public void testSetVender() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setVendor("testVendor");
        String result = jujuvnfm.getVendor();
        assertEquals("testVendor", result);
    }

    @Test
    public void testGetExtraInfo() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getExtraInfo();
        assertNull(result);
    }

    @Test
    public void testSetExtraInfo() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setExtraInfo("testExtraInfo");
        String result = jujuvnfm.getExtraInfo();
        assertEquals("testExtraInfo", result);
    }

    @Test
    public void testGetStatus() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getStatus();
        assertNull(result);
    }

    @Test
    public void testSetStatus() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setStatus("testStatus");
        String result = jujuvnfm.getStatus();
        assertEquals("testStatus", result);
    }

    @Test
    public void testGetCreateAt() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getCreateAt();
        assertNull(result);
    }

    @Test
    public void testSetCreateAt() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setCreateAt("testCreateAt");
        String result = jujuvnfm.getCreateAt();
        assertEquals("testCreateAt", result);
    }

    @Test
    public void testGetUpdateAt() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.getUpdateAt();
        assertNull(result);
    }

    @Test
    public void testSetUpdateAt() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setUpdateAt("testUpdateAt");
        String result = jujuvnfm.getUpdateAt();
        assertEquals("testUpdateAt", result);
    }

    @Test
    public void testToString() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        String result = jujuvnfm.toString();
        assertEquals(
                "JujuVnfm[id=<null>,name=<null>,type=<null>,version=<null>,userName=<null>,pwd=<null>,url=<null>,vendor=<null>,extraInfo=<null>,status=<null>,createAt=<null>,updateAt=<null>]",
                result);
    }

    @Test
    public void testToString1() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setId("testId");
        jujuvnfm.setType("testType");
        jujuvnfm.setName("testName");
        jujuvnfm.setVersion("testVersion");
        jujuvnfm.setUserName("testUserName");
        jujuvnfm.setUrl("testUrl");
        jujuvnfm.setPwd("testPwd");
        jujuvnfm.setVendor("testVendor");
        jujuvnfm.setExtraInfo("testExtraInfo");
        jujuvnfm.setStatus("testStatus");
        jujuvnfm.setCreateAt("testCreateAt");
        jujuvnfm.setUpdateAt("testUpdateAt");
        String result = jujuvnfm.toString();
        assertEquals(
                "JujuVnfm[id=testId,name=testName,type=testType,version=testVersion,userName=testUserName,pwd=testPwd,url=testUrl,vendor=testVendor,extraInfo=testExtraInfo,status=testStatus,createAt=testCreateAt,updateAt=testUpdateAt]",
                result);
    }

    @Test
    public void testHashCode() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        int result = jujuvnfm.hashCode();
        assertEquals(31, result);
    }

    @Test
    public void testHashCode1() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setId("testId");
        int result = jujuvnfm.hashCode();
        assertEquals(-877170324, result);
    }

    @Test
    public void testEquals() {
        boolean result = new JujuVnfm().equals(new JujuVnfm());
        assertTrue(result);
    }

    @Test
    public void testEquals1() {
        boolean result = new JujuVnfm().equals("");
        assertFalse(result);
    }

    @Test
    public void testEquals2() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        jujuvnfm.setId("testId");
        VnfmOpResult obj = new VnfmOpResult();
        boolean result = new JujuVnfm().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals3() {
        JujuVnfm obj = new JujuVnfm();
        obj.setId("testId");
        boolean result = new JujuVnfm().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals4() {
        JujuVnfm obj = new JujuVnfm();
        boolean result = obj.equals(obj);
        assertTrue(result);
    }

    @Test
    public void testEquals5() {
        boolean result = new JujuVnfm().equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals6() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        JujuVnfm vnfm2 = new JujuVnfm();
        jujuvnfm.setId("");
        vnfm2.setId("");
        boolean result = jujuvnfm.equals(vnfm2);
        assertTrue(result);
    }

    @Test
    public void testEquals7() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        JujuVnfm vnfm2 = new JujuVnfm();
        jujuvnfm.setId("vnfmId");
        vnfm2.setId("vnfm2Id");
        boolean result = jujuvnfm.equals(vnfm2);
        assertFalse(result);
    }

    @Test
    public void testUpdateVnfm() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        JSONObject obj = new JSONObject();
        obj.put("name", "name");
        obj.put("userName", "userName");
        obj.put("pwd", "pwd");
        obj.put("extraInfo", "extraInfo");
        jujuvnfm.updateVnfm(obj);
        assertEquals("name", jujuvnfm.getName());
        assertEquals("userName", jujuvnfm.getUserName());
    }

    @Test
    public void testUpdateVnfmByEmpty() {
        JujuVnfm jujuvnfm = new JujuVnfm();
        JSONObject obj = new JSONObject();
        obj.put("name", "");
        obj.put("userName", "");
        obj.put("pwd", "");
        obj.put("extraInfo", "");
        jujuvnfm.updateVnfm(obj);
        assertNull(jujuvnfm.getName());
        assertNull(jujuvnfm.getUserName());
    }
}
