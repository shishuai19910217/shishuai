-- ----------------------------
-- Table structure for `td_b_dict_issues`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `td_b_dict_issues` (
  `dict_issues_id` int(11) NOT NULL COMMENT '唯一主键',
  `issues_level` char(1) DEFAULT NULL COMMENT '问题等级',
  `issues_code` varchar(10) DEFAULT NULL COMMENT '问题编码',
  `issues_lable` varchar(200) DEFAULT NULL COMMENT '问题名称',
  `issues_type` varchar(64) DEFAULT NULL COMMENT '问题类型',
  `site_flag` char(1) DEFAULT NULL COMMENT '整站是否可用 0不可用 1可用',
  `page_flag` char(1) DEFAULT NULL COMMENT '页面是否可用 0不可用 1可用',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` char(1) DEFAULT NULL COMMENT '0:未失效 1：已失效',
  `end_date` datetime DEFAULT NULL COMMENT '失效时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_issues_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='这个表是对网站可能出现问题类型的一个汇总表   （一般只会累加）';


-- ----------------------------
-- Records of td_b_dict_issues
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_dashboard_keyword`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_dashboard_keyword` (
  `dashboard_keyword_id` bigint(20) NOT NULL,
  `crawl_date` date NOT NULL,
  `project_id` int(11) NOT NULL,
  `competitor_id` int(11) DEFAULT NULL,
  `engine_type` int(11) DEFAULT NULL,
  `keyword_id` int(11) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT '0',
  `baidu_record` int(11) NOT NULL DEFAULT '0',
  `baidu_index` int(11) NOT NULL DEFAULT '0',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '0:未失效 1：已失效',
  PRIMARY KEY (`dashboard_keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_dashboard_keyword
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_dashboard_visibility`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_dashboard_visibility` (
  `dashboard_visibility_id` bigint(20) NOT NULL,
  `crawl_date` date NOT NULL,
  `project_id` int(11) NOT NULL,
  `competitor_id` int(11) NOT NULL,
  `engine_type` int(11) NOT NULL,
  `visibility` decimal(10,6) NOT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '0:未失效 1：已失效',
  PRIMARY KEY (`dashboard_visibility_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_dashboard_visibility
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_pro_detail`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_pro_detail` (
  `pro_detail_id` bigint(20) NOT NULL,
  `crawl_date` date NOT NULL,
  `project_id` int(11) NOT NULL,
  `competitor_id` int(11) NOT NULL,
  `keyword_id` int(11) NOT NULL,
  `engine_type` int(11) NOT NULL,
  `detail_id` bigint(20) NOT NULL,
  `rank` int(11) NOT NULL,
  `visibility` decimal(10,6) NOT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '0:未失效 1：已失效',
  PRIMARY KEY (`pro_detail_id`),
  KEY `FK_Reference_14` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_pro_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_serp_detail`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_serp_detail` (
  `detail_id` bigint(20) NOT NULL,
  `crawl_date` date NOT NULL,
  `keyword_id` int(11) NOT NULL,
  `engine_type` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `url` varchar(500) NOT NULL,
  `link_url` varchar(500) DEFAULT '',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '0:未失效 1：已失效',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_serp_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_anchor`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_anchor` (
  `pro_anchor_id` bigint(20) NOT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `anchor` varchar(255) DEFAULT NULL,
  `nofollow_links` bigint(20) DEFAULT NULL,
  `deleted_links` bigint(20) DEFAULT NULL,
  `total_links` bigint(20) DEFAULT NULL,
  `ref_domains` bigint(20) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `crawl_date` date NOT NULL,
  PRIMARY KEY (`pro_anchor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_f_pro_anchor
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_crawl`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_crawl` (
  `pro_crawl_id` bigint(16) NOT NULL,
  `crawl_date` date NOT NULL,
  `project_id` int(11) NOT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '0：否，1：是',
  PRIMARY KEY (`pro_crawl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='抓取日期表';

-- ----------------------------
-- Records of tf_f_pro_crawl
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_domain`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_domain` (
  `pro_domain_id` bigint(20) NOT NULL,
  `tenant_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `ext_links` bigint(20) DEFAULT NULL,
  `total_links` bigint(255) DEFAULT NULL,
  `ref_domains` bigint(255) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `crawl_date` date NOT NULL,
  PRIMARY KEY (`pro_domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_f_pro_domain
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_down_url`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_down_url` (
  `pro_down_id` bigint(20) NOT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  `project_id` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `crawl_result` varchar(255) DEFAULT NULL,
  `redirect_url` varchar(255) DEFAULT NULL,
  `ext_links` bigint(20) DEFAULT NULL,
  `ref_domains` bigint(20) DEFAULT NULL,
  `last_date` date DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `crawl_date` date NOT NULL,
  PRIMARY KEY (`pro_down_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_f_pro_down_url
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_links`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_links` (
  `pro_ext_id` bigint(20) NOT NULL,
  `project_id` int(11) NOT NULL,
  `tenant_id` int(11) NOT NULL,
  `down_url` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `anchor` varchar(255) DEFAULT NULL,
  `flag_frame` char(1) DEFAULT NULL,
  `flag_nofollow` char(1) DEFAULT NULL,
  `flag_mention` char(1) DEFAULT NULL,
  `flag_alttext` char(1) DEFAULT NULL,
  `flag_deleted` char(1) DEFAULT NULL,
  `flag_images` char(1) DEFAULT NULL,
  `flag_redirect` char(1) DEFAULT NULL,
  `date_lost` date DEFAULT NULL,
  `last_seen_date` date DEFAULT NULL,
  `first_indexed_date` date DEFAULT NULL,
  `crawl_date` date DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pro_ext_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_f_pro_links
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_f_pro_links_view`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_f_pro_links_view` (
  `pro_links_id` bigint(20) NOT NULL,
  `tenant_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `competitor_id` int(11) DEFAULT NULL,
  `crawl_date` date NOT NULL,
  `ext_back_links` bigint(20) DEFAULT NULL COMMENT '外链个数',
  `ref_domains` bigint(20) DEFAULT NULL COMMENT '域名个数',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pro_links_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_f_pro_links_view
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_page_detail`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_page_detail` (
  `detail_id` bigint(64) NOT NULL,
  `project_id` int(11) NOT NULL,
  `page_url` varchar(1000) NOT NULL,
  `title` varchar(500) DEFAULT NULL,
  `title_num` int(11) unsigned zerofill DEFAULT '00000000000',
  `status_code` varchar(10) DEFAULT NULL,
  `link_num` int(11) DEFAULT NULL,
  `domains` int(11) unsigned zerofill DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_flash` int(11) DEFAULT NULL,
  `response_time` varchar(10) DEFAULT NULL,
  `h1_sum` int(11) DEFAULT NULL,
  `h2_sum` int(11) DEFAULT NULL,
  `h3_sum` int(11) DEFAULT NULL,
  `img_alt` int(11) DEFAULT NULL,
  `des` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_page_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_page_main`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_page_main` (
  `page_main_id` bigint(64) NOT NULL,
  `project_id` int(64) DEFAULT NULL,
  `is_entering_baidu` int(11) NOT NULL,
  `is_entering_sogou` int(11) DEFAULT NULL,
  `is_entering_haoso` int(11) NOT NULL,
  `is_safe_baidu` int(11) DEFAULT NULL,
  `is_safe_haosou` int(11) DEFAULT NULL,
  `is_safe_sogou` int(11) DEFAULT NULL,
  `is_robots` int(11) DEFAULT NULL,
  `gzip` int(11) DEFAULT NULL,
  PRIMARY KEY (`page_main_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_page_main
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_page_realate`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_page_realate` (
  `page_realate_id` bigint(64) NOT NULL,
  `detail_id` bigint(64) DEFAULT NULL,
  `issues_code` varchar(10) NOT NULL,
  `issues_level` char(1) NOT NULL,
  `issues_lable` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`page_realate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_page_realate
-- ----------------------------

-- ----------------------------
-- Table structure for `tf_b_site_statistic`
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tf_b_site_statistic` (
  `site_id` bigint(64) NOT NULL,
  `project_id` int(11) NOT NULL,
  `create_date` date NOT NULL,
  `issues_code` varchar(10) NOT NULL,
  `issues_level` char(2) NOT NULL,
  `num` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tf_b_site_statistic
-- ----------------------------
INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10100', '2', '10100', '页面缺少或者存在多个H1标签', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10100' and issues_level='2' and issues_code = '10100' and issues_lable = '页面缺少或者存在多个H1标签' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10101', '2', '10101', '页面中存在未添加alt属性的图片', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10101' and issues_level='2' and issues_code = '10101' and issues_lable = '页面中存在未添加alt属性的图片' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10102', '2', '10102', '网站未启用gzip压缩', 'site_issues', '0', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10102' and issues_level='2' and issues_code = '10102' and issues_lable = '网站未启用gzip压缩' and issues_type='site_issues' and site_flag = '0' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10103', '2', '10103', '网站URL超过76个字节', 'site_issues', '1', '0', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10103' and issues_level='2' and issues_code = '10103' and issues_lable = '网站URL超过76个字节' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10104', '2', '10104', 'title长度超过80个字符', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10104' and issues_level='2' and issues_code = '10104' and issues_lable = 'title长度超过80个字符' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10105', '2', '10105', 'description长度超过200', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10105' and issues_level='2' and issues_code = '10105' and issues_lable = 'description长度超过200' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '10106', '2', '10106', '页面缺少H2或H3标签', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '10106' and issues_level='2' and issues_code = '10106' and issues_lable = '页面缺少H2或H3标签' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');
INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20100', '1', '20100', '页面中存在flash、iframe框架等内容', 'site_issues', '1', '0', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20100' and issues_level='1' and issues_code = '20100' and issues_lable = '页面中存在flash、iframe框架等内容' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '0');
INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20101', '1', '20101', '网站导出数量未被收录', 'site_issues', '1', '0', '1', '1' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20101' and issues_level='1' and issues_code = '20101' and issues_lable = '网站导出数量未被收录' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '1');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20102', '1', '20102', '网站的访问速度超过10s', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20102' and issues_level='1' and issues_code = '20102' and issues_lable = '网站的访问速度超过10s' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20103', '1', '20103', '网站不存在robots文件', 'site_issues', '1', '0', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20103' and issues_level='1' and issues_code = '20103' and  issues_lable = '网站不存在robots文件' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20104', '1', '20104', '关键词没有在title或者description标签中出现', 'site_issues', '0', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20104' and issues_level='1' and issues_code = '20104' and issues_lable = '关键词没有在title或者description标签中出现' and issues_type='site_issues' and site_flag = '0' and page_flag = '1' and sort='1' and del_flag = '1');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '20105', '1', '20105', '页面关键词密度小于2%或者大于8%', 'site_issues', '0', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '20105' and issues_level='1' and issues_code = '20105' and issues_lable = '页面关键词密度小于2%或者大于8%' and issues_type='site_issues' and site_flag = '0' and page_flag = '1' and sort='1' and del_flag = '1');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30100', '0', '30100', 'title属性重复', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30100' and issues_level='0' and issues_code = '30100' and issues_lable = 'title属性重复' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30101', '0', '30101', '网站未被搜索引擎收录', 'site_issues', '1', '0', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30101' and issues_level='0' and issues_code = '30101' and issues_lable = '网站未被搜索引擎收录' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30102', '0', '30102', '网站被搜索引擎标记为风险网站', 'site_issues', '1', '0', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30102' and issues_level='0' and issues_code = '30102' and issues_lable = '网站被搜索引擎标记为风险网站' and issues_type='site_issues' and site_flag = '1' and page_flag = '0' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30103', '0', '30103', '50**返回码', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30103' and issues_level='0' and issues_code = '30103' and issues_lable = '50**返回码' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');


INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30104', '0', '30104', '30**返回码', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30104' and issues_level='0' and issues_code = '30104' and issues_lable = '30**返回码' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');

INSERT INTO `td_b_dict_issues` (dict_issues_id,issues_level,issues_code,issues_lable,issues_type,site_flag,page_flag,sort,del_flag) 
SELECT '30105', '0', '30105', '40**返回码', 'site_issues', '1', '1', '1', '0' FROM DUAL WHERE NOT EXISTS (
SELECT * FROM `td_b_dict_issues` where dict_issues_id = '30105' and issues_level='0' and issues_code = '30105' and issues_lable = '40**返回码' and issues_type='site_issues' and site_flag = '1' and page_flag = '1' and sort='1' and del_flag = '0');
