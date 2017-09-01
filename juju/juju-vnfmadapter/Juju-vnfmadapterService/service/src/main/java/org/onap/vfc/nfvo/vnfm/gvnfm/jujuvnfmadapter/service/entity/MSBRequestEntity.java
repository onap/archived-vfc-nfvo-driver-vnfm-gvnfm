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

import java.io.Serializable;
import java.util.List;

import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.EntityUtils;

/**
 * <br/>
 * <p>
 * The request parameters of Register service to the Microservice Bus
 * URL-path: /openoapi/microservices/v1/services
 * example:
 * {
 * "serviceName": "catalog",
 * "version": "v1",
 * "url": "/openoapi/catalog/v1",
 * "protocol": "REST",
 * "visualRange": ["1"],
 * "nodes": [
 * {
 * "ip": "10.74.56.36",
 * "port": "8988",
 * "ttl": 0
 * }
 * ]
 * }
 * </p>
 * <p>
 * the response example:
 * {
 * "serviceName": "catalog",
 * "version": "v1",
 * "url": "/openoapi/catalog/v1",
 * "protocol": "REST",
 * "visualRange": ["1"],
 * "nodes": [
 * {
 * "ip": "10.74.55.66",
 * "port": "6666",
 * "ttl": 0,
 * "expiration": "2016-02-18T16:19:40+08:00",
 * "created_at": "2016-02-18T16:03:00+08:00",
 * "updated_at": "2016-02-18T16:03:00+08:00"
 * },
 * {
 * "ip": "10.74.56.37",
 * "port": "8989",
 * "ttl": 0,
 * "expiration": "2016-02-18T16:13:00+08:00",
 * "created_at": "2016-02-18T16:03:00+08:00",
 * "updated_at": "2016-02-18T16:03:00+08:00"
 * },
 * {
 * "ip": "10.74.56.36",
 * "port": "8988",
 * "ttl": 0,
 * "expiration": "2016-02-18T17:04:36+08:00",
 * "created_at": "2016-02-18T16:03:00+08:00",
 * "updated_at": "2016-02-18T16:03:00+08:00"
 * }
 * ],
 * "status": "1"
 * }
 * the params description:
 * serviceName: service name
 * version: version
 * url: url of the created service instance
 * protocol: supported protocols: 'REST', 'UI'
 * nodes: the service instance nodes list to register
 * ip: the ip of the service instance node, it could also be a hostname like catalog.openo.org
 * port: the port of the service instance node
 * ttl: time to live, this parameter is reserved for later use
 * status: service status, 1: eanbled, 0:disabled
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Aug 18, 2016
 */
public class MSBRequestEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4548158998952436572L;

    private String serviceName;

    private String version;

    /**
     * the actual URL of the service to be registered
     */
    private String url;

    /**
     * supported protocols: 'REST', 'UI'
     */
    private String protocol = "REST";

    /**
     * Visibility of the service.
     * External(can be accessed by external systems):0
     * Internal(can only be accessed by OPEN-O consumers):1
     */
    private String visualRange = "1";

    /**
     * only exist in response;
     * status: service status, 1: eanbled, 0:disabled
     */
    private String status;

    /**
     * ip: the ip of the service instance node, it could also be a hostname like catalog.openo.org
     * port: the port of the service instance node
     * ttl: time to live, this parameter is reserved for later use
     */
    private List<Node> nodes;

    /**
     * @return Returns the serviceName.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName The serviceName to set.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Returns the protocol.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol The protocol to set.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return Returns the visualRange.
     */
    public String getVisualRange() {
        return visualRange;
    }

    /**
     * @param visualRange The visualRange to set.
     */
    public void setVisualRange(String visualRange) {
        this.visualRange = visualRange;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the nodes.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * @param nodes The nodes to set.
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * <br/>
     * <p>
     * </p>
     * 
     * @author
     * @version NFVO 0.5 Aug 18, 2016
     */
    public static class Node implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 7618395945064516270L;

        private String ip;

        private String port;

        private String ttl;

        private String expiration;

        private String createdAt;

        private String updatedAt;

        /**
         * @return Returns the ip.
         */
        public String getIp() {
            return ip;
        }

        /**
         * @param ip The ip to set.
         */
        public void setIp(String ip) {
            this.ip = ip;
        }

        /**
         * @return Returns the port.
         */
        public String getPort() {
            return port;
        }

        /**
         * @param port The port to set.
         */
        public void setPort(String port) {
            this.port = port;
        }

        /**
         * @return Returns the ttl.
         */
        public String getTtl() {
            return ttl;
        }

        /**
         * @param ttl The ttl to set.
         */
        public void setTtl(String ttl) {
            this.ttl = ttl;
        }

        /**
         * @return Returns the expiration.
         */
        public String getExpiration() {
            return expiration;
        }

        /**
         * @param expiration The expiration to set.
         */
        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        /**
         * @return Returns the created_at.
         */
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * @param createdAt The created_at to set.
         */
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        /**
         * @return Returns the updated_at.
         */
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         * @param updatedAt The updated_at to set.
         */
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        /**
         * <br/>
         * 
         * @return
         * @since NFVO 0.5
         */
        @Override
        public String toString() {
            return EntityUtils.toString(this, Node.class);
        }

    }

    /**
     * <br/>
     * 
     * @return
     * @since NFVO 0.5
     */
    @Override
    public String toString() {
        return EntityUtils.toString(this, MSBRequestEntity.class);
    }

}
