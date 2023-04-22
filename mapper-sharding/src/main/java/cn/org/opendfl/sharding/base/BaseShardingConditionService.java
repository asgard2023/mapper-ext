package cn.org.opendfl.sharding.base;

import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.base.BaseConditionService;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Map;

/**
 * 查询条件处理
 *
 * @author chenjh
 */
public interface BaseShardingConditionService<T> extends BaseConditionService<T> {
    public static final String SHARDING_KEY_FIELD = "shardingKeyField";
    /**
     * 获得表的主键属性名
     * @param entityClass
     * @return
     */
    public default String getIdField(Class<T> entityClass) {
        return AnnotationUtils.getIdField(entityClass);
    }

    /**
     * 获得分表键属性名
     * @param entityClass
     * @return
     */
    public default String getShardingKeyField(Class<T> entityClass) {
        return AnnotationUtils.getShardingKeyField(entityClass);
    }

    public default ShardingKeyVo getShardingKey(Class<T> entityClass) {
        return AnnotationUtils.getShardingKey(entityClass);
    }
}
