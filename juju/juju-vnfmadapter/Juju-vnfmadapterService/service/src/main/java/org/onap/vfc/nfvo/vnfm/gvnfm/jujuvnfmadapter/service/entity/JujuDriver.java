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

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * Juju Driver Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 10, 2016
 */
public class JujuDriver {

    private String serviceName;

    private String protocol;

    private String version;

    private String visualRange;

    private String url;

    private List nodes;

    private String ip;

    private String port;

    private String ttl;

    private String status;

    public static final String NOTES = "notes";
       
    public JujuDriver() {
       // Default Constructor
    }
    
    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param jujuJsonObject
     * @since  NFVO 0.5
     */
    public JujuDriver(JSONObject jujuJsonObject) {
        this.serviceName = jujuJsonObject.getString("serviceName");
        this.protocol = jujuJsonObject.getString("protocol");
        this.version = jujuJsonObject.getString("version");
        this.visualRange = jujuJsonObject.getString("visualRange");
        this.url = jujuJsonObject.getString("url");
        this.nodes = jujuJsonObject.getJSONArray("nodes");
        this.ip = jujuJsonObject.getJSONArray(NOTES).getString(0);
        this.port = jujuJsonObject.getJSONArray(NOTES).getString(1);
        this.ttl = jujuJsonObject.getJSONArray(NOTES).getString(2);
        this.status = jujuJsonObject.getString("status");
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVisualRange() {
        return visualRange;
    }

    public void setVisualRange(String visualRange) {
        this.visualRange = visualRange;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List getNodes() {
        return nodes;
    }

    public void setNodes(List nodes) {
        this.nodes = nodes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serviceName == null) ? 0 : serviceName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof JujuDriver)) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        JujuDriver other = (JujuDriver)obj;
        if(serviceName == null) {
            if(other.serviceName != null) {
                return false;
            }
        } else if(!serviceName.equals(other.serviceName)) {
            return false;
        }
        return true;
    }

}
