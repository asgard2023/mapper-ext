package cn.org.opendfl.sharding.config.algorithm;

import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表分片算法，配合@ShardingKey一起使用
 * 然后以@ShardingKey配置的值为主导
 *
 * @author chenjh
 */
public class TbShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
        if (shardingValue.getValue() == null) {
            throw new UnsupportedOperationException();
        }
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(shardingValue.getLogicTableName());
        //分片数shardCount=0，走shardingjdbc配置，并默认固定4个分片(建议走另外的分片实现类)
        if (shardingKeyVo.getShardCount() == 0) {
            for (String each : tableNames) {
                long v = Long.parseLong("" + shardingValue.getValue()) % 4;
                if (each.endsWith(v + "")) {
                    return each;
                }
            }
            throw new UnsupportedOperationException();
        }
        //tablePrefix为空则取逻辑表+‘_'，例如：t_user_
        String tablePrefix = shardingKeyVo.getTablePrefix();
        if (tablePrefix == null || "".equals(tablePrefix)) {
            tablePrefix = shardingValue.getLogicTableName() + "_";
        }
        return tablePrefix + Long.parseLong("" + shardingValue.getValue()) % shardingKeyVo.getShardCount();
    }

}  

