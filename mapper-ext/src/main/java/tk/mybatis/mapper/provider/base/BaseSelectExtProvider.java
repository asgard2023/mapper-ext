package tk.mybatis.mapper.provider.base;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlExtHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Set;

public class BaseSelectExtProvider extends BaseSelectProvider{
    public BaseSelectExtProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectMaxId(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlExtHelper.selectMaxId(entityClass));
        sql.append(SqlExtHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlExtHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }


    /**
     * 查询主键Id
     *
     * @param ms
     * @return
     */
    public String selectIds(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        String pkColumn=SqlExtHelper.getPkIdColumn(entityClass);
        sql.append(pkColumn);
        sql.append(SqlExtHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlExtHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

    /**
     * 检查数据是否存在，性能
     *
     * @param ms
     * @return
     */
    public String selectCountExist(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("select ifnull((select 1 ");
        sql.append(SqlExtHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlExtHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(" limit 1),0)");
        return sql.toString();
    }

}
