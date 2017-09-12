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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.EntityUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.FileUtils;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.JujuConfigUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.YamlUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.EntityUtils.ExeRes;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.servicetoken.VnfmRestfulUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.adapter.inf.IJujuClientManager;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.UrlConstant;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.juju.JujuHelper;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.RestfulResponse;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.restclient.SystemEnvVariablesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSON;
import net.sf.json.JSONObject;


/**
 * <br/>
 * <p>
 * </p>
 * 
 * @author        
 * @version     NFVO 0.5  Sep 7, 2016
 */
public class JujuClientManager implements IJujuClientManager {
    private static final Logger LOG = LoggerFactory.getLogger(JujuClientManager.class);
    
    public static final String ADDRESOURCE="addResource";
    public static final String REMOVERESOURCE = "removeResource";

    /**
     * <br/>
     * 
     * @param charmPath
     * @param appName
     * @return
     * @since   NFVO 0.5
     */
    @Override
    public JSONObject deploy(String charmPath, String appName) {
        JSONObject result = new JSONObject();
        if(charmPath == null || appName == null){
            String msg = "the 'charmPath' or 'appName' can not be null";
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, msg);
            LOG.error(msg);
            return result;
        }
        String modelName = JujuHelper.getModelName(appName);
        //add-model
        this.addModel(modelName);//use appName as modelName
        //switch model
        this.switchModel(modelName);
        //deploy service
        List<String> commands = new ArrayList<>();
        commands.add("juju");
        commands.add("deploy");
        if(StringUtils.isNotBlank(charmPath)){
            String fullPath = charmPath+appName;
            commands.add(fullPath);
        }else{
            commands.add(appName);
        }
        commands.add("-m"); 
        commands.add(modelName);
        ExeRes exeRes = EntityUtils.execute(charmPath,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("deploy success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            result.put(EntityUtils.DATA_KEY, exeRes.getBody());
        }else{
            LOG.error("deploy failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "deploy failed:"+exeRes.getBody());
        }
        
        return result;
    }
    
    /**
     * 
     * <br/>
     * 
     * @param modelName
     * @return
     * @since  NFVO 0.5
     */
    private JSONObject addModel(String modelName) {
        JSONObject result = new JSONObject();
        List<String> commands = new ArrayList<>();
        commands.add("juju");
        commands.add("add-model");
        commands.add(modelName);
        getExtraParam(commands);
        ExeRes exeRes = EntityUtils.execute(null,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("addModel success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            result.put(EntityUtils.DATA_KEY, exeRes.getBody());
        }else{
            LOG.error("addModel failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "addModel failed:"+exeRes.getBody());
        }
        
        return result;
    }

    /**
     * getExtraParam
     * add model
        juju add-model <model-name> --config image-metadata-url=http://192.168.20.106/images --config network=demo-net --config use-floating-ip=True --config use-default-secgroup=True
     * @return
     */
    private void getExtraParam(List<String> commands){
        try {
            String configInfo = readJujuConfigInfo();
            if(configInfo != null){
                JSONObject json = JSONObject.fromObject(configInfo);
                commands.add("--config");
                commands.add("image-metadata-url="+json.getString("image-metadata-url"));
                commands.add("--config");
                commands.add("network="+json.getString("network"));
                commands.add("--config");
                commands.add("use-floating-ip="+json.getString("use-floating-ip"));
                commands.add("--config");
                commands.add("use-default-secgroup="+json.getString("use-default-secgroup"));
            }
        } catch (Exception e) {
            LOG.error("read juju command config error:",e);
        }
    }

    /**
     * Get csar package information.<br>
     *
     * @return
     * @throws IOException
     * @since  NFVO 0.5
     */
    public static String readJujuConfigInfo() {
        InputStream ins = null;

        BufferedInputStream bins = null;
        String fileContent = null;
        String fileName = SystemEnvVariablesFactory.getInstance().getAppRoot() + System.getProperty("file.separator")
                + "etc" + System.getProperty("file.separator") + "conf" + System.getProperty("file.separator")
                + "juju_conf.json";
        try {
            ins = new FileInputStream(fileName);
            bins = new BufferedInputStream(ins);

            byte[] contentByte = new byte[ins.available()];
            int num = bins.read(contentByte);

            if (num > 0) {
                fileContent = new String(contentByte);
            }
        } catch (Exception e) {
            LOG.error(fileName + "is not found!", e);
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (bins != null) {
                    bins.close();
                }
            } catch (IOException e) {
            }
        }
        return fileContent;
    }
    private JSONObject changeDir(String charmPath) {
        JSONObject result = new JSONObject();
        List<String> commands = new ArrayList<>();
        commands.add("cd");
        commands.add(charmPath);
        ExeRes exeRes = EntityUtils.execute(null,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("changeDir success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            result.put(EntityUtils.DATA_KEY, exeRes.getBody());
        }else{
            LOG.error("changeDir failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "addModel failed:"+exeRes.getBody());
        }
        
        return result;
    }
    
    /**
     * 
     * <br/>
     * 
     * @param modelName
     * @return
     * @since  NFVO 0.5
     */
    private JSONObject switchModel(String modelName) {
        JSONObject result = new JSONObject();
        List<String> commands = new ArrayList<>();
        commands.add("juju");
        commands.add("switch");
        commands.add(modelName);
        ExeRes exeRes = EntityUtils.execute(null,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("switchModel success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            result.put(EntityUtils.DATA_KEY, exeRes.getBody());
        }else{
            LOG.error("switchModel failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "addModel failed:"+exeRes.getBody());
        }
        
        return result;
    }

    /**
     * <br/>
     * 
     * @param appName
     * @return
     * @since   NFVO 0.5
     */
    @Override
    public JSONObject destroy(String appName) {
        String modelName = JujuHelper.getModelName(appName);
        JSONObject result = new JSONObject();
        List<String> commands = new ArrayList<>();
        commands.add("juju");
        commands.add("destroy-model");
        commands.add("-y");
        commands.add(modelName);
        
        ExeRes exeRes = EntityUtils.execute(null,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("remove success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            result.put(EntityUtils.DATA_KEY, exeRes.getBody());
        }else{
            LOG.error("remove failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "remove failed:"+exeRes.getBody());
        }
        
        return result;
       
    }

    /**
     * <br/>
     * 
     * @param appName
     * @return
     * @since   NFVO 0.5
     */
    @Override
    public JSONObject getStatus(String appName) {
        String modelName = JujuHelper.getModelName(appName);
        JSONObject result = new JSONObject();
        List<String> commands = new ArrayList<>();
        commands.add("juju");
        commands.add("status");
        if(StringUtils.isNotBlank(modelName)){
            commands.add("-m"); 
            commands.add(modelName);
        }
        commands.add("--format=json");
        
        ExeRes exeRes = EntityUtils.execute(null,commands);
        if(exeRes.getCode() == ExeRes.SUCCESS){
            LOG.info("getStatus success. command:"+EntityUtils.formatCommand(commands));
            result.put(EntityUtils.RESULT_CODE_KEY, 0);
            JSONObject dataObj = buildDataObj(exeRes);
            result.put(EntityUtils.DATA_KEY, dataObj);
        }else{
            LOG.error("getStatus failed. command:"+EntityUtils.formatCommand(commands)+"\n"+exeRes);
            result.put(EntityUtils.RESULT_CODE_KEY, -1);
            result.put(EntityUtils.MSG_KEY, "getStatus failed:"+exeRes.getBody());
        }
        
        return result;
    }

    /**
     * <br/>
     * 
     * @param exeRes
     * @return
     * @since  NFVO 0.5
     */
    private JSONObject buildDataObj(ExeRes exeRes) {
        JSONObject dataObj = null;
        if(StringUtils.isNotBlank(exeRes.getBody())){
            dataObj = JSONObject.fromObject(exeRes.getBody());
            //according to appName to select
        }
        return dataObj;
    }
    
    
    /**
     * call the juju vnfm to grant resource(disk,mem,cpu)
     * <br/>
     *  (fields:cpu,mem,disk,action(addResource/removeResource))
     * @param vnfId
     * @param appName
     * @param action
     * @since  NFVO 0.5
     * @return
     */
    @Override
    public boolean grantResource(String charmPath, String appName,String action , String vnfId){
        //1.parse yaml file
        JSONObject params;
        try {
            params = this.parseYaml(charmPath, appName, action);
        } catch(Exception e) {
            LOG.error("ParseYaml error,please check it.",e);
            return false;
        }
        if(params == null){
            LOG.error("ParseYaml fail,please check it.");
            return false;
        }
        
        //2. call grant service
        String url = JujuConfigUtil.getValue("grant_jujuvnfm_url");
        Map<String, String> paramsMap = new HashMap<>(6);
        paramsMap.put("url", url);
        paramsMap.put(Constant.METHOD_TYPE, Constant.PUT);
        paramsMap.put("path", String.format(UrlConstant.REST_JUJU_VNFM_GRANT_URL,vnfId));
        paramsMap.put(Constant.AUTH_MODE, Constant.AuthenticationMode.ANONYMOUS);
        RestfulResponse rsp = VnfmRestfulUtil.getRemoteResponse(paramsMap, params.toString(), null);
        if(rsp == null || rsp.getStatus() != Constant.HTTP_OK) {
            LOG.error("function=grantResource, msg=send grantResource msg to juju-vnfm get wrong results");
            return false;
        }
        //3.process result
        String response = rsp.getResponseContent();
        LOG.info("grant resource result:"+response);
        return true;
    }
    
    /**
     * 
     * <br/>
     * "constraints":"arch=amd64 cpu-cores=1 cpu-power=100 mem=1740 root-disk=8192"
     * @param charmPath
     * @param appName
     * @param action
     * @return
     * @since  NFVO 0.5
     */
    public JSONObject parseYaml(String charmPath, String appName,String action){
        JSONObject compute = new JSONObject();
        compute.put("action", action);
        if(StringUtils.isBlank(charmPath)){
            LOG.error("the charmPath can't be null! [in unzipFileAndParseYaml]");
            return null;
        }
      //set default values for non 'yaml' type
        if(!appName.endsWith(".yaml")){
            compute.put("cpu", 4);
            compute.put("mem", 2);
            compute.put("disk", 40);
            return compute;
        }
        String yaml = FileUtils.getFriendlyPath(charmPath)+appName;
        File yamlFile = new File(yaml);
        if(yamlFile.exists()){
            JSON json = YamlUtil.yamlToJson(yamlFile.getAbsolutePath());
            LOG.info(yaml+":\n"+json);
            if(json.isArray()){
                LOG.error("the yaml file has error format,please check it!"+yamlFile);
                return null;
            }
            JSONObject jsonObj = (JSONObject)json;
            JSONObject services = jsonObj.getJSONObject("services");
            int cpu = 0;
            int mem = 0;
            int disk = 0;
            for(Object key: services.keySet()){
                JSONObject app = services.getJSONObject(key.toString());
                if(app.containsKey("constraints")){
                    String constraints = app.getString("constraints");
                    String[] vals = constraints.split("\\s+");
                    LOG.info(key+"="+constraints);
                    for(String val : vals){
                        String[] kv = val.split("=");
                        if("cpu-cores".equals(kv[0]) && StringUtils.isNotBlank(kv[1])){
                            cpu+=Integer.valueOf(kv[1]);
                        }else if("mem".equals(kv[0]) && StringUtils.isNotBlank(kv[1])){
                            mem+=Integer.valueOf(kv[1]);
                        }else if("root-disk".equals(kv[0]) && StringUtils.isNotBlank(kv[1])){
                            disk+=Integer.valueOf(kv[1]);
                        }
                    }
                }
            }
            compute.put("cpu", cpu);
            compute.put("mem", mem);
            compute.put("disk", disk);
        }else{
            LOG.error("the yaml file not exist!file="+yamlFile); 
            return null;
        }
        LOG.info("parse yaml result-->"+compute);
        return compute;
    }

  
    public static void main(String[] args) {
        JujuClientManager jujuClientManager = new JujuClientManager();
        jujuClientManager.parseYaml("E:/workspace/openo-common-utils/src/org/openo/common/yaml", "test.yaml", "addResource");
    }
    
}
