# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table administrators (
  id                        bigint auto_increment not null,
  person_id                 bigint,
  constraint pk_administrators primary key (id))
;

create table capacities (
  id                        bigint auto_increment not null,
  constraint pk_capacities primary key (id))
;

create table courses (
  id                        bigint auto_increment not null,
  constraint pk_courses primary key (id))
;

create table courses_sessions (
  id                        bigint auto_increment not null,
  constraint pk_courses_sessions primary key (id))
;

create table instructors (
  id                        bigint auto_increment not null,
  person_id                 bigint,
  constraint pk_instructors primary key (id))
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

create table students (
  id                        bigint auto_increment not null,
  person_id                 bigint,
  constraint pk_students primary key (id))
;

alter table administrators add constraint fk_administrators_person_1 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_administrators_person_1 on administrators (person_id);
alter table instructors add constraint fk_instructors_person_2 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_instructors_person_2 on instructors (person_id);
alter table roles add constraint fk_roles_person_3 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_roles_person_3 on roles (person_id);
alter table students add constraint fk_students_person_4 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_students_person_4 on students (person_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table administrators;

drop table capacities;

drop table courses;

drop table courses_sessions;

drop table instructors;

drop table persons;

drop table programs;

drop table records;

drop table roles;

drop table students;

SET FOREIGN_KEY_CHECKS=1;

