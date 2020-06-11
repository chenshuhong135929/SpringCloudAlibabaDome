CREATE TABLE `personnel` (
  `personnel_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '人员主键',
  `unit_id` int DEFAULT NULL COMMENT '分队id',
  `personnel_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员名称',
  `personnel_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员头像',
  `position_id` int DEFAULT NULL COMMENT '职位名称id：6厅长，5市局局长，4分局局长，3局领导，2大队，1中队，0民警',
  `personnel_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员代号',
  `personnel_siren` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员警号',
  `age` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员年龄',
  `iphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员电话',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '人员状态：0启用，1停用',
  `imsi_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机IMSI码',
  `imsi_change` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'IMSI变更：0允许变更，1禁止变更',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_del` int DEFAULT '0' COMMENT '是否删除：0不是，1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='人员列表 ';

CREATE TABLE `personnel_chat` (
  `cid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '聊天主键',
  `pgid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '人员分组主键',
  `personnel_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '人员Id',
  `chat_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '聊天内容',
  `create_by` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='聊天详情表';

CREATE TABLE `personnel_chat_temporary` (
  `cid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '聊天主键',
  `pgid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '人员分组主键',
  `personnel_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '人员Id',
  `chat_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '聊天内容',
  `create_by` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='聊天用户离线临时表';

CREATE TABLE `personnel_group` (
  `pgid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '人员分组主键',
 `personnel_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '创建人',
  `pg_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分组名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_by` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`pgid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='人员分组表';

CREATE TABLE `personnel_group_details` (
  `pgdid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '人员分组详情主键',
  `pgid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '人员分组主键',
  `personnel_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '人员Id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_by` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`pgdid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='人员分组详情表';

#测试用
CREATE TABLE `payment` (
  `id` int NOT NULL,
  `serial` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
