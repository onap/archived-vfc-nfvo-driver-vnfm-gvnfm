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
import org.openo.nfvo.jujuvnfmadapter.service.entity.Vnfm;
import org.openo.nfvo.jujuvnfmadapter.service.entity.VnfmOpResult;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class VnfmTest {

    @Test
    public void testGetId() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getId();
        assertNull(result);
    }

    @Test
    public void testSetId() {
        Vnfm vnfm = new Vnfm();
        vnfm.setId("testId");
        String result = vnfm.getId();
        assertEquals("testId", result);
    }

    @Test
    public void testGetType() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getType();
        assertNull(result);
    }

    @Test
    public void testSetType() {
        Vnfm vnfm = new Vnfm();
        vnfm.setType("testType");
        String result = vnfm.getType();
        assertEquals("testType", result);
    }

    @Test
    public void testGetName() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getName();
        assertNull(result);
    }

    @Test
    public void testSetName() {
        Vnfm vnfm = new Vnfm();
        vnfm.setName("testName");
        String result = vnfm.getName();
        assertEquals("testName", result);
    }

    @Test
    public void testGetVersion() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getVersion();
        assertNull(result);
    }

    @Test
    public void testSetVersion() {
        Vnfm vnfm = new Vnfm();
        vnfm.setVersion("testVersion");
        String result = vnfm.getVersion();
        assertEquals("testVersion", result);
    }

    @Test
    public void testGetUserName() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getUserName();
        assertNull(result);
    }

    @Test
    public void testSetUserName() {
        Vnfm vnfm = new Vnfm();
        vnfm.setUserName("testUserName");
        String result = vnfm.getUserName();
        assertEquals("testUserName", result);
    }

    @Test
    public void testGetUrl() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getUrl();
        assertNull(result);
    }

    @Test
    public void testSetUrl() {
        Vnfm vnfm = new Vnfm();
        vnfm.setUrl("testUrl");
        String result = vnfm.getUrl();
        assertEquals("testUrl", result);
    }

    @Test
    public void testGetPwd() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getPwd();
        assertNull(result);
    }

    @Test
    public void testSetPwd() {
        Vnfm vnfm = new Vnfm();
        vnfm.setPwd("testPwd");
        String result = vnfm.getPwd();
        assertEquals("testPwd", result);
    }

    @Test
    public void testGetSites() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getSites();
        assertNull(result);
    }

    @Test
    public void testSetSites() {
        Vnfm vnfm = new Vnfm();
        vnfm.setSites("testSites");
        String result = vnfm.getSites();
        assertEquals("testSites", result);
    }

    @Test
    public void testGetExtraInfo() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getExtraInfo();
        assertNull(result);
    }

    @Test
    public void testSetExtraInfo() {
        Vnfm vnfm = new Vnfm();
        vnfm.setExtraInfo("testExtraInfo");
        String result = vnfm.getExtraInfo();
        assertEquals("testExtraInfo", result);
    }

    @Test
    public void testGetStatus() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getStatus();
        assertNull(result);
    }

    @Test
    public void testSetStatus() {
        Vnfm vnfm = new Vnfm();
        vnfm.setStatus("testStatus");
        String result = vnfm.getStatus();
        assertEquals("testStatus", result);
    }

    @Test
    public void testGetCreateAt() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getCreateAt();
        assertNull(result);
    }

    @Test
    public void testSetCreateAt() {
        Vnfm vnfm = new Vnfm();
        vnfm.setCreateAt("testCreateAt");
        String result = vnfm.getCreateAt();
        assertEquals("testCreateAt", result);
    }

    @Test
    public void testGetUpdateAt() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.getUpdateAt();
        assertNull(result);
    }

    @Test
    public void testSetUpdateAt() {
        Vnfm vnfm = new Vnfm();
        vnfm.setUpdateAt("testUpdateAt");
        String result = vnfm.getUpdateAt();
        assertEquals("testUpdateAt", result);
    }

    @Test
    public void testToString() {
        Vnfm vnfm = new Vnfm();
        String result = vnfm.toString();
        assertEquals(
                "Vnfm[id=<null>,name=<null>,type=<null>,version=<null>,userName=<null>,pwd=<null>,url=<null>,sites=<null>,extraInfo=<null>,status=<null>,createAt=<null>,updateAt=<null>]",
                result);
    }

    @Test
    public void testToString1() {
        Vnfm vnfm = new Vnfm();
        vnfm.setId("testId");
        vnfm.setType("testType");
        vnfm.setName("testName");
        vnfm.setVersion("testVersion");
        vnfm.setUserName("testUserName");
        vnfm.setUrl("testUrl");
        vnfm.setPwd("testPwd");
        vnfm.setSites("testSites");
        vnfm.setExtraInfo("testExtraInfo");
        vnfm.setStatus("testStatus");
        vnfm.setCreateAt("testCreateAt");
        vnfm.setUpdateAt("testUpdateAt");
        String result = vnfm.toString();
        assertEquals(
                "Vnfm[id=testId,name=testName,type=testType,version=testVersion,userName=testUserName,pwd=testPwd,url=testUrl,sites=testSites,extraInfo=testExtraInfo,status=testStatus,createAt=testCreateAt,updateAt=testUpdateAt]",
                result);
    }

    @Test
    public void testHashCode() {
        Vnfm vnfm = new Vnfm();
        int result = vnfm.hashCode();
        assertEquals(31, result);
    }

    @Test
    public void testHashCode1() {
        Vnfm vnfm = new Vnfm();
        vnfm.setId("testId");
        int result = vnfm.hashCode();
        assertEquals(-877170324, result);
    }

    @Test
    public void testEquals() {
        boolean result = new Vnfm().equals(new Vnfm());
        assertTrue(result);
    }

    @Test
    public void testEquals1() {
        boolean result = new Vnfm().equals("");
        assertFalse(result);
    }

    @Test
    public void testEquals2() {
        Vnfm vnfm = new Vnfm();
        vnfm.setId("testId");
        VnfmOpResult obj = new VnfmOpResult();
        boolean result = new Vnfm().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals3() {
        Vnfm obj = new Vnfm();
        obj.setId("testId");
        boolean result = new Vnfm().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals4() {
        Vnfm obj = new Vnfm();
        boolean result = obj.equals(obj);
        assertTrue(result);
    }

    @Test
    public void testEquals5() {
        boolean result = new Vnfm().equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals6() {
        Vnfm vnfm = new Vnfm();
        Vnfm vnfm2 = new Vnfm();
        vnfm.setId("");
        vnfm2.setId("");
        boolean result = vnfm.equals(vnfm2);
        assertTrue(result);
    }

    @Test
    public void testEquals7() {
        Vnfm vnfm = new Vnfm();
        Vnfm vnfm2 = new Vnfm();
        vnfm.setId("vnfmId");
        vnfm2.setId("vnfm2Id");
        boolean result = vnfm.equals(vnfm2);
        assertFalse(result);
    }

    @Test
    public void testUpdateVnfm() {
        Vnfm vnfm = new Vnfm();
        JSONObject obj = new JSONObject();
        obj.put("name", "name");
        obj.put("userName", "userName");
        obj.put("pwd", "pwd");
        obj.put("extraInfo", "extraInfo");
        vnfm.updateVnfm(obj);
        assertEquals("name", vnfm.getName());
        assertEquals("userName", vnfm.getUserName());
    }

    @Test
    public void testUpdateVnfmByEmpty() {
        Vnfm vnfm = new Vnfm();
        JSONObject obj = new JSONObject();
        obj.put("name", "");
        obj.put("userName", "");
        obj.put("pwd", "");
        obj.put("extraInfo", "");
        vnfm.updateVnfm(obj);
        //assertNull(vnfm.getName());
        //assertNull(vnfm.getUserName());
    }
}
