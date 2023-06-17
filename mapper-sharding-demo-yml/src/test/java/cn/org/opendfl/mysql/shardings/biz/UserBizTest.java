package cn.org.opendfl.mysql.shardings.biz;

import cn.org.opendfl.base.MyPageInfo;
import cn.org.opendfl.base.PageUtils;
import cn.org.opendfl.MysqlDemoYmlApplication;
import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.shardings.biz.UserBiz;
import cn.org.opendfl.shardings.po.User;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MysqlDemoYmlApplication.class)
@Slf4j
public class UserBizTest {
    @Resource
    private UserBiz userBiz;

    @Test
    public void findById() {
        User user = userBiz.findById(1L);
        System.out.println((user));
        Assertions.assertTrue(user != null);
    }

    @Test
    public void findBy() {
        User search = new User();
        search.setUserId(4L);
        PageHelper.startPage(1, 5);
        List<User> list = userBiz.findBy(search);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void shardingKey() throws Exception {
        System.out.println("----shardingKey-");
        User user = new User();
        user.setId(1L);
        user.setUserId(4L);
        Field[] fields = User.class.getDeclaredFields();
        Map<String, Object> paramsMap = new HashMap<>();
        for (Field field : fields) {
            ShardingKey shardingKey = field.getAnnotation(ShardingKey.class);
            if (shardingKey != null) {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(user));
            }
        }

        System.out.println(paramsMap);

        String tableName = AnnotationUtils.getTableName(User.class);
        System.out.println(tableName);
    }

    @Test
    public void findPageBy() {
        Map<String, Object> paramsMap = new HashMap<>();
        User entity = new User();
        MyPageInfo<User> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 5);
        MyPageInfo<User> pageInfo = PageUtils.createPageInfo(paramsMap);

//        严格类型匹配，以下这两种都会出错
//        paramsMap.put("userId", "5");
//        paramsMap.put("userId", 5);
//        list = this.userBiz.findPageBy(entity, pageInfo, paramsMap);
//        Assertions.assertTrue(list.getList().size() > 0);

//        paramsMap.put("userId", 5L);
        entity = new User();
        list = this.userBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println((list.getList().size()));
        Assertions.assertTrue(list.getList().size() > 0);

    }


    @Test
    public void findPageBy_shardingkey() {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<User> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 100);
        MyPageInfo<User> pageInfo = PageUtils.createPageInfo(paramsMap);

        User entity = new User();
        entity.setUserId(5L);
        paramsMap.remove("userId");
        list = this.userBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println(list.getList().size() + " " + (list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    public void findPageBy_shardingkey2() {
        Map<String, Object> paramsMap = new HashMap<>();
        MyPageInfo<User> list = null;
        paramsMap.put("pageNum", 1);
        paramsMap.put("pageSize", 5);
        MyPageInfo<User> pageInfo = PageUtils.createPageInfo(paramsMap);

        User entity = new User();
        paramsMap.put("userId", 5L);
        list = this.userBiz.findPageBy(entity, pageInfo, paramsMap);
        System.out.println((list));
        Assertions.assertTrue(list.getList().size() > 0);
    }

    @Test
    public void saveUser() {
        User user = new User();
//        user.setId(99L);
//        userBiz.delete(user.getId());
        user.setName("20191001");
        user.setUserId(200L);
        user.setAge(10);
        userBiz.saveUser(user);
    }

    @Test
    public void updateUser() {
        Long id = 100L;
        User exist = this.userBiz.findById(id);
        if (exist == null) {
            User user = new User();
            user.setId(id);
            user.setName("20191001");
            user.setUserId(4L);
            user.setAge(10);
            this.userBiz.saveUser(user);
        }

        User user = new User();
        user.setId(id);
//        userBiz.delete(user.getId());
        user.setName("20191001update");
        user.setUserId(4L);
        user.setAge(10);
        userBiz.updateUser(user);
    }

    @Test
    public void saveBatch() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setName("20191001");
        user.setUserId(400L);
        user.setAge(10);
        list.add(user);
        user = new User();
        user.setName("20191001");
        user.setUserId(401L);
        user.setAge(10);
        list.add(user);
        int v = this.userBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    public void saveBatch_withId() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(300L);
        userBiz.delete(user.getId());
        user.setName("20191001");
        user.setUserId(300L);
        user.setAge(10);
        list.add(user);

        user = new User();
        user.setId(301L);
        userBiz.delete(user.getId());
        user.setName("20191001");
        user.setUserId(301L);
        user.setAge(10);
        list.add(user);
        int v = this.userBiz.saveBatch(list);
        Assertions.assertTrue(v > 0);
    }

    @Test
    public void findByIds() {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L});
        List<User> list = this.userBiz.findByIds(ids, User.class);
        System.out.println((list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void findMapByIds() {
        List ids = Arrays.asList(new Long[]{1L, 2L, 3L, 4L});
        Map<String, User> map = this.userBiz.findMapByIds(ids, User.class);
        System.out.println((map));
        Assertions.assertTrue(map.size() > 0);
    }

    @Test
    public void findOne() {
        User user = this.userBiz.findOne("id", 1, User.class);
        System.out.println((user));
        Assertions.assertTrue(user != null);
    }

    @Test
    public void deleteUser() {
        int v = this.userBiz.deleteUser(1L, "test", "test");
        System.out.println((v));
    }
}
