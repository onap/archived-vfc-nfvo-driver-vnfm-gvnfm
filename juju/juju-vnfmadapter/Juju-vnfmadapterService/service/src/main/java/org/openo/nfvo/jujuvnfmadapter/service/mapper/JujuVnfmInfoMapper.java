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

package org.openo.nfvo.jujuvnfmadapter.service.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.openo.nfvo.jujuvnfmadapter.service.entity.JujuVnfmInfo;
import org.openo.nfvo.jujuvnfmadapter.service.entity.JujuVnfmInfoExample;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version     NFVO 0.5  Sep 14, 2016
 */
public interface JujuVnfmInfoMapper {
    /**
     * <br>
     * 
     * @param example
     * @return int
     */
    int countByExample(JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param example
     * @return int
     */
    int deleteByExample(JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param id
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * <br>
     * 
     * @param record
     * @return int
     */
    int insert(JujuVnfmInfo record);

    /**
     * <br>
     * 
     * @param record
     * @return int
     */
    int insertSelective(JujuVnfmInfo record);

    /**
     * <br>
     * 
     * @param example
     * @return list
     */
    List<JujuVnfmInfo> selectByExampleWithBLOBs(JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param example
     * @return list
     */
    List<JujuVnfmInfo> selectByExample(JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param id
     * @return jujuVnfmInfo
     */
    JujuVnfmInfo selectByPrimaryKey(String id);

    /**
     * <br>
     * 
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleSelective(@Param("record") JujuVnfmInfo record, @Param("example") JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param record
     * @param example
     * @return int
     */
    int updateByExampleWithBLOBs(@Param("record") JujuVnfmInfo record, @Param("example") JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param record
     * @param example
     * @return int
     */
    int updateByExample(@Param("record") JujuVnfmInfo record, @Param("example") JujuVnfmInfoExample example);

    /**
     * <br>
     * 
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(JujuVnfmInfo record);

    /**
     * <br>
     * 
     * @param record
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(JujuVnfmInfo record);

    /**
     * <br>
     * 
     * @param record
     * @return int
     */
    int updateByPrimaryKey(JujuVnfmInfo record);
}