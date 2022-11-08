#tk.mybatis mapper扩展功能

## Quick Start
http://localhost:8080/index.html

# 基于tk.mybatis mapper
见： https://gitee.com/free/Mapper

## 目标
* 支持对常用功能的统计，查询，以简化查询功能。
* 增加一些新特性。

## 功能特性
* 支持selectIds select id from table_name where condition
* 支持selectMaxId select max(id) from table_name where condition
* 支持selectCountExist select exists(select 1 from table_name where condition limit 1)
* 支持selectCountExistByExample select exists(select 1 from table_name where condition limit 1)
* 支持selectCountAndMinMaxTimeByExample select count(*) count,min(create_time),max(create_time) from table_name where 
* 支持sharding-jdbc注解，见mapper-sharding模块

## 使用
* 配置见：MybatisConfiguration.java,MybatisLoadInit.java
* 使用见DflRoleBiz.java

## 演示示例
见： IDflRoleBizMapperExtTest.java