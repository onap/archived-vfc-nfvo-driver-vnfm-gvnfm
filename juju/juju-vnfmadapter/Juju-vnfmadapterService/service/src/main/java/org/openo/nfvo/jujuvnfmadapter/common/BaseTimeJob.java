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

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Base time job class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public abstract class BaseTimeJob implements Runnable {

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private long initialDelay = 1;

    private long period = 1;

    private String startTime = "";

    @Override
    public abstract void run();

    /**
     * 
     * Stop method.<br>
     * 
     * @since  NFVO 0.5
     */
    public void stop() {
        service.shutdown();
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * 
     * Start method.<br>
     * 
     * @since  NFVO 0.5
     */
    public void start() {
        if(startTime.length() != 0) {
            String[] vnfTime = startTime.split(":");
            if(vnfTime.length == 2) {
                int minute = Integer.parseInt(vnfTime[0]) * 60 + Integer.parseInt(vnfTime[1]);
                Calendar calendar = Calendar.getInstance();
                int curMinute = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
                if(curMinute <= minute) {
                    initialDelay = (minute - curMinute) * 60L;
                } else {
                    initialDelay = (minute + 24 * 60 - curMinute) * 60L;
                }
            }
        }
        service.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.SECONDS);
    }
}
