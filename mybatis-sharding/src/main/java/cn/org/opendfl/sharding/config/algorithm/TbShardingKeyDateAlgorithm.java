package cn.org.opendfl.sharding.config.algorithm;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;

import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.config.utils.ShardingTableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * 按时间精确分表，比如按月，天等
 * 表分片算法，配合@ShardingKey一起使用
 * 然后以@ShardingKey配置的值为主导
 *
 * @author chenjh
 */
@Slf4j
public class TbShardingKeyDateAlgorithm implements PreciseShardingAlgorithm<Date> {
    /**
     * 精确分片算法（按月分表）
     *
     * @param tableNames           分表集合
     * @param preciseShardingValue 分片字段值
     * @return 分表
     */
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Date> preciseShardingValue) {
        ShardingKeyVo shardingKeyDateVo = AnnotationUtils.getShardingKey(preciseShardingValue.getLogicTableName());
        Date shardingValueDate = preciseShardingValue.getValue();
        return getShardingRealTableName(null, preciseShardingValue.getLogicTableName(), shardingValueDate, shardingKeyDateVo);
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, Date shardingValueDate, ShardingKeyVo shardingKeyVo) {
        String timeValue = DateUtil.format(shardingValueDate, shardingKeyVo.getDateFormat());
        return getShardingRealTableName(dbName, logicTableName, shardingKeyVo, timeValue);
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, ShardingKeyVo shardingKeyVo, String timeValue) {
        String tablePrefix = ShardingTableUtils.getTablePrefix(shardingKeyVo.getTablePrefix(), logicTableName);
        String tbDbName = "";
        if (CharSequenceUtil.isNotBlank(dbName)) {
            tbDbName = dbName + ".";
        }
        return tbDbName + tablePrefix + timeValue;
    }
}
