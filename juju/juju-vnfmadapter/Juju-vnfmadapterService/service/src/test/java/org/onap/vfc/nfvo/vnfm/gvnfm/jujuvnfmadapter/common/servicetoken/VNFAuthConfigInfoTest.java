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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VNFAuthConfigInfo;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Aug 10, 2016
 */
public class VNFAuthConfigInfoTest {

    @Test
    public void testGetDomain() {
        VNFAuthConfigInfo authConfig = VNFAuthConfigInfo.getInstance();
        authConfig.setDomain("vnfDomain");
        assertEquals("vnfDomain", authConfig.getDomain());
    }

    @Test
    public void testGetResourceDomain() {
        VNFAuthConfigInfo authConfig = VNFAuthConfigInfo.getInstance();
        authConfig.setResourceDomain("vnfResourceDomain");
        assertEquals("vnfResourceDomain", authConfig.getResourceDomain());
    }

    @Test
    public void testGetDefaultDomain() {
        VNFAuthConfigInfo authConfig = VNFAuthConfigInfo.getInstance();
        authConfig.setDefaultDomain("defaultDomain");
        assertEquals("defaultDomain", authConfig.getDefaultDomain());
    }

    @Test
    public void testGetUserName() {
        VNFAuthConfigInfo authConfig = VNFAuthConfigInfo.getInstance();
        authConfig.setUserName("vnfuserName");
        assertEquals("vnfuserName", authConfig.getUserName());
    }

    @Test
    public void testGetEncryptedPW() {
        VNFAuthConfigInfo authConfig = VNFAuthConfigInfo.getInstance();
        authConfig.setEncryptedPW("vnfencryptedPW");
        assertEquals("vnfencryptedPW", authConfig.getEncryptedPW());
    }
}
