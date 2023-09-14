package cn.org.opendfl.sharding.config.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.sharding.config.algorithm.TbShardingKeyAlgorithm;
import cn.org.opendfl.sharding.config.algorithm.TbShardingKeyDateAlgorithm;
import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private static MybatisType mybatisType = MybatisType.MYBATIS_PLUS;
    private static Date minDate = DateUtil.offsetMonth(new Date(), -120);

    public static void setMybatisType(String mybatisType) {
        MybatisType mybatisTypeEnum = MybatisType.parse(mybatisType);
        if (mybatisTypeEnum != null) {
            AnnotationUtils.mybatisType = mybatisTypeEnum;
        } else {
            log.warn("----setMybatisType--mybatisType={} invalid", mybatisType);
        }
    }

    public static void setMinDate(String minDate) {
        if (CharSequenceUtil.isBlank(minDate)) {
            return;
        }
        Date minDateValue = DateUtil.parseDate(minDate);
        if (minDateValue != null) {
            AnnotationUtils.minDate = minDateValue;
        } else {
            log.warn("----setMinDate--minDate={} invalid", minDate);
        }
    }

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
        String idField = idFieldMap.computeIfAbsent(className, k -> {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object idClass = null;
                if (mybatisType == MybatisType.MYBATIS_MAPPER) {
                    idClass = field.getAnnotation(Id.class);
                } else {
                    idClass = field.getAnnotation(TableId.class);
                }
                if (idClass != null) {
                    return field.getName();
                }
            }
            return "none";
        });
        return "none".equals(idField) ? null : idField;
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
        String tableName = getTableName(clazz);
        if (tableName == null) {
            return null;
        }

        ShardingKeyVo shardingKeyField = shardingKeyFieldMap.computeIfAbsent(className, k -> {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ShardingKey shardingKey = field.getAnnotation(ShardingKey.class);
                if (shardingKey != null) {
                    return new ShardingKeyVo(shardingKey, field.getName());
                }
            }
            return new ShardingKeyVo();
        });
        if (shardingKeyField.getField() == null) {
            return null;
        }

        tableShardingKeyMap.computeIfAbsent(tableName, k -> {
            return shardingKeyField;
        });
        return shardingKeyField.getField();
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
            Object tableClass = null;
            String tableName = null;
            if (mybatisType == MybatisType.MYBATIS_MAPPER) {
                Table table = (Table) clazz.getAnnotation(Table.class);
                tableClass = table;
                tableName = table.name();
            } else {
                TableName table = (TableName) clazz.getAnnotation(TableName.class);
                tableClass = table;
                tableName = table.value();
            }
            if (tableClass == null) {
                return null;
            }
            log.debug("getTableName-className={} tableName={}", className, tableName);
            return tableName;
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

    public static String getRealTableName(String dbName, Class entityClass, Date shardingValueDate) {
        String tableName = AnnotationUtils.getTableName(entityClass);
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(tableName);
        return TbShardingKeyDateAlgorithm.getShardingRealTableName(dbName, tableName, shardingValueDate, shardingKeyVo);
    }


    /**
     * 获取时间的结尾时间
     *
     * @param date
     * @param shardingType
     * @return
     */
    public static Date getDateEnd(Date date, ShardingType shardingType) {
        if (ShardingType.MONTH == shardingType) {
            date = DateUtil.offsetMonth(date, 1);
            date = DateUtil.truncate(date, DateField.MONTH);
        } else if (ShardingType.YEAR == shardingType) {
            date = DateUtil.offsetMonth(date, 12);
            date = DateUtil.truncate(date, DateField.YEAR);
        } else {
            date = DateUtil.offsetDay(date, 1);
            date = DateUtil.truncate(date, DateField.HOUR);
            date.setHours(0);
        }
        return date;
    }


    /**
     * 获得两个日期之间的所有月份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     * @author 510830970@qq.com
     */
    public static List<String> getTableBetween(String dbName, String logicTableName, ShardingKeyVo shardingKeyVo, Date minDate, Date maxDate) {
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(shardingKeyVo.getDateFormat());//格式化为年月
        String tbDbName = "";
        if (CharSequenceUtil.isNotBlank(dbName)) {
            tbDbName = dbName + ".";
        }
        String tablePrefix = ShardingTableUtils.getTablePrefix(shardingKeyVo.getTablePrefix(), logicTableName);
        ShardingType shardingType = ShardingType.parse(shardingKeyVo.getShardingType());
        Date curr = minDate;
        Date now = new Date();
        now = getDateEnd(now, shardingType);
        //minDate以shardKey的配置为优先
        final Date limitMinDate = shardingKeyVo.getMinDate() != null ? shardingKeyVo.getMinDate() : AnnotationUtils.minDate;
        final Date maxDateDate = getDateEnd(maxDate, shardingType);
        while (curr.before(maxDateDate)) {
            String table = tbDbName + tablePrefix + sdf.format(curr.getTime());
            curr = ShardingTableUtils.addDateByType(curr, 1, shardingType);
            //避免过低的时间表名
            if (curr.before(limitMinDate)) {
                continue;
            }
            result.add(table);
            //curr>now表示超过当前时间，则停止
            if (curr.after(now)) {
                break;
            }
        }
        return result;
    }
}


