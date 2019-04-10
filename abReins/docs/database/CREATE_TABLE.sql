/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/10/27 18:02:03                          */
/*==============================================================*/


drop table if exists INCOME_INFO;

drop table if exists MENU_INFO;

drop table if exists RESOURCE_INFO;

drop table if exists ROLE_RESOURCE_REL;

drop table if exists STATION_SECTION_INFO;

drop table if exists TICKET_STATION_INFO;

drop table if exists TRAIIN_NUMBER_INFO;

drop table if exists TRAIN_STATION_INFO;

drop table if exists UNDERTAKE_ENTERPRISE_INFO;

drop table if exists URL_INFO;

drop table if exists USER_INFO;

drop table if exists USER_ROLE_REL;

drop table if exists `ROLE_INFO`;

/*==============================================================*/
/* Table: INCOME_INFO                                           */
/*==============================================================*/
create table INCOME_INFO
(
   ID                   varchar(32) not null comment '收入信息ID',
   TRAIN_NUMBER_ID      varchar(32) comment '车次ID',
   TICKET_STATION_ID    varchar(32) comment '售票站ID',
   INCOME               double comment '收入',
   PEOPLE_COUNT         int comment '人数',
   CREATE_DATE          date comment '记录日期',
   DATA_DATE            date comment '数据日期',
   primary key (ID)
);

/*==============================================================*/
/* Table: MENU_INFO                                             */
/*==============================================================*/
create table MENU_INFO
(
   ID                   varchar(32) not null comment '功能ID',
   MENU_NAME            varchar(20) comment '功能名称',
   MENU_URL             varchar(100) comment '菜单路径',
   CREATE_TIME          date comment '创建时间',
   CREATE_OPER          varchar(32) comment '创建者',
   UPDATE_TIME          date comment '修改时间',
   UPDATE_OPER          varchar(32) comment '修改者',
   primary key (ID)
);

/*==============================================================*/
/* Table: RESOURCE_INFO                                         */
/*==============================================================*/
create table RESOURCE_INFO
(
   ID                   varchar(32) not null comment 'ID',
   RESOURCE_ID          varchar(32) comment '角色ID',
   RESOURCE_TYPE        int comment '功能ID',
   primary key (ID)
);

/*==============================================================*/
/* Table: ROLE_RESOURCE_REL                                     */
/*==============================================================*/
create table ROLE_RESOURCE_REL
(
   ID                   varchar(32) not null comment 'ID',
   ROLE_ID              varchar(32) comment '用户ID',
   RESOURCE_ID          varchar(32) comment '角色ID',
   primary key (ID)
);

/*==============================================================*/
/* Table: STATION_SECTION_INFO                                  */
/*==============================================================*/
create table STATION_SECTION_INFO
(
   ID                   varchar(32) not null comment 'ID',
   NAME                 varchar(20) comment '站段名称',
   primary key (ID)
);

/*==============================================================*/
/* Table: TICKET_STATION_INFO                                   */
/*==============================================================*/
create table TICKET_STATION_INFO
(
   ID                   varchar(32) not null comment 'ID',
   NAME                 varchar(20) comment '名称',
   STATION_SECTION_ID   varchar(32) comment '所属站段',
   primary key (ID)
);

/*==============================================================*/
/* Table: TRAIIN_NUMBER_INFO                                    */
/*==============================================================*/
create table TRAIIN_NUMBER_INFO
(
   ID                   varchar(32) not null comment '车次ID',
   TRAIN_NO             varchar(5) comment '车次编号',
   START_STATION_ID     varchar(32) comment '始发站',
   END_STATION_ID       varchar(32) comment '终点站',
   UNDER_ENTER_ID       varchar(32) comment '担当企业ID',
   primary key (ID)
);

/*==============================================================*/
/* Table: TRAIN_STATION_INFO                                    */
/*==============================================================*/
create table TRAIN_STATION_INFO
(
   ID                   varchar(32) not null comment '车次到发站ID',
   NAME                 varchar(20) comment '名称',
   primary key (ID)
);

/*==============================================================*/
/* Table: UNDERTAKE_ENTERPRISE_INFO                             */
/*==============================================================*/
create table UNDERTAKE_ENTERPRISE_INFO
(
   ID                   varchar(32) not null comment '担当企业ID',
   NAME                 varchar(20) comment '担当企业名称',
   primary key (ID)
);

/*==============================================================*/
/* Table: URL_INFO                                              */
/*==============================================================*/
create table URL_INFO
(
   ID                   varchar(32) not null comment '角色ID',
   URL_NAME             varchar(200) comment '角色名称',
   URL_PATH             varchar(200),
   CREATE_TIME          date comment '创建时间',
   CREATE_OPER          varchar(32) comment '创建者',
   UPDATE_TIME          date comment '修改时间',
   UPDATE_OPER          varchar(32) comment '修改者',
   primary key (ID)
);

/*==============================================================*/
/* Table: USER_INFO                                             */
/*==============================================================*/
create table USER_INFO
(
   ID                   varchar(32) not null comment '用户ID',
   ACCOUNT              varchar(30) comment '账号',
   PASSWORD             varchar(30) comment '密码',
   NAME                 varchar(10) comment '姓名',
   CREATE_TIME          date comment '创建时间',
   CREATE_OPER          varchar(32) comment '创建者',
   UPDATE_TIME          date comment '修改时间',
   UPDATE_OPER          varchar(32) comment '修改者',
   primary key (ID)
);

/*==============================================================*/
/* Table: USER_ROLE_REL                                         */
/*==============================================================*/
create table USER_ROLE_REL
(
   ID                   varchar(32) not null comment 'ID',
   USER_ID              varchar(32) comment '用户ID',
   ROLE_ID              varchar(32) comment '角色ID',
   primary key (ID)
);

/*==============================================================*/
/* Table: ROLE_INFO                                             */
/*==============================================================*/
CREATE TABLE `ROLE_INFO` (
  `ID` varchar(32) NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_OPER` varchar(32) DEFAULT NULL COMMENT '创建者',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '修改时间',
  `UPDATE_OPER` varchar(32) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

