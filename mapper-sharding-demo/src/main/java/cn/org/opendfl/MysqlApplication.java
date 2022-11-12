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
public class MysqlApplication {
    public static final Logger logger = LoggerFactory.getLogger(MysqlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }
}
