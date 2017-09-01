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
package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest.exceptionmapper;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest.exceptionmapper.ServiceExceptionMapper;
import org.openo.baseservice.remoteservice.exception.ServiceException;

public class ServiceExceptionMapperTest {

    ServiceExceptionMapper serviceExMapper;


    @Before
    public void setUp(){
        serviceExMapper = new ServiceExceptionMapper();
    }


    @Test
    public void toResponseTest() {
        Response resp = serviceExMapper.toResponse(new ServiceException("Testing"));
        assertEquals(resp.getStatus(),500);

    }

}
