/*
 Navicat Premium Data Transfer

 Source Server         : localhost3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : canyin

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 30/04/2026 17:15:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dish_category
-- ----------------------------
DROP TABLE IF EXISTS `dish_category`;
CREATE TABLE `dish_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称(search-like)',
  `category_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类编号(search-like)',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序值(number)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '分类状态[0:禁用,1:启用]',
  `category_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类图片(img)',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类描述(textarea)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜品分类_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dish_category
-- ----------------------------
INSERT INTO `dish_category` VALUES (1, '热菜', 'DC001', 9, 1, 'http://localhost:8099/uploads/chain_catering/60d3c6cb-2788-4fac-96c7-3855cd0e0df3-kh1.png', '各类热炒、炖煮菜品，是正餐的主要组成部分', '2023-03-01 10:00:00');
INSERT INTO `dish_category` VALUES (2, '凉菜', 'DC002', 2, 1, NULL, '开胃凉菜，口感清爽，适合餐前食用', '2023-03-01 10:00:00');
INSERT INTO `dish_category` VALUES (3, '汤品', 'DC003', 3, 1, NULL, '各类营养汤羹，滋补养生，口感鲜美', '2023-03-01 10:00:00');
INSERT INTO `dish_category` VALUES (4, '主食', 'DC004', 4, 1, NULL, '米饭、面条、馒头等饱腹类主食', '2023-03-01 10:00:00');
INSERT INTO `dish_category` VALUES (5, '小吃', 'DC005', 5, 1, NULL, '特色小吃，口味多样，方便快捷', '2023-03-01 10:00:00');
INSERT INTO `dish_category` VALUES (6, '饮品', 'DC006', 6, 1, NULL, '各类酒水、饮料、茶饮，满足不同饮用需求', '2023-03-01 10:00:00');

-- ----------------------------
-- Table structure for dish_info
-- ----------------------------
DROP TABLE IF EXISTS `dish_info`;
CREATE TABLE `dish_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dish_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜品名称(search-like)',
  `category_id` bigint(20) NOT NULL COMMENT '所属分类<dish_category:id:category_name>',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格(number2)',
  `cost_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本价格(number2)',
  `discount_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠价格(number2)',
  `dish_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜品图片(img)',
  `material` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '食材配料(textarea)',
  `flavor` tinyint(2) NULL DEFAULT 1 COMMENT '口味[1:原味,2:微辣,3:中辣,4:特辣]',
  `cooking_time` int(11) NULL DEFAULT NULL COMMENT '制作时长(分钟)(number)',
  `calorie` decimal(8, 2) NULL DEFAULT NULL COMMENT '热量(千卡)(number2)',
  `is_spicy` tinyint(1) NULL DEFAULT 0 COMMENT '是否辣[0:否,1:是]',
  `is_vegetarian` tinyint(1) NULL DEFAULT 0 COMMENT '是否素食[0:否,1:是]',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜品描述(md)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '菜品状态[0:禁用,1:启用]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜品信息_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dish_info
-- ----------------------------
INSERT INTO `dish_info` VALUES (1, '鱼香肉丝', 1, 38.00, 15.00, 35.00, 'http://localhost:8099/uploads/chain_catering/212f9b80-18b9-41d0-bfcb-7d911c6587be-生成菜品饮品图片.png', '猪肉、木耳、胡萝卜、青椒、葱姜蒜', 2, 15, 280.50, 1, 0, '经典川菜，鱼香味浓郁，肉丝鲜嫩，配菜爽口', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (2, '麻婆豆腐', 1, 28.00, 10.00, 25.00, 'http://localhost:8099/uploads/chain_catering/fa94dffd-f492-4c63-b701-4d09e404ec83-生成菜品饮品图片 (4).png', '豆腐、牛肉末、豆瓣酱、花椒、葱姜蒜', 4, 10, 220.30, 1, 0, '麻辣鲜香，豆腐嫩滑，牛肉末酥香，下饭神器', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (3, '凉拌黄瓜', 2, 12.00, 3.00, 10.00, 'http://localhost:8099/uploads/chain_catering/e41bbda5-ff49-40ff-8aab-5aa7887e1126-生成菜品饮品图片 (2).png', '黄瓜、蒜末、生抽、醋、香油、盐', 1, 5, 50.20, 0, 1, '清爽解腻，口感脆嫩，简单易做的家常凉菜', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (4, '老火靓汤', 3, 48.00, 20.00, 45.00, 'http://localhost:8099/uploads/chain_catering/ecb5a279-1c90-4bcd-bf6b-8ac357d3077c-生成菜品饮品图片 (7).png', '排骨、玉米、胡萝卜、山药、姜片', 1, 120, 180.40, 0, 0, '慢火熬制，汤鲜味美，营养丰富，滋补养生', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (5, '扬州炒饭', 4, 22.00, 8.00, 20.00, 'http://localhost:8099/uploads/chain_catering/f91119b7-fbb3-4bf8-99bd-908addf56c3f-生成菜品饮品图片 (3).png', '米饭、鸡蛋、火腿、青豆、胡萝卜、葱花', 1, 10, 320.70, 0, 0, '颗粒分明，配料丰富，咸香适口，经典主食', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (6, '红糖糍粑', 5, 18.00, 6.00, 16.00, 'http://localhost:8099/uploads/chain_catering/45a93d8e-b395-4108-81e4-c9e94231968f-生成菜品饮品图片 (21).png', '糯米、红糖、黄豆粉、熟芝麻', 1, 10, 300.50, 0, 1, '外酥里糯，香甜可口，传统小吃，老少皆宜', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (7, '鲜榨橙汁', 6, 18.00, 7.00, 16.00, 'http://localhost:8099/uploads/chain_catering/b383d896-5b15-4d5b-bf7b-2a125f4c37de-生成菜品饮品图片 (1).png', '新鲜橙子、纯净水、冰糖', 1, 5, 120.80, 0, 1, '新鲜橙子现榨，口感酸甜，富含维生素C，健康饮品', 1, '2023-03-03 10:00:00');
INSERT INTO `dish_info` VALUES (8, '宫保鸡丁', 1, 32.00, 15.00, 29.90, 'http://localhost:8099/uploads/chain_catering/f34649d4-d886-4542-b8a9-942fa5ae9985-生成菜品饮品图片 (13).png', '鸡腿肉、花生米、干辣椒、黄瓜、葱段、姜片', 3, 28, 450.80, 1, 0, '麻辣鲜香，外酥里嫩，经典下饭硬菜', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (9, '清蒸鲈鱼', 1, 48.00, 22.00, 43.90, 'http://localhost:8099/uploads/chain_catering/ff5a62ef-21f9-4c0c-a238-337184713b9d-生成菜品饮品图片 (12).png', '鲈鱼、姜丝、葱段、料酒、生抽、蒸鱼豉油', 1, 35, 180.20, 0, 0, '鲜嫩可口，清淡不腻，保留鱼肉原汁原味', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (10, '红烧排骨', 1, 38.00, 18.00, 35.80, 'http://localhost:8099/uploads/chain_catering/161a2650-ab9e-4cc8-aebd-2acccb6fd44e-生成菜品饮品图片 (14).png', '猪肋排、冰糖、八角、桂皮、生抽、老抽、姜片', 1, 40, 520.30, 0, 0, '色泽红亮，肉质软烂，酱香浓郁入味', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (11, '凉拌海带丝', 2, 9.00, 3.00, 7.90, 'http://localhost:8099/uploads/chain_catering/7b1d8d28-6f85-43cb-bfaa-f428648428a3-生成菜品饮品图片 (11).png', '海带丝、蒜末、香醋、生抽、香油、小米辣、香菜', 2, 10, 60.50, 1, 1, '口感脆爽，酸辣开胃，解腻必备凉菜', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (12, '夫妻肺片', 2, 26.00, 12.00, 23.80, 'http://localhost:8099/uploads/chain_catering/023ba958-8270-4d24-b10a-54c7c1578207-生成菜品饮品图片 (10).png', '牛腱子肉、牛百叶、牛舌、辣椒油、花椒粉、生抽', 4, 15, 380.60, 1, 0, '麻辣鲜香，口感丰富，川菜凉菜经典', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (13, '凉拌藕片', 2, 10.00, 4.00, 8.90, 'http://localhost:8099/uploads/chain_catering/e5759127-d856-4c04-9c2c-a6646849b540-生成菜品饮品图片 (9).png', '莲藕、蒜末、香醋、白糖、生抽、辣椒油、葱花', 2, 12, 80.20, 1, 1, '清脆爽口，酸甜微辣，老少皆宜', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (14, '番茄鸡蛋汤', 3, 12.00, 4.00, 9.90, 'http://localhost:8099/uploads/chain_catering/819d6c1d-b3ce-4f95-acf1-a049581d7762-生成菜品饮品图片 (8).png', '番茄、鸡蛋、葱花、食盐、香油、水淀粉', 1, 15, 120.30, 0, 1, '汤色红润，酸甜可口，做法简单快捷', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (15, '冬瓜海带排骨汤', 3, 28.00, 13.00, 25.80, 'http://localhost:8099/uploads/chain_catering/6fabdf38-d7cf-4ab3-b499-963886ceb8b9-生成菜品饮品图片 (15).png', '冬瓜、海带、猪排骨、姜片、食盐、料酒', 1, 60, 210.70, 0, 0, '汤鲜味美，清热利湿，营养丰富', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (16, '香菇鸡汤', 3, 32.00, 15.00, 29.90, 'http://localhost:8099/uploads/chain_catering/43b91ed5-966e-4d73-b9b8-d173967ce4e9-生成菜品饮品图片 (18).png', '土鸡、干香菇、姜片、红枣、枸杞、食盐', 1, 90, 280.40, 0, 0, '汤浓味鲜，补气养血，滋补佳品', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (17, '番茄鸡蛋面', 4, 15.00, 6.00, 12.90, 'http://localhost:8099/uploads/chain_catering/647dce86-2544-4682-b488-22a28ede9ad8-生成菜品饮品图片 (16).png', '面条、番茄、鸡蛋、葱花、食盐、生抽、食用油', 1, 20, 350.80, 0, 1, '口感鲜香，饱腹感强，家常必备主食', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (18, '牛肉拉面', 4, 22.00, 10.00, 19.90, 'http://localhost:8099/uploads/chain_catering/a8ec1b5b-9d83-4661-8369-b82db67d5245-生成菜品饮品图片 (17).png', '拉面、牛腱子肉、香菜、蒜苗、牛肉汤、辣椒油', 2, 25, 480.50, 1, 0, '面条筋道，汤鲜味美，营养均衡', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (19, '杂粮饭', 4, 10.00, 4.00, 8.90, 'http://localhost:8099/uploads/chain_catering/4b0c2b9b-b298-4a30-96e2-9acf3d775a90-生成菜品饮品图片 (19).png', '大米、小米、糙米、黑米、燕麦米', 1, 30, 290.20, 0, 1, '口感软糯，营养丰富，健康低脂主食', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (20, '炸薯条', 5, 12.00, 5.00, 9.90, 'http://localhost:8099/uploads/chain_catering/dbd6e1bc-e966-457c-aee5-20a4eea90477-生成菜品饮品图片 (20).png', '土豆、食用油、食盐、番茄酱', 1, 20, 420.60, 0, 1, '外酥里嫩，香甜可口，老少皆宜小吃', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (21, '小笼包', 5, 18.00, 8.00, 15.90, 'http://localhost:8099/uploads/chain_catering/e7ac9786-289a-401a-bc6c-b3658f234bf5-生成菜品饮品图片 (26).png', '面粉、猪肉馅、生姜、葱花、生抽、料酒', 1, 25, 380.30, 0, 0, '皮薄馅大，汤汁鲜美，经典中式小吃', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (22, '烤肠', 5, 8.00, 3.00, 6.90, 'http://localhost:8099/uploads/chain_catering/639f8563-1e20-45e3-b01a-c6ac141f5441-生成菜品饮品图片 (24).png', '猪肉肠、食用油、黑胡椒粉、孜然粉', 1, 10, 260.80, 0, 0, '香气浓郁，口感弹牙，便捷美味小吃', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (23, '蜂蜜柠檬水', 6, 10.00, 3.00, 8.90, 'http://localhost:8099/uploads/chain_catering/8531967f-6bec-493e-a89f-4e690235ebce-生成菜品饮品图片 (23).png', '柠檬、蜂蜜、纯净水、冰块', 1, 5, 60.50, 0, 1, '酸甜可口，清热解暑，补水养颜饮品', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (24, '酸梅汤', 6, 12.00, 4.00, 9.90, 'http://localhost:8099/uploads/chain_catering/469e09f4-109b-4c86-85b5-3131b9fc78e4-生成菜品饮品图片 (25).png', '乌梅、山楂、甘草、冰糖、陈皮、纯净水', 1, 40, 90.20, 0, 1, '酸甜开胃，解腻消暑，传统中式饮品', 1, '2026-02-04 20:56:25');
INSERT INTO `dish_info` VALUES (25, '原味豆浆', 6, 6.00, 2.00, 4.90, 'http://localhost:8099/uploads/chain_catering/21ddc9fc-98ba-4f2a-94ab-bc852b7c6858-生成菜品饮品图片 (22).png', '黄豆、纯净水、冰糖', 1, 25, 80.70, 0, 1, '口感醇厚，营养丰富，早餐必备饮品', 1, '2026-02-04 20:56:25');

-- ----------------------------
-- Table structure for ingredient
-- ----------------------------
DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ingredient_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原材料名称',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '斤' COMMENT '单位（斤/个/包/升等）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态[0:禁用,1:启用]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_ingredient_name`(`ingredient_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '原材料基础表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ingredient
-- ----------------------------
INSERT INTO `ingredient` VALUES (1, '猪肉', '斤', '新鲜猪肉，用于制作各类菜品', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (2, '牛肉', '斤', '优质牛肉，适合炖煮和炒制', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (3, '鸡肉', '斤', '新鲜鸡肉，营养丰富', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (4, '鱼肉', '斤', '新鲜鱼类，适合清蒸和红烧', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (5, '鸡蛋', '个', '新鲜鸡蛋，用于炒菜和汤品', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (6, '大米', '斤', '优质大米，主食原料', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (7, '面粉', '斤', '中筋面粉，用于制作面食', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (8, '食用油', '升', '菜籽油，烹饪用油', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (9, '盐', '包', '食用盐，调味必备', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (10, '糖', '斤', '白砂糖，用于调味和制作甜品', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (11, '酱油', '瓶', '生抽酱油，调味上色', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (12, '醋', '瓶', '陈醋，用于凉拌和调味', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (13, '料酒', '瓶', '黄酒，去腥增香', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (14, '生姜', '斤', '新鲜生姜，去腥提味', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (15, '大蒜', '斤', '新鲜大蒜，调味增香', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (16, '大葱', '斤', '新鲜大葱，用于炒菜和调味', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (17, '胡萝卜', '斤', '新鲜胡萝卜，营养丰富', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (18, '土豆', '斤', '新鲜土豆，适合多种烹饪方式', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (19, '白菜', '斤', '新鲜白菜，适合炒制和做汤', 1, '2026-03-15 10:00:00');
INSERT INTO `ingredient` VALUES (20, '青椒', '斤', '新鲜青椒，用于配菜和调味', 1, '2026-03-15 10:00:00');

-- ----------------------------
-- Table structure for member_info
-- ----------------------------
DROP TABLE IF EXISTS `member_info`;
CREATE TABLE `member_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号(search-like)',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名(search-like)',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '性别[0:未知,1:男,2:女]',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期(time)(search-bw)',
  `register_store_id` bigint(20) NULL DEFAULT NULL COMMENT '注册门店<sys_store:id:store_name>',
  `register_time` datetime NULL DEFAULT NULL COMMENT '注册时间(time)(search-bw)',
  `total_points` int(11) NULL DEFAULT 0 COMMENT '总积分(number)',
  `consume_amount` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '累计消费金额(number2)',
  `consume_count` int(11) NULL DEFAULT 0 COMMENT '消费次数(number)',
  `last_consume_time` datetime NULL DEFAULT NULL COMMENT '最后消费时间(time)(search-bw)',
  `preferred_flavor` tinyint(2) NULL DEFAULT 1 COMMENT '偏好口味[1:原味,2:微辣,3:中辣,4:特辣]',
  `is_vip` tinyint(1) NULL DEFAULT 0 COMMENT '是否VIP[0:否,1:是]',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员备注(textarea)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '会员状态[0:禁用,1:正常]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员信息_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_info
-- ----------------------------
INSERT INTO `member_info` VALUES (1, '13900000001', '张会员', 1, '1990-01-01', 1, '2023-04-01 10:00:00', 870, 2587.00, 13, '2026-02-04 22:43:33', 2, 1, '经常到店消费，口味偏好微辣', 1, '2023-04-01 10:00:00');
INSERT INTO `member_info` VALUES (2, '13900000002', '李会员', 2, '1992-02-02', 1, '2023-04-01 10:00:00', 300, 1500.00, 8, '2023-05-08 19:00:00', 1, 0, '偶尔到店消费，偏好原味菜品', 1, '2023-04-01 10:00:00');
INSERT INTO `member_info` VALUES (3, '13900000003', '王会员', 1, '1988-03-03', 2, '2023-04-01 10:00:00', 800, 4000.00, 15, '2023-05-12 12:30:00', 3, 1, 'VIP会员，消费能力强，偏好中辣', 1, '2023-04-01 10:00:00');
INSERT INTO `member_info` VALUES (4, '13900000004', '赵会员', 2, '1995-04-04', 2, '2023-04-01 10:00:00', 200, 1000.00, 5, '2023-05-05 20:00:00', 1, 0, '新注册会员，消费次数较少', 1, '2023-04-01 10:00:00');
INSERT INTO `member_info` VALUES (5, '13900000005', '钱会员', 1, '1993-05-05', 3, '2023-04-01 10:00:00', 400, 2000.00, 10, '2023-05-09 18:00:00', 2, 0, '经常消费，偏好微辣菜品', 1, '2023-04-01 10:00:00');
INSERT INTO `member_info` VALUES (6, '15618526312', 'azhou', 0, NULL, 1, '2026-02-04 22:20:07', 630, 175.80, 3, '2026-02-04 22:33:15', 1, 0, NULL, 1, '2026-02-04 22:20:07');

-- ----------------------------
-- Table structure for member_points_record
-- ----------------------------
DROP TABLE IF EXISTS `member_points_record`;
CREATE TABLE `member_points_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID<member_info:id:real_name>',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '门店ID<sys_store:id:store_name>',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '关联订单<order_main:id:order_no>',
  `points_type` tinyint(1) NOT NULL COMMENT '积分类型[1:增加,2:减少]',
  `points_num` int(11) NOT NULL COMMENT '积分数量(number)',
  `biz_type` tinyint(2) NOT NULL COMMENT '业务类型[1:消费获取,2:积分抵扣]',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人<user_info:id:real_name>',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注(textarea)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员积分记录_son-member_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_points_record
-- ----------------------------
INSERT INTO `member_points_record` VALUES (1, 1, 1, 1, 1, 120, 1, 4, '消费获取积分', '2023-05-01 18:50:00');
INSERT INTO `member_points_record` VALUES (2, 2, 1, 2, 1, 83, 1, 5, '消费获取积分', '2023-05-01 19:25:00');
INSERT INTO `member_points_record` VALUES (3, 2, 1, 2, 2, 50, 2, 5, '积分抵扣消费', '2023-05-01 19:25:00');
INSERT INTO `member_points_record` VALUES (4, 3, 2, 3, 1, 200, 1, 6, '消费获取积分', '2023-05-02 12:50:00');
INSERT INTO `member_points_record` VALUES (5, 4, 2, 4, 1, 68, 1, 7, '消费获取积分', '2023-05-02 20:20:00');
INSERT INTO `member_points_record` VALUES (6, 5, 3, 5, 1, 148, 1, 4, '消费获取积分', '2023-05-03 18:20:00');
INSERT INTO `member_points_record` VALUES (7, 6, 1, 9, 1, 260, 1, 2, '订单消费获得积分', '2026-02-04 22:20:16');
INSERT INTO `member_points_record` VALUES (8, 6, 1, 8, -1, 260, 2, 2, '订单抵扣积分', '2026-02-04 22:26:56');
INSERT INTO `member_points_record` VALUES (9, 6, 1, 8, 1, 860, 1, 2, '订单消费获得积分', '2026-02-04 22:26:56');
INSERT INTO `member_points_record` VALUES (10, 6, 1, 10, -1, 860, 2, 2, '订单抵扣积分', '2026-02-04 22:33:15');
INSERT INTO `member_points_record` VALUES (11, 6, 1, 10, 1, 630, 1, 2, '订单消费获得积分', '2026-02-04 22:33:15');
INSERT INTO `member_points_record` VALUES (12, 1, 1, 11, -1, 500, 2, 2, '订单抵扣积分', '2026-02-04 22:43:33');
INSERT INTO `member_points_record` VALUES (13, 1, 1, 11, 1, 870, 1, 2, '订单消费获得积分', '2026-02-04 22:43:33');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID<order_main:id:order_no>',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品ID<dish_info:id:dish_name>',
  `dish_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜品名称',
  `flavor` tinyint(2) NULL DEFAULT 1 COMMENT '菜品口味[1:原味,2:微辣,3:中辣,4:特辣]',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价(number2)',
  `quantity` int(11) NOT NULL COMMENT '数量(number)',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '小计金额(number2)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单明细_son-order_main' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (1, 1, 1, '鱼香肉丝', 2, 38.00, 1, 38.00, '2023-05-01 18:30:00');
INSERT INTO `order_item` VALUES (2, 1, 3, '凉拌黄瓜', 1, 12.00, 1, 12.00, '2023-05-01 18:30:00');
INSERT INTO `order_item` VALUES (3, 1, 5, '扬州炒饭', 1, 22.00, 1, 22.00, '2023-05-01 18:30:00');
INSERT INTO `order_item` VALUES (4, 1, 7, '鲜榨橙汁', 1, 18.00, 2, 36.00, '2023-05-01 18:30:00');
INSERT INTO `order_item` VALUES (5, 2, 2, '麻婆豆腐', 4, 28.00, 1, 28.00, '2023-05-01 19:05:00');
INSERT INTO `order_item` VALUES (6, 2, 3, '凉拌黄瓜', 1, 12.00, 1, 12.00, '2023-05-01 19:05:00');
INSERT INTO `order_item` VALUES (7, 2, 5, '扬州炒饭', 1, 22.00, 1, 22.00, '2023-05-01 19:05:00');
INSERT INTO `order_item` VALUES (8, 3, 4, '老火靓汤', 1, 48.00, 1, 48.00, '2023-05-02 12:30:00');
INSERT INTO `order_item` VALUES (9, 3, 5, '扬州炒饭', 1, 22.00, 2, 44.00, '2023-05-02 12:30:00');
INSERT INTO `order_item` VALUES (10, 3, 7, '鲜榨橙汁', 1, 18.00, 2, 36.00, '2023-05-02 12:30:00');
INSERT INTO `order_item` VALUES (11, 6, 13, '凉拌藕片', 1, 10.00, 2, 20.00, '2026-02-04 21:38:48');
INSERT INTO `order_item` VALUES (12, 6, 12, '夫妻肺片', 1, 26.00, 3, 78.00, '2026-02-04 21:38:48');
INSERT INTO `order_item` VALUES (13, 7, 11, '凉拌海带丝', 1, 9.00, 1, 9.00, '2026-02-04 21:46:36');
INSERT INTO `order_item` VALUES (14, 7, 13, '凉拌藕片', 1, 10.00, 1, 10.00, '2026-02-04 21:46:36');
INSERT INTO `order_item` VALUES (15, 7, 3, '凉拌黄瓜', 1, 12.00, 1, 12.00, '2026-02-04 21:46:36');
INSERT INTO `order_item` VALUES (16, 7, 15, '冬瓜海带排骨汤', 1, 28.00, 1, 28.00, '2026-02-04 21:46:36');
INSERT INTO `order_item` VALUES (17, 8, 11, '凉拌海带丝', 1, 9.00, 1, 9.00, '2026-02-04 21:58:19');
INSERT INTO `order_item` VALUES (18, 8, 16, '香菇鸡汤', 1, 32.00, 1, 32.00, '2026-02-04 21:58:19');
INSERT INTO `order_item` VALUES (19, 8, 4, '老火靓汤', 1, 48.00, 1, 48.00, '2026-02-04 21:58:19');
INSERT INTO `order_item` VALUES (20, 9, 12, '夫妻肺片', 1, 26.00, 1, 26.00, '2026-02-04 22:00:08');
INSERT INTO `order_item` VALUES (21, 10, 12, '夫妻肺片', 1, 26.00, 1, 26.00, '2026-02-04 22:32:48');
INSERT INTO `order_item` VALUES (22, 10, 13, '凉拌藕片', 1, 10.00, 1, 10.00, '2026-02-04 22:32:48');
INSERT INTO `order_item` VALUES (23, 10, 3, '凉拌黄瓜', 1, 12.00, 3, 36.00, '2026-02-04 22:32:48');
INSERT INTO `order_item` VALUES (24, 11, 13, '凉拌藕片', 1, 10.00, 1, 10.00, '2026-02-04 22:40:35');
INSERT INTO `order_item` VALUES (25, 11, 12, '夫妻肺片', 1, 26.00, 1, 26.00, '2026-02-04 22:40:35');
INSERT INTO `order_item` VALUES (26, 11, 3, '凉拌黄瓜', 1, 12.00, 1, 12.00, '2026-02-04 22:40:35');
INSERT INTO `order_item` VALUES (27, 11, 14, '番茄鸡蛋汤', 1, 12.00, 1, 12.00, '2026-02-04 22:40:35');
INSERT INTO `order_item` VALUES (28, 11, 16, '香菇鸡汤', 1, 32.00, 1, 32.00, '2026-02-04 22:40:35');
INSERT INTO `order_item` VALUES (29, 12, 11, '凉拌海带丝', 1, 9.00, 1, 9.00, '2026-02-04 22:43:42');
INSERT INTO `order_item` VALUES (30, 12, 12, '夫妻肺片', 1, 26.00, 2, 52.00, '2026-02-04 22:43:42');
INSERT INTO `order_item` VALUES (31, 12, 15, '冬瓜海带排骨汤', 1, 28.00, 1, 28.00, '2026-02-04 22:43:42');
INSERT INTO `order_item` VALUES (32, 12, 14, '番茄鸡蛋汤', 1, 12.00, 1, 12.00, '2026-02-04 22:43:42');
INSERT INTO `order_item` VALUES (33, 13, 11, '凉拌海带丝', 1, 9.00, 1, 9.00, '2026-02-04 22:47:55');
INSERT INTO `order_item` VALUES (34, 13, 12, '夫妻肺片', 1, 26.00, 1, 26.00, '2026-02-04 22:47:55');
INSERT INTO `order_item` VALUES (35, 14, 12, '夫妻肺片', 1, 26.00, 1, 26.00, '2026-02-04 22:48:17');
INSERT INTO `order_item` VALUES (36, 14, 3, '凉拌黄瓜', 1, 12.00, 1, 12.00, '2026-02-04 22:48:17');
INSERT INTO `order_item` VALUES (37, 14, 13, '凉拌藕片', 1, 10.00, 1, 10.00, '2026-02-04 22:48:17');
INSERT INTO `order_item` VALUES (38, 14, 15, '冬瓜海带排骨汤', 1, 28.00, 1, 28.00, '2026-02-04 22:48:17');
INSERT INTO `order_item` VALUES (39, 14, 14, '番茄鸡蛋汤', 1, 12.00, 1, 12.00, '2026-02-04 22:48:17');

-- ----------------------------
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号(search-like)',
  `store_id` bigint(20) NOT NULL COMMENT '所属门店<sys_store:id:store_name>',
  `clerk_id` bigint(20) NULL DEFAULT NULL COMMENT '下单店员<user_info:id:real_name>',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '消费会员<member_info:id:real_name>',
  `table_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '桌号(search-like)',
  `order_status` tinyint(2) NULL DEFAULT 1 COMMENT '订单状态[1:待付款,2:已付款,3:已完成,4:已取消]',
  `order_amount` decimal(10, 2) NOT NULL COMMENT '订单金额(number2)',
  `discount_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '优惠金额(number2)',
  `points_deduct` int(11) NULL DEFAULT 0 COMMENT '抵扣积分(number)',
  `points_deduct_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '积分抵扣金额(number2)',
  `actual_pay` decimal(10, 2) NOT NULL COMMENT '实付金额(number2)',
  `pay_type` tinyint(2) NULL DEFAULT 1 COMMENT '支付方式[1:微信,2:支付宝,3:现金,4:刷卡]',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间(time)(search-bw)',
  `complete_time` datetime NULL DEFAULT NULL COMMENT '完成时间(time)(search-bw)',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间(time)(search-bw)',
  `cancel_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取消原因(textarea)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注(textarea)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单主表_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_main
-- ----------------------------
INSERT INTO `order_main` VALUES (1, 'ORD20230501001', 1, 4, 1, 'A01', 3, 128.00, 8.00, 0, 0.00, 120.00, 1, '2023-05-01 18:35:00', '2023-05-01 18:50:00', NULL, NULL, '无特殊要求', '2023-05-01 18:30:00');
INSERT INTO `order_main` VALUES (2, 'ORD20230501002', 1, 5, 2, 'A02', 3, 88.00, 0.00, 50, 5.00, 83.00, 2, '2023-05-01 19:10:00', '2023-05-01 19:25:00', NULL, NULL, '少放辣椒', '2023-05-01 19:05:00');
INSERT INTO `order_main` VALUES (3, 'ORD20230502001', 2, 6, 3, 'B01', 3, 218.00, 18.00, 0, 0.00, 200.00, 3, '2023-05-02 12:35:00', '2023-05-02 12:50:00', NULL, NULL, 'VIP客户，优先上菜', '2023-05-02 12:30:00');
INSERT INTO `order_main` VALUES (4, 'ORD20230502002', 2, 7, 4, 'B02', 3, 68.00, 0.00, 0, 0.00, 68.00, 1, '2023-05-02 20:05:00', '2023-05-02 20:20:00', NULL, NULL, '打包带走', '2023-05-02 20:00:00');
INSERT INTO `order_main` VALUES (5, 'ORD20230503001', 3, 4, 5, 'C01', 3, 158.00, 10.00, 0, 0.00, 148.00, 2, '2023-05-03 18:05:00', '2023-05-03 18:20:00', NULL, NULL, '无特殊要求', '2023-05-03 18:00:00');
INSERT INTO `order_main` VALUES (6, 'O1770212328025', 1, 2, NULL, NULL, 4, 98.00, 0.00, 0, 0.00, 98.00, 1, '2026-02-04 21:38:48', '2026-02-04 21:38:48', '2026-02-04 22:39:01', '3333333333', '', '2026-02-04 21:38:48');
INSERT INTO `order_main` VALUES (8, 'O1770213499358', 1, 2, 6, NULL, 3, 89.00, 0.00, 260, 2.60, 86.40, 1, '2026-02-04 22:26:56', '2026-02-04 22:26:56', NULL, NULL, '', '2026-02-04 21:58:19');
INSERT INTO `order_main` VALUES (10, 'O1770215568168', 1, 2, 6, 'A02', 3, 72.00, 0.00, 860, 8.60, 63.40, 3, '2026-02-04 22:33:15', '2026-02-04 22:33:15', NULL, NULL, '', '2026-02-04 22:32:48');
INSERT INTO `order_main` VALUES (11, 'O1770216034671', 1, 2, 1, 'A022', 2, 92.00, 0.00, 500, 5.00, 87.00, 1, '2026-02-04 22:43:33', '2026-02-04 22:43:33', NULL, NULL, '', '2026-02-04 22:40:35');
INSERT INTO `order_main` VALUES (12, 'O1770216221993', 1, 2, NULL, NULL, 1, 101.00, 0.00, 0, 0.00, 101.00, 1, NULL, NULL, NULL, NULL, '', '2026-02-04 22:43:42');
INSERT INTO `order_main` VALUES (13, 'O1770216474658', 1, 2, NULL, NULL, 4, 35.00, 0.00, 0, 0.00, 35.00, 1, NULL, NULL, '2026-02-04 22:48:08', '', '', '2026-02-04 22:47:55');
INSERT INTO `order_main` VALUES (14, 'O1770216497012', 1, 2, NULL, NULL, 1, 88.00, 0.00, 0, 0.00, 88.00, 1, NULL, NULL, NULL, NULL, '', '2026-02-04 22:48:17');

-- ----------------------------
-- Table structure for store_dish
-- ----------------------------
DROP TABLE IF EXISTS `store_dish`;
CREATE TABLE `store_dish`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `store_id` bigint(20) NOT NULL COMMENT '门店ID<sys_store:id:store_name>',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品ID<dish_info:id:dish_name>',
  `sale_status` tinyint(1) NULL DEFAULT 1 COMMENT '上架状态[0:下架,1:上架]',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '门店售价(number2)',
  `stock_count` int(11) NULL DEFAULT NULL COMMENT '库存数量(number)',
  `warning_count` int(11) NULL DEFAULT NULL COMMENT '库存预警值(number)',
  `sort` int(11) NULL DEFAULT NULL COMMENT '门店内排序值(number)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店菜品关联_son-sys_store' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_dish
-- ----------------------------
INSERT INTO `store_dish` VALUES (38, 5, 7, 1, 28.00, 20, 0, 0, '2026-02-04 17:33:57');
INSERT INTO `store_dish` VALUES (57, 1, 8, 1, 32.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (58, 1, 9, 1, 48.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (59, 1, 10, 1, 38.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (60, 1, 2, 1, 28.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (61, 1, 1, 1, 38.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (62, 1, 3, 1, 12.00, 14, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (63, 1, 13, 1, 10.00, 18, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (64, 1, 12, 1, 26.00, 17, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (65, 1, 11, 1, 9.00, 17, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (66, 1, 4, 1, 48.00, 19, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (67, 1, 14, 1, 12.00, 17, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (68, 1, 15, 1, 28.00, 17, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (69, 1, 16, 1, 32.00, 18, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (70, 1, 17, 1, 15.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (71, 1, 18, 1, 22.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (72, 1, 19, 1, 10.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (73, 1, 20, 1, 12.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (74, 1, 22, 1, 8.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (75, 1, 21, 1, 18.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (76, 1, 6, 1, 18.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (77, 1, 7, 1, 18.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (78, 1, 24, 1, 12.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (79, 1, 25, 1, 6.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (80, 1, 23, 1, 10.00, 20, 0, 0, '2026-02-04 21:10:05');
INSERT INTO `store_dish` VALUES (81, 2, 1, 1, 38.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (82, 2, 2, 1, 28.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (83, 2, 10, 1, 38.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (84, 2, 3, 1, 12.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (85, 2, 4, 1, 48.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (86, 2, 5, 1, 22.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (87, 2, 6, 1, 18.00, 0, 0, 0, '2026-02-04 23:14:50');
INSERT INTO `store_dish` VALUES (88, 2, 7, 1, 18.00, 0, 0, 0, '2026-02-04 23:14:50');

-- ----------------------------
-- Table structure for store_dish_category
-- ----------------------------
DROP TABLE IF EXISTS `store_dish_category`;
CREATE TABLE `store_dish_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `store_id` bigint(20) NOT NULL COMMENT '门店ID<sys_store:id:store_name>',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID<dish_category:id:category_name>',
  `sort` int(11) NULL DEFAULT NULL COMMENT '门店内排序值(number)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '分配状态[0:取消,1:生效]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店菜品分类关联_son-sys_store' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_dish_category
-- ----------------------------
INSERT INTO `store_dish_category` VALUES (45, 5, 6, 0, 1, '2026-02-04 17:33:57');
INSERT INTO `store_dish_category` VALUES (61, 1, 1, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (62, 1, 2, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (63, 1, 3, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (64, 1, 4, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (65, 1, 5, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (66, 1, 6, 0, 1, '2026-02-04 21:10:05');
INSERT INTO `store_dish_category` VALUES (67, 2, 1, 0, 1, '2026-02-04 23:14:50');
INSERT INTO `store_dish_category` VALUES (68, 2, 2, 0, 1, '2026-02-04 23:14:50');
INSERT INTO `store_dish_category` VALUES (69, 2, 3, 0, 1, '2026-02-04 23:14:50');
INSERT INTO `store_dish_category` VALUES (70, 2, 4, 0, 1, '2026-02-04 23:14:50');
INSERT INTO `store_dish_category` VALUES (71, 2, 5, 0, 1, '2026-02-04 23:14:50');
INSERT INTO `store_dish_category` VALUES (72, 2, 6, 0, 1, '2026-02-04 23:14:50');

-- ----------------------------
-- Table structure for store_ingredient_stock
-- ----------------------------
DROP TABLE IF EXISTS `store_ingredient_stock`;
CREATE TABLE `store_ingredient_stock`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `store_id` bigint(20) NOT NULL COMMENT '门店ID',
  `ingredient_id` bigint(20) NOT NULL COMMENT '原材料ID',
  `stock_quantity` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '库存数量',
  `warning_quantity` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '预警数量',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_store_ingredient`(`store_id`, `ingredient_id`) USING BTREE,
  INDEX `idx_store_id`(`store_id`) USING BTREE,
  INDEX `idx_ingredient_id`(`ingredient_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店原材料库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store_ingredient_stock
-- ----------------------------
INSERT INTO `store_ingredient_stock` VALUES (1, 1, 1, 50.00, 10.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (2, 1, 2, 30.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (3, 1, 3, 40.00, 10.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (4, 1, 4, 25.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (5, 1, 5, 200.00, 50.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (6, 1, 6, 100.00, 20.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (7, 1, 7, 80.00, 15.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (8, 1, 8, 20.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (9, 1, 9, 30.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (10, 1, 10, 15.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (11, 1, 11, 25.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (12, 1, 12, 20.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (13, 1, 13, 15.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (14, 1, 14, 10.00, 3.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (15, 1, 15, 8.00, 2.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (16, 2, 5, 150.00, 40.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (17, 2, 6, 120.00, 25.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (18, 2, 7, 90.00, 20.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (19, 2, 8, 25.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (20, 2, 9, 35.00, 9.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (21, 2, 10, 18.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (22, 2, 11, 30.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (23, 2, 12, 22.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (24, 2, 13, 18.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (25, 2, 14, 12.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (26, 2, 15, 10.00, 3.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (27, 2, 16, 15.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (28, 2, 17, 20.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (29, 2, 18, 35.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (30, 2, 19, 30.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (31, 2, 20, 25.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (32, 3, 1, 45.00, 12.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (33, 3, 2, 35.00, 10.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (34, 3, 3, 50.00, 15.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (35, 3, 4, 20.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (36, 3, 5, 180.00, 45.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (37, 3, 6, 110.00, 22.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (38, 3, 7, 85.00, 18.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (39, 3, 8, 18.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (40, 3, 9, 28.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (41, 3, 10, 12.00, 3.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (42, 3, 16, 18.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (43, 3, 17, 22.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (44, 3, 18, 40.00, 10.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (45, 3, 19, 35.00, 9.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (46, 3, 20, 28.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (47, 4, 3, 55.00, 15.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (48, 4, 4, 30.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (49, 4, 5, 220.00, 55.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (50, 4, 6, 130.00, 30.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (51, 4, 7, 95.00, 22.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (52, 4, 8, 30.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (53, 4, 9, 40.00, 10.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (54, 4, 10, 20.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (55, 4, 11, 35.00, 9.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (56, 4, 12, 28.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (57, 4, 13, 22.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (58, 4, 14, 15.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (59, 4, 15, 12.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (60, 4, 16, 20.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (61, 4, 17, 25.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (62, 4, 18, 45.00, 12.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (63, 5, 1, 60.00, 15.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (64, 5, 2, 40.00, 12.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (65, 5, 3, 48.00, 13.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (66, 5, 4, 28.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (67, 5, 5, 190.00, 50.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (68, 5, 11, 32.00, 9.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (69, 5, 12, 25.00, 7.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (70, 5, 13, 20.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (71, 5, 14, 14.00, 5.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (72, 5, 15, 11.00, 4.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (73, 5, 16, 22.00, 6.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (74, 5, 17, 28.00, 8.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (75, 5, 18, 50.00, 13.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (76, 5, 19, 42.00, 11.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');
INSERT INTO `store_ingredient_stock` VALUES (77, 5, 20, 32.00, 9.00, '2026-03-15 09:00:00', '2026-03-15 09:00:00');

-- ----------------------------
-- Table structure for sys_store
-- ----------------------------
DROP TABLE IF EXISTS `sys_store`;
CREATE TABLE `sys_store`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `store_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '门店名称(search-like)',
  `store_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '门店编号(search-like)',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店地址(textarea)',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话(search-like)',
  `business_status` tinyint(1) NULL DEFAULT 1 COMMENT '营业状态[0:停业,1:营业]',
  `business_hours` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业时间',
  `cover_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店封面(img)',
  `store_img` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店环境图片(img)',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店描述(md)',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '门店店长<user_info:id:real_name>',
  `area` decimal(10, 2) NULL DEFAULT NULL COMMENT '门店面积(number2)',
  `employee_count` int(11) NULL DEFAULT NULL COMMENT '员工数量(number)',
  `register_capital` decimal(12, 2) NULL DEFAULT NULL COMMENT '注册资金(number2)',
  `business_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业执照(img)',
  `tax_register_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税务登记号(search-like)',
  `parking_space` int(11) NULL DEFAULT NULL COMMENT '停车位数量(number)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '门店状态[0:停用,1:启用]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店信息_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_store
-- ----------------------------
INSERT INTO `sys_store` VALUES (1, '朝阳美食城店', 'ST001', '北京市朝阳区建国路88号', '010-88888881', 1, '10:00-22:00', 'http://localhost:8099/uploads/chain_catering/66ce625f-d0a1-407a-b686-7ae3a2bc2614-生成朝阳美食城门店图 (1).png', 'http://localhost:8099/uploads/chain_catering/3b674c66-4bad-4093-8766-94220198a8b3-生成朝阳美食城门店图 (3).png,http://localhost:8099/uploads/chain_catering/ed77bdac-7d26-4cb5-ad97-cae9626b250d-生成朝阳美食城门店图 (2).png,http://localhost:8099/uploads/chain_catering/c913415f-e78d-4e97-b6b4-392fbbfaeedb-生成朝阳美食城门店图 (1).png', '位于朝阳区核心商圈，交通便利，环境优雅，提供各类特色美食', 2, 150.00, 8, 500000.00, 'http://localhost:8099/uploads/chain_catering/89e1d479-d5d6-4981-b5aa-50af9dd663e2-1.jpg', '91110105MA01234567', 10, 1, '2023-02-01 10:00:00');
INSERT INTO `sys_store` VALUES (2, '西城风味馆店', 'ST002', '北京市西城区西单北大街12号', '010-88888882', 1, '09:30-23:00', NULL, NULL, '地处西城区繁华地段，主打地方特色风味，菜品丰富，服务周到', 3, 120.00, 6, 400000.00, NULL, '91110106MA01234568', 8, 1, '2023-02-01 10:00:00');
INSERT INTO `sys_store` VALUES (3, '海淀家常菜店', 'ST003', '北京市海淀区中关村大街56号', '010-88888883', 1, '10:00-21:30', NULL, NULL, '靠近高校区，以家常菜为主，价格亲民，适合学生和上班族', 2, 100.00, 5, 300000.00, NULL, '91110108MA01234569', 5, 1, '2023-02-01 10:00:00');
INSERT INTO `sys_store` VALUES (4, '丰台川湘馆店', 'ST004', '北京市丰台区南三环西路28号', '010-88888884', 1, '10:30-22:30', NULL, NULL, '专注川湘菜系，口味正宗，辣度可选，满足不同食客需求', 3, 130.00, 7, 450000.00, NULL, '91110106MA01234570', 12, 1, '2023-02-01 10:00:00');
INSERT INTO `sys_store` VALUES (5, '石景山粤菜馆店', 'ST005', '北京市石景山区石景山路66号', '010-88888885', 1, '11:00-22:00', NULL, NULL, '主打粤菜，食材新鲜，制作精细，提供早茶和正餐服务', 2, 140.00, 9, 550000.00, NULL, '91110107MA01234571', 15, 1, '2023-02-01 10:00:00');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号(search-like)',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名(search-like)',
  `role_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户角色[admin:系统管理员,manager:店长,clerk:店员]',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号(search-like)',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像(img)',
  `sex` tinyint(1) NULL DEFAULT 1 COMMENT '性别[0:女,1:男]',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄(number)',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号(search-like)',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱(search-like)',
  `entry_time` date NULL DEFAULT NULL COMMENT '入职时间(time)(search-bw)',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '薪资(number2)',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '账号状态[0:禁用,1:启用]',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '所属门店<sys_store:id:store_name>',
  `emergency_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系人',
  `emergency_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '紧急联系电话',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '居住地址(textarea)',
  `bank_card` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `education` tinyint(2) NULL DEFAULT 5 COMMENT '学历[1:小学,2:初中,3:高中,4:大专,5:本科,6:硕士,7:博士]',
  `political_status` tinyint(2) NULL DEFAULT 1 COMMENT '政治面貌[1:群众,2:党员,3:团员,4:其他]',
  `marital_status` tinyint(2) NULL DEFAULT 1 COMMENT '婚姻状况[1:未婚,2:已婚,3:离异,4:丧偶]',
  `health_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健康状况',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息(textarea)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户_menu' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin', '123456', '系统管理员', 'admin', '13800138000', NULL, 1, 30, '110101199401011234', 'admin@demo.com', '2023-01-01', '系统管理员', 15000.00, 1, NULL, '张三', '13900139000', '北京市海淀区', '6222021234567890123', '中国工商银行', 5, 2, 1, '良好', '系统初始管理员', '2023-01-01 10:00:00');
INSERT INTO `user_info` VALUES (2, 'manager01', '123456', '李店长', 'manager', '13800138001', NULL, 1, 32, '110101199202021235', 'manager01@demo.com', '2023-02-01', '门店店长', 12000.00, 1, 1, '李四', '13900139001', '北京市朝阳区', '6222021234567890124', '中国建设银行', 5, 2, 2, '良好', '负责门店日常运营', '2023-02-01 10:00:00');
INSERT INTO `user_info` VALUES (3, 'manager02', '123456', '王店长', 'manager', '13800138002', NULL, 0, 35, '110101198903031236', 'manager02@demo.com', '2023-02-01', '门店店长', 12000.00, 1, 2, '王五', '13900139002', '北京市西城区', '6222021234567890125', '中国农业银行', 5, 2, 2, '良好', '负责门店日常运营', '2023-02-01 10:00:00');
INSERT INTO `user_info` VALUES (4, 'clerk01', '123456', '赵店员', 'clerk', '13800138003', NULL, 1, 25, '110101199904041237', 'clerk01@demo.com', '2023-03-01', '前厅店员', 6000.00, 1, 1, '赵六', '13900139003', '北京市丰台区', '6222021234567890126', '中国银行', 4, 3, 1, '良好', '负责点餐收银', '2023-03-01 10:00:00');
INSERT INTO `user_info` VALUES (5, 'clerk02', '123456', '钱店员', 'clerk', '13800138004', NULL, 0, 24, '110101200005051238', 'clerk02@demo.com', '2023-03-01', '后厨店员', 6000.00, 1, 1, '钱七', '13900139004', '北京市石景山区', '6222021234567890127', '交通银行', 4, 3, 1, '良好', '负责菜品制作', '2023-03-01 10:00:00');
INSERT INTO `user_info` VALUES (6, 'clerk03', '123456', '孙店员', 'clerk', '13800138005', NULL, 1, 26, '110101199806061239', 'clerk03@demo.com', '2023-03-01', '前厅店员', 6000.00, 1, 2, '孙八', '13900139005', '北京市通州区', '6222021234567890128', '中国邮政储蓄银行', 4, 3, 1, '良好', '负责点餐收银', '2023-03-01 10:00:00');
INSERT INTO `user_info` VALUES (7, 'clerk04', '123456', '周店员', 'clerk', '13800138006', 'http://localhost:8099/uploads/chain_catering/e346be8b-0421-4304-b25e-0e4e03d6ad2d-微信图片_2025-08-20_201544_823.jpg', 0, 23, '110101200107071240', 'clerk04@demo.com', '2023-03-01', '后厨店员', 6000.00, 1, 2, '周九', '13900139006', '北京市顺义区', '6222021234567890129', '招商银行', 4, 3, 1, '良好', '负责菜品制作', '2023-03-01 10:00:00');
INSERT INTO `user_info` VALUES (8, 'clerk4', '123123', '123', 'clerk', '123123', NULL, 1, 123, NULL, NULL, NULL, NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, 5, 1, 1, NULL, NULL, '2026-02-04 23:28:50');

SET FOREIGN_KEY_CHECKS = 1;
