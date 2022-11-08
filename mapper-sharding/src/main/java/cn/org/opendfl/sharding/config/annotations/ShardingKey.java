package cn.org.opendfl.sharding.config.annotations;

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
     * 分片数shardCount=0，走shardingjdbc配置，并默认固定4个分片(建议走另外的分片实现类)
     * 分片表=逻辑表_分片值，exp: t_user对应的分片表t_user_{0..shardCount}
     *
     * @return
     */
    int shardCount() default 0;

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
}
