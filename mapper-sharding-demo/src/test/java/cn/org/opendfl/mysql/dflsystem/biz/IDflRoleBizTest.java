package cn.org.opendfl.mysql.dflsystem.biz;

import cn.hutool.json.JSONUtil;
import cn.org.opendfl.MysqlDemoApplication;
import cn.org.opendfl.mysql.dflsystem.po.DflRolePo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MysqlDemoApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class IDflRoleBizTest {
    @Autowired
    private IDflRoleBiz dflRoleBiz;

    @Test
    void getDataById() {
        DflRolePo dflRolePo = dflRoleBiz.getDataById(4, "ifDel,createTime,createUser");
        System.out.println(JSONUtil.toJsonStr(dflRolePo));
        Assertions.assertEquals("test22", dflRolePo.getCode());
        Assertions.assertNull(dflRolePo.getIfDel(), "ifDel ignored");

        dflRolePo = dflRoleBiz.getDataById(4, "ifDel,createTime");
        System.out.println(JSONUtil.toJsonStr(dflRolePo));

        dflRolePo = dflRoleBiz.getDataById(4, "code,ifDel,createTime");
        System.out.println(JSONUtil.toJsonStr(dflRolePo));
    }

}
