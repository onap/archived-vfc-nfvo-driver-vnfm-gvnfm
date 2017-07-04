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

package org.openo.nfvo.jujuvnfmadapter.service.juju;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Aug 22, 2016
 */
public class PyObjectFactory {

    private static PyObject environmentClass;

    private static PyObject environmentObj;

    /**
     * <br>
     * 
     * @param envName
     * @return
     * @since NFVO 0.5
     */
    public static PyObjectFactory build(String envName) {
        PythonInterpreter interpreter = new PythonInterpreter();
        
        interpreter.exec("from jujuclient import Environment");
        
        environmentClass = interpreter.get("Environment");
        PyObject env = environmentClass.__call__();
        environmentObj = env.invoke("connect", new PyString(envName));
        return new PyObjectFactory();
    }

    /**
     * <br>
     * 
     * @param methodName
     * @return
     * @since NFVO 0.5
     */
    public PyObject execute(String methodName) {
        environmentObj.toString();
        return environmentObj.invoke(methodName);
    }

    /**
     * <br>
     * 
     * @param methodName
     * @param args
     * @return
     * @since NFVO 0.5
     */
    public PyObject execute(String methodName, PyObject args) {
        return environmentObj.invoke(methodName, args);
    }

    /**
     * <br>
     * 
     * @param methodName
     * @param arg1
     * @param arg2
     * @return
     * @since NFVO 0.5
     */
    public PyObject execute(String methodName, PyObject arg1, PyObject arg2) {
        return environmentObj.invoke(methodName, arg1, arg2);
    }

    /**
     * <br>
     * 
     * @param methodName
     * @param args
     * @return
     * @since NFVO 0.5
     */
    public PyObject execute(String methodName, PyObject[] args) {
        return environmentObj.invoke(methodName, args);
    }

}
