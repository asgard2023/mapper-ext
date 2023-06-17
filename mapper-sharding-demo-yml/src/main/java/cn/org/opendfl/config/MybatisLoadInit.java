package cn.org.opendfl.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.mapperhelper.SqlExtHelper;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class MybatisLoadInit {
    @Autowired
    private MybatisConfiguration mybatisConfiguration;


    @PostConstruct
    public void initMybatisConfiguration(){
        String idField = mybatisConfiguration.getIdField();
        String createTimeField = mybatisConfiguration.getCreateTimeField();
        String updateTimeField = mybatisConfiguration.getUpdateTimeField();
        log.info("initMybatisConfiguration:idField={} createTimeField={} updateTimeField={}", idField, createTimeField, updateTimeField);
        SqlExtHelper.setDefaultIdField(idField);
        SqlExtHelper.setDefaultCreateTimeField(createTimeField);
        SqlExtHelper.setDefaultUpdateTimeField(updateTimeField);
    }
}
