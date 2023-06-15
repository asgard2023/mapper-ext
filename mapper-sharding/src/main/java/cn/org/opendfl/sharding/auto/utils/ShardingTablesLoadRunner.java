package cn.org.opendfl.sharding.auto.utils;

import cn.org.opendfl.sharding.auto.mapper.CommonMapper;
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

    @Value("${db.schema-name}")
    private String schemaName;

    @Resource
    private CommonMapper commonMapper;

    @PostConstruct
    public void run() throws Exception {

        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper);
        // 调用缓存重载方法
        ShardingAlgorithmTool.tableNameCacheReload(schemaName);

        log.info("ShardingTablesLoadRunner start schemaName={} OK", schemaName);
    }
}