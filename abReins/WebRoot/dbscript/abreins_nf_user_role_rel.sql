CREATE TABLE NF_USER_ROLE_REL
(
    ID VARCHAR2(32) NOT NULL,
    ROLE_ID VARCHAR2(32) NOT NULL,
    USER_ID VARCHAR2(32) NOT NULL,
    ACTIVE_FLAG CHAR(1),
    INSERT_OPER VARCHAR2(32),
    INSERT_TIME DATE,
    UPDATE_OPER VARCHAR2(32),
    UPDATE_TIME DATE
);
COMMENT ON TABLE NF_USER_ROLE_REL
IS '用户角色关系表';
COMMENT ON column NF_USER_ROLE_REL.ID
IS '关系ID';
COMMENT ON column NF_USER_ROLE_REL.ROLE_ID
IS '角色ID';
COMMENT ON column NF_USER_ROLE_REL.USER_ID
IS '用户ID';
COMMENT ON column NF_USER_ROLE_REL.ACTIVE_FLAG
IS '使用状态';
COMMENT ON column NF_USER_ROLE_REL.INSERT_OPER
IS '创建人';
COMMENT ON column NF_USER_ROLE_REL.INSERT_TIME
IS '创建时间';
COMMENT ON column NF_USER_ROLE_REL.UPDATE_OPER
IS '修改人';
COMMENT ON column NF_USER_ROLE_REL.UPDATE_TIME
IS '修改时间';
ALTER TABLE NF_USER_ROLE_REL ADD CONSTRAINT NF_USER_ROLE_REL_PK PRIMARY KEY (ID);