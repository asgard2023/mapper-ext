package tk.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlExtHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class ExampleExtProvider extends ExampleProvider {
    public ExampleExtProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 主要用于检查数据是否存在，直接取第一条，免count
     * select ifnull((select 1 from table_name limit 1),0)
     *
     * @param ms
     * @return
     */
    public String selectCountExistByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append("select ifnull((select 1 ");
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(" limit 1),0)");
        return sql.toString();
    }

    public String selectCountAndMaxTimeByExample(MappedStatement ms){
        return selectCountAndMinMaxTimeByExampleCall(ms, false, true);
    }

    public String selectCountAndMinTimeByExample(MappedStatement ms){
        return selectCountAndMinMaxTimeByExampleCall(ms, true, false);
    }

    public String selectCountAndMinMaxTimeByExample(MappedStatement ms){
        return selectCountAndMinMaxTimeByExampleCall(ms, true, true);
    }

    /**
     * SELECT count(*) count, min(create_time) minTime, max(create_time) maxTime
     * @param ms
     * @param isMin
     * @param isMax
     * @return
     */
    private String selectCountAndMinMaxTimeByExampleCall(MappedStatement ms, boolean isMin, boolean isMax){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append(SqlExtHelper.selectCountMinMaxCreateTime(isMin, isMax));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }

    /**
     * SELECT max(create_time)
     *
     * @param ms
     * @return
     */
    public String selectMaxCreateTimeByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlExtHelper.selectMaxCreateTime());
        sql.append(SqlExtHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlExtHelper.exampleWhereClause());
        return sql.toString();
    }

    /**
     * SELECT max(modify_time)
     *
     * @param ms
     * @return
     */
    public String selectMaxModifyDateByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlExtHelper.selectMaxUpdateTime());
        sql.append(SqlExtHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlExtHelper.exampleWhereClause());
        return sql.toString();
    }
}
