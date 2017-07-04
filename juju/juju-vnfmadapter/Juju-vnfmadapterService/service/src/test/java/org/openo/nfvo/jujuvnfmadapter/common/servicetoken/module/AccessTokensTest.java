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

package org.openo.nfvo.jujuvnfmadapter.common.servicetoken.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.openo.nfvo.jujuvnfmadapter.common.servicetoken.module.AccessTokens;

public class AccessTokensTest {

    @Test
    public void testVimAccessTokens() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 123);
        assertEquals("accessToken", accessTokens.getAccessToken());
        assertEquals(123, accessTokens.getExpire());
    }

    @Test
    public void testAccessTokens1() {
        AccessTokens accessTokens = new AccessTokens("accessToken", null, null);
        assertEquals("accessToken", accessTokens.getAccessToken());
        assertEquals(0, accessTokens.getExpire());
        assertEquals(0, accessTokens.getCreateTime());
    }

    @Test
    public void testAccessTokens2() {
        Integer vimExpire = (Integer)123;
        Long createTime = (Long)1L;
        AccessTokens accessTokens = new AccessTokens("accessToken", vimExpire, createTime);
        assertEquals("accessToken", accessTokens.getAccessToken());
        assertEquals(123, accessTokens.getExpire());
        assertEquals(1L, accessTokens.getCreateTime());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVimAccessTokens3() {
        AccessTokens accessTokens = new AccessTokens();
    }

    @Test
    public void testSetVimAccessToken() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 123);
        accessTokens.setAccessToken("anotherToken");
        assertEquals("anotherToken", accessTokens.getAccessToken());
    }

    @Test
    public void testValidExpire() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 0);
        assertTrue(accessTokens.valid());
    }

    @Test
    public void testValidExpire1() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 123);
        assertTrue(accessTokens.valid());
    }

    @Test
    public void testValidExpire2() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 123, 1L);
        assertFalse(accessTokens.valid());
    }

    @Test
    public void testToString() {
        AccessTokens accessTokens = new AccessTokens("accessToken", 123, 1L);
        assertEquals("{accessToken','expire': '123','createTime': '1'}", accessTokens.toString());
    }

    @Test
    public void testToEntity() {
        String data = "{'accessToken': 'accessToken','expire': '123','createTime': '1'}";
        JSONObject jsonObject = JSONObject.fromObject(data);
        AccessTokens accessTokens = new AccessTokens("accessToken", 123, 1L);
        assertEquals(accessTokens.toString(), AccessTokens.toEntity(jsonObject).toString());
    }

}
