package org.ccs.opendfl.mysql.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.mapperhelper.SqlExtHelper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class MybatisLoadInit {
    @Autowired
    private MybatisConfiguration mybatisConfiguration;

    @Autowired
    private MapperScannerConfigurer mapperScannerConfigurer;


    @PostConstruct
    public void initMybatisConfiguration(){
        String basePackage = mybatisConfiguration.getBasePackage();
        this.mapperScannerConfigurer.setBasePackage(basePackage);
        String idField = mybatisConfiguration.getIdField();
        String createTimeField = mybatisConfiguration.getCreateTimeField();
        String updateTimeField = mybatisConfiguration.getUpdateTimeField();
        log.info("initMybatisConfiguration:--->mybatis.basePackage={} idField={} createTimeField={} updateTimeField={}", basePackage, idField, createTimeField, updateTimeField);
        SqlExtHelper.setDefaultIdField(idField);
        SqlExtHelper.setDefaultCreateTimeField(createTimeField);
        SqlExtHelper.setDefaultUpdateTimeField(updateTimeField);
    }
}
