# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table administrators (
  id                        bigint auto_increment not null,
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
  id                        bigint auto_increment not null,
  constraint pk_roles primary key (id))
;

create table students (
  id                        bigint auto_increment not null,
  constraint pk_students primary key (id))
;




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

