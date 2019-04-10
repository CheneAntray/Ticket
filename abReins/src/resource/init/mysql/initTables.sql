/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/11/23 15:04:14                          */
/*==============================================================*/
DROP TABLE IF EXISTS INCOME_INFO;

DROP TABLE IF EXISTS MENU_INFO;

DROP TABLE IF EXISTS REPORT_DEFINED;

DROP TABLE IF EXISTS RESOURCE_INFO;

DROP TABLE IF EXISTS ROLE_INFO;

DROP TABLE IF EXISTS ROLE_RESOURCE_REL;

DROP TABLE IF EXISTS SPZ_OFDAY;

DROP TABLE IF EXISTS SPZ_OFMONTH;

DROP TABLE IF EXISTS SPZ_OFYEAR;

DROP TABLE IF EXISTS STATION_SECTION_INFO;

DROP TABLE IF EXISTS TICKET_STATION_INFO;

DROP TABLE IF EXISTS TRAIN_NUMBER_INFO;

DROP TABLE IF EXISTS TRAIN_STATION_INFO;

DROP TABLE IF EXISTS UNDERTAKE_ENTERPRISE_INFO;

DROP TABLE IF EXISTS URL_INFO;

DROP TABLE IF EXISTS USER_INFO;

DROP TABLE IF EXISTS USER_ROLE_REL;

DROP TABLE IF EXISTS ZD_OFDAY;

DROP TABLE IF EXISTS ZD_OFMONTH;

DROP TABLE IF EXISTS ZD_OFYEAR;

DROP TABLE IF EXISTS TICKETSTATION_TRAINNUMBER_OFMONTH;

DROP TABLE IF EXISTS TICKETSTATION_TRAINNUMBER_OFYEAR;

DROP TABLE IF EXISTS TEMP_INCOME;

DROP TABLE IF EXISTS TIMEDTASK_INFO;

DROP TABLE IF EXISTS TRAIN_DIRECTION;

/*==============================================================*/
/* Table: INCOME_INFO                                           */
/*==============================================================*/
CREATE TABLE INCOME_INFO
(
   ID                   VARCHAR(32) NOT NULL COMMENT '收入信息ID',
   TRAIN_NUMBER_ID      INT COMMENT '车次ID',
   TICKET_STATION_ID    INT COMMENT '售票站ID',
   INCOME               DOUBLE COMMENT '收入',
   PEOPLE_COUNT         INT COMMENT '人数',
   CREATE_DATE          DATETIME COMMENT '记录日期',
   DATA_DATE            DATE COMMENT '数据日期',
   PRIMARY KEY (ID),
   KEY `INCOME_CREATE_DATE` (`CREATE_DATE`),
   KEY `INCOME_DATA_DATE` (`DATA_DATE`),
   KEY `INCOME_INCOME` (`INCOME`),
   KEY `INCOME_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `INCOME_TICKET_STATION_ID` (`TICKET_STATION_ID`),
   KEY `INCOME_TRAIN_NUMBER_ID` (`TRAIN_NUMBER_ID`)
);
ALTER TABLE INCOME_INFO COMMENT '收入信息表';

/*==============================================================*/
/* Table: MENU_INFO                                             */
/*==============================================================*/
CREATE TABLE `MENU_INFO` (
  `ID` VARCHAR(32) NOT NULL COMMENT '菜单ID',
  `MENU_NAME` VARCHAR(20) DEFAULT NULL COMMENT '菜单名称',
  `CREATE_TIME` DATE DEFAULT NULL COMMENT '创建时间',
  `CREATE_OPER` VARCHAR(32) DEFAULT NULL COMMENT '创建者',
  `UPDATE_TIME` DATE DEFAULT NULL COMMENT '修改时间',
  `UPDATE_OPER` VARCHAR(32) DEFAULT NULL COMMENT '修改者',
  `ACTIVEFLAG` BIGINT(20) DEFAULT NULL,
  `APP_ID` VARCHAR(20) DEFAULT NULL,
  `DESCRIPTION` VARCHAR(20) DEFAULT NULL,
  `ICON` VARCHAR(30) DEFAULT NULL,
  `ISDEFAULT` VARCHAR(10) DEFAULT NULL,
  `ISLEAF` BIGINT(20) DEFAULT NULL,
  `MENU_URL` VARCHAR(100) DEFAULT NULL,
  `MENULV` BIGINT(20) DEFAULT NULL,
  `ORDERNUM` BIGINT(20) DEFAULT NULL,
  `PARENTID` VARCHAR(32) DEFAULT NULL,
  `TITLE` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
ALTER TABLE MENU_INFO COMMENT '菜单信息表';

/*==============================================================*/
/* Table: REPORT_DEFINED                                        */
/*==============================================================*/
CREATE TABLE REPORT_DEFINED
(
   ID                   VARCHAR(32) NOT NULL COMMENT '报表ID',
   REPORT_CODE      VARCHAR(32) COMMENT '报表编号',
   REPORT_NAME    VARCHAR(32) COMMENT '报表名称',
   REPORT_FILE_NAME  VARCHAR(32) COMMENT '报表文件名',
   PRIMARY KEY (ID)
);
ALTER TABLE REPORT_DEFINED COMMENT '报表信息表';

/*==============================================================*/
/* Table: RESOURCE_INFO                                         */
/*==============================================================*/
CREATE TABLE RESOURCE_INFO
(
   ID                   VARCHAR(32) NOT NULL COMMENT 'ID',
   RESOURCE_ID          VARCHAR(32) COMMENT '资源ID',
   RESOURCE_TYPE        INT COMMENT '资源类型',
   PARENT_MENU          VARCHAR(32) COMMENT '父节点ID',
   PRIMARY KEY (ID)
);
ALTER TABLE RESOURCE_INFO COMMENT '资源信息表';

/*==============================================================*/
/* Table: ROLE_INFO                                             */
/*==============================================================*/
CREATE TABLE ROLE_INFO
(
   ID                   VARCHAR(32) NOT NULL COMMENT '角色ID',
   ROLE_NAME            VARCHAR(20) COMMENT '角色名称',
   CREATE_TIME          DATE COMMENT '创建时间',
   CREATE_OPER          VARCHAR(32) COMMENT '创建者',
   UPDATE_TIME          DATE COMMENT '修改时间',
   UPDATE_OPER          VARCHAR(32) COMMENT '修改者',
   PRIMARY KEY (ID)
);
ALTER TABLE ROLE_INFO COMMENT '角色信息表';

/*==============================================================*/
/* Table: ROLE_RESOURCE_REL                                     */
/*==============================================================*/
CREATE TABLE ROLE_RESOURCE_REL
(
   ID                   VARCHAR(32) NOT NULL COMMENT 'ID',
   ROLE_ID              VARCHAR(32) COMMENT '角色ID',
   RESOURCE_ID          VARCHAR(32) COMMENT '资源ID',
   PRIMARY KEY (ID)
);
ALTER TABLE ROLE_RESOURCE_REL COMMENT '角色资源关联表';

/*==============================================================*/
/* Table: SPZ_OFDAY                                              */
/*==============================================================*/
CREATE TABLE SPZ_OFDAY
(
   ID                   VARCHAR(32) COMMENT 'ID',
   SPZ_ID                INT COMMENT '售票站',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   DATA_DATE            DATE COMMENT '数据日期',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `SPZOFDAY_SPZ_ID` (`SPZ_ID`),
   KEY `SPZOFDAY_INCOME` (`INCOME`),
   KEY `SPZOFDAY_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `SPZOFDAY_DATA_DATE` (`DATA_DATE`)
);
ALTER TABLE SPZ_OFDAY COMMENT '售票站日统计表';

/*==============================================================*/
/* Table: SPZ_OFMONTH                                           */
/*==============================================================*/
CREATE TABLE SPZ_OFMONTH
(
   ID                   VARCHAR(32) COMMENT 'ID',
   SPZ_ID               INT COMMENT '售票站',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT  COMMENT '售票人数',
   MONTH                VARCHAR(20) COMMENT '年份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `SPZOFMONTH_SPZ_ID` (`SPZ_ID`),
   KEY `SPZOFMONTH_INCOME` (`INCOME`),
   KEY `SPZOFMONTH_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `SPZOFMONTH_MONTH` (`MONTH`)
);
ALTER TABLE SPZ_OFMONTH COMMENT '售票站月统计表';

/*==============================================================*/
/* Table: SPZ_OFYEAR                                            */
/*==============================================================*/
CREATE TABLE SPZ_OFYEAR
(
   ID                   VARCHAR(32) COMMENT 'ID',
   SPZ_ID               INT COMMENT '售票站',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   YEAR                 INT COMMENT '年份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `SPZOFYEAR_SPZ_ID` (`SPZ_ID`),
   KEY `SPZOFYEAR_INCOME` (`INCOME`),
   KEY `SPZOFYEAR_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `SPZOFYEAR_YEAR` (`YEAR`)
);
ALTER TABLE SPZ_OFYEAR COMMENT '售票站年统计表';

/*==============================================================*/
/* Table: STATION_SECTION_INFO                                  */
/*==============================================================*/
CREATE TABLE STATION_SECTION_INFO
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT 'ID',
   NAME                 VARCHAR(20) COMMENT '站段名称',
   PRIMARY KEY (ID)
);
ALTER TABLE STATION_SECTION_INFO COMMENT '站段信息表';

/*==============================================================*/
/* Table: TICKET_STATION_INFO                                   */
/*==============================================================*/
CREATE TABLE TICKET_STATION_INFO
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT 'ID',
   NAME                 VARCHAR(20) COMMENT '名称',
   STATION_SECTION_ID   INT COMMENT '所属站段',
   PRIMARY KEY (ID)
);
ALTER TABLE TICKET_STATION_INFO COMMENT '售票站信息表';

/*==============================================================*/
/* Table: TRAIN_NUMBER_INFO                                     */
/*==============================================================*/
CREATE TABLE TRAIN_NUMBER_INFO
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT '车次ID',
   TRAIN_NO             VARCHAR(5) COMMENT '车次编号',
   START_STATION_ID     INT COMMENT '始发站',
   END_STATION_ID       INT COMMENT '终点站',
   UNDER_ENTER_ID       INT COMMENT '担当企业ID',
   DIRECTION_ID         INT COMMENT '方向ID',
   PRIMARY KEY (ID)
);
ALTER TABLE TRAIN_NUMBER_INFO COMMENT '车次信息表';

/*==============================================================*/
/* Table: TRAIN_STATION_INFO                                    */
/*==============================================================*/
CREATE TABLE TRAIN_STATION_INFO
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT '车次到发站ID',
   NAME                 VARCHAR(20) COMMENT '名称',
   PRIMARY KEY (ID)
);
ALTER TABLE TRAIN_STATION_INFO COMMENT '车次到发站信息表';

/*==============================================================*/
/* Table: UNDERTAKE_ENTERPRISE_INFO                             */
/*==============================================================*/
CREATE TABLE UNDERTAKE_ENTERPRISE_INFO
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT '担当企业ID',
   NAME                 VARCHAR(20) COMMENT '担当企业名称',
   PRIMARY KEY (ID)
);
ALTER TABLE UNDERTAKE_ENTERPRISE_INFO COMMENT '担当企业信息表';

/*==============================================================*/
/* Table: URL_INFO                                              */
/*==============================================================*/
CREATE TABLE URL_INFO
(
   ID                   VARCHAR(32) NOT NULL COMMENT '路径ID',
   URL_NAME             VARCHAR(200) COMMENT '路径名称',
   URL_PATH             VARCHAR(200) COMMENT '路径地址',
   CREATE_TIME          DATE COMMENT '创建时间',
   CREATE_OPER          VARCHAR(32) COMMENT '创建者',
   UPDATE_TIME          DATE COMMENT '修改时间',
   UPDATE_OPER          VARCHAR(32) COMMENT '修改者',
   PRIMARY KEY (ID)
);
ALTER TABLE URL_INFO COMMENT '路径信息表';

/*==============================================================*/
/* Table: USER_INFO                                             */
/*==============================================================*/
CREATE TABLE USER_INFO
(
   ID                   VARCHAR(32) NOT NULL COMMENT '用户ID',
   ACCOUNT              VARCHAR(30) COMMENT '账号',
   PASSWORD             VARCHAR(30) COMMENT '密码',
   NAME                 VARCHAR(10) COMMENT '姓名',
   CREATE_TIME          DATE COMMENT '创建时间',
   CREATE_OPER          VARCHAR(32) COMMENT '创建者',
   UPDATE_TIME          DATE COMMENT '修改时间',
   UPDATE_OPER          VARCHAR(32) COMMENT '修改者',
   PRIMARY KEY (ID)
);
ALTER TABLE USER_INFO COMMENT '用户信息表';

/*==============================================================*/
/* Table: USER_ROLE_REL                                         */
/*==============================================================*/
CREATE TABLE USER_ROLE_REL
(
   ID                   VARCHAR(32) NOT NULL COMMENT 'ID',
   USER_ID              VARCHAR(32) COMMENT '用户ID',
   ROLE_ID              VARCHAR(32) COMMENT '角色ID',
   PRIMARY KEY (ID)
);
ALTER TABLE USER_ROLE_REL COMMENT '用户角色关联表';

/*==============================================================*/
/* Table: ZD_OFDAY                                              */
/*==============================================================*/
CREATE TABLE ZD_OFDAY
(
   ID                   VARCHAR(32) COMMENT 'ID',
   ZD_ID                INT COMMENT '站段',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   DATA_DATE            DATE COMMENT '数据日期',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `ZDOFDAY_ZD_ID` (`ZD_ID`),
   KEY `ZDOFDAY_INCOME` (`INCOME`),
   KEY `ZDOFDAY_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `ZDOFDAY_DATA_DATE` (`DATA_DATE`)
);
ALTER TABLE ZD_OFDAY COMMENT '站段日统计表';

/*==============================================================*/
/* Table: ZD_OFMONTH                                            */
/*==============================================================*/
CREATE TABLE ZD_OFMONTH
(
   ID                   VARCHAR(32) COMMENT 'ID',
   ZD_ID                INT COMMENT '站段',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   MONTH                VARCHAR(20) COMMENT '月份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `ZDOFMONTH_ZD_ID` (`ZD_ID`),
   KEY `ZDOFMONTH_INCOME` (`INCOME`),
   KEY `ZDOFMONTH_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `ZDOFMONTH_MONTH` (`MONTH`)
);
ALTER TABLE ZD_OFMONTH COMMENT '站段月统计表';

/*==============================================================*/
/* Table: ZD_OFYEAR                                             */
/*==============================================================*/
CREATE TABLE ZD_OFYEAR
(
   ID                   VARCHAR(32) COMMENT 'ID',
   ZD_ID                INT COMMENT '站段',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   YEAR                 INT COMMENT '年份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `ZDOFYEAR_ZD_ID` (`ZD_ID`),
   KEY `ZDOFYEAR_INCOME` (`INCOME`),
   KEY `ZDOFYEAR_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `ZDOFYEAR_YEAR` (`YEAR`)
);
ALTER TABLE ZD_OFYEAR COMMENT '站段年统计表';

/*==============================================================*/
/* Table: TICKETSTATION_TRAINNUMBER_OFMONTH                     */
/*==============================================================*/
CREATE TABLE TICKETSTATION_TRAINNUMBER_OFMONTH
(
   ID                   VARCHAR(32) COMMENT 'ID',
   STATION_SECTION_ID   INT COMMENT '站段ID',
   TICKET_STATION_ID    INT COMMENT '售票站ID',
   TRAIN_NUMBER_ID      INT COMMENT '车次ID',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   MONTH                VARCHAR(20) COMMENT '月份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `TTOFMONTH_STATION_SECTION_ID` (`STATION_SECTION_ID`),
   KEY `TTOFMONTH_TICKET_STATION_ID` (`TICKET_STATION_ID`),
   KEY `TTOFMONTH_TRAIN_NUMBER_ID` (`TRAIN_NUMBER_ID`),
   KEY `TTOFMONTH_INCOME` (`INCOME`),
   KEY `TTOFMONTH_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `TTOFMONTH_MONTH` (`MONTH`)
);
ALTER TABLE TICKETSTATION_TRAINNUMBER_OFMONTH COMMENT '售票站车次月统计信息表';

/*==============================================================*/
/* Table: TICKETSTATION_TRAINNUMBER_OFYEAR                      */
/*==============================================================*/
CREATE TABLE TICKETSTATION_TRAINNUMBER_OFYEAR
(
   ID                   VARCHAR(32) COMMENT 'ID',
   STATION_SECTION_ID   INT COMMENT '站段ID',
   TICKET_STATION_ID    INT COMMENT '售票站ID',
   TRAIN_NUMBER_ID      INT COMMENT '车次ID',
   INCOME               DOUBLE COMMENT '票款',
   PEOPLE_COUNT         INT COMMENT '售票人数',
   YEAR                 INT COMMENT '年份',
   CREATE_DATE          DATETIME COMMENT '创建时间',
   PRIMARY KEY (ID),
   KEY `TTOFYEAR_STATION_SECTION_ID` (`STATION_SECTION_ID`),
   KEY `TTOFYEAR_TICKET_STATION_ID` (`TICKET_STATION_ID`),
   KEY `TTOFYEAR_TRAIN_NUMBER_ID` (`TRAIN_NUMBER_ID`),
   KEY `TTOFYEAR_INCOME` (`INCOME`),
   KEY `TTOFYEAR_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `TTOFYEAR_YEAR` (`YEAR`)
);
ALTER TABLE TICKETSTATION_TRAINNUMBER_OFYEAR COMMENT '售票站车次年统计信息表';

-- ----------------------------
--  Table structure for  TEMP_INCOME 
-- ----------------------------
CREATE TABLE TEMP_INCOME (
   ID VARCHAR(32)  NOT NULL COMMENT '收入信息ID',
   TRAIN_NUMBER_ID  INT(11)  COMMENT '车次ID',
   TICKET_STATION_ID  INT(11) DEFAULT NULL COMMENT '售票站ID',
   INCOME  DOUBLE  COMMENT '收入',
   PEOPLE_COUNT  INT(11)  COMMENT '人数',
   CREATE_DATE  DATETIME  COMMENT '记录日期',
   DATA_DATE  DATE  COMMENT '数据日期',
   PRIMARY KEY ( ID ),
   KEY `TEMPINCOME_TRAIN_NUMBER_ID` (`TRAIN_NUMBER_ID`),
   KEY `TEMPINCOME_TICKET_STATION_ID` (`TICKET_STATION_ID`),
   KEY `TEMPINCOME_INCOME` (`INCOME`),
   KEY `TEMPINCOME_PEOPLE_COUNT` (`PEOPLE_COUNT`),
   KEY `TEMPINCOME_DATA_DATE` (`DATA_DATE`)
) ;
ALTER TABLE TEMP_INCOME COMMENT '临时收入表';

-- ----------------------------
--  Table structure for  TIMEDTASK_INFO 
-- ----------------------------
CREATE TABLE TIMEDTASK_INFO
(
   ID                   VARCHAR(32) COMMENT 'ID',
   TIMEDTASK_TYPE      INT COMMENT '定时任务类型',
   TIMEDTASK_DATE      DATE COMMENT '定时任务时间',
   PRIMARY KEY (ID)
);
ALTER TABLE TIMEDTASK_INFO COMMENT '定时任务表';


-- ----------------------------
--  Table structure for  TRAIN_DIRECTION 
-- ----------------------------
CREATE TABLE TRAIN_DIRECTION
(
   ID                   INT AUTO_INCREMENT NOT NULL COMMENT '车次方向ID',
   DIRECTION_NAME    	VARCHAR(32) COMMENT '方向名称',
   PRIMARY KEY (ID)
);
ALTER TABLE TRAIN_DIRECTION COMMENT '方向信息表';