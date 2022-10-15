package tk.mybatis.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class SpecialExtProvider extends SpecialProvider{
    public SpecialExtProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }



    private static String driverName=null;
    private boolean isMysql(MappedStatement ms){
        boolean isMysql=false;

        if(driverName==null){
            synchronized (this) {
                try {
                    if(driverName!=null){
                        return driverName.toLowerCase().indexOf("mysql")>=0;
                    }
                    //Edit by caco
                    //driverName=ms.getConfiguration().getEnvironment().getDataSource().getConnection().getMetaData().getDriverName();;
                    Connection conn = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
                    driverName=conn.getMetaData().getDriverName();
                    conn.close();
                } catch (SQLException e) {
                    //				e.printStackTrace();
                    driverName="no";
                }
            }
        }
        isMysql=driverName.toLowerCase().indexOf("mysql")>=0;
        return isMysql;
    }

    public String insertList(MappedStatement ms, boolean skipId) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '" + ms.getId() + " 方法参数为空')\"/>");
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass), "list[0]"));
        sql.append(SqlHelper.insertColumns(entityClass, skipId, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");

        // 反射把MappedStatement中的设置主键名
        EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

        return sql.toString();
    }



    public String insertListById(MappedStatement ms) {
        return this.insertList(ms, true);
    }

    /**
     * 批量修改
     *
     * @param ms
     */
    public String updateListByIdSelective(MappedStatement ms) {

        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if(this.isMysql(ms)){
            sql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\" >");
        }
        else{
            sql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\" >");
        }

        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, "item", true, isNotEmpty()));
        sql.append(this.wherePKColumns(entityClass, "item"));
        sql.append("</foreach>");
        return sql.toString();
    }

    /**
     * 批量修改
     *
     * @param ms
     */
    public String updateListById(MappedStatement ms) {

        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if(this.isMysql(ms)){
            sql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\" >");
        }
        else{
            sql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\"end;\" separator=\";\" >");
        }
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, "item", false, !isNotEmpty()));
        sql.append(this.wherePKColumns(entityClass, "item"));
        sql.append("</foreach>");
        return sql.toString();
    }

    private String wherePKColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" and " + column.getColumnEqualsHolder(entityName));
        }
        sql.append("</where>");
        return sql.toString();
    }

}
