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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.VNFJsonUtil;

import mockit.Mock;
import mockit.MockUp;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
public class VNFJsonUtilTest {

    @Test
    public void testGetJsonFieldStr() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", "testName");
        String result = VNFJsonUtil.getJsonFieldStr(VNFJsonObj, "name");
        assertEquals("testName", result);
    }

    @Test
    public void testGetJsonFieldStrByNull() {
        String result = VNFJsonUtil.getJsonFieldStr(null, "name");
        assertEquals("", result);
    }

    @Test
    public void testGetJsonFieldStrByFieldNameNull() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("names", "testName");
        String result = VNFJsonUtil.getJsonFieldStr(VNFJsonObj, "name");
        assertEquals("", result);
    }

    @Test
    public void testGetJsonFieldStrByNameNull() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", "null");
        String result = VNFJsonUtil.getJsonFieldStr(VNFJsonObj, "name");
        assertEquals("", result);
    }

    @Test
    public void testGetJsonFieldInt() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", 5);
        Integer result = VNFJsonUtil.getJsonFieldInt(VNFJsonObj, "name");
        assertEquals((Integer)5, result);
    }

    @Test
    public void testGetJsonFieldIntByNull() {
        Integer result = VNFJsonUtil.getJsonFieldInt(null, "name");
        assertEquals((Integer)0, result);
    }

    @Test
    public void testGetJsonFieldIntByNameNull() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", 5);
        Integer result = VNFJsonUtil.getJsonFieldInt(VNFJsonObj, "names");
        assertEquals((Integer)0, result);
    }

    @Test
    public void testGetJsonFieldLong() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", 5L);
        Long result = VNFJsonUtil.getJsonFieldLong(VNFJsonObj, "name");
        assertEquals((Long)5L, result);
    }

    @Test
    public void testGetJsonFieldLongByNull() {
        Long result = VNFJsonUtil.getJsonFieldLong(null, "name");
        assertEquals((Long)0L, result);
    }

    @Test
    public void testGetJsonFieldLongByNameNull() {
        JSONObject VNFJsonObj = new JSONObject();
        VNFJsonObj.put("name", 5L);
        Long result = VNFJsonUtil.getJsonFieldLong(VNFJsonObj, "names");
        assertEquals((Long)0L, result);
    }

    @Test
    public void testParseErrorInfo() {
        String errorInfo = "{\"error\":{\"message\":\"errorMessage\"}}";
        String result = VNFJsonUtil.parseErrorInfo(errorInfo);
        assertEquals("errorMessage", result);
    }

    @Test
    public void testParseErrorInfoByNull() {
        String result = VNFJsonUtil.parseErrorInfo(null);
        assertEquals("System Error!", result);
    }

    @Test
    public void testParseErrorInfoByEmpty() {
        String errorInfo = "";
        String result = VNFJsonUtil.parseErrorInfo(errorInfo);
        assertEquals("System Error!", result);
    }

    @Test
    public void testParseErrorInfoByNoError() {
        String errorInfo = "{\"errors\":{\"message\":\"errorMessage\"}}";
        String result = VNFJsonUtil.parseErrorInfo(errorInfo);
        assertEquals("System Error!", result);
    }

    @Test
    public void testParseErrorInfoByNoMessage() {
        String errorInfo = "{\"error\":{\"messages\":\"errorMessage\"}}";
        String result = VNFJsonUtil.parseErrorInfo(errorInfo);
        assertEquals("System Error!", result);
    }

    @Test
    public void testObjectToJsonStr() {
        String data = "{\"vnf1\":{\"id\":\"id\"}}";
        String result = VNFJsonUtil.objectToJsonStr(data);
        assertEquals(data, result);
    }

    @Test
    public void testObjectToJson() {
        String data = "{\"vnf1\":{\"id\":\"id\"}}";
        JSONObject result = VNFJsonUtil.objectToJson(data);
        assertEquals(JSONObject.fromObject(data), result);
    }
    @Test
    public void marshalTest() throws IOException{
        net.sf.json.JSON json = new JSONObject();
        String res = VNFJsonUtil.marshal(json);
        assertTrue(res.equals("{}"));
    }

    @Test
    public void marshalTestException() throws IOException{
        String str="test";
        String res = VNFJsonUtil.marshal(str);
        assertTrue(res != null);
    }

    @Test
    public void VNFJsonToListsTest(){
        List<TestPojo> pojoList = VNFJsonUtil.vnfJsonToLists("[{\"name\":\"test\",\"id\":\"123\"}]",TestPojo.class);
        assertTrue(pojoList.size()==1);
    }

    @Test
    public void VNFJsonToListTest(){
        List<TestPojo> pojoList = VNFJsonUtil.vnfJsonToList("[{\"name\":\"test\",\"id\":\"123\"}]", TestPojo.class, "test");
        assertTrue(pojoList.size()==1);
    }

    @Test
    public void VNFJsonToObjectsTest(){
        TestPojo pojo = VNFJsonUtil.vnfJsonToObjects("{\"name\":\"test\",\"id\":\"123\"}", TestPojo.class);
        assertTrue("test".equals(pojo.getName()) && "123".equals(pojo.getId()));
    }

    @Test(expected = Exception.class)
    public void VNFJsonToObjectsTestException(){
        JsonConfig VNFJsonConfig = new JsonConfig();
        TestPojo pojo = VNFJsonUtil.vnfJsonToObjects("{\"id\":\"123\",\"name\":\"test\"}", VNFJsonConfig);
    }

    @Test
    public void objectToJsonTest(){
        JsonConfig VNFJsonConfig = new JsonConfig();
        TestPojo pojo = new TestPojo();
        pojo.setName("test");
        pojo.setId("123");
        String res = VNFJsonUtil.objectToJson(pojo, VNFJsonConfig);
        assertTrue("{\"id\":\"123\",\"name\":\"test\"}".equals(res));
    }

    @Test
    public void objectToJsonTest2(){
        TestPojo pojo = new TestPojo();
        pojo.setName("test");
        pojo.setId("123");
        String res = VNFJsonUtil.objectToJson(pojo, "");
        assertTrue("{\"id\":\"123\",\"name\":\"test\"}".equals(res));
    }

    @Test
    public void listToJsonTest(){
        List<TestPojo> pojoList = new ArrayList<TestPojo>();
        TestPojo pojo = new TestPojo();
        pojo.setName("test");
        pojo.setId("123");
        pojoList.add(pojo);
        String res = VNFJsonUtil.listToJson(pojoList);
        assertTrue("[{\"id\":\"123\",\"name\":\"test\"}]".equals(res));
    }

    @Test
    public void listToJsonTest2(){
        List<TestPojo> pojoList = new ArrayList<TestPojo>();
        TestPojo pojo = new TestPojo();
        pojo.setName("test");
        pojo.setId("123");
        pojoList.add(pojo);
        String res = VNFJsonUtil.listToJson(pojoList,"");
        assertTrue("[{\"id\":\"123\",\"name\":\"test\"}]".equals(res));
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = VNFJsonUtil.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


}
