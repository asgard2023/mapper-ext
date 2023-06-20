package cn.org.opendfl.sharding.config.algorithm;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.config.utils.ShardingTableUtils;
import cn.org.opendfl.sharding.config.utils.ShardingType;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自定义分表
 */
@Slf4j
public class TbShardingKeyRangeAlgorithm implements RangeShardingAlgorithm<Date> {
    /**
     * 精确分片算法（按月分表）
     *
     * @param tableNames         分表集合
     * @param rangeShardingValue 分片字段值
     * @return 分表
     */
    @Override
    public Collection<String> doSharding(Collection<String> tableNames, RangeShardingValue<Date> rangeShardingValue) {
        ShardingKeyVo shardingKeyDateVo = AnnotationUtils.getShardingKey(rangeShardingValue.getLogicTableName());
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerEnd = valueRange.lowerEndpoint();
        Date upperEnd = valueRange.upperEndpoint();
        Set<String> routTables = new HashSet<>();
        if (lowerEnd != null && upperEnd != null) {
            List<String> rangeNameList = AnnotationUtils.getTableBetween(null, rangeShardingValue.getLogicTableName(), shardingKeyDateVo, lowerEnd, upperEnd);
            routTables.addAll(rangeNameList);
        } else {
            routTables.addAll(tableNames);
        }
        log.debug("--doSharding-start:{},end:{},tables:{}", lowerEnd, upperEnd, routTables.toString());
        return routTables;
    }



}
