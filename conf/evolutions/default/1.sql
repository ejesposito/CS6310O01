# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table allocations (
  id                        bigint auto_increment not null,
  instructor_id             bigint,
  value                     bigint,
  constraint pk_allocations primary key (id))
;

create table courses (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  description               varchar(255),
  in_fall                   tinyint(1) default 0,
  in_spring                 tinyint(1) default 0,
  in_summer                 tinyint(1) default 0,
  constraint pk_courses primary key (id))
;

create table courses_sessions (
  id                        bigint auto_increment not null,
  session_year              bigint,
  session_term              varchar(6),
  total_capacity            bigint,
  current_allocation        bigint,
  course_id                 bigint,
  constraint ck_courses_sessions_session_term check (session_term in ('SPRING','FALL','SUMMER')),
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
  name                      varchar(255),
  current_year              bigint,
  current_term              varchar(6),
  constraint ck_programs_current_term check (current_term in ('SPRING','FALL','SUMMER')),
  constraint pk_programs primary key (id))
;

create table records (
  id                        bigint auto_increment not null,
  grade                     varchar(1),
  comment                   varchar(255),
  student_id                bigint,
  course_session_id         bigint,
  constraint ck_records_grade check (grade in ('A','B','C','D','E','F')),
  constraint pk_records primary key (id))
;

create table roles (
  DTYPE                     varchar(31) not null,
  id                        bigint auto_increment not null,
  type                      varchar(255),
  person_id                 bigint,
  constraint pk_roles primary key (id))
;


create table course_prerequisites (
  course_id                      bigint not null,
  prerequisite_id                bigint not null,
  constraint pk_course_prerequisites primary key (course_id, prerequisite_id))
;

create table programs_persons (
  programs_id                    bigint not null,
  persons_id                     bigint not null,
  constraint pk_programs_persons primary key (programs_id, persons_id))
;

create table programs_courses (
  programs_id                    bigint not null,
  courses_id                     bigint not null,
  constraint pk_programs_courses primary key (programs_id, courses_id))
;

create table instructor_courses_sessions (
  instructor_id                  bigint not null,
  course_session_id              bigint not null,
  constraint pk_instructor_courses_sessions primary key (instructor_id, course_session_id))
;

create table student_courses (
  student_id                     bigint not null,
  courses_id                     bigint not null,
  constraint pk_student_courses primary key (student_id, courses_id))
;
alter table allocations add constraint fk_allocations_instructor_1 foreign key (instructor_id) references roles (id) on delete restrict on update restrict;
create index ix_allocations_instructor_1 on allocations (instructor_id);
alter table courses_sessions add constraint fk_courses_sessions_course_2 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_courses_sessions_course_2 on courses_sessions (course_id);
alter table records add constraint fk_records_student_3 foreign key (student_id) references roles (id) on delete restrict on update restrict;
create index ix_records_student_3 on records (student_id);
alter table records add constraint fk_records_courseSession_4 foreign key (course_session_id) references courses_sessions (id) on delete restrict on update restrict;
create index ix_records_courseSession_4 on records (course_session_id);
alter table roles add constraint fk_roles_person_5 foreign key (person_id) references persons (id) on delete restrict on update restrict;
create index ix_roles_person_5 on roles (person_id);



alter table course_prerequisites add constraint fk_course_prerequisites_courses_01 foreign key (course_id) references courses (id) on delete restrict on update restrict;

alter table course_prerequisites add constraint fk_course_prerequisites_courses_02 foreign key (prerequisite_id) references courses (id) on delete restrict on update restrict;

alter table programs_persons add constraint fk_programs_persons_programs_01 foreign key (programs_id) references programs (id) on delete restrict on update restrict;

alter table programs_persons add constraint fk_programs_persons_persons_02 foreign key (persons_id) references persons (id) on delete restrict on update restrict;

alter table programs_courses add constraint fk_programs_courses_programs_01 foreign key (programs_id) references programs (id) on delete restrict on update restrict;

alter table programs_courses add constraint fk_programs_courses_courses_02 foreign key (courses_id) references courses (id) on delete restrict on update restrict;

alter table instructor_courses_sessions add constraint fk_instructor_courses_sessions_roles_01 foreign key (instructor_id) references roles (id) on delete restrict on update restrict;

alter table instructor_courses_sessions add constraint fk_instructor_courses_sessions_courses_sessions_02 foreign key (course_session_id) references courses_sessions (id) on delete restrict on update restrict;

alter table student_courses add constraint fk_student_courses_roles_01 foreign key (student_id) references roles (id) on delete restrict on update restrict;

alter table student_courses add constraint fk_student_courses_courses_02 foreign key (courses_id) references courses (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table allocations;

drop table courses;

drop table course_prerequisites;

drop table courses_sessions;

drop table instructor_courses_sessions;

drop table persons;

drop table programs;

drop table programs_persons;

drop table programs_courses;

drop table records;

drop table roles;

SET FOREIGN_KEY_CHECKS=1;

