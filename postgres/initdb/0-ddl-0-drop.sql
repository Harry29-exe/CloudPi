alter table if exists file_ancestor
    drop constraint if exists FK7173dv1t7m43v1scn47nbxksx;
alter table if exists file_ancestor
    drop constraint if exists FKegg1kearh0ieqi2fkfpgjsa8n;
alter table if exists file_details
    drop constraint if exists FK175bqent5becveiqnop8t1r8v;
alter table if exists file_info
    drop constraint if exists FKhiqhfxxph5u62xkqm243wgs4l;
alter table if exists file_info
    drop constraint if exists FK4qoiilgpiu5qg5asnqgi84jnx;
alter table if exists file_info
    drop constraint if exists FK35j0h3g2aiqlstxcefqc4jo6b;
alter table if exists file_permission
    drop constraint if exists FKn3kpjngwoce5euneoca0mdrpl;
alter table if exists file_permission
    drop constraint if exists FKp43uy7esj9vsuqvotjod8i0ty;
alter table if exists file_permission
    drop constraint if exists FKjpfsll8d9e62tmhyc5jo7yjj5;
alter table if exists filesystems
    drop constraint if exists FKhf1rlrrl3r5qoewirr7hagfb6;
alter table if exists filesystems
    drop constraint if exists FKgdl0qxqsj86u4ec55pgcg7vl3;
alter table if exists role_entity
    drop constraint if exists FKih8eyutwr9a19m9q2ihue15r0;
alter table if exists user_details
    drop constraint if exists FKicouhgavvmiiohc28mgk0kuj5;
drop table if exists drive cascade;
drop table if exists file_ancestor cascade;
drop table if exists file_details cascade;
drop table if exists file_info cascade;
drop table if exists file_permission cascade;
drop table if exists filesystems cascade;
drop table if exists role_entity cascade;
drop table if exists user_details cascade;
drop table if exists users cascade;
drop sequence if exists hibernate_sequence;
