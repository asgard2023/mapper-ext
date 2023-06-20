package cn.org.opendfl.sharding.config.algorithm;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.org.opendfl.sharding.auto.utils.ShardingAlgorithmTool;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 按时间分表
 * 支持自动建表
 */
@Slf4j
public class TbShardingKeyDateRangeAlgorithm extends ShardingAlgorithmTool<Date> {
    private static Set<String> tableNextDateSet = new ConcurrentSkipListSet<>();

    /**
     * 可提前3天自动创建新表
     * @param shardingKeyDateVo
     * @param preciseShardingValue
     */
    private void autoCreateNext(ShardingKeyVo shardingKeyDateVo, PreciseShardingValue<Date> preciseShardingValue){
        Date shardingValueDate = preciseShardingValue.getValue();
        shardingValueDate = DateUtil.offsetDay(shardingValueDate, 3);
        String realTableNameNext = TbShardingKeyDateAlgorithm.getShardingRealTableName(null, preciseShardingValue.getLogicTableName(), shardingValueDate, shardingKeyDateVo);
        if(!tableNextDateSet.contains(realTableNameNext)){
            String routeTable = shardingTablesCheckAndCreatAndReturn(preciseShardingValue.getLogicTableName(), realTableNameNext);
            log.info("--autoCreateNextTable-routeTable={}", routeTable);
            tableNextDateSet.add(realTableNameNext);
        }
    }
    /**
     * 获取 指定分表
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
        ShardingKeyVo shardingKeyDateVo = AnnotationUtils.getShardingKey(preciseShardingValue.getLogicTableName());
        ThreadUtil.execAsync(()->autoCreateNext(shardingKeyDateVo, preciseShardingValue));
        autoCreateNext(shardingKeyDateVo, preciseShardingValue);
        Date shardingValueDate = preciseShardingValue.getValue();
        String realTableName = TbShardingKeyDateAlgorithm.getShardingRealTableName(null, preciseShardingValue.getLogicTableName(), shardingValueDate, shardingKeyDateVo);


        String routeTable = shardingTablesCheckAndCreatAndReturn(preciseShardingValue.getLogicTableName(), realTableName);
        log.debug("--doSharding-routeTable={}", routeTable);
        return routeTable;
    }

    /**
     * 获取 范围分表
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        ShardingKeyVo shardingKeyDateVo = AnnotationUtils.getShardingKey(rangeShardingValue.getLogicTableName());
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerEnd = valueRange.lowerEndpoint();
        Date upperEnd = valueRange.upperEndpoint();
        Set<String> routTables = new HashSet<>();
        if (lowerEnd != null && upperEnd != null) {
            List<String> rangeNameList = AnnotationUtils.getTableBetween(null, rangeShardingValue.getLogicTableName(), shardingKeyDateVo, lowerEnd, upperEnd);
            routTables.addAll(rangeNameList);
        } else {
            routTables.addAll(availableTargetNames);
        }
        log.debug("--doSharding-start:{},end:{},tables:{}", lowerEnd, upperEnd, routTables.toString());
        return routTables;
    }
}
