/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class ServiceExceptionTest {
    @Mock
    Throwable throwable;
    @Mock
    ExceptionArgs exceptionArgs;
    ServiceException serviceException = new ServiceException();

    @Test
    public void testConstructors()
    {
        Object object= new Object();
        ServiceException serviceException1 = new ServiceException("huawei",throwable);
        ServiceException serviceException2 = new ServiceException("huawei");
        ServiceException serviceException3 = new ServiceException("huawei","huawei");
        ServiceException serviceException4 = new ServiceException("huawei",200);
        ServiceException serviceException5 = new ServiceException(200,"huawei");
        ServiceException serviceException6 = new ServiceException("huawei",200,exceptionArgs);
        ServiceException serviceException7 = new ServiceException("200","huawei",object);
        ServiceException serviceException8 = new ServiceException("200","huawei",throwable,object);
        ServiceException serviceException9 = new ServiceException("200","huawei",throwable);
        ServiceException serviceException10 = new ServiceException(throwable);


    }
    @Test
    public void testGettermethods()
    {
        serviceException.getArgs();
        serviceException.getExceptionArgs();
        serviceException.getId();
        serviceException.setId(null);
        serviceException.getId();
    }

}