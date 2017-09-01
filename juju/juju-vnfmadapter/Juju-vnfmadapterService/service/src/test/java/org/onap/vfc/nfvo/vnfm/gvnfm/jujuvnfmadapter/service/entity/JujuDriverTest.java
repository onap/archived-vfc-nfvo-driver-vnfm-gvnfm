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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.CryptUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuDriver;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfd;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.VnfmOpResult;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JujuDriverTest {

    @Test
    public void testGetServiceName() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getServiceName();
        assertNull(result);
    }

    @Test
    public void testSetServiceName() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setServiceName("testServiceName");
        String result = jujudriver.getServiceName();
        assertEquals("testServiceName", result);
    }

    @Test
    public void testGetProtocol() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getProtocol();
        assertNull(result);
    }

    @Test
    public void testSetProtocol() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setProtocol("protocol");
        String result = jujudriver.getProtocol();
        assertEquals("protocol", result);
    }

    @Test
    public void testGetVisualRange() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getVisualRange();
        assertNull(result);
    }

    @Test
    public void testSetVisualRange() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setVisualRange("getVisualRange");
        String result = jujudriver.getVisualRange();
        assertEquals("getVisualRange", result);
    }

    @Test
    public void testGetVersion() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getVersion();
        assertNull(result);
    }

    @Test
    public void testSetVersion() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setVersion("testVersion");
        String result = jujudriver.getVersion();
        assertEquals("testVersion", result);
    }

    @Test
    public void testGetUrl() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getUrl();
        assertNull(result);
    }

    @Test
    public void testSetUrl() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setUrl("testUrl");
        String result = jujudriver.getUrl();
        assertEquals("testUrl", result);
    }

    @Test
    public void testGetIP() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getIP();
        assertNull(result);
    }

    @Test
    public void testSetIP() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setIP("testIP");
        String result = jujudriver.getIP();
        assertEquals("testIP", result);
    }


    @Test
    public void testGetPort() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getPort();
        assertNull(result);
    }

    @Test
    public void testSetPort() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setPort("testPort");
        String result = jujudriver.getPort();
        assertEquals("testPort", result);
    }

    @Test
    public void testGetTtl() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getTtl();
        assertNull(result);
    }

    @Test
    public void testSetTtl() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setTtl("testTtl");
        String result = jujudriver.getTtl();
        assertEquals("testTtl", result);
    }

    @Test
    public void testGetStatus() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.getStatus();
        assertNull(result);
    }

    @Test
    public void testSetStatus() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setStatus("testStatus");
        String result = jujudriver.getStatus();
        assertEquals("testStatus", result);
    }


    @Test
    public void testToString() {
        JujuDriver jujudriver = new JujuDriver();
        String result = jujudriver.toString();
        assertEquals(
                "JujuDriver[serviceName=<null>,protocol=<null>,version=<null>,visualRange=<null>,url=<null>,nodes=<null>,ip=<null>,port=<null>,ttl=<null>,status=<null>]",
                result);
    }

    @Test
    public void testToString1() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setServiceName("serviceName");
        jujudriver.setProtocol("protocol");
        jujudriver.setVersion("version");
        jujudriver.setVisualRange("visualRange");
        jujudriver.setUrl("testUrl");
        jujudriver.setIP("testIP");
        jujudriver.setPort("testPort");
        jujudriver.setTtl("testTtl");
        jujudriver.setStatus("testStatus");
        String result = jujudriver.toString();
        assertEquals(
                "JujuDriver[serviceName=serviceName,protocol=protocol,version=version,visualRange=visualRange,url=testUrl,nodes=<null>,ip=testIP,port=testPort,ttl=testTtl,status=testStatus]",
                result);
    }

    @Test
    public void testHashCode() {
        JujuDriver jujudriver = new JujuDriver();
        int result = jujudriver.hashCode();
        assertEquals(31, result);
    }

    @Test
    public void testHashCode1() {
        JujuDriver jujudriver = new JujuDriver();
        jujudriver.setServiceName("serviceName");
        int result = jujudriver.hashCode();
        assertEquals(-1928572161, result);
    }

    @Test
    public void testEquals() {
        boolean result = new JujuDriver().equals(new JujuDriver());
        assertTrue(result);
    }

    @Test
    public void testEquals1() {
        boolean result = new JujuDriver().equals("");
        assertFalse(result);
    }

    @Test
    public void testEquals2() {
        JujuVnfd obj = new JujuVnfd();
        boolean result = new JujuDriver().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals3() {
        JujuDriver obj = new JujuDriver();
        obj.setServiceName("serviceName");
        boolean result = new JujuDriver().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals4() {
        JujuDriver obj = new JujuDriver();
        boolean result = obj.equals(obj);
        assertTrue(result);
    }

    @Test
    public void testEquals5() {
        boolean result = new JujuDriver().equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals6() {
        JujuDriver jujudriver = new JujuDriver();
        JujuDriver jujudriver2 = new JujuDriver();
        jujudriver.setPort("");
        jujudriver2.setPort("");
        boolean result = jujudriver.equals(jujudriver2);
        assertTrue(result);
    }

    @Test
    public void testEquals7() {
        JujuDriver jujudriver = new JujuDriver();
        JujuDriver jujudriver2 = new JujuDriver();
        jujudriver.setServiceName("serviceName");
        jujudriver2.setServiceName("serName");
        boolean result = jujudriver.equals(jujudriver2);
        assertFalse(result);
    }
    @Test
    public void JujuDriverConsTest(){
        JSONObject jujuJsonObject = new JSONObject();
        JSONArray array= new JSONArray();
        array.add("1.1.1.1");
        array.add("8080");
        array.add("ttl");
        jujuJsonObject.put("serviceName", "test123");
        jujuJsonObject.put("protocol", "http");
        jujuJsonObject.put("version", "v1");
        jujuJsonObject.put("visualRange", "10");
        jujuJsonObject.put("url", "/test");
        jujuJsonObject.put("nodes", "[]");
        jujuJsonObject.put("notes", array);
        jujuJsonObject.put("status", "active");
        JujuDriver driver = new JujuDriver(jujuJsonObject);
        assertTrue("test123".equals(driver.getServiceName()));
    }
    @Test
    public void testsetNodes() {
        JujuDriver jujudriver = new JujuDriver();
        List<String> nodes = new ArrayList<String>();
        nodes.add("nodes");
        jujudriver.setNodes(nodes);
    }
    @Test
    public void testgetNodes() {
        JujuDriver jujudriver = new JujuDriver();
        List<String> nodes = new ArrayList<String>();
        nodes.add("nodes");
        jujudriver.getNodes();
    }
}
