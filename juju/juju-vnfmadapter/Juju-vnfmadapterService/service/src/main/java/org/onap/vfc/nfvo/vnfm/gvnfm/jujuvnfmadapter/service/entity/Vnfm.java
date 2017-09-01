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

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.common.CryptUtil;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.constant.Constant;

/**
 * 
 * VNFM class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 12, 2016
 */
public class Vnfm {

    private String id;

    private String name;

    private String type;

    private String version;

    private String userName;

    private String pwd;

    private String url;

    private String sites;

    private String extraInfo;

    private String status;

    private String createAt;

    private String updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Vnfm)) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Vnfm other = (Vnfm)obj;
        if(id == null) {
            if(other.id != null) {
                return false;
            }
        } else if(!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * Update Vnfm.<br>
     * 
     * @param otherVnfm
     * @since  NFVO 0.5
     */
    public void updateVnfm(JSONObject otherVnfm) {
        String vnfmName = otherVnfm.getString("name");
        String vnfmUserName = otherVnfm.getString("userName");
        String vnfmPwd = CryptUtil.enCrypt(otherVnfm.getString("pwd"));

        if(!StringUtils.isEmpty(vnfmName)) {
            this.name = vnfmName;
        }

        if(!StringUtils.isEmpty(vnfmUserName)) {
            this.userName = vnfmUserName;
        }

        if(!StringUtils.isEmpty(vnfmPwd)) {
            this.pwd = vnfmPwd;
        }

        String vnfmExtraInfo = otherVnfm.get("extraInfo").toString();
        if(!StringUtils.isEmpty(vnfmExtraInfo)) {
            this.extraInfo = vnfmExtraInfo;
        }

        this.updateAt = new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date());
    }
}
