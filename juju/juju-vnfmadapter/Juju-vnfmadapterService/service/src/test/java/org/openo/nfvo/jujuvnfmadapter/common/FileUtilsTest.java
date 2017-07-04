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

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FileUtilsTest {

    FileUtils fileUtils;
    @Before
    public void setUp(){
        fileUtils.getAppAbsoluteUrl();
    }

    @Test
    public void testWriteFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("").getFile());
        String filePath = "";
        byte[] bytes =  new byte[] {40,50};
        int b = fileUtils.writeFile(bytes, filePath);
        assertNotNull(b);
    }
    @Test
    @Ignore
    public void testReadFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File f = new File(classLoader.getResource("").getFile());
        System.out.println(f.isAbsolute());
        String charsetName = "UTF-8";
        byte[] b = fileUtils.readFile(f, charsetName);
        assertNotNull(b);
    }
    @Test
    public void testListFiles() throws Exception {
        String file = ".";
        File f = new File(file);
        List<File> files = fileUtils.listFiles(f);
        assertNotNull(files);

    }
    @Test
    public void testMkDirs() throws Exception {
        File resourcesDirectory = new File("src/test/resources");
        String path = resourcesDirectory.getAbsolutePath()+"/TestDir";
        resourcesDirectory.getAbsolutePath();
        fileUtils.mkDirs(path);
    }
    @Test
    public void testDelFiles() throws Exception {
        File resourcesDirectory = new File("src/test/resources/TestDir/Test.txt");
        assertTrue(fileUtils.delFiles(resourcesDirectory.getAbsolutePath()));
    }
    @Test
    public void testgetFiles() throws Exception {
        File resourcesDirectory = new File("src/test/resources");
        List<File> files = fileUtils.getFiles(resourcesDirectory.getAbsolutePath());
        assertNotNull(files);
    }
    @Test
    public void testCopy() throws Exception {
        File oldfile = new File("");
        File newfile = new File("");
        fileUtils.copy(oldfile.getAbsolutePath(), newfile.getAbsolutePath(), true);
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = FileUtils.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
