package cn.org.opendfl.sharding.auto.utils;


import cn.org.opendfl.sharding.auto.mapper.CommonMapper;
import cn.org.opendfl.sharding.auto.po.CreateTableSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 分表工具
 */
@Slf4j
public abstract class ShardingAlgorithmTool<T extends Comparable<?>> implements PreciseShardingAlgorithm<T>, RangeShardingAlgorithm<T> {

    private static CommonMapper commonMapper;

    private static String schemaName;
    private static final Set<String> tableNameCache = new HashSet<>();

    /**
     * 手动注入
     */
    public static void setCommonMapper(CommonMapper commonMapper) {
        ShardingAlgorithmTool.commonMapper = commonMapper;
    }
    public static void setSchemaName(String schemaName){
        ShardingAlgorithmTool.schemaName=schemaName;
    }

    /**
     * 判断 分表获取的表名是否存在 不存在则自动建表
     *
     * @param logicTableName  逻辑表名(表头)
     * @param resultTableName 真实表名
     * @return 确认存在于数据库中的真实表名
     */
    public String shardingTablesCheckAndCreatAndReturn(String logicTableName, String resultTableName) {
        // 缓存中有此表 返回
        if (shardingTablesExistsCheck(resultTableName)) {
            return resultTableName;
        }

        synchronized (logicTableName.intern()) {
            int exists = commonMapper.existTable(schemaName, logicTableName);
            //检查如果表已存在
            if(exists==1){
                tableNameCache.add(resultTableName);
            }
            else {
                // 缓存中无此表 建表 并添加缓存
                CreateTableSql createTableSql = commonMapper.selectTableCreateSql(logicTableName);
                String sql = createTableSql.getCreateTable();
                sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
                sql = sql.replace(logicTableName, resultTableName);
                commonMapper.executeSql(sql);
            }
            tableNameCache.add(resultTableName);
        }

        return resultTableName;
    }

    /**
     * 判断表是否存在于缓存中
     *
     * @param resultTableName 表名
     * @return 是否存在于缓存中
     */
    public boolean shardingTablesExistsCheck(String resultTableName) {
        return tableNameCache.contains(resultTableName);
    }

    /**
     * 缓存重载方法
     *
     * @param schemaName 待加载表名所属数据库名
     */
    public static void tableNameCacheReload(String schemaName) {
        // 读取数据库中所有表名
        List<String> tableNameList = commonMapper.getAllTableNameBySchema(schemaName);
        // 删除旧的缓存(如果存在)
        ShardingAlgorithmTool.tableNameCache.clear();
        // 写入新的缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableNameList);
    }

}