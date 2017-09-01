/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.api.internalsvc.inf;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Jan 24, 2017
 */
public interface IJujuAdapter2DriverMgrService {

    /**
     * VimAdapter register interface.<br>
     *
     * @since NFVO 0.5
     */
    void register();

    /**
     * VimAdapter unregister interface.<br>
     *
     * @since NFVO 0.5
     */
    void unregister();

}
