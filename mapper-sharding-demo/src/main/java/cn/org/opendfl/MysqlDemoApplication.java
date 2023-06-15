package cn.org.opendfl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * @author chenjh
 */
@ImportResource(value = {"classpath:spring/spring.xml"})
@SpringBootApplication
@EnableConfigurationProperties
public class MysqlDemoApplication {
    public static final Logger logger = LoggerFactory.getLogger(MysqlDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MysqlDemoApplication.class, args);
    }
}
