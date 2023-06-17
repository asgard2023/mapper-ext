package cn.org.opendfl.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "cn.org.opendfl.sharding.auto.mapper", sqlSessionTemplateRef = "sqlSessionTemplateShardingCommon")
public class DataSourceShardingCommonConfig {
    @Bean(name = "sqlSessionFactoryShardingCommon")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dsSingle") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/CommonMapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplateShardingCommon")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactoryShardingCommon") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}