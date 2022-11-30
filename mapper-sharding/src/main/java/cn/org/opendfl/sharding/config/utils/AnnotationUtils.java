package cn.org.opendfl.sharding.config.utils;

import cn.org.opendfl.sharding.config.algorithm.TbShardingKeyAlgorithm;
import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ShardingKey及id注解读取
 * 会以map方式缓存起来，以优化性能
 */
@Slf4j
public class AnnotationUtils {
    /**
     * map缓存大小，如果要分表的数据表很多，可以加大这个值
     */
    public static final int INIT_CACHE_SIZE = 32;

    /**
     * 用于初始化，以免加载时才去初始化
     *
     * @param clazz PO对象的class，如UserPo.java
     */
    public static void initShardkingKey(Class clazz) {
        getShardingKeyField(clazz);
        getIdField(clazz);
    }

    private static Map<String, String> idFieldMap = new ConcurrentHashMap<>(INIT_CACHE_SIZE);

    /**
     * 取对象Id属性名
     *
     * @param clazz PO对象的class，如UserPo.java
     * @return id属性名
     */
    public static String getIdField(Class clazz) {
        String className = clazz.getName();
        return idFieldMap.computeIfAbsent(className, k -> {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Id idField = field.getAnnotation(Id.class);
                if (idField != null) {
                    return field.getName();
                }
            }
            return null;
        });
    }

    private static Map<String, ShardingKeyVo> shardingKeyFieldMap = new ConcurrentHashMap<>(INIT_CACHE_SIZE);

    /**
     * 取对象shardingKey分片属性名
     *
     * @param clazz PO对象的class，如UserPo.java
     * @return shardingKey属性名
     */
    public static String getShardingKeyField(Class clazz) {
        String className = clazz.getName();

        ShardingKeyVo shardingKeyVo = shardingKeyFieldMap.computeIfAbsent(className, k -> {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ShardingKey shardingKey = field.getAnnotation(ShardingKey.class);
                if (shardingKey != null) {
                    log.debug("getShardingKeyField-className={} field={} shardCount={}", className, field, shardingKey.shardCount());
                    return new ShardingKeyVo(shardingKey, field.getName());
                }
            }
            return null;
        });

        if (shardingKeyVo == null) {
            return null;
        }

        String tableName = getTableName(clazz);
        tableShardingKeyMap.computeIfAbsent(tableName, k -> {
            log.debug("getShardingKeyField-tableName={} field={} shardCount={}", tableName, shardingKeyVo.getField(), shardingKeyVo.getShardCount());
            return shardingKeyVo;
        });
        return shardingKeyVo.getField();
    }


    private static Map<String, String> tableFieldMap = new ConcurrentHashMap<>(INIT_CACHE_SIZE);

    private static Map<String, ShardingKeyVo> tableShardingKeyMap = new ConcurrentHashMap<>(INIT_CACHE_SIZE);

    /**
     * 取对象对应的表名
     *
     * @param clazz PO对象的class，如UserPo.java
     * @return 表名
     */
    public static String getTableName(Class clazz) {
        String className = clazz.getName();
        return tableFieldMap.computeIfAbsent(className, k -> {
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table == null) {
                return null;
            }
            log.debug("getTableName-className={} table={}", className, table.name());
            return table.name();
        });
    }

    public static ShardingKeyVo getShardingKey(String tableName) {
        return tableShardingKeyMap.get(tableName);
    }


    /**
     * 获取分片key信息
     *
     * @param entityClass
     * @return
     */
    public static ShardingKeyVo getShardingKey(Class entityClass) {
        String tableName = AnnotationUtils.getTableName(entityClass);
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(tableName);
        return shardingKeyVo;
    }

    public static String getRealTableName(Class entityClass, Long shardingValue) {
        return getRealTableName(null, entityClass, shardingValue);
    }

    /**
     * 获取分表的实际表名
     *
     * @param entityClass
     * @param shardingValue
     * @return
     */
    public static String getRealTableName(String dbName, Class entityClass, Long shardingValue) {
        String tableName = AnnotationUtils.getTableName(entityClass);
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(tableName);
        return TbShardingKeyAlgorithm.getShardingRealTableName(dbName, tableName, shardingValue, shardingKeyVo);
    }
}


