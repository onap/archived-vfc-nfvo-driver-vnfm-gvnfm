/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample.Criteria;
import org.onap.vfc.nfvo.vnfm.gvnfm.jujuvnfmadapter.service.entity.JujuVnfmInfoExample.Criterion;

public class JujuVnfmInfoExampleTest {

    JujuVnfmInfoExample jujuexample = new JujuVnfmInfoExample();
    protected List<Criterion> criteria;
    @Test
    public void createCriteriaTest(){
        Criteria criteria =  jujuexample.createCriteria();
        String condition = "";
        String value = "";
        String property = "";
        String value1 = "";
        String value2 = "";
        Date d1 =new Date();
        Date d2 =new Date();
        List<String> values = new ArrayList<String>();
        criteria.addCriterion(condition);
        criteria.addCriterion(condition, value, property);
        criteria.addCriterion(condition, value1, value2, property);
        criteria.andDeleteTimeBetween(d1, d2);
        criteria.andAppNameBetween(value1, value2);
        criteria.andAppNameEqualTo(value);
        criteria.andAppNameGreaterThan(value);
        criteria.andAppNameGreaterThanOrEqualTo(value);
        criteria.andAppNameIn(values);
        criteria.andAppNameLessThan(value);
        criteria.andIdLessThanOrEqualTo(value);
        criteria.andAppNameLike(value2);
        criteria.andAppNameNotBetween(value1, value2);
        criteria.andAppNameNotEqualTo(value2);
        criteria.andAppNameNotIn(values);
        criteria.andIdNotLike(value2);
        criteria.andCreateTimeBetween(d1, d2);
        criteria.andCreateTimeNotBetween(d1, d2);
        criteria.andJobIdBetween(value1, value2);
        criteria.andJobIdNotBetween(value1, value2);
        criteria.andModifyTimeBetween(d1, d2);
        criteria.andJobIdEqualTo(value2);
        criteria.andJobIdGreaterThan(value2);
        criteria.andJobIdGreaterThanOrEqualTo(value2);
        criteria.andJobIdIn(values);
        criteria.andJobIdLessThan(value2);
        criteria.andJobIdLessThanOrEqualTo(value2);
        criteria.andJobIdLike(value2);
        criteria.andJobIdNotBetween(value1, value2);
        criteria.andJobIdNotEqualTo(value2);
        criteria.andJobIdNotIn(values);
        criteria.andJobIdNotLike(value2);
        assertNotNull(criteria);
    }
    @Test
    public void orTest(){
        Criteria criteria =  jujuexample.or();
        assertNotNull(criteria);
    }
    @Test
    public void clearTest(){
        jujuexample.clear();
        assertTrue(true);
    }
    @Test
    public void CriteriaTest(){
        JujuVnfmInfoExample.Criteria criteria = new JujuVnfmInfoExample.Criteria();
        boolean isValid = criteria.isValid();
        assertTrue(!isValid);
    }

    @Test
    public void CriterionTest(){
        String condition="";
        String value="";
        String secondValue="";
        String typeHandler="";

        Criterion c = new Criterion(condition, value, secondValue, typeHandler);
        assertEquals(c.getCondition(), "");
        assertEquals(c.getValue(), "");
        assertEquals(c.getSecondValue(), "");
        assertEquals(c.getTypeHandler(), "");
        assertEquals(c.isSingleValue(), false);
        assertEquals(c.isBetweenValue(), true);
        assertEquals(c.isListValue(), false);

    }

    @Test
    public void generatedCriteriaTest(){
        JujuVnfmInfoExample.Criteria criteria = new JujuVnfmInfoExample.Criteria();
        boolean isValid = criteria.isValid();

        assertTrue(!isValid);
    }

    @Test
    public void andIdIsNullTest(){
        JujuVnfmInfoExample.Criteria criteria = new JujuVnfmInfoExample.Criteria();
        Criteria c= criteria.andIdIsNull();
        assertNotNull(c);
    }
    @Test
    public void getConditionCroterionTest(){
        JujuVnfmInfoExample.Criterion criterion = new JujuVnfmInfoExample.Criterion("test",new Object(),"typeHandler");
       String condition = criterion.getCondition();
        assertEquals(condition,"test");
    }
    @Test
       public void testLimitStart() {
           JujuVnfmInfoExample jujuVnfmInfoExample=new JujuVnfmInfoExample();
           jujuVnfmInfoExample.setLimitStart(-1);

           int result = jujuVnfmInfoExample.getLimitStart();
           assertEquals(-1, result);
       }
       @Test
          public void testLimitEnd() {
              JujuVnfmInfoExample jujuVnfmInfoExample=new JujuVnfmInfoExample();
              jujuVnfmInfoExample.setLimitEnd(-1);

              int result = jujuVnfmInfoExample.getLimitEnd();
              assertEquals(-1, result);
          }
       @Test
         public void testOrderByClause() {
             JujuVnfmInfoExample jujuVnfmInfoExample=new JujuVnfmInfoExample();
             jujuVnfmInfoExample.setOrderByClause("order");

             String result = jujuVnfmInfoExample.getOrderByClause();
             assertEquals("order", result);
         }
       @Test
        public void testsetDistinct() {
            JujuVnfmInfoExample jujuVnfmInfoExample=new JujuVnfmInfoExample();
            jujuVnfmInfoExample.setDistinct(true);

            boolean result=jujuVnfmInfoExample.isDistinct();
            assertEquals(true,result);
        }

}
