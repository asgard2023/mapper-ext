package cn.org.opendfl.sharding.config.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;

import java.util.Date;

/**
 * @author chenjh
 */
public class ShardingTableUtils {
    private ShardingTableUtils() {

    }

    /**
     * tablePrefix为空则取逻辑表+‘_'，例如：t_user_
     *
     * @param tablePrefix
     * @param logicTableName
     * @return
     */
    public static String getTablePrefix(String tablePrefix, String logicTableName) {
        if (CharSequenceUtil.isBlank(tablePrefix)) {
            tablePrefix = logicTableName + "_";
        } else {
            if (!tablePrefix.endsWith("_")) {
                tablePrefix += "_";
            }
        }
        return tablePrefix;
    }

    /**
     * 按分布类型，加减时间
     * @param date
     * @param offset
     * @param shardingType
     * @return
     */
    public static Date addDateByType(Date date, int offset, ShardingType shardingType) {
        if (shardingType == ShardingType.DATE) {
            date = DateUtil.offsetDay(date, offset);
        } else if (shardingType == ShardingType.MONTH) {
            date = DateUtil.offsetMonth(date, offset);
        } else if (shardingType == ShardingType.YEAR) {
            date = DateUtil.offsetMonth(date, offset * 12);
        }
        return date;
    }
}
