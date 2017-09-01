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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Vnfm operation result class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 10, 2016
 */
public class VnfmOpResult {

    private String errorMessage;

    /**
     * 
     * Task status enumerator.<br>
     * <p>
     * </p>
     * 
     * @author
     * @version     NFVO 0.5  Sep 10, 2016
     */
    public enum TaskStatus {
        INIT, SUCCESS, FAIL, PART_SUCCESS, RUNNING, TIMEOUT
    }

    private TaskStatus operateStatus;

    private List<Object> results = new ArrayList<>(10);

    private int errorCode;

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since  NFVO 0.5
     */
    public VnfmOpResult() {
        operateStatus = TaskStatus.INIT;
        errorMessage = "";
        errorCode = 0;
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param operateStatus
     * @param errorMessage
     * @since  NFVO 0.5
     */
    public VnfmOpResult(TaskStatus operateStatus, String errorMessage) {
        this.operateStatus = operateStatus;
        this.errorMessage = errorMessage;
        errorCode = 0;
    }

    public void setOperateStatus(TaskStatus operateStatus) {
        this.operateStatus = operateStatus;
    }

    public TaskStatus getOperateStatus() {
        return operateStatus;
    }

    public List<Object> getResult() {
        return results;
    }

    /**
     * 
     * Add Result.<br>
     * 
     * @param result
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public void addResult(Object result) {
        if(result instanceof List<?>) {
            this.results.addAll((List<Object>)result);
        } else {
            this.results.add(result);
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@[" + "operateStatus=" + operateStatus + ", " + "errorCode=" + errorCode + ", " +
                "errorMessage=" + errorMessage + "]";
    }
}
