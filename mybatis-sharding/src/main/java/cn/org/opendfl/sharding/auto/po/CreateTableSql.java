package cn.org.opendfl.sharding.auto.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 建表语句查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTableSql {

    private String table;

    private String createTable;
}