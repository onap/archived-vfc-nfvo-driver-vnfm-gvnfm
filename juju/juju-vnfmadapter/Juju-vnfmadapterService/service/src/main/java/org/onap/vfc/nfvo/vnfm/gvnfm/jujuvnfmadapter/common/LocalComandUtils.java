/**
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For execute local command
 * (support control overtime)
 * <br/>
 * <p>
 * </p>
 * 
 * @author quanzhong@huawei.com
 * @version NFVO 0.5 Sep 19, 2016
 */
public class LocalComandUtils {
    private static final Logger log = LoggerFactory.getLogger(LocalComandUtils.class);

    private LocalComandUtils(){
        
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
     * @param timeout  millis
     * @return response msg
     * @since NFVO 0.5
     */
    public static ExeRes execute(String dir, List<String> command,long timeout) {
        ExeRes er = new ExeRes();
        StringBuilder sb = new StringBuilder();
        try {
            if(SwitchController.isDebugModel()) {
                command.set(0, "juju.bat");
            }
            ProcessBuilder pb = new ProcessBuilder(command);
            if(StringUtils.isNotBlank(dir)) {
                pb.directory(new File(dir));
            }
            pb.redirectErrorStream(true);
            Process p = pb.start();

            // wait the process result
            buildProcessResult(er, p, timeout);

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
            log.error("execute the command failed:{}", command, e);
        }
        return er;
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
        return execute(dir,command,Constant.PROCESS_WAIT_MILLIS);
    }

    /**
     * <br/>
     * 
     * @param er
     * @param p
     * @param timeout millis
     * @throws TimeoutException
     * @throws InterruptedException
     * @since NFVO 0.5
     */
    private static void buildProcessResult(ExeRes er, Process p,long timeout) throws TimeoutException, InterruptedException {
        Worker worker = new Worker(p);
        worker.start();
        try {
            worker.join(timeout);
            if(worker.exitValue != null) {
                int exit = worker.exitValue;
                if(exit != 0) {
                    er.setCode(ExeRes.FAILURE);
                    log.warn("the process exit non-normal");
                } else {
                    er.setCode(ExeRes.SUCCESS);
                }
            } else {
                er.setCode(ExeRes.FAILURE);
                log.warn("the process execute timeout.");
                throw new TimeoutException();
            }
        } catch(InterruptedException e) {
            worker.interrupt();
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    /**
     * 
     * <br/>
     * <p>
     * </p>
     * 
     * @author		quanzhong@huawei.com
     * @version     NFVO 0.5  Sep 19, 2016
     */
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
            } catch(Exception e) {
		log.error("process.waitFor(): ",e);
                return;
            }
        }

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
                log.error("to string error:", e);
                return "code:" + this.getCode() + ",body:" + this.getBody();
            }
        }

    }
}
