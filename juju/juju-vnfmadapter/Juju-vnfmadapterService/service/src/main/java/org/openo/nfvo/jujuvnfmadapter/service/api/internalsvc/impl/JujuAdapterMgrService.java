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

package org.openo.nfvo.jujuvnfmadapter.service.api.internalsvc.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.openo.baseservice.util.impl.SystemEnvVariablesFactory;
import org.openo.nfvo.jujuvnfmadapter.service.adapter.impl.JujuAdapter2MSBManager;
import org.openo.nfvo.jujuvnfmadapter.service.adapter.inf.IJujuAdapter2MSBManager;
import org.openo.nfvo.jujuvnfmadapter.service.api.internalsvc.inf.IJujuAdapterMgrService;
import org.openo.nfvo.jujuvnfmadapter.service.constant.Constant;
import org.openo.nfvo.jujuvnfmadapter.service.constant.UrlConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * Juju adapter manager service class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version NFVO 0.5 Sep 12, 2016
 */
public class JujuAdapterMgrService implements IJujuAdapterMgrService {

    private static final Logger LOG = LoggerFactory.getLogger(JujuAdapterMgrService.class);

    @Override
    public void register() {
        // set BUS URL and mothedtype
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("url", UrlConstant.REST_MSB_REGISTER);
        paramsMap.put("methodType", Constant.POST);

        // get juju adapter info and raise registration
        try {
            String adapterInfo = readJujuAdapterInfoFromJson();
            if(!"".equals(adapterInfo)) {
                JSONObject adapterObject = JSONObject.fromObject(adapterInfo);
                RegisterJujuAdapterThread jujuAdapterThread = new RegisterJujuAdapterThread(paramsMap, adapterObject);
                Executors.newSingleThreadExecutor().submit(jujuAdapterThread);
            } else {
                LOG.error("JujuVnfmAdapter info is null,please check!", JujuAdapterMgrService.class);
            }

        } catch(IOException e) {
            LOG.error("Failed to read JujuVnfmAdapter info!" + e, JujuAdapterMgrService.class);
        }

    }

    /**
     * Read juju adapter information from Json.<br>
     * 
     * @return
     * @throws IOException
     * @since NFVO 0.5
     */
    public static String readJujuAdapterInfoFromJson() throws IOException {
        InputStream ins = null;
        BufferedInputStream bins = null;
        String fileContent = "";

        String fileName = SystemEnvVariablesFactory.getInstance().getAppRoot()
                + System.getProperty(Constant.FILE_SEPARATOR) + "etc" + System.getProperty(Constant.FILE_SEPARATOR)
                + "adapterInfo" + System.getProperty(Constant.FILE_SEPARATOR) + Constant.JUJUADAPTERINFO;

        try {
            ins = new FileInputStream(fileName);
            bins = new BufferedInputStream(ins);

            byte[] contentByte = new byte[ins.available()];
            int num = bins.read(contentByte);

            if(num > 0) {
                fileContent = new String(contentByte);
            }
        } catch(FileNotFoundException e) {
            LOG.error(fileName + "is not found!", e, JujuAdapterMgrService.class);
        } finally {
            if(null != ins) {
                ins.close();
            }

            if(null != bins) {
                bins.close();
            }
        }

        return fileContent;
    }

    private static class RegisterJujuAdapterThread implements Runnable {

        // Thread lock Object
        private final Object lockObject = new Object();

        private IJujuAdapter2MSBManager adapter2MSBMgr = new JujuAdapter2MSBManager();

        // url and mothedtype
        private Map<String, String> paramsMap;

        // driver body
        private JSONObject adapterInfo;

        public RegisterJujuAdapterThread(Map<String, String> paramsMap, JSONObject adapterInfo) {
            this.paramsMap = paramsMap;
            this.adapterInfo = adapterInfo;
        }

        @Override
        public void run() {
            LOG.info("start register jujuvnfmadapter", RegisterJujuAdapterThread.class);

            if(paramsMap == null || adapterInfo == null) {
                LOG.error("parameter is null,please check!", RegisterJujuAdapterThread.class);
                return;
            }

            // catch Runtime Exception
            try {
                sendRequest(paramsMap, adapterInfo);
            } catch(RuntimeException e) {
                LOG.error(e.getMessage(), e);
            }

        }

        private void sendRequest(Map<String, String> paramsMap, JSONObject driverInfo) {
            JSONObject resultObj = adapter2MSBMgr.registerJujuAdapter(paramsMap, driverInfo);

            if(Integer.valueOf(resultObj.get("retCode").toString()) == Constant.HTTP_CREATED) {
                LOG.info("JujuVnfmAdapter has now Successfully Registered to the Microservice BUS!",
                        JujuAdapterMgrService.class);
            } else {
                LOG.error("JujuVnfmAdapter failed to  Register to the Microservice BUS! Reason:"
                        + resultObj.get("reason").toString() + " retCode:" + resultObj.get("retCode").toString());

                // if registration fails,wait one minute and try again
                try {
                    synchronized(lockObject) {
                        lockObject.wait(Constant.REPEAT_REG_TIME);
                    }
                } catch(InterruptedException e) {
                    LOG.error("sendRequest error", e);
                }

                sendRequest(this.paramsMap, this.adapterInfo);
            }

        }

    }

    @Override
    public void unregister() {
        // Not implemented

    }

    /**
     * Main method.<br>
     * 
     * @param args
     * @since NFVO 0.5
     */
    public static void main(String[] args) {
        LOG.info(SystemEnvVariablesFactory.getInstance().getAppRoot() + System.getProperty(Constant.FILE_SEPARATOR)
                + "etc" + System.getProperty(Constant.FILE_SEPARATOR) + "adapterInfo"
                + System.getProperty(Constant.FILE_SEPARATOR) + Constant.JUJUADAPTERINFO);
    }

}
