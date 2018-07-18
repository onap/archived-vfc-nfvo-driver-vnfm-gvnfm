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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Vnf Authentication configuration information class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class VNFAuthConfigInfo {

    private static final Logger LOG = LoggerFactory.getLogger(VNFAuthConfigInfo.class);

    private static final String AUTH_CONFIG_FILE = "identity.VNFProperties";

    private static VNFAuthConfigInfo authConfig = new VNFAuthConfigInfo();

    private static long lastModify = 0L;

    private String vnfUserName;

    private String vnfEncryptedPW;

    private String vnfDomain;

    private String vnfResourceDomain;

    private String defaultDomain;

    private VNFAuthConfigInfo() {
        Properties vnfProp = new Properties();

        if(!isVNFProModified(getAuthCofigPath())) {
            //cannot find the config path, hence return
            LOG.debug("loadAuthConfig can't find config file");
            return;
        }
        try(InputStream authIn = new FileInputStream(getAuthCofigPath())) {
            vnfProp.load(authIn);
            vnfUserName = vnfProp.getProperty("name");
            vnfEncryptedPW = vnfProp.getProperty("value");
            vnfDomain = vnfProp.getProperty("vnfDomain");
            vnfResourceDomain = vnfProp.getProperty("vnfResourceDomain");
            defaultDomain = vnfProp.getProperty("defaultDomain");
        } catch(IOException e) {
            LOG.error("loadAuthConfig can't find config file>> e = {}", e);
        }
    }

    private String getAuthCofigPath() {
        return AUTH_CONFIG_FILE;
    }
    
    public static VNFAuthConfigInfo getInstance() {
        return authConfig;
    }

    public String getUserName() {
        return vnfUserName;
    }

    public String getEncryptedPW() {
        return vnfEncryptedPW;
    }

    public String getDomain() {
        return vnfDomain;
    }

    public void setUserName(String vnfuserName) {
        this.vnfUserName = vnfuserName;
    }

    public void setEncryptedPW(String vnfencryptedPW) {
        this.vnfEncryptedPW = vnfencryptedPW;
    }

    public void setDomain(String vnfDomain) {
        this.vnfDomain = vnfDomain;
    }

    public String getResourceDomain() {
        return vnfResourceDomain;
    }

    public void setResourceDomain(String vnfResourceDomain) {
        this.vnfResourceDomain = vnfResourceDomain;
    }

    public String getDefaultDomain() {
        return defaultDomain;
    }

    public void setDefaultDomain(String defaultDomain) {
        this.defaultDomain = defaultDomain;
    }

    private static boolean isVNFProModified(String filename) {
        boolean returnValue = false;
        File inputFile = new File(filename);
        if(inputFile.lastModified() > lastModify) {
            lastModify = inputFile.lastModified();
            returnValue = true;
        }
        return returnValue;
    }
}
