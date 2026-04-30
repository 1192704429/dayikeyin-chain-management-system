# 基于 SpringBoot3 + Vue2 + Redis + JDK17 的餐饮连锁信息管理系统的设计与实现

> 本文围绕连锁餐饮行业数字化转型需求，基于 SpringBoot3、Vue2、Redis 及 JDK17 技术栈，设计并实现了一套覆盖总部、门店与一线人员的连锁餐饮信息管理系统。

## 一、项目背景

随着连锁餐饮行业规模化发展，传统手工管理方式难以满足多门店统一管控、数据实时同步和高效运营的需求。本系统面向餐饮连锁企业，解决总部对门店信息、菜品、会员与营业数据缺乏统一管理平台的问题。同时，门店日常运营中的人员工管理、订单处理与库存控制也需要数字化支撑。本文设计的大可以餐饮连锁信息管理系统旨在实现总部全局管控、门店自主运营、一线便捷操作的三层结构，提升连锁餐饮整体运营效率与数据透明度。

## 二、功能模块介绍

系统划分为三个系统端：总部管理端、门店管理端与门店收银端。

| 系统端 | 对应角色 |
| --- | --- |
| 总部管理端 | 系统管理员 |
| 门店管理端 | 店长 |
| 门店收银端 | 店员 |


![功能模块图](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/azhou_promotion/img/module_structure/func_module_f5f6405f680f4aefbb13e08ba8a1cbee.png)


## 三、技术栈说明

前端：Vue2

后端：SpringBoot3、JDK17

数据库与中间件：Redis

## 四、核心模块介绍

###### 总部管理端

###### 系统管理员

| 功能模块 | 功能说明 |
| --- | --- |
| 门店管理 | 新增、编辑、启用、停用门店，维护门店基础信息（名称、地址、联系电话、营业状态） |
| 用户管理 | 查看整体系统人员信息（包括所有店长店员信息），可对账号进行启用、停用 |
| 菜品分类管理 | 给不同门店分配不同的菜品分类 |
| 菜品管理 | 菜品所属某个分类，某店有此分类则可以销售该分类下的菜品 |
| 会员管理 | 查看整个系统里的所有会员信息 |
| 原材料管理 | 集中管理原材料信息 |
| 统计分析（全局） | 连锁整体营业额统计、各门店销售数据对比、热销菜品统计 |

##### 界面展示

登录页：管理员输入登录信息，登录餐饮连锁后台系统

![登录页：管理员输入登录信息，登录餐饮连锁后台系统](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/8bdf712a-205f-42da-83a4-946079241880.png)

门店管理页：管理员查看并管理所有门店的基础信息

![门店管理页：管理员查看并管理所有门店的基础信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/2fef04c2-cb29-47b6-88f1-864d8ed20f97.png)

门店管理页：为指定门店分配可售卖的菜单与菜品

![门店管理页：为指定门店分配可售卖的菜单与菜品](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/e3945b00-9d0f-4167-82a7-c8ed48bcd1b3.png)

用户管理页：查看并管理系统内所有人员的账号信息

![用户管理页：查看并管理系统内所有人员的账号信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/5fe8e24d-1d00-4b48-8542-cdda21dc679c.png)

用户管理页：查询系统用户信息，查看指定用户详情

![用户管理页：查询系统用户信息，查看指定用户详情](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/177210e5-a55c-4b59-8702-e913299bf7e4.png)

菜品分类管理：管理菜品分类，支持增删改查操作

![菜品分类管理：管理菜品分类，支持增删改查操作](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/e9c7bdd0-4a3b-4732-811f-59a3db32cce2.png)

菜品管理：管理员查看并管理系统内的菜品信息

![菜品管理：管理员查看并管理系统内的菜品信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/0f03d1f1-b2f4-4b86-830a-84e7dbb1dc14.png)

菜品管理：编辑修改菜品的基础信息

![菜品管理：编辑修改菜品的基础信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/60d4b2d0-7ccc-4acc-80f7-e6f9a55d3504.png)

会员管理页：查询并管理餐饮系统的所有会员信息

![会员管理页：查询并管理餐饮系统的所有会员信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/3885de43-e7e8-4845-81de-1e91e5dfb3d6.png)

原材料管理页：查看并管理系统内所有原材料的基础信息

![原材料管理页：查看并管理系统内所有原材料的基础信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/1c2f4767-13c5-4c19-82ea-36186d613614.png)

门店库存管理：查看管理各门店的原材料库存信息

![门店库存管理：查看管理各门店的原材料库存信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/1335a36a-5e4f-496c-83a7-4eab7f6507dc.png)

订单管理页：按条件查询管理系统内的所有餐饮订单

![订单管理页：按条件查询管理系统内的所有餐饮订单](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/a900f231-f467-4281-8307-6fdca9e42d53.png)

订单管理页：查看所有订单，展示选中订单的详情

![订单管理页：查看所有订单，展示选中订单的详情](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/0703c74d-c18e-45d5-89f6-e80e42a5c70c.png)

统计分析页：统计展示各门店营业额与订单相关数据

![统计分析页：统计展示各门店营业额与订单相关数据](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/d500cf3b-3f7f-4cf6-84b7-a64fa1e9db3d.png)


###### 门店管理端

###### 店长

| 功能模块 | 功能说明 |
| --- | --- |
| 本门店信息管理 | 查看和维护本门店基础信息、营业状态 |
| 门店人员管理 | 查看本门店员工信息，可新增、编辑、管理、启用或停用店员账号 |
| 菜品管理（门店层面） | 查看可售菜品信息，控制菜品在本门店的上架与下架 |
| 订单管理 | 查看本门店所有订单及订单详情 |
| 会员管理 | 查看本门店的所有会员信息、会员积分 |
| 菜品库存管理 | 管理门店菜品库存 |
| 统计分析（本店） | 本门店营业额统计、菜品销售情况分析 |

##### 界面展示

门店信息管理页：查看维护本门店的基础信息与营业状态

![门店信息管理页：查看维护本门店的基础信息与营业状态](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/8ad88c73-1836-49c2-8bd6-28c0a472de37.png)

门店人员管理页：店长查看和管理本店的人员信息

![门店人员管理页：店长查看和管理本店的人员信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/34b941ff-8ca3-459b-820e-7cf9d28fa2c9.png)

菜品管理页：店长查看并管理本店可售菜品信息

![菜品管理页：店长查看并管理本店可售菜品信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/3bcde8bd-4120-4ff7-86d0-e6f8282c3c03.png)

库存管理页：管理查看本门店原材料库存信息

![库存管理页：管理查看本门店原材料库存信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/6c971324-d5e5-4aaf-89f9-6fee12c207d1.png)

订单管理页：店长查看并管理本店所有订单信息

![订单管理页：店长查看并管理本店所有订单信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/2767d7ae-3240-4911-8a19-58ce6240e99f.png)

会员管理页：店长查看管理本店所有会员信息

![会员管理页：店长查看管理本店所有会员信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/4b6cb681-29f8-454e-83ae-a19c6795af4e.png)

消费统计页：展示本店各类经营数据的统计信息

![消费统计页：展示本店各类经营数据的统计信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/6a69c73c-0fed-4215-8ec8-4eedbc59d66a.png)

收银结算页：选择菜品创建订单，完成顾客消费结算

![收银结算页：选择菜品创建订单，完成顾客消费结算](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/39829cb5-0aa9-45f9-8e89-c547e73e5d5c.png)


###### 门店收银端

###### 店员

| 功能模块 | 功能说明 |
| --- | --- |
| 点餐功能 | 为顾客创建堂食订单，选择菜品及数量，自动计算订单金额 |
| 订单结算 | 完成订单结算操作，订单完成后自动记录消费数据 |
| 会员操作 | 通过手机号为顾客注册会员，消费自动累计积分，积分可按比例抵扣消费金额 |
| 订单查询 | 查看本店历史订单记录 |

##### 界面展示

门店信息管理页：查看维护本门店的基础信息与营业状态

![门店信息管理页：查看维护本门店的基础信息与营业状态](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/8ad88c73-1836-49c2-8bd6-28c0a472de37.png)

门店人员管理页：店长查看和管理本店的人员信息

![门店人员管理页：店长查看和管理本店的人员信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/34b941ff-8ca3-459b-820e-7cf9d28fa2c9.png)

菜品管理页：店长查看并管理本店可售菜品信息

![菜品管理页：店长查看并管理本店可售菜品信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/3bcde8bd-4120-4ff7-86d0-e6f8282c3c03.png)

库存管理页：管理查看本门店原材料库存信息

![库存管理页：管理查看本门店原材料库存信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/6c971324-d5e5-4aaf-89f9-6fee12c207d1.png)

订单管理页：店长查看并管理本店所有订单信息

![订单管理页：店长查看并管理本店所有订单信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/2767d7ae-3240-4911-8a19-58ce6240e99f.png)

会员管理页：店长查看管理本店所有会员信息

![会员管理页：店长查看管理本店所有会员信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/4b6cb681-29f8-454e-83ae-a19c6795af4e.png)

消费统计页：展示本店各类经营数据的统计信息

![消费统计页：展示本店各类经营数据的统计信息](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/6a69c73c-0fed-4215-8ec8-4eedbc59d66a.png)

收银结算页：选择菜品创建订单，完成顾客消费结算

![收银结算页：选择菜品创建订单，完成顾客消费结算](https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2026-04-30/39829cb5-0aa9-45f9-8e89-c547e73e5d5c.png)


## 五、主要业务流程

1. 系统管理员创建门店并配置门店基础信息。
2. 系统管理员为门店分配菜品分类与菜品。
3. 店长登录系统，管理本门店人员，控制菜品上架与下架。
4. 店员为顾客创建堂食订单，选择菜品与数量，系统自动计算订单金额。
5. 店员完成订单结算，系统自动记录消费数据并累计会员积分。
6. 顾客可使用会员积分按比例抵扣消费金额。
7. 系统自动汇总门店营业数据，供店长查看本店统计分析。
8. 系统自动汇总全连锁营业数据，供系统管理员查看全局统计分析。

## 六、项目拓展性

- 支持增加线上外卖订单接入模块，扩展业务渠道。
- 支持接入第三方支付网关，丰富结算方式。
- 支持引入消息队列，提升高并发场景下订单处理性能。
- 支持增加供应链管理模块，强化原材料采购与配送流程。

## 代码说明 
本仓库为项目的 **后端核心代码部分** 主要用于展示系统的业务设计思路与后端实现方式 出于学习展示与版权保护考虑 前端实现、完整数据库脚本及部署配置未在本仓库中公开 

##  项目交流 
本项目用于学习展示 如需项目完整功能演示、答辩说明或技术交流 请前往作者 GitHub 主页查看联系方式