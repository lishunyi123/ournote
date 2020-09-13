CREATE TABLE `t_member`
(
    `id`                      bigint(20)  NOT NULL COMMENT 'ID',
    `username`                varchar(32) NULL COMMENT '用户名',
    `password`                varchar(64) NULL COMMENT '密码',
    `phone`                   varchar(32) NULL COMMENT '手机号',
    `email`                   varchar(64) NULL COMMENT '邮箱',
    `enabled`                 tinyint(1)  NULL DEFAULT 1 COMMENT '是否可用，默认1，可用',
    `account_non_expired`     tinyint(1)  NULL DEFAULT 1 COMMENT '是否过期，默认1，没过期',
    `account_non_locked`      tinyint(1)  NULL DEFAULT 1 COMMENT '是否锁定，默认1，没锁定',
    `credentials_non_expired` tinyint(1)  NULL DEFAULT 1 COMMENT '密码是否过期，默认1，没过期',
    PRIMARY KEY (`id`)
);
