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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Aug 30, 2016
 */
public class EntityUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EntityUtils.class);

    public static final String RESULT_CODE_KEY = "retCode";

    public static final String MSG_KEY = "msg";

    public static final String DATA_KEY = "data";

    public static final String STATUS = "status";

    /**
     * Constructor<br/>
     * <p>
     * </p>
     */
    private EntityUtils() {
        // Empty Constructor
    }

    /**
     * <br>
     * 
     * @param jsonObject
     * @param clazz
     * @return T
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T toEntity(JSONObject jsonObject, Class<?> clazz) throws Exception { // NOSONAR
        T instance = (T)clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(instance, jsonObject.get(fields[i].getName()));
        }
        return instance;
    }

    /**
     * format the obj to str style as json format.
     * <br/>
     * 
     * @param obj
     * @param clazz
     * @return
     * @since NFVO 0.5
     */
    public static String toString(Object obj, Class<?> clazz) {
        JSONObject jsonObj = new JSONObject();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                jsonObj.put(fields[i].getName(), obj != null ? fields[i].get(obj) : "");
            }
        } catch(Exception e) {
            LOG.error("to string error:", e);
        }
        return jsonObj.toString();
    }

    /**
     * <br>
     * <p>
     * </p>
     * 
     * @author
     */
    public static class ExeRes {

        public static final int SUCCESS = 0;

        public static final int FAILURE = -1;

        private int code;

        private String body;

        /**
         * @return Returns the code.
         */
        public int getCode() {
            return code;
        }

        /**
         * @param code The code to set.
         */
        public void setCode(int code) {
            this.code = code;
        }

        /**
         * @return Returns the body.
         */
        public String getBody() {
            return body;
        }

        /**
         * @param body The body to set.
         */
        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            try {
                return EntityUtils.toString(this, this.getClass());
            } catch(Exception e) {
                LOG.error("to string error:", e);
                return "code:" + this.getCode() + ",body:" + this.getBody();
            }
        }

    }

    /**
     * <br>
     * 
     * @param command
     * @return String
     */
    public static String formatCommand(List<String> command) {
        StringBuilder builder = new StringBuilder();
        if(command != null) {
            for(String cmd : command) {
                builder.append(cmd).append(" "); // NOSONAR
            }
        }
        return builder.toString();

    }

    /**
     * <br>
     * 
     * @param dir
     * @param command
     * @return
     */
    public static ExeRes execute(String dir, String... command) {
        List<String> commands = new ArrayList<>(command.length);
        for(String arg : command) {
            commands.add(arg);
        }
        return execute(dir, commands);

    }

    /**
     * execute local command
     * <br/>
     * 
     * @param dir the command path
     * @param command
     * @return response msg
     * @since NFVO 0.5
     */
    public static ExeRes execute(String dir, List<String> command) {
        ExeRes er = new ExeRes();
        StringBuilder sb = new StringBuilder();
        try {
            if(SwitchController.isDebugModel()) {
                String resContent = new String(FileUtils.readFile(new File(JujuConfigUtil.getValue("juju_cmd_res_file")), "UTF-8"));
                er.setBody(resContent);
                return er;
            }
            ProcessBuilder pb = new ProcessBuilder(command);
            if(StringUtils.isNotBlank(dir)) {
                pb.directory(new File(dir));
            }
            pb.redirectErrorStream(true);
            Process p = pb.start();

            // wait the process result
            buildProcessResult(er, p);

            InputStream in = p.getInputStream();
            byte[] buffer = new byte[1024];
            int length;
            while((length = in.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, length));
            }
            in.close();
            er.setBody(sb.toString());
        } catch(Exception e) {
            er.setCode(ExeRes.FAILURE);
            er.setBody(e.getMessage());
            LOG.error("execute the command failed:{}", command, e);
        }
        return er;
    }

    /**
     * <br/>
     * 
     * @param er
     * @param p
     * @throws TimeoutException
     * @throws InterruptedException
     * @since NFVO 0.5
     */
    private static void buildProcessResult(ExeRes er, Process p) throws TimeoutException, InterruptedException {
        Worker worker = new Worker(p);
        worker.start();
        try {
            worker.join(Constant.PROCESS_WAIT_MILLIS);
            if(worker.exitValue != null) {
                int exit = worker.exitValue;
                if(exit != 0) {
                    er.setCode(ExeRes.FAILURE);
                    LOG.warn("the process exit non-normal");
                } else {
                    er.setCode(ExeRes.SUCCESS);
                }
            } else {
                er.setCode(ExeRes.FAILURE);
                LOG.warn("the process execute timeout.");
                throw new TimeoutException();
            }
        } catch(InterruptedException e) {
            worker.interrupt();
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    private static class Worker extends Thread {

        private final Process process;

        private Integer exitValue;

        private Worker(Process process) {
            this.process = process;
        }

        /**
         * <br/>
         * 
         * @since NFVO 0.5
         */
        @Override
        public void run() {
            try {
                exitValue = process.waitFor();
            } catch(InterruptedException e) {
                return;
            }
        }

    }
}
