delete from function;
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1000, '会员管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1001, '会员管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01010000', 1000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1002, '会员查询', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01010100', 1001);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1003, '已进入审核', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01010200', 1001);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1004, '资料审核', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01020000', 1000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1005, '待审列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01020100', 1004);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1006, '初定列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01020200', 1004);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1007, '终定列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01020300', 1004);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (1008, '驳回列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '01020400', 1004);


insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2000, '借款管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2001, '待审核借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000100', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2002, '招标中借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000200', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2003, '已满标借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000300', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2004, '还款中借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000400', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2005, '已还清借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000500', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2006, '流标借款列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000600', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2007, '初级催收列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000700', 2000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (2008, '高级催收列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '02000800', 2000);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (3000, '提现管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '03000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (3001, '新申请提现列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '03000100', 3000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (3002, '处理中提现列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '03000200', 3000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (3003, '成功提现列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '03000300', 3000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (3004, '失败提现列表', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '03000400', 3000);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (4000, '资金管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '04000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (4001, '网站资金帐户', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '04000100', 4000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (4002, '风险金资金帐户', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '04000200', 4000);


insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5000, '内容管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5001, '最新公告', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05000100', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5002, 'banner广告', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05000200', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5003, '新闻中心', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05010000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5004, '行业新闻', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05010100', 5003);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5005, '媒体报道', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05010200', 5003);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5006, '微金融故事', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05010300', 5003);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5007, '使用帮助', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5008, '常见问题', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020100', 5007);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5009, '平台原理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020200', 5007);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5010, '政策法规', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020300', 5007);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5011, '如何借款', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020400', 5007);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5012, '如何理财', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020500', 5007);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5013, '使用技巧', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05020600', 5007);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5014, '安全保障', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05030000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5015, '风险金代偿', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05030100', 5014);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5016, '风险管控', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05030200', 5014);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5017, '交易安全保障', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05030300', 5014);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5018, '网络安全保障', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05030400', 5014);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5019, '资费说明', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05040000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5020, '收费标准', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05040100', 5019);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5021, '提现标准', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05040200', 5019);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5022, '代理收费', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05040300', 5019);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5023, '关于我们', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05050000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5024, '公司简介', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05050100', 5023);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5025, '专家顾问', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05050200', 5023);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5026, '合作伙伴', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05050300', 5023);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5027, '联系我们', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05050400', 5023);

insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5028, '工具箱', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05060000', 5000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (5029, '借款协议范本', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '05060100', 5028);


insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (6000, '系统管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '06000000', null);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (6001, '帐号管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '06000100', 6000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (6002, '角色管理', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '06000200', 6000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (6003, '操作日志', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '06000300', 6000);
insert into "ROOT"."FUNCTION" (ID, FUNCTION_NAME, CREATE_TIME, REMARKS, FUNCTION_CODE, PARENT_ID) values (6004, '个人设置', to_date( '2000-11-26   00:04:47 ', 'yyyy-mm-dd   hh24:mi:ss '), '备注', '06000400', 6000);