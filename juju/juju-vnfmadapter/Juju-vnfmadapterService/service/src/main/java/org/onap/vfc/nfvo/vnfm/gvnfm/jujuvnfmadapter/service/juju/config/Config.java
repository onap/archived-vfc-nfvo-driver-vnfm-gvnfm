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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju.config;

import java.util.Map;

/**
 * Created by QuanZhong on 2016/12/1.
 */
public class Config {
    public Config(Map<String, ConfigItem> options) {
        this.options = options;
    }

    private Map<String,ConfigItem> options;

    public Map<String, ConfigItem> getOptions() {
        return options;
    }

    public void setOptions(Map<String, ConfigItem> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Config{" +
                "options=" + options +
                '}';
    }
}
