-- begin APP_TASK
create table APP_TASK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    PROJECT_ID varchar(36),
    EXAMPLE_TWO double precision,
    EXAMPLE_THREE integer,
    ESTIMATION double precision,
    TIME_CONSUMED integer,
    TYPE_ integer,
    PRIORITY integer,
    --
    primary key (ID)
)^
-- end APP_TASK
-- begin APP_PROJECT
create table APP_PROJECT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TITLE varchar(255),
    --
    primary key (ID)
)^
-- end APP_PROJECT
