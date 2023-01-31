package cn.org.opendfl.sharding.config.algorithm;


import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.config.utils.CommUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
        String tablePrefix = shardingKeyDateVo.getTablePrefix();
        if (StringUtils.isBlank(tablePrefix)) {
            tablePrefix = preciseShardingValue.getLogicTableName() + "_";
        }
        String timeValue= DateFormatUtils.format(shardingValueDate, shardingKeyDateVo.getDateFormat());
        return tablePrefix + timeValue;
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, Date shardingValueDate, ShardingKeyVo shardingKeyVo) {
        String timeValue= DateFormatUtils.format(shardingValueDate, shardingKeyVo.getDateFormat());
        return getShardingRealTableName(dbName, logicTableName, shardingKeyVo, timeValue);
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, ShardingKeyVo shardingKeyVo, String timeValue) {
        //tablePrefix为空则取逻辑表+‘_'，例如：t_user_
        String tablePrefix = shardingKeyVo.getTablePrefix();
        if (CommUtils.isBlank(tablePrefix)) {
            tablePrefix = logicTableName + "_";
        }
        String tbDbName = "";
        if (CommUtils.isNotBlank(dbName)) {
            tbDbName = dbName + ".";
        }
        return tbDbName + tablePrefix + timeValue;
    }
}
