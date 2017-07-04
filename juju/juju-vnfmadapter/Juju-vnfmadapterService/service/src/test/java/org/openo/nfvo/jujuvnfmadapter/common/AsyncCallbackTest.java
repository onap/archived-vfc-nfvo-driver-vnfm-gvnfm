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
package org.openo.nfvo.jujuvnfmadapter.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.nfvo.jujuvnfmadapter.service.rest.fullstack.JujuClientRoaTest;

public class AsyncCallbackTest {
    Logger logger = Logger.getLogger(JujuClientRoaTest.class);

    AsyncCallback Async;
    RestfulResponse response = new RestfulResponse();

    @Before
    public void setUp() {
        Async = new AsyncCallback();

    }

    @Test
    public void testhandleExcepion() {

        logger.warn("function=callback, msg=status={}, content={}.");
        Async.callback(response);
        Async.handleExcepion(null);

    }
}
