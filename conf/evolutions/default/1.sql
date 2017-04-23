# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table capacities (
  id                        bigint auto_increment not null,
  instructor_id             bigint,
  constraint pk_capacities primary key (id))
;

create table courses (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  constraint pk_courses primary key (id))
;

create table courses_sessions (
  id                        bigint auto_increment not null,
  role_id                   bigint,
  constraint pk_courses_sessions primary key (id))
;

create table persons (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  address                   varchar(255),
  phone                     varchar(255),
  constraint pk_persons primary key (id))
;

create table programs (
  id                        bigint auto_increment not null,
  constraint pk_programs primary key (id))
;

create table records (
  id                        bigint auto_increment not null,
  constraint pk_records primary key (id))
;

create table roles (
  DTYPE                     varchar(31) not null,
  id                        bigint auto_increment not null,
  type                      varchar(255),
  person_id                 bigint,
  constraint pk_roles primary key (id))
;

alter table capacities add constraint fk_capacities_instructor_1 foreign key (instructor_id) references roles (id) on delete restrict on update restrict;
create index ix_capacities_instructor_1 on capacities (instructor_id);
alter table courses_sessions add constraint fk_courses_sessions_role_2 foreign key (role_id) references roles (id) on delete restrict on update restrict;
create index ix_courses_sessions_role_2 on courses_sessions (role_id);
alter table roles add constraint fk_roles_person_3 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_roles_person_3 on roles (person_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table capacities;

drop table courses;

drop table courses_sessions;

drop table persons;

drop table programs;

drop table records;

drop table roles;

SET FOREIGN_KEY_CHECKS=1;

