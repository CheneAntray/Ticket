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
   ID                   varchar(32) not null comment '������ϢID',
   TRAIN_NUMBER_ID      varchar(32) comment '����ID',
   TICKET_STATION_ID    varchar(32) comment '��ƱվID',
   INCOME               double comment '����',
   PEOPLE_COUNT         int comment '����',
   CREATE_DATE          date comment '��¼����',
   DATA_DATE            date comment '��������',
   primary key (ID)
);

/*==============================================================*/
/* Table: MENU_INFO                                             */
/*==============================================================*/
create table MENU_INFO
(
   ID                   varchar(32) not null comment '����ID',
   MENU_NAME            varchar(20) comment '��������',
   MENU_URL             varchar(100) comment '�˵�·��',
   CREATE_TIME          date comment '����ʱ��',
   CREATE_OPER          varchar(32) comment '������',
   UPDATE_TIME          date comment '�޸�ʱ��',
   UPDATE_OPER          varchar(32) comment '�޸���',
   primary key (ID)
);

/*==============================================================*/
/* Table: RESOURCE_INFO                                         */
/*==============================================================*/
create table RESOURCE_INFO
(
   ID                   varchar(32) not null comment 'ID',
   RESOURCE_ID          varchar(32) comment '��ɫID',
   RESOURCE_TYPE        int comment '����ID',
   primary key (ID)
);

/*==============================================================*/
/* Table: ROLE_RESOURCE_REL                                     */
/*==============================================================*/
create table ROLE_RESOURCE_REL
(
   ID                   varchar(32) not null comment 'ID',
   ROLE_ID              varchar(32) comment '�û�ID',
   RESOURCE_ID          varchar(32) comment '��ɫID',
   primary key (ID)
);

/*==============================================================*/
/* Table: STATION_SECTION_INFO                                  */
/*==============================================================*/
create table STATION_SECTION_INFO
(
   ID                   varchar(32) not null comment 'ID',
   NAME                 varchar(20) comment 'վ������',
   primary key (ID)
);

/*==============================================================*/
/* Table: TICKET_STATION_INFO                                   */
/*==============================================================*/
create table TICKET_STATION_INFO
(
   ID                   varchar(32) not null comment 'ID',
   NAME                 varchar(20) comment '����',
   STATION_SECTION_ID   varchar(32) comment '����վ��',
   primary key (ID)
);

/*==============================================================*/
/* Table: TRAIIN_NUMBER_INFO                                    */
/*==============================================================*/
create table TRAIIN_NUMBER_INFO
(
   ID                   varchar(32) not null comment '����ID',
   TRAIN_NO             varchar(5) comment '���α��',
   START_STATION_ID     varchar(32) comment 'ʼ��վ',
   END_STATION_ID       varchar(32) comment '�յ�վ',
   UNDER_ENTER_ID       varchar(32) comment '������ҵID',
   primary key (ID)
);

/*==============================================================*/
/* Table: TRAIN_STATION_INFO                                    */
/*==============================================================*/
create table TRAIN_STATION_INFO
(
   ID                   varchar(32) not null comment '���ε���վID',
   NAME                 varchar(20) comment '����',
   primary key (ID)
);

/*==============================================================*/
/* Table: UNDERTAKE_ENTERPRISE_INFO                             */
/*==============================================================*/
create table UNDERTAKE_ENTERPRISE_INFO
(
   ID                   varchar(32) not null comment '������ҵID',
   NAME                 varchar(20) comment '������ҵ����',
   primary key (ID)
);

/*==============================================================*/
/* Table: URL_INFO                                              */
/*==============================================================*/
create table URL_INFO
(
   ID                   varchar(32) not null comment '��ɫID',
   URL_NAME             varchar(200) comment '��ɫ����',
   URL_PATH             varchar(200),
   CREATE_TIME          date comment '����ʱ��',
   CREATE_OPER          varchar(32) comment '������',
   UPDATE_TIME          date comment '�޸�ʱ��',
   UPDATE_OPER          varchar(32) comment '�޸���',
   primary key (ID)
);

/*==============================================================*/
/* Table: USER_INFO                                             */
/*==============================================================*/
create table USER_INFO
(
   ID                   varchar(32) not null comment '�û�ID',
   ACCOUNT              varchar(30) comment '�˺�',
   PASSWORD             varchar(30) comment '����',
   NAME                 varchar(10) comment '����',
   CREATE_TIME          date comment '����ʱ��',
   CREATE_OPER          varchar(32) comment '������',
   UPDATE_TIME          date comment '�޸�ʱ��',
   UPDATE_OPER          varchar(32) comment '�޸���',
   primary key (ID)
);

/*==============================================================*/
/* Table: USER_ROLE_REL                                         */
/*==============================================================*/
create table USER_ROLE_REL
(
   ID                   varchar(32) not null comment 'ID',
   USER_ID              varchar(32) comment '�û�ID',
   ROLE_ID              varchar(32) comment '��ɫID',
   primary key (ID)
);

/*==============================================================*/
/* Table: ROLE_INFO                                             */
/*==============================================================*/
CREATE TABLE `ROLE_INFO` (
  `ID` varchar(32) NOT NULL COMMENT '��ɫID',
  `ROLE_NAME` varchar(20) DEFAULT NULL COMMENT '��ɫ����',
  `CREATE_TIME` date DEFAULT NULL COMMENT '����ʱ��',
  `CREATE_OPER` varchar(32) DEFAULT NULL COMMENT '������',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '�޸�ʱ��',
  `UPDATE_OPER` varchar(32) DEFAULT NULL COMMENT '�޸���',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

