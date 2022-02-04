create sequence hibernate_sequence start 1 increment 1;
create table drive
(
    id             uuid         not null,
    assigned_space int8         not null,
    free_space     int8         not null,
    path           varchar(255) not null,
    primary key (id)
);
create table file_info
(
    id                int8         not null,
    file_info_version int4,
    file_version      int4,
    name              varchar(255) not null,
    path              varchar(255) not null,
    pub_id            uuid         not null,
    type              int4         not null,
    drive_files       uuid,
    parent_id         int8,
    root_id           int8         not null,
    primary key (id)
);
create table file_info_details
(
    file_id       int8      not null,
    created_at    timestamp not null,
    has_thumbnail boolean,
    modified_at   timestamp not null,
    size          int8      not null,
    primary key (file_id)
);
create table file_permission
(
    id          int8 not null,
    type        int4 not null,
    file_id     int8,
    user_id     int8,
    permissions int8,
    primary key (id)
);
create table filesystems
(
    id                int8 not null,
    assigned_capacity int8 not null,
    user_id           int8,
    root_directory    int8,
    primary key (id)
);
create table user_details
(
    user_id                 int8         not null,
    email                   varchar(255),
    nickname                varchar(255) not null,
    path_to_profile_picture varchar(255),
    primary key (user_id)
);
create table user_entity_roles
(
    user_entity_id int8 not null,
    roles          int4
);
create table users
(
    id       int8         not null,
    locked   boolean      not null,
    password varchar(255) not null,
    pub_id   uuid         not null,
    username varchar(255) not null,
    primary key (id)
);
alter table if exists drive
    add constraint UK_jxrd4wbx6353wfridjbutg5i5 unique (path);
alter table if exists file_info
    add constraint UK_pi4obrmd1wc2h2yyjawwuccuc unique (path);
alter table if exists file_info
    add constraint UK_5igikvx316l8nlbtsby5tf41e unique (pub_id);
alter table if exists filesystems
    add constraint UK_n70l2fs0utyi933krv5u5g68i unique (user_id);
alter table if exists users
    add constraint UK_lfiagvamoj0k9pprynqpmi9i9 unique (pub_id);
alter table if exists users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table if exists file_info
    add constraint FKhiqhfxxph5u62xkqm243wgs4l foreign key (drive_files) references drive;
alter table if exists file_info
    add constraint FK4qoiilgpiu5qg5asnqgi84jnx foreign key (parent_id) references file_info;
alter table if exists file_info
    add constraint FK35j0h3g2aiqlstxcefqc4jo6b foreign key (root_id) references filesystems;
alter table if exists file_info_details
    add constraint FKcidosbf2q6fipgial60vu4f2d foreign key (file_id) references file_info;
alter table if exists file_permission
    add constraint FKn3kpjngwoce5euneoca0mdrpl foreign key (file_id) references file_info;
alter table if exists file_permission
    add constraint FKp43uy7esj9vsuqvotjod8i0ty foreign key (user_id) references users;
alter table if exists file_permission
    add constraint FKjpfsll8d9e62tmhyc5jo7yjj5 foreign key (permissions) references file_info;
alter table if exists filesystems
    add constraint FKhf1rlrrl3r5qoewirr7hagfb6 foreign key (user_id) references users;
alter table if exists filesystems
    add constraint FKgdl0qxqsj86u4ec55pgcg7vl3 foreign key (root_directory) references file_info;
alter table if exists user_details
    add constraint FKicouhgavvmiiohc28mgk0kuj5 foreign key (user_id) references users;
alter table if exists user_entity_roles
    add constraint FK80w28k99mayei90r6mycds2em foreign key (user_entity_id) references users;
