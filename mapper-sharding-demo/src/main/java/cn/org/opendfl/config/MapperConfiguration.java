//package cn.org.opendfl.mysql.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import tk.mybatis.mapper.common.MapperMy;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Slf4j
//public class MapperConfiguration implements ApplicationContextAware {
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//
//    @Autowired
//    private MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        String basePackage = mybatisConfiguration.getBasePackage();
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        scannerConfigurer.setBasePackage(basePackage);
//        scannerConfigurer.setMarkerInterface(MapperMy.class);
//        scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        return scannerConfigurer;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        String mapperLocations = mybatisConfiguration.getMapperLocations();
//        int defaultStatementTimeout = mybatisConfiguration.getDefaultStatementTimeout();
//        DataSource dataSource = (DataSource)applicationContext.getBean("dsSingle");
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setDefaultStatementTimeout(defaultStatementTimeout);
//        configuration.setCacheEnabled(true);
//        configuration.setUseGeneratedKeys(true);
//        sqlSessionFactoryBean.setConfiguration(configuration);
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        return sqlSessionFactoryBean.getObject();
//    }
//}
