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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JUJU VNFM Information example
 * .</br>
 * 
 * @author
 * @version     NFVO 0.5  Sep 14, 2016
 */
public class JujuVnfmInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public static final String VNFM_ID = "vnfmId";

    public static final String VNF_ID = "vnfId";

    public static final String APP_NAME = "appName";

    public static final String JOB_ID = "jobId";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String MODIFY_TIME  = "modifyTime";

    public static final String DELETE_TIME   = "deleteTime";

    /**
     * Constructor<br/>
     * <p>
     * </p>
     */
    public JujuVnfmInfoExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * <br>
     * 
     * @param criteria
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * <br>
     * 
     * @return
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * <br>
     * 
     * @return
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.isEmpty()) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    /**
     * <br>
     * 
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return !criteria.isEmpty();
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null"); //NOSONAR
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null"); //NOSONARr
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null"); //NOSONAR
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andVnfmIdIsNull() {
            addCriterion("VNFM_ID is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andVnfmIdIsNotNull() {
            addCriterion("VNFM_ID is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdEqualTo(String value) {
            addCriterion("VNFM_ID =", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdNotEqualTo(String value) {
            addCriterion("VNFM_ID <>", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdGreaterThan(String value) {
            addCriterion("VNFM_ID >", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdGreaterThanOrEqualTo(String value) {
            addCriterion("VNFM_ID >=", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdLessThan(String value) {
            addCriterion("VNFM_ID <", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdLessThanOrEqualTo(String value) {
            addCriterion("VNFM_ID <=", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdLike(String value) {
            addCriterion("VNFM_ID like", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfmIdNotLike(String value) {
            addCriterion("VNFM_ID not like", value, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andVnfmIdIn(List<String> values) {
            addCriterion("VNFM_ID in", values, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andVnfmIdNotIn(List<String> values) {
            addCriterion("VNFM_ID not in", values, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andVnfmIdBetween(String value1, String value2) {
            addCriterion("VNFM_ID between", value1, value2, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andVnfmIdNotBetween(String value1, String value2) {
            addCriterion("VNFM_ID not between", value1, value2, VNFM_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andVnfIdIsNull() {
            addCriterion("VNF_ID is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andVnfIdIsNotNull() {
            addCriterion("VNF_ID is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdEqualTo(String value) {
            addCriterion("VNF_ID =", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdNotEqualTo(String value) {
            addCriterion("VNF_ID <>", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdGreaterThan(String value) {
            addCriterion("VNF_ID >", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdGreaterThanOrEqualTo(String value) {
            addCriterion("VNF_ID >=", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdLessThan(String value) {
            addCriterion("VNF_ID <", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdLessThanOrEqualTo(String value) {
            addCriterion("VNF_ID <=", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdLike(String value) {
            addCriterion("VNF_ID like", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andVnfIdNotLike(String value) {
            addCriterion("VNF_ID not like", value, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andVnfIdIn(List<String> values) {
            addCriterion("VNF_ID in", values, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andVnfIdNotIn(List<String> values) {
            addCriterion("VNF_ID not in", values, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andVnfIdBetween(String value1, String value2) {
            addCriterion("VNF_ID between", value1, value2, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andVnfIdNotBetween(String value1, String value2) {
            addCriterion("VNF_ID not between", value1, value2, VNF_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andAppNameIsNull() {
            addCriterion("APP_NAME is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andAppNameIsNotNull() {
            addCriterion("APP_NAME is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameEqualTo(String value) {
            addCriterion("APP_NAME =", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("APP_NAME <>", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("APP_NAME >", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("APP_NAME >=", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameLessThan(String value) {
            addCriterion("APP_NAME <", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("APP_NAME <=", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameLike(String value) {
            addCriterion("APP_NAME like", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andAppNameNotLike(String value) {
            addCriterion("APP_NAME not like", value, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andAppNameIn(List<String> values) {
            addCriterion("APP_NAME in", values, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("APP_NAME not in", values, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("APP_NAME between", value1, value2, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("APP_NAME not between", value1, value2, APP_NAME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andJobIdIsNull() {
            addCriterion("JOB_ID is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andJobIdIsNotNull() {
            addCriterion("JOB_ID is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdEqualTo(String value) {
            addCriterion("JOB_ID =", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdNotEqualTo(String value) {
            addCriterion("JOB_ID <>", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdGreaterThan(String value) {
            addCriterion("JOB_ID >", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdGreaterThanOrEqualTo(String value) {
            addCriterion("JOB_ID >=", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdLessThan(String value) {
            addCriterion("JOB_ID <", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdLessThanOrEqualTo(String value) {
            addCriterion("JOB_ID <=", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdLike(String value) {
            addCriterion("JOB_ID like", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andJobIdNotLike(String value) {
            addCriterion("JOB_ID not like", value, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andJobIdIn(List<String> values) {
            addCriterion("JOB_ID in", values, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andJobIdNotIn(List<String> values) {
            addCriterion("JOB_ID not in", values, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andJobIdBetween(String value1, String value2) {
            addCriterion("JOB_ID between", value1, value2, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andJobIdNotBetween(String value1, String value2) {
            addCriterion("JOB_ID not between", value1, value2, JOB_ID);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, STATUS);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, CREATE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andModifyTimeIsNull() {
            addCriterion("MODIFY_TIME is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andModifyTimeIsNotNull() {
            addCriterion("MODIFY_TIME is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("MODIFY_TIME =", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("MODIFY_TIME <>", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("MODIFY_TIME >", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("MODIFY_TIME >=", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("MODIFY_TIME <", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("MODIFY_TIME <=", value, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("MODIFY_TIME in", values, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("MODIFY_TIME not in", values, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("MODIFY_TIME between", value1, value2, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("MODIFY_TIME not between", value1, value2, MODIFY_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andDeleteTimeIsNull() {
            addCriterion("DELETE_TIME is null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Criteria andDeleteTimeIsNotNull() {
            addCriterion("DELETE_TIME is not null");
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeEqualTo(Date value) {
            addCriterion("DELETE_TIME =", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeNotEqualTo(Date value) {
            addCriterion("DELETE_TIME <>", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeGreaterThan(Date value) {
            addCriterion("DELETE_TIME >", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DELETE_TIME >=", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeLessThan(Date value) {
            addCriterion("DELETE_TIME <", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value
         * @return
         */
        public Criteria andDeleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("DELETE_TIME <=", value, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andDeleteTimeIn(List<Date> values) {
            addCriterion("DELETE_TIME in", values, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param values
         * @return
         */
        public Criteria andDeleteTimeNotIn(List<Date> values) {
            addCriterion("DELETE_TIME not in", values, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andDeleteTimeBetween(Date value1, Date value2) {
            addCriterion("DELETE_TIME between", value1, value2, DELETE_TIME);
            return (Criteria) this;
        }

        /**
         * <br>
         * 
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andDeleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("DELETE_TIME not between", value1, value2, DELETE_TIME);
            return (Criteria) this;
        }
    }

    /**
     * <br>
     * <p>
     * </p>
     * 
     * @author z00292420
     * @date 2016-9-14
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * Criterion
     * .</br>
     * 
     * @author
     * @version     NFVO 0.5  Sep 14, 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;



        /**
         * Constructor<br/>
         * <p>
         * </p>
         * 
         * @param condition
         */
        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        /**
         * Constructor<br/>
         * <p>
         * </p>
         * 
         * @param condition
         * @param value
         * @param typeHandler
         */
        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        /**
         * Constructor<br/>
         * <p>
         * </p>
         * 
         * @param condition
         * @param value
         */
        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        /**
         * Constructor<br/>
         * <p>
         * </p>
         * 
         * @param condition
         * @param value
         * @param secondValue
         * @param typeHandler
         */
        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        /**
         * Constructor<br/>
         * <p>
         * </p>
         * 
         * @param condition
         * @param value
         * @param secondValue
         */
        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
        

        /**
         * <br>
         * 
         * @return
         */
        public String getCondition() {
            return condition;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Object getValue() {
            return value;
        }

        /**
         * <br>
         * 
         * @return
         */
        public Object getSecondValue() {
            return secondValue;
        }

        /**
         * <br>
         * 
         * @return
         */
        public boolean isNoValue() {
            return noValue;
        }

        /**
         * <br>
         * 
         * @return
         */
        public boolean isSingleValue() {
            return singleValue;
        }

        /**
         * <br>
         * 
         * @return
         */
        public boolean isBetweenValue() {
            return betweenValue;
        }

        /**
         * <br>
         * 
         * @return
         */
        public boolean isListValue() {
            return listValue;
        }

        /**
         * <br>
         * 
         * @return
         */
        public String getTypeHandler() {
            return typeHandler;
        }
    }
}