package tk.mybatis.mapper.mapperhelper;

import tk.mybatis.mapper.entity.EntityColumn;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SqlExtHelper extends SqlHelper {

    private static Set<String> ignoreColumnSet = new HashSet<>();
    private static Set<String> ignoreColumnAllSet = new HashSet<>();

    public static void addIgnore(Class<?> entityClass, String propertyName) {
        String className = null;
        if (entityClass != null) {
            className = entityClass.getName();
        }
        addIgnore(className, propertyName);
    }

    public static void addIgnore(String className, String propertyName) {
        String[] arrays = propertyName.split(",");
        for (String str : arrays) {
            if (className != null) {
                ignoreColumnSet.add(className + "." + str);
            } else {
                ignoreColumnAllSet.add(str);
            }
        }
    }

    public static boolean isIgnore(String className, String propertyName) {
        boolean isIgnore = false;
        if (ignoreColumnAllSet.contains(propertyName) ||
                ignoreColumnSet.contains(className + "." + propertyName)) {
            isIgnore = true;
        }
        return isIgnore;
    }

    /**
     * select max(id)
     *
     * @param entityClass
     * @return
     */
    public static String selectMaxId(Class<?> entityClass) {
        String pkColumn = getPkIdColumn(entityClass);
        return String.format("SELECT max(%s) ", pkColumn);
    }

    /**
     * 取第一个ID字段
     * @param entityClass
     * @return
     */
    public static String getPkIdColumn(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getPKColumns(entityClass);
        Optional<String> firstPkColumn= columnSet.stream().map(EntityColumn::getColumn).findFirst();
        String pkColumn=defaultIdField;
        if(firstPkColumn.isPresent()){
            pkColumn=firstPkColumn.get();
        }
        return pkColumn;
    }

    public static String selectMaxCreateTime(){
        return String.format("SELECT max(%s) ", defaultCreateTimeField);
    }

    public static String selectMaxUpdateTime(){
        return String.format("SELECT max(%s) ", defaultUpdateTimeField);
    }

    public static String selectCountMinMaxCreateTime(boolean isMin, boolean isMax) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) count");
        if(isMin) {
            sql.append(String.format(", min(%s) minTime", defaultCreateTimeField));
        }
        if(isMax) {
            sql.append(String.format(", max(%s) minTime", defaultCreateTimeField));
        }
        return sql.toString();
    }



    /**
     * 默认表id属性名
     */
    private static String defaultIdField = "id";

    /**
     * 默认表数据创建时间属性名
     */
    private static String defaultCreateTimeField = "create_time";
    /**
     * 默认表数据修改时间属性名
     */
    private static String defaultUpdateTimeField = "modify_time";

    public static void setDefaultIdField(String defaultIdField) {
        SqlExtHelper.defaultIdField = defaultIdField;
    }

    public static void setDefaultCreateTimeField(String defaultCreateTimeField) {
        SqlExtHelper.defaultCreateTimeField = defaultCreateTimeField;
    }

    public static void setDefaultUpdateTimeField(String defaultUpdateTimeField) {
        SqlExtHelper.defaultUpdateTimeField = defaultUpdateTimeField;
    }

}
