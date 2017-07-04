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

package org.openo.nfvo.jujuvnfmadapter.common.servicetoken.module;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.openo.nfvo.jujuvnfmadapter.common.VNFJsonUtil;

/**
 * 
 * Access tokens class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class AccessTokens {

    private String accesTokens;

    private int expire;

    private long createTime;

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since  NFVO 0.5
     */
    public AccessTokens() {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param token
     * @param ttl
     * @since  NFVO 0.5
     */
    public AccessTokens(String token, int ttl) {
        this.accesTokens = token;
        this.expire = ttl;
        this.createTime = System.currentTimeMillis();
    }

    /**
     * 
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @param accessToken
     * @param expire
     * @param createTime
     * @since  NFVO 0.5
     */
    public AccessTokens(String accessToken, Integer expire, Long createTime) {
        this.accesTokens = accessToken;
        this.expire = expire == null ? 0 : expire;
        this.createTime = createTime == null ? 0 : createTime;
    }

    public String getAccessToken() {
        return this.accesTokens;
    }

    public void setAccessToken(String token) {
        this.accesTokens = token;
    }

    public int getExpire() {
        return this.expire;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    /**
     * 
     * Check time validity.<br>
     * 
     * @return
     * @since  NFVO 0.5
     */
    public boolean valid() {
        if(this.expire == 0) {
            return true;
        }
        return System.currentTimeMillis() - this.createTime <= 1000L * this.expire;
    }

    @Override
    public String toString() {
        return '{'+ StringUtils.trimToEmpty(this.getAccessToken()) + '\'' + ",'expire': '" + this.getExpire() + '\'' +
                ",'createTime': '" + this.getCreateTime() + '\'' + '}';
    }

    /**
     * 
     * To entity.<br>
     * 
     * @param jsonObject
     * @return
     * @since  NFVO 0.5
     */
    public static AccessTokens toEntity(JSONObject jsonObject) {
        String token = VNFJsonUtil.getJsonFieldStr(jsonObject, "accessToken");
        Integer expire = VNFJsonUtil.getJsonFieldInt(jsonObject, "expire");
        Long createTime = VNFJsonUtil.getJsonFieldLong(jsonObject, "createTime");
        return new AccessTokens(token, expire, createTime);
    }
}
