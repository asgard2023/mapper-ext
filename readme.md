#tk.mybatis mapper扩展功能

## Quick Start
http://localhost:8080/index.html  
http://localhost:8080/doc.html

# 基于tk.mybatis mapper
见： https://gitee.com/free/Mapper


## 功能特性
* 支持selectIds select id from table_name where condition
* 支持selectMaxId select max(id) from table_name where condition
* 支持selectCountExist select 1 from table_name where condition limit 1
* 支持selectCountExistByExample select 1 from table_name where condition limit 1
* 支持selectCountAndMinMaxTimeByExample select count(*) count,min(create_time),max(create_time) from table_name where 

## 使用
* 配置见：MybatisConfiguration.java,MybatisLoadInit.java
* 使用见DflRoleBiz.java

## 演示示例
见： IDflRoleBizMapperExtTest.java