/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.openo.nfvo.jujuvnfmadapter.common.StringUtil;

public class StringUtilTest {

    @Test
    public void testIsValidString() {
        boolean result = StringUtil.isValidString("abc");
        assertTrue(result);
    }

    @Test
    public void testIsValidString1() {
        boolean result = StringUtil.isValidString(" abc ");
        assertTrue(result);
    }

    @Test
    public void testIsValidString2() {
        boolean result = StringUtil.isValidString("  ");
        assertFalse(result);
    }

    @Test
    public void testIsValidString3() {
        boolean result = StringUtil.isValidString("");
        assertFalse(result);
    }

    @Test
    public void testIsValidString4() {
        boolean result = StringUtil.isValidString(null);
        assertFalse(result);
    }

    @Test
    public void testIsValidUrl() {
        boolean result = StringUtil.isValidUrl("https://127.0.0.1:31943");
        assertTrue(result);
    }

    @Test
    public void testIsValidUrl1() {
        boolean result = StringUtil.isValidUrl("http://255.250.255.1:31943");
        assertTrue(result);
    }

    @Test
    public void testIsValidUrl2() {
        boolean result = StringUtil.isValidUrl("http:");
        assertFalse(result);
    }

    @Test
    public void testIsValidUrl3() {
        boolean result = StringUtil.isValidUrl("http://255.250");
        assertFalse(result);
    }

    @Test
    public void testIsValidUrl4() {
        boolean result = StringUtil.isValidUrl("");
        assertFalse(result);
    }

    @Test
    public void testIsValidAnyString() {
        boolean result = StringUtil.isValidAnyString("abc", "aaa", "bbb");
        assertTrue(result);
    }

    @Test
    public void testIsValidAnyString1() {
        boolean result = StringUtil.isValidAnyString("abc", "", "bbb");
        assertFalse(result);
    }

    @Test
    public void testTransSitesToArray() {
        String sites = "Beijing&Shanghai";
        JSONArray result = StringUtil.transSitesToArray(sites);

        JSONArray siteArray = new JSONArray();
        siteArray.add("Beijing");
        siteArray.add("Shanghai");
        assertEquals(siteArray, result);
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = StringUtil.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
