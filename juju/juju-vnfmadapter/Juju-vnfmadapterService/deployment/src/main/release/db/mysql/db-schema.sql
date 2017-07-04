#*******************************************************************************
# Copyright 2016 Huawei Technologies Co., Ltd.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#*******************************************************************************
/******************drop old database and user***************************/
use mysql;
drop database IF  EXISTS jujuvnfmdb;
delete from user where User='jujuvnfm';
FLUSH PRIVILEGES;

/******************create new database and user***************************/
create database jujuvnfmdb CHARACTER SET utf8;

GRANT ALL PRIVILEGES ON jujuvnfmdb.* TO 'jujuvnfm'@'%' IDENTIFIED BY 'jujuvnfm' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mysql.* TO 'jujuvnfm'@'%' IDENTIFIED BY 'jujuvnfm' WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON jujuvnfmdb.* TO 'jujuvnfm'@'localhost' IDENTIFIED BY 'jujuvnfm' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mysql.* TO 'jujuvnfm'@'localhost' IDENTIFIED BY 'jujuvnfm' WITH GRANT OPTION;
FLUSH PRIVILEGES;

use jujuvnfmdb;
set Names 'utf8';

/******************drop old table and create new***************************/

DROP TABLE IF EXISTS jujuvnfm;
CREATE TABLE jujuvnfm (
    ID                        VARCHAR(128)       NOT NULL,
    VNFM_ID                    VARCHAR(256)       NULL,
    VNF_ID                  VARCHAR(256)       NULL,
    APP_NAME                  VARCHAR(256)       NULL,
    JOB_ID                  VARCHAR(256)       NULL,
    STATUS                    INT                   NULL,
    CREATE_TIME             DATETIME           NULL,
    MODIFY_TIME             DATETIME           NULL,
    DELETE_TIME             DATETIME            NULL,
    EXTEND		TEXT 	 NULL,
    CONSTRAINT jujuvnfm PRIMARY KEY(ID)
);