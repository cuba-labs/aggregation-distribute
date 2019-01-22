-- begin APP_TASK
alter table APP_TASK add constraint FK_APP_TASK_ON_PROJECT foreign key (PROJECT_ID) references APP_PROJECT(ID)^
create index IDX_APP_TASK_ON_PROJECT on APP_TASK (PROJECT_ID)^
-- end APP_TASK
