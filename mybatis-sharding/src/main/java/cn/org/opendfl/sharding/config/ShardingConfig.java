package cn.org.opendfl.sharding.config;

import cn.org.opendfl.sharding.config.utils.MybatisType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * sharding配置
 *
 * @author chenjh
 */
@Configuration
@ConfigurationProperties(prefix = "db")
@Data
public class ShardingConfig {
    private String schemeDb;
    private String mybatisType = MybatisType.MYBATIS_MAPPER.getType();
    /**
     * 按时间分表的最小时间 yyyy-MM-dd HH:mm:ss
     */
    private String minDate;
}
