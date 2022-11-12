package cn.org.opendfl.mysql;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chenjh
 */
@SpringBootApplication
@EnableConfigurationProperties
public class MysqlApplication {
    public static final Logger logger = LoggerFactory.getLogger(MysqlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }
}
