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
package org.openo.nfvo.jujuvnfmadapter.common;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.openo.nfvo.jujuvnfmadapter.common.EntityUtils.ExeRes;

import net.sf.json.JSONObject;

public class EntityUtilsTest {

    @Test
    public void formatCommandTest(){
        List<String> command = new ArrayList<>();
        command.add("test");
        String result =  EntityUtils.formatCommand(command);
        assertTrue(result.contains("test"));
    }
    @Test
    public void formatCommandTestNull(){
        List<String> command = null;
        String result =  EntityUtils.formatCommand(command);
        assertTrue(result.equals(""));
    }
    @Test
    public void executeTest(){
        ExeRes res = EntityUtils.execute("test", "test","test2");
        assertNotNull(res);
    }
    @Test(expected = Exception.class)
    public void toEntity() throws Exception{
        JSONObject jsonObject = new JSONObject();
        HashMap map = EntityUtils.toEntity(jsonObject, HashMap.class);
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = EntityUtils.class.getDeclaredConstructor();
        assertTrue("Constructor private", Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
