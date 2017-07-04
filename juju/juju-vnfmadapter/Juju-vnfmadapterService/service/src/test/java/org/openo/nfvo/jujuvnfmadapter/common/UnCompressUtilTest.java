/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.nfvo.jujuvnfmadapter.common;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UnCompressUtilTest {

    UnCompressUtil util;

    FileInputStream fis;

    @Before
    public void setUp(){
         util = new UnCompressUtil();

    }

    @Test

    public void unCompressGzipTest() {
        String zipfileName = "";
        String outputDirectory = "";
        List<String> fileNames = new ArrayList<String>();
        //fileNames.add("test1");
        util.unCompressGzip(zipfileName, outputDirectory, fileNames);
    }

    @Test

    public void unCompressZipTest() {
        String zipfileName = "";
        String outputDirectory = "";
        List<String> fileNames = new ArrayList<String>();
        util.unCompressZip(zipfileName, outputDirectory, fileNames);
    }


    @Test

    public void unCompressTarXZTest() {
        String zipfileName = "";
        String outputDirectory = "";
        List<String> fileNames = new ArrayList<String>();
        util.unCompressTarXZ(zipfileName, outputDirectory, fileNames);
    }

    @Test

    public void unCompressTest() {
        String zipfileName = "";
        String outputDirectory = "src//test//resources";
        List<String> fileNames = new ArrayList<String>();
        util.unCompress(zipfileName, outputDirectory, fileNames);
    }


}
