package cn.org.opendfl.sharding.config.algorithm;




import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.config.utils.ShardingTableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表分片算法，配合@ShardingKey一起使用
 * 然后以@ShardingKey配置的值为主导
 *
 * @author chenjh
 */
public class TbShardingKeyAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
        if (shardingValue.getValue() == null) {
            throw new UnsupportedOperationException();
        }
        ShardingKeyVo shardingKeyVo = AnnotationUtils.getShardingKey(shardingValue.getLogicTableName());
        //分片数shardTableCount=0，走shardingjdbc配置，并默认固定4个分片(建议走另外的分片实现类)
        if (shardingKeyVo.getShardTableCount() == 0) {
            long tbIdx = shardingValue.getValue() % 4;
            for (String each : tableNames) {
                if (each.endsWith(tbIdx + "")) {
                    return each;
                }
            }
            throw new UnsupportedOperationException("logicTableName=" + shardingValue.getLogicTableName() + " shardingKey=" + shardingKeyVo.getField() + " tbIdx=" + tbIdx);
        }
        return getShardingRealTableName(null, shardingValue.getLogicTableName(), shardingValue.getValue(), shardingKeyVo);
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, Long shardingValue, ShardingKeyVo shardingKeyVo) {
        Long tbIdx = shardingValue % shardingKeyVo.getShardTableCount();
        //用于支持值为负数的情况
        if (tbIdx < 0) {
            tbIdx += shardingKeyVo.getShardTableCount();
        }
        return getShardingRealTableName(dbName, logicTableName, shardingKeyVo, tbIdx.intValue());
    }

    public static String getShardingRealTableName(String dbName, String logicTableName, ShardingKeyVo shardingKeyVo, Integer tbIdx) {
        //tablePrefix为空则取逻辑表+‘_'，例如：t_user_
        String tablePrefix = ShardingTableUtils.getTablePrefix(shardingKeyVo.getTablePrefix(), logicTableName);
        String tbDbName = "";
        if (CharSequenceUtil.isNotBlank(dbName)) {
            tbDbName = dbName + ".";
        }
        return tbDbName + tablePrefix + tbIdx;
    }

}  

