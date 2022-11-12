package cn.org.opendfl.mysql.dflsystem.biz;

import cn.hutool.json.JSONUtil;
import cn.org.opendfl.MysqlApplication;
import cn.org.opendfl.mysql.dflsystem.po.DflResourcePo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MysqlApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class IDflResourceBizTest {
    @Autowired
    private IDflResourceBiz dflResourceBiz;

    @Test
    void getDataById() {
        DflResourcePo dflUserPo = dflResourceBiz.getDataById(5, "ifDel,createTime,createUser");
        System.out.println(JSONUtil.toJsonStr(dflUserPo));
        Assertions.assertEquals("test_2bb", dflUserPo.getUri());
        Assertions.assertNull(dflUserPo.getIfDel(), "ifDel ignored");
    }
}
