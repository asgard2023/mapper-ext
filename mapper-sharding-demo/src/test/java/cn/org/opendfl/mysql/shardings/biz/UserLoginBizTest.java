package cn.org.opendfl.mysql.shardings.biz;

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
public class UserLoginBizTest  {
    @Resource
    private UserLoginBiz userLoginBiz;

    public static String[] FORMAT_DATETIME=new String[]{"yyyy-MM-dd HH:mm:ss"};

    @Test
    public void findById() {
        UserLogin user = userLoginBiz.findById(1L);
        System.out.println((user));
        Assertions.assertTrue(user != null);
    }

    @Test
    public void findBy() throws Exception {
        UserLogin search = new UserLogin();
        search.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        PageHelper.startPage(1, 5);
        List<UserLogin> list = userLoginBiz.findBy(search);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void shardingKey() throws Exception {
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
    }

    @Test
    public void findPageBy() {
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
        list = this.userLoginBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println((list.getList().size()));
        Assertions.assertTrue(list.getList().size() > 0);

    }


    @Test
    public void findPageBy_shardingkey() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<UserLogin> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 100);
        MyPageInfo<UserLogin> pageInfo = PageUtils.createPageInfo(paramsMap);

        UserLogin entity = new UserLogin();
        entity.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        paramsMap.remove("createTime");
        list = this.userLoginBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println(list.getList().size() + " " + (list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    public void findPageBy_shardingkey2() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<UserLogin> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 5);
        MyPageInfo<UserLogin> pageInfo = PageUtils.createPageInfo(paramsMap);

        UserLogin entity = new UserLogin();
//        paramsMap.put("userId", 5L);
        Date createTime=DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        paramsMap.put("createTime", createTime);
        list = this.userLoginBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println((list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    public void saveUserLogin() throws Exception{
        UserLogin user = new UserLogin();
        user.setId(98L);
//        userBiz.delete(user.getId());
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:01:00", FORMAT_DATETIME));
        user.setLoginType("1");
        user.setFuncCode("test");
        userLoginBiz.saveUserLogin(user);
    }

    @Test
    public void updateUserLogin() throws Exception{
        Long id = 100L;
        Date begin=DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME);
        UserLogin exist = null;
        if (exist == null) {
            UserLogin user = new UserLogin();
            user.setId(id);
//            begin=DateUtils.addDays(begin, -1);
            user.setCreateTime(begin);
            user.setLoginType("login");
            user.setFuncCode("test");
            this.userLoginBiz.saveUserLogin(user);
        }

        UserLogin user = new UserLogin();
        user.setId(id);
//        userBiz.delete(user.getId());
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("login");
        user.setFuncCode("test");
        userLoginBiz.updateUserLogin(user);
    }

    @Test
    public void saveBatch() throws Exception{
        List<UserLogin> list = new ArrayList<>();
        UserLogin user = new UserLogin();
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("batch");
        user.setFuncCode("test");
        list.add(user);
        user = new UserLogin();
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("batch");
        user.setFuncCode("test");
        list.add(user);
        int v = this.userLoginBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    public void saveBatchInsert() throws Exception{
        Long id = 100L;
        Date begin=DateUtils.parseDate("2022-06-30 01:00:00", FORMAT_DATETIME);
        for(int i=0; i<100; i++){
            UserLogin user = new UserLogin();
            user.setId(id+i);
            user.setUserId(user.getId()%10);
            begin=DateUtils.addDays(begin, 1);
            user.setCreateTime(begin);
            user.setLoginType("login");
            user.setFuncCode("test");
            try {
                this.userLoginBiz.saveUserLogin(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void saveBatch_withId() throws Exception {
        List<UserLogin> list = new ArrayList<>();
        UserLogin user = new UserLogin();
//        user.setId(300L);
        userLoginBiz.delete(user.getId());
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("withId");
        user.setFuncCode("test");
        list.add(user);

        user = new UserLogin();
//        user.setId(301L);
        userLoginBiz.delete(user.getId());
        user.setCreateTime(DateUtils.parseDate("2022-12-30 01:00:00", FORMAT_DATETIME));
        user.setLoginType("withId");
        user.setFuncCode("test");
        list.add(user);
        int v = this.userLoginBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    public void findByIds() {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L});
        List<UserLogin> list = this.userLoginBiz.findByIds(ids, UserLogin.class);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void findMapByIds() {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L});
        Map<String, UserLogin> map = this.userLoginBiz.findMapByIds(ids, UserLogin.class);
        System.out.println((map));
        Assertions.assertTrue(map.size() > 0);
    }

    @Test
    public void findOne() {
        UserLogin user = this.userLoginBiz.findOne("id", 1, UserLogin.class);
        System.out.println((user));
        Assertions.assertTrue(user != null);
    }

    @Test
    public void deleteUserLogin() {
        int v = this.userLoginBiz.deleteUserLogin(1L, "test", "test");
        System.out.println((v));
    }
}
