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

package org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.activator;

import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.api.internalsvc.inf.IJujuAdapterMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * 
 * ROA juju service post processor class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class ROAJujuServicePostProcessor implements DestructionAwareBeanPostProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(ROAJujuServicePostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        if(bean instanceof IJujuAdapterMgrService){
            IJujuAdapterMgrService jujuAdapterSvc = (IJujuAdapterMgrService)bean;
            jujuAdapterSvc.register();
            LOG.info("Successfully Registered to Microservice BUS!", ROAJujuServicePostProcessor.class);
        }

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        // TODO Auto-generated method stub
        return bean;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String name) throws BeansException {
        // TODO Auto-generated method stub
        
    }

}
