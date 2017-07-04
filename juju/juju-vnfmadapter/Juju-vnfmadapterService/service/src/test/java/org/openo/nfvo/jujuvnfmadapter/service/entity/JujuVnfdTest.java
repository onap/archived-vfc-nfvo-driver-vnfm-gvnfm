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

public class JujuVnfdTest {

    @Test
    public void testGetDownloadUri() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        String result = jujuvnfd.getDownloadUri();
        assertNull(result);
    }

    @Test
    public void testSetDownloadUri() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        jujuvnfd.setDownloadUri("testDownloadUri");
        String result = jujuvnfd.getDownloadUri();
        assertEquals("testDownloadUri", result);
    }

    @Test
    public void testGetLocalPath() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        String result = jujuvnfd.getLocalPath();
        assertNull(result);
    }

    @Test
    public void testSetProtocol() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        jujuvnfd.setLocalPath("testLocalPath");
        String result = jujuvnfd.getLocalPath();
        assertEquals("testLocalPath", result);
    }


    @Test
    public void testToString() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        String result = jujuvnfd.toString();
        assertEquals(
                "JujuVnfd[downloadUri=<null>,localPath=<null>]",
                result);
    }

    @Test
    public void testToString1() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        jujuvnfd.setDownloadUri("testDownloadUri");
        jujuvnfd.setLocalPath("testLocalPath");
        String result = jujuvnfd.toString();
        assertEquals(
                "JujuVnfd[downloadUri=testDownloadUri,localPath=testLocalPath]",
                result);
    }

    @Test
    public void testHashCode() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        int result = jujuvnfd.hashCode();
        assertEquals(31, result);
    }

    @Test
    public void testHashCode1() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        jujuvnfd.setDownloadUri("testDownloadUri");
        int result = jujuvnfd.hashCode();
        assertEquals(-1870623759, result);
    }

    @Test
    public void testEquals() {
        boolean result = new JujuVnfd().equals(new JujuVnfd());
        assertTrue(result);
    }

    @Test
    public void testEquals1() {
        boolean result = new JujuVnfd().equals("");
        assertFalse(result);
    }

    @Test
    public void testEquals2() {
        JujuDriver obj = new JujuDriver();
        boolean result = new JujuVnfd().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals3() {
        JujuVnfd obj = new JujuVnfd();
        obj.setDownloadUri("testDownloadUri");
        boolean result = new JujuVnfd().equals(obj);
        assertFalse(result);
    }

    @Test
    public void testEquals4() {
        JujuVnfd obj = new JujuVnfd();
        boolean result = obj.equals(obj);
        assertTrue(result);
    }

    @Test
    public void testEquals5() {
        boolean result = new JujuVnfd().equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals6() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        JujuVnfd jujudriver2 = new JujuVnfd();
        jujuvnfd.setDownloadUri("");
        jujudriver2.setDownloadUri("");
        boolean result = jujuvnfd.equals(jujudriver2);
        assertTrue(result);
    }

    @Test
    public void testEquals7() {
        JujuVnfd jujuvnfd = new JujuVnfd();
        JujuVnfd jujudriver2 = new JujuVnfd();
        jujuvnfd.setDownloadUri("testDownloadUri");
        jujudriver2.setDownloadUri("DownloadUri");
        boolean result = jujuvnfd.equals(jujudriver2);
        assertFalse(result);
    }

}
