package cn.org.opendfl.mysql.shardings.biz;

import cn.hutool.core.date.DatePattern;
import cn.org.opendfl.MysqlApplication;
import cn.org.opendfl.base.MyPageInfo;
import cn.org.opendfl.base.PageUtils;
import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.shardings.biz.UserLoginBiz;
import cn.org.opendfl.shardings.po.UserLogin;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

@SpringBootTest(classes = MysqlApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
class UserLoginBizTest {
    @Resource
    private UserLoginBiz userLoginBiz;

    static String[] FORMAT_DATETIME = new String[]{"yyyy-MM-dd HH:mm:ss"};

    @Test
    void findById() throws Exception {
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        UserLogin user = userLoginBiz.findById(98L, createTime, UserLogin.class);
        System.out.println((user));
        Assertions.assertNotNull(user, "user is null");
    }

    @Test
    void findBy() throws Exception {
        UserLogin search = new UserLogin();
        search.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        PageHelper.startPage(1, 5);
        List<UserLogin> list = userLoginBiz.findBy(search, UserLogin.class);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void selectByTime() throws Exception{
        Date startTime=DateUtils.parseDate("2022-11-30 01:00:00", FORMAT_DATETIME);
        Date endTime=DateUtils.parseDate("2023-02-30 01:00:00", FORMAT_DATETIME);
        List<UserLogin> list = this.userLoginBiz.selectByTime(startTime, endTime);
        System.out.println(list);
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void selectByExample() throws Exception{
        Date startTime=DateUtils.parseDate("2022-11-30 01:00:00", FORMAT_DATETIME);
        Date endTime=DateUtils.parseDate("2023-02-30 01:00:00", FORMAT_DATETIME);
        List<UserLogin> list = this.userLoginBiz.selectByExample(startTime, endTime);
        System.out.println(list);
        Assertions.assertTrue(list.size() > 0);
    }


    @Test
    void shardingKey() throws Exception {
        System.out.println("----shardingKey-");
        UserLogin user = new UserLogin();
        user.setId(1L);
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        Field[] fields = UserLogin.class.getDeclaredFields();
        Map<String, Object> paramsMap = new HashMap<>();
        for (Field field : fields) {
            ShardingKey shardingKey = field.getAnnotation(ShardingKey.class);
            if (shardingKey != null) {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(user));
            }
        }

        System.out.println(paramsMap);

        String tableName = AnnotationUtils.getTableName(UserLogin.class);
        System.out.println(tableName);
        Assertions.assertNotNull(tableName);
    }

    @Test
    void findPageBy() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        UserLogin entity = new UserLogin();
        MyPageInfo<UserLogin> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 5);
        MyPageInfo<UserLogin> pageInfo = PageUtils.createPageInfo(paramsMap);

//        严格类型匹配，以下这两种都会出错
//        paramsMap.put("userId", "5");
//        paramsMap.put("userId", 5);
//        list = this.userBiz.findPageBy(entity, pageInfo, paramsMap);
//        Assertions.assertTrue(list.getList().size() > 0);

//        paramsMap.put("userId", 5L);
        entity = new UserLogin();
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        entity.setCreateTime(createTime);
        list = this.userLoginBiz.findPageBy(entity, UserLogin.class, pageInfo, paramsMap);
        System.out.println((list.getList().size()));
        Assertions.assertTrue(list.getList().size() > 0);

    }


    @Test
    void findPageBy_shardingkey() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<UserLogin> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 100);
        MyPageInfo<UserLogin> pageInfo = PageUtils.createPageInfo(paramsMap);

        UserLogin entity = new UserLogin();
        entity.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        list = this.userLoginBiz.findPageBy(entity, UserLogin.class, pageInfo, paramsMap);
        System.out.println(list.getList().size() + " " + (list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    void findPageBy_shardingkey2() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<UserLogin> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 5);
        MyPageInfo<UserLogin> pageInfo = PageUtils.createPageInfo(paramsMap);

        UserLogin entity = new UserLogin();
//        paramsMap.put("userId", 5L);
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        paramsMap.put("createTime", createTime);
        list = this.userLoginBiz.findPageBy(entity, UserLogin.class, pageInfo, paramsMap);
        System.out.println((list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    void saveUserLogin() throws Exception {
        UserLogin user = new UserLogin();
        user.setId(98L);
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:01:00", FORMAT_DATETIME));
        userLoginBiz.delete(user.getId(), user.getCreateTime(), UserLogin.class);
        user.setLoginType("1");
        user.setFuncCode("test");
        int v = userLoginBiz.saveUserLogin(user);
        Assertions.assertEquals(1, v, "saveUserLogin");
    }

    @Test
    void saveUserLogin2() throws Exception {
        UserLogin user = new UserLogin();
        user.setId(198L);
        user.setCreateTime(new Date());
        user.setLoginType("1");
        user.setFuncCode("test");
        int v = userLoginBiz.saveUserLogin(user);
        Assertions.assertEquals(1, v, "saveUserLogin");
    }

    @Test
    void updateUserLogin() throws Exception {
        Long id = 100L;
        Date begin = DateUtils.parseDate("2022-12-30 01:00:00", DatePattern.NORM_DATETIME_PATTERN);
        UserLogin exist = this.userLoginBiz.findById(id, begin, UserLogin.class);
        if (exist == null) {
            UserLogin user = new UserLogin();
            user.setId(id);
            user.setCreateTime(begin);
            user.setLoginType("login");
            user.setFuncCode("test");
            this.userLoginBiz.saveUserLogin(user);
        }

        UserLogin user = new UserLogin();
        user.setId(id);
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", DatePattern.NORM_DATETIME_PATTERN));
        user.setLoginType("login");
        user.setFuncCode("test");
        int v = userLoginBiz.updateUserLogin(user, user.getCreateTime());
        Assertions.assertEquals(1, v, "updateUserLogin");
    }

    @Test
    void saveBatch() throws Exception {
        List<UserLogin> list = new ArrayList<>();
        UserLogin user = new UserLogin();
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", DatePattern.NORM_DATETIME_PATTERN));
        user.setLoginType("batch");
        user.setFuncCode("test");
        list.add(user);
        user = new UserLogin();
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", DatePattern.NORM_DATETIME_PATTERN));
        user.setLoginType("batch");
        user.setFuncCode("test");
        list.add(user);
        int v = this.userLoginBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    void saveBatchInsert() throws Exception {
        Long id = 100L;
        Date begin = new Date();
        int v=0;
        for (int i = 0; i < 20; i++) {
            UserLogin user = new UserLogin();
            user.setId(id + i);
            user.setUserId(user.getId() % 10);
            begin = DateUtils.addDays(begin, 1);
            user.setCreateTime(begin);
            user.setLoginType("login");
            user.setFuncCode("test");
            try {
                v+=this.userLoginBiz.saveUserLogin(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Assertions.assertEquals(20, v);

    }

    @Test
    void saveBatch_withId() throws Exception {
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        List<UserLogin> list = new ArrayList<>();
        UserLogin user = new UserLogin();
        user.setId(300L);
        userLoginBiz.delete(user.getId(), createTime, UserLogin.class);
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("withId");
        user.setFuncCode("test");
        list.add(user);

        user = new UserLogin();
        user.setId(301L);
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        userLoginBiz.delete(user.getId(), createTime, UserLogin.class);
        user.setLoginType("withId");
        user.setFuncCode("test");
        list.add(user);
        int v = this.userLoginBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    void findByIds() throws Exception {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L,98L,100L});
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        List<UserLogin> list = this.userLoginBiz.findByIds(ids, createTime);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void findMapByIds() throws Exception {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L, 98L,100L});
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        List<UserLogin> list = this.userLoginBiz.findByIds(ids, createTime, UserLogin.class);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }



//    @Test
//    void findOne() {
//        UserLogin user = this.userLoginBiz.findOne("id", 1, UserLogin.class);
//        System.out.println((user));
//        Assertions.assertTrue(user != null);
//    }

    @Test
    void deleteUserLogin() throws Exception {
        Date createTime = DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        int v = this.userLoginBiz.deleteUserLogin(1L, createTime, "test", "test");
        System.out.println((v));
    }
}
