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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.VnfmOpResult;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.VnfmOpResult.TaskStatus;

public class VnfmOpResultTest {

    @Test
    public void testVnfmOpResult() {
        VnfmOpResult result = new VnfmOpResult();
        assertEquals(TaskStatus.INIT, result.getOperateStatus());
        assertEquals("", result.getErrorMessage());
    }

    @Test
    public void testVnfmOpResult1() {
        VnfmOpResult result = new VnfmOpResult(TaskStatus.SUCCESS, "success");
        assertEquals(TaskStatus.SUCCESS, result.getOperateStatus());
        assertEquals("success", result.getErrorMessage());
    }

    @Test
    public void testSotOperateStatus() {
        VnfmOpResult result = new VnfmOpResult();
        result.setOperateStatus(TaskStatus.SUCCESS);
        assertEquals(TaskStatus.SUCCESS, result.getOperateStatus());
    }

    @Test
    public void testSotOperateStatusByNull() {
        VnfmOpResult result = new VnfmOpResult();
        result.setOperateStatus(null);
        assertNull(result.getOperateStatus());
    }

    @Test
    public void testSotErrorMessage() {
        VnfmOpResult result = new VnfmOpResult();
        result.setErrorMessage("Fail!");
        assertEquals("Fail!", result.getErrorMessage());
    }

    @Test
    public void testSotErrorMessageByNull() {
        VnfmOpResult result = new VnfmOpResult();
        result.setErrorMessage(null);
        assertNull(result.getErrorMessage());
    }

    @Test
    public void testAddResult() {
        VnfmOpResult result = new VnfmOpResult();
        result.addResult("Result");
        List<String> arr = new ArrayList<String>();
        arr.add("Result");
        assertEquals(arr, result.getResult());
    }

    @Test
    public void testAddResultByList() {
        VnfmOpResult result = new VnfmOpResult();
        List<String> arr = new ArrayList<String>();
        arr.add("Result");
        arr.add("Test");
        result.addResult(arr);
        assertEquals(arr, result.getResult());
    }

    @Test
    public void testAddResultByNull() {
        VnfmOpResult result = new VnfmOpResult();
        List<String> arr = new ArrayList<String>();
        arr.add(null);
        result.addResult(null);
        assertEquals(arr, result.getResult());
    }

    @Test
    public void testToString() {
        VnfmOpResult result = new VnfmOpResult();
        assertEquals(
                "org.openo.nfvo.jujuvnfmadapter.service.entity.VnfmOpResult@[operateStatus=INIT, errorCode=0, errorMessage=]",
                result.toString());
    }

    @Test
    public void testToString1() {
        VnfmOpResult result = new VnfmOpResult(TaskStatus.SUCCESS, "success");
        assertEquals(
                "org.openo.nfvo.jujuvnfmadapter.service.entity.VnfmOpResult@[operateStatus=SUCCESS, errorCode=0, errorMessage=success]",
                result.toString());
    }

    @Test
    public void testTaskStatus() {
        VnfmOpResult result=new VnfmOpResult();
        assertEquals(TaskStatus.INIT, TaskStatus.valueOf("INIT"));
        assertEquals(TaskStatus.SUCCESS, TaskStatus.valueOf("SUCCESS"));
        assertEquals(TaskStatus.PART_SUCCESS, TaskStatus.valueOf("PART_SUCCESS"));
        assertEquals(TaskStatus.RUNNING, TaskStatus.valueOf("RUNNING"));
        assertEquals(TaskStatus.TIMEOUT, TaskStatus.valueOf("TIMEOUT"));
        assertEquals(TaskStatus.FAIL, TaskStatus.valueOf("FAIL"));

    }

}
