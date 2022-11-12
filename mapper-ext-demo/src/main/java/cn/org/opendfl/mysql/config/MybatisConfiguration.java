package cn.org.opendfl.mysql.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mybatis")
@Data
@Slf4j
public class MybatisConfiguration {


    private String mapperLocations = "classpath:mappers/*/*.xml";
    private String configLocation = "classpath:mybatis-config.xml";

    private int defaultStatementTimeout = 3000;

    private String basePackage = "cn.org.opendfl.mysql";

    private String idField = "id";
    private String createTimeField = "create_time";
    private String updateTimeField = "modify_time";

    public void setCreateTimeField(String createTimeField) {
        this.createTimeField = createTimeField;
    }

}