package cn.org.opendfl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author chenjh
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan(basePackages = {"cn.org.opendfl.shardings.mapper"})
public class MysqlDemoYmlApplication {
    public static final Logger logger = LoggerFactory.getLogger(MysqlDemoYmlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MysqlDemoYmlApplication.class, args);
    }
}
