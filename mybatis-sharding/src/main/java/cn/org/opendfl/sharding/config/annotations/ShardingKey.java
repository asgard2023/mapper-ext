package cn.org.opendfl.sharding.config.annotations;





import cn.org.opendfl.sharding.config.utils.ShardingType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可用于biz参数shardingKey校验
 * 仅用于单个分片key的处理，多个会取第一个
 * 需配置cn.org.opendfl.sharding.config.algorithm.TbShardingAlgorithm为分片算法
 *
 * @author chenjh
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardingKey {
    /**
     * 分片类型
     * @return
     */
    ShardingType shardingType() default ShardingType.ID_MOD;

    /**
     * shardingType=DATE时，起作用，默认按月
     * @return
     */
    String dateFormat() default "yyMM";
    /**
     * 分表分片数shardTableCount=0，走shardingjdbc配置，并默认固定4个分片(建议走另外的分片实现类)
     * 分片表=逻辑表_分片值，exp: t_user对应的分片表t_user_{0..shardTableCount}
     *
     * @return
     */
    int shardTableCount() default 0;

    /**
     * 分库分片数shardDbCount=0, 且dbPrefix不为空，表示是单库模式，库名就是dbPrefix
     * 分库分片数shardDbCount=0, 且dbPrefix为空，取库集合第1个
     * @return
     */
    int shardDbCount() default 0;

    /**
     * 预留，未用
     *
     * @return
     */
    String name() default "";

    /**
     * 真实表前缀，比如t_user_
     * 为空默认取逻辑表+’_‘，exp: 逻辑表t_user对应t_user_
     *
     * @return
     */
    String tablePrefix() default "";

    /**
     * 真实库前缀，比如ds_
     * @return
     */
    String dbPrefix() default "";

    String minDate() default "";
}
