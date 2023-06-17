package cn.org.opendfl.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mapper-ext")
@Data
@Slf4j
public class MybatisConfiguration {

    private int defaultStatementTimeout = 3000;

    /**
     * 用于配置默认id属性名，方便执行selectMaxId（未加@Id时）
     */
    private String idField = "id";
    /**
     * 用于配置默认创建时间，方便执行selectMaxCreateTime
     */
    private String createTimeField = "create_time";
    private String updateTimeField = "modify_time";

    public void setCreateTimeField(String createTimeField) {
        this.createTimeField = createTimeField;
    }

}