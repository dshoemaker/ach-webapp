# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table payment (
  id                        integer not null,
  reclamation_id            integer not null,
  amount                    double,
  effective_date            timestamp,
  constraint pk_payment primary key (id))
;

create table reclamation (
  id                        integer not null,
  case_type                 varchar(255),
  date_created              timestamp,
  created_by                varchar(255),
  status                    varchar(8),
  date_closed               timestamp,
  sub_status                varchar(255),
  case_subtype              varchar(255),
  watch_item                boolean,
  beneficiary_name          varchar(255),
  beneficiary_acct_number   varchar(255),
  beneficiary_ssn           varchar(255),
  beneficiary_cust_id       varchar(255),
  date_of_death             timestamp,
  date_cbaware              timestamp,
  other_gov_benefits        boolean,
  gov_benefits              varchar(255),
  claim_number              varchar(255),
  recovery_method           varchar(255),
  check_mailed              boolean,
  check_number              varchar(255),
  mailed_to                 varchar(255),
  full_recovery             boolean,
  completed_date            timestamp,
  verified_date             timestamp,
  verified_by               varchar(255),
  additional_notes          varchar(255),
  constraint ck_reclamation_status check (status in ('OPEN','CLOSED','REOPENED')),
  constraint pk_reclamation primary key (id))
;

create sequence payment_seq;

create sequence reclamation_seq;

alter table payment add constraint fk_payment_reclamation_1 foreign key (reclamation_id) references reclamation (id) on delete restrict on update restrict;
create index ix_payment_reclamation_1 on payment (reclamation_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists payment;

drop table if exists reclamation;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists payment_seq;

drop sequence if exists reclamation_seq;

