package cn.org.opendfl.mysql.dflsystem.biz;

import cn.hutool.json.JSONUtil;
import cn.org.opendfl.MysqlDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tk.mybatis.mapper.ext.vo.DataCountVo;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = MysqlDemoApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class IDflRoleBizMapperExtTest {
    @Autowired
    private IDflRoleBiz dflRoleBiz;

    @Test
    void selectIds() {
        List<Long> seqId = dflRoleBiz.selectIds(1);
        System.out.println("---selectSeqId--seqId=" + seqId);
        seqId = dflRoleBiz.selectIds(0);
        System.out.println("---selectSeqId--seqId=" + seqId);
    }

    @Test
    void selectMaxId() {
        Long maxId = dflRoleBiz.selectMaxId(1);
        System.out.println("---selectMaxId--maxId=" + maxId);
        maxId = dflRoleBiz.selectMaxId(0);
        System.out.println("---selectMaxId--maxId=" + maxId);
    }

    @Test
    void selectCount() {
        int count = dflRoleBiz.selectCount(1);
        System.out.println("---selectCount--count=" + count);
        count = dflRoleBiz.selectCount(0);
        System.out.println("---selectCount--count=" + count);
    }

    @Test
    void selectCountExist() {
        int count = dflRoleBiz.selectCountExist(1);
        System.out.println("---selectCountExist--count=" + count);
        count = dflRoleBiz.selectCountExist(0);
        System.out.println("---selectCountExist--count=" + count);
    }

    @Test
    void selectCountByExample() {
        Date now = new Date();
        int count = dflRoleBiz.selectCountByExample(null);
        System.out.println("---selectCountByExample--count=" + count);
        count = dflRoleBiz.selectCountByExample(now);
        System.out.println("---selectCountByExample--count=" + count);
    }

    @Test
    void selectCountExistByExample() {
        Date now = new Date();
        int count = dflRoleBiz.selectCountExistByExample(null);
        System.out.println("---selectCountExistByExample--count=" + count);
        count = dflRoleBiz.selectCountExistByExample(now);
        System.out.println("---selectCountExistByExample--count=" + count);
    }

    @Test
    void selectCountAndMinMaxTimeByExample() {
        Date now = new Date();
        DataCountVo dataCountVo = dflRoleBiz.selectCountAndMinMaxTimeByExample(null);
        System.out.println("---selectCountAndMinMaxTimeByExample--dataCountVo=" + JSONUtil.toJsonStr(dataCountVo));
        dataCountVo = dflRoleBiz.selectCountAndMinMaxTimeByExample(now);
        System.out.println("---selectCountAndMinMaxTimeByExample--dataCountVo=" + JSONUtil.toJsonStr(dataCountVo));

    }
}
