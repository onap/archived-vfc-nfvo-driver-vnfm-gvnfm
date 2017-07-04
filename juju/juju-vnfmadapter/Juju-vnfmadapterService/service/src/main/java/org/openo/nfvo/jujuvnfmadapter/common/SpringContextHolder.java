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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * Spring context holder class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext appContext;

    /**
     * application context<br/>
     * 
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        setAppContext(applicationContext);
    }

    /**
     * <br>
     * 
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return appContext;
    }

    /**
     * 
     * Get spring bean.<br>
     * 
     * @param name
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSpringBean(String name) {
        checkApplicationContext();
        return (T)appContext.getBean(name);
    }

    /**
     * 
     * Get spring bean.<br>
     * 
     * @param requiredType
     * @return
     * @since  NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSpringBean(Class<T> requiredType) {
        checkApplicationContext();
        return (T)appContext.getBeansOfType(requiredType);
    }

    private static void checkApplicationContext() {
        if(appContext == null) {
            LOG.error("spring appContext do not insert.");
            throw new IllegalStateException("spring appContext is null.");
        }
    }

    /**
     * 
     * Clean application context.<br>
     * 
     * @since  NFVO 0.5
     */
    public static void cleanApplicationContext() {
        appContext = null;
    }

    private static void setAppContext(ApplicationContext applicationContext) {
        SpringContextHolder.appContext = applicationContext;
    }
}
