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

package org.openo.nfvo.jujuvnfmadapter.service.adapter.inf;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author 
 * @version NFVO 0.5 Sep 7, 2016
 */
public interface IJujuClientManager {

    /**
     * * deploy juju charm from local filesystem. The charm will get deployed on a trusty instance
     * with at least 2 GB RAM
     * juju deploy $HOME/charms/example --series trusty --constraints mem=2G --config
     * /path/to/custom/charm-config.yaml
     * <br/>
     * 
     * @param charmPath
     * @param mem (at least 2 GB RAM) unit/GB
     * @param appName
     * @return
     * @since NFVO 0.5
     */
    JSONObject deploy(String charmPath, String appName);

    /**
     * * remove a charm completely
     * juju remove-application example
     * juju destroy-model model-name
     * <br/>
     * 
     * @param appName
     * @return
     * @since NFVO 0.5
     */
    JSONObject destroy(String appName);

    /**
     * juju status --format=json
     * juju status -m model-name --format=json
     * get the status of a single charm
     * juju status example --format=json
     * Here is an example output from juju status --format=json
     * http://paste.ubuntu.com/23113992/
     * <br/>
     * 
     * @param modelName
     * @return
     * @since NFVO 0.5
     */
    JSONObject getStatus(String modelName);
    /**
     * 
     * <br/>
     * 
     * @param charmPath
     * @param appName
     * @param action
     * @param vnfId
     * @return
     * @since  NFVO 0.5
     */
    public boolean grantResource(String charmPath, String appName,String action , String vnfId);
}
