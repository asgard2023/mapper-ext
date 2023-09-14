package cn.org.opendfl.sharding.config.utils;


import cn.hutool.core.date.DateUtil;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class AnnotationUtilTest {
    @Test
    public void getTableBetween() throws Exception {
        String logicTableName = "test";
        ShardingKeyVo shardingKeyDateVo = new ShardingKeyVo();
        shardingKeyDateVo.setDateFormat("yyyyMM");
        shardingKeyDateVo.setShardingType(ShardingType.MONTH.getType());
        shardingKeyDateVo.setTablePrefix("user_login_log_");
        Date lowerEnd = DateUtil.parse("2023-08-20 00:00:00");
        Date upperEnd = DateUtil.parse("2023-09-14 16:27:43", new String[]{"yyyy-MM-dd HH:mm:ss"});
        List<String> rangeNameList = AnnotationUtils.getTableBetween(null, logicTableName, shardingKeyDateVo, lowerEnd, upperEnd);
        System.out.println("查1月:" + rangeNameList);

        lowerEnd = DateUtil.parse("2023-08-01 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
        upperEnd = DateUtil.parse("2023-09-14 16:27:43", new String[]{"yyyy-MM-dd HH:mm:ss"});
        rangeNameList = AnnotationUtils.getTableBetween(null, logicTableName, shardingKeyDateVo, lowerEnd, upperEnd);
        System.out.println("查1月初:" + rangeNameList);

        lowerEnd = DateUtil.parse("2023-07-01 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
        upperEnd = DateUtil.parse("2023-08-14 16:27:43", new String[]{"yyyy-MM-dd HH:mm:ss"});
        rangeNameList = AnnotationUtils.getTableBetween(null, logicTableName, shardingKeyDateVo, lowerEnd, upperEnd);
        System.out.println("查之前:" + rangeNameList);


        lowerEnd = DateUtil.parse("2023-06-20 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
        upperEnd = DateUtil.parse("2023-09-14 16:27:43", new String[]{"yyyy-MM-dd HH:mm:ss"});
        rangeNameList = AnnotationUtils.getTableBetween(null, logicTableName, shardingKeyDateVo, lowerEnd, upperEnd);
        System.out.println("含之前:" + rangeNameList);

        lowerEnd = DateUtil.parse("2023-08-20 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
        upperEnd = DateUtil.parse("2023-11-14 16:27:43", new String[]{"yyyy-MM-dd HH:mm:ss"});
        rangeNameList = AnnotationUtils.getTableBetween(null, logicTableName, shardingKeyDateVo, lowerEnd, upperEnd);
        System.out.println("含之后:" + rangeNameList);
    }

    @Test
    public void getDateEnd() {

        Date date = AnnotationUtils.getDateEnd(new Date(), ShardingType.YEAR);
        System.out.println("year=" + date);
        date = AnnotationUtils.getDateEnd(new Date(), ShardingType.MONTH);
        System.out.println("month=" + date);
        date = AnnotationUtils.getDateEnd(new Date(), ShardingType.DATE);
        System.out.println("month=" + date);
    }
}
