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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {

    FileUtils fileUtils;

    @Before
    public void setUp() {
        fileUtils.getAppAbsoluteUrl();
    }

    @Test
    public void testGetClassPath() throws Exception {
        assertNotNull(FileUtils.getClassPath());
    }

    @Test
    public void testReadFileNull() throws Exception {
        assertNotNull(FileUtils.readFile(null, null));
    }

    @Test
    public void testReadFile() throws Exception {
        assertNotNull(FileUtils.readFile(
                new File("src/test/java/org/onap/vfc/nfvo/vnfm/gvnfm/jujuvnfmadapter/common/FileUtil.java"), "utf-8"));
    }

    @Test(expected = IOException.class)
    public void testReadFileFail() throws Exception {
        FileUtils.readFile(new File("src/test/java/org/onap/vfc/nfvo/vnfm/gvnfm/jujuvnfmadapter/common/aaa.java"),
                "utf-8");
    }

    @Test
    public void testWriteFile() throws Exception {
        String filePath = "";
        byte[] bytes = new byte[] {40, 50};
        int b = FileUtils.writeFile(bytes, filePath);
        assertNotNull(b);
    }

    @Test
    public void testWriteFileSuccess() throws Exception {
        String filePath = "src/test/java/FileTest";
        byte[] bytes = new byte[] {40, 50};
        int b = FileUtils.writeFile(bytes, filePath);
        assertNotNull(b);
    }

    @Test
    public void testListFiles() throws Exception {
        String file = ".";
        File f = new File(file);
        List<File> files = FileUtils.listFiles(f);
        assertNotNull(files);

    }

    @Test(expected = FileNotFoundException.class)
    public void testListFilesFail() throws Exception {
        String file = "abc";
        File f = new File(file);
        List<File> files = FileUtils.listFiles(f);
        assertNotNull(files);

    }

    @Test
    public void testMkDirs() throws Exception {
        File resourcesDirectory = new File("src/test/resources");
        String path = resourcesDirectory.getAbsolutePath() + "/TestDir";
        resourcesDirectory.getAbsolutePath();
        FileUtils.newFloder(path);
        FileUtils.mkDirs(path);
        FileUtils.newFloder(path);
    }

    @Test
    public void testDelFiles() throws Exception {
        File resourcesDirectory = new File("src/test/resources/TestDir/Test.txt");
        assertTrue(FileUtils.delFiles(resourcesDirectory.getAbsolutePath()));
    }

    @Test
    public void testgetFiles() throws Exception {
        File resourcesDirectory = new File("src/test/resources");
        List<File> files = FileUtils.getFiles(resourcesDirectory.getAbsolutePath());
        assertNotNull(files);
    }

    @Test
    public void testCopy() throws Exception {
        File oldfile = new File("");
        File newfile = new File("");
        FileUtils.copy(oldfile.getAbsolutePath(), newfile.getAbsolutePath(), true);
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = FileUtils.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test(expected = FileNotFoundException.class)
    public void testIsUsed() throws Exception {
        assertNotNull(FileUtils.isUsed("abc.txt"));

    }

    @Test
    public void testGetBaseFileName() throws Exception {
        assertNotNull(FileUtils.getBaseFileName(new File("aaa.txt")));
        assertNotNull(FileUtils.getBaseFileName(new File("aaa")));

    }

    @Test
    public void testFixPath() throws Exception {
        assertNull(FileUtils.fixPath(null));
        assertNotNull(FileUtils.fixPath("/abc"));
        assertNotNull(FileUtils.fixPath("/abc/"));
        assertNotNull(FileUtils.fixPath("abc"));
        assertNotNull(FileUtils.fixPath("abc/"));

    }

    @Test
    public void testGetFiendlyPath() throws Exception {
        assertNull(FileUtils.getFriendlyPath(null));
        assertNotNull(FileUtils.getFriendlyPath("/abc"));
        assertNotNull(FileUtils.getFriendlyPath("/abc/"));
        assertNotNull(FileUtils.getFriendlyPath("abc"));
        assertNotNull(FileUtils.getFriendlyPath("abc/"));

    }
}
