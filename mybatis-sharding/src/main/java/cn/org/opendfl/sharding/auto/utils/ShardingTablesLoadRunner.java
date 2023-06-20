package cn.org.opendfl.sharding.auto.utils;


import cn.org.opendfl.sharding.config.ShardingConfig;
import cn.org.opendfl.sharding.auto.mapper.CommonMapper;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * 项目启动后 读取已有分表 进行缓存
 */
@Slf4j
@Service
public class ShardingTablesLoadRunner {

    @Resource
    private ShardingConfig shardingConfig;

    @Value("${db.schema-name}")
    private String schemaName;

    @Resource
    private CommonMapper commonMapper;

    @PostConstruct
    public void run() throws Exception {
        AnnotationUtils.setMybatisType(shardingConfig.getMybatisType());
        AnnotationUtils.setMinDate(shardingConfig.getMinDate());
        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper);
        ShardingAlgorithmTool.setSchemaName(schemaName);

        log.info("ShardingTablesLoadRunner start mybatisType={} schemaName={} OK", shardingConfig.getMybatisType(), schemaName);
    }
}