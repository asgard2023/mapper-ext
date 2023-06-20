package cn.org.opendfl.sharding.config.algorithm;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
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
public class DbShardingKeyAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> dbNames, PreciseShardingValue<Long> shardingValue) {
        if (shardingValue.getValue() == null) {
            throw new UnsupportedOperationException();
        }
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(shardingValue.getLogicTableName());

        if (shardingKeyVo.getShardDbCount() > 0) {
            long dbIdx = shardingValue.getValue() % shardingKeyVo.getShardDbCount();
            for (String each : dbNames) {
                if (each.endsWith(dbIdx + "")) {
                    return each;
                }
            }
            throw new UnsupportedOperationException("logicTableName=" + shardingValue.getLogicTableName() + " shardingKey=" + shardingKeyVo.getField() + " dbIdx=" + dbIdx);
        }
        //分片数shardDbCount=0，走单库模式
        //dbPrefix不为空，直接取dbPrefix
        if (CharSequenceUtil.isNotBlank(shardingKeyVo.getDbPrefix())) {
            return shardingKeyVo.getDbPrefix();
        }
        //否则取dbNames第1个
        return CollUtil.getFirst(dbNames);
    }
}  

