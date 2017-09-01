/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.rest.fullstack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.FileUtils;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Nov 3, 2016
 */
public class TestHelper {

    @SuppressWarnings("deprecation")
    public static MockHttpServletRequest buildMockRequest(File file) throws FileNotFoundException, IOException {

        String contnet = IOUtils.toString(new FileInputStream(file));
        byte[] content = contnet.getBytes();
        MockHttpServletRequest context = new MockHttpServletRequest();
        context.setContentType("application/json");
        context.setContent(content);
        return context;
    }

    public static MockHttpServletRequest buildMockRequest(String content) throws FileNotFoundException, IOException {
        MockHttpServletRequest context = new MockHttpServletRequest();
        context.setContentType("application/json");
        context.setContent(content.getBytes());
        return context;
    }

    public static MockHttpServletRequest buildDefaultRequest(String fileName)
            throws FileNotFoundException, IOException {
        MockHttpServletRequest context = new MockHttpServletRequest();
        context.setContentType("application/json");
        if(fileName != null) {
            String file = FileUtils.getClassPath() + File.separator + fileName;
            InputStream input = new FileInputStream(file);
            @SuppressWarnings("deprecation")
            String contnet = IOUtils.toString(input);
            byte[] content = contnet.getBytes();
            context.setContent(content);
        }
        return context;
    }

}
