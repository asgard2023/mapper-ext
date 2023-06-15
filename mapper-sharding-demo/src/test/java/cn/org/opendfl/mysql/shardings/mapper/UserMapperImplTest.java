package cn.org.opendfl.mysql.shardings.mapper;


import cn.org.opendfl.MysqlDemoApplication;
import cn.org.opendfl.shardings.mapper.UserMapper;
import cn.org.opendfl.shardings.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjh
 */
@SpringBootTest(classes = MysqlDemoApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class UserMapperImplTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void selectOne() {
        User queryWrapper = new User();
        queryWrapper.setId(1L);
        User selectById = this.userMapper.selectOne(queryWrapper);
        Assertions.assertTrue(selectById != null);
    }

    @Test
    public void selectByPrimaryKey() {
        User selectById = this.userMapper.selectByPrimaryKey(1L);
        Assertions.assertTrue(selectById != null);
    }

    @Test
    public void select() {
        User queryWrapper = new User();
        queryWrapper.setUserId(4L);
        List<User> list = this.userMapper.select(queryWrapper);
        Assertions.assertTrue(list.size() > 0);

        queryWrapper = new User();
        queryWrapper.setAge(10);
        list = this.userMapper.select(queryWrapper);
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void selectCount() {
        User user = new User();
        int count = this.userMapper.selectCount(user);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    /**
     * 这个功扩展功能，在sharding-jdbc模式下失败
     */
    @Test
    public void fail_selectCountExist() {
        User user = new User();
        user.setUserId(100L);
        int count = this.userMapper.selectCountExist(user);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }


    /**
     * sharding-jdbc模式下失败，原因：不能修改shardingkey，且userId不能为空
     */
    @Test
    public void fail_updateByExample() {
        User user = new User();
        user.setId(3L);
        user.setName("updateByExample_fail");
        user.setUserId(4L);
        user.setAge(10);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", user.getId());
        int v=this.userMapper.updateByExample(user, example);
        User r = this.userMapper.selectByPrimaryKey(user.getId());
        Assertions.assertEquals(user.getName(), r.getName());
        System.out.println("---updateByExampleSelective--v="+v+" name=" + r.getName());
    }

    @Test
    public void updateByExampleSelective_shardingkey() {
        Long id = 3L;
        Long userId = 4L;
        User user = new User();
        user.setName("updateByExampleSelective");
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", 3L);
        criteria.andEqualTo("userId", userId);//shardingkey
        int v= this.userMapper.updateByExampleSelective(user, example);
        User r = this.userMapper.selectByPrimaryKey(id);
        System.out.println("---updateByExampleSelective_shardingkey--v="+v+" r=" + r);
        Assertions.assertEquals(user.getName(), r.getName());
        System.out.println("---updateByExampleSelective--" + r.getName());
    }

    /**
     * sharding-jdbc分表模式失败，原因：不能修改sharding-key
     */
    @Test
    public void fail_updateByExampleSelective_shardingkey() {
        User user = new User();
        user.setId(3L);
        user.setName("updateByExampleSelective_shardingkey2");
        user.setUserId(3L);//不能修改sharding-key
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", user.getId());
        criteria.andEqualTo("userId", 4L);//shardingkey
        this.userMapper.updateByExampleSelective(user, example);
        User r = this.userMapper.selectByPrimaryKey(user.getId());
        Assertions.assertEquals(user.getName(), r.getName());
        System.out.println("---updateByExampleSelective--" + r.getName());
    }


    /**
     * sharding-jdbc模式下失败，原因：空指针
     * 正常模式下，userId为空异常
     */
    @Test
    public void fail_updateByPrimaryKey() {
        User user = new User();
        user.setId(3L);
        user.setName("updateByPrimaryKey");
//        user.setUserId(4L);//shardingkey
        user.setAge(10);
        this.userMapper.updateByPrimaryKey(user);
        user = new User();
        user.setId(4L);
        user.setName("20191002");
//        user.setUserId(5L);//shardingkey
        user.setAge(120);
        this.userMapper.updateByPrimaryKey(user);
        User r = this.userMapper.selectByPrimaryKey(user.getId());
        Assertions.assertEquals(user.getName(), r.getName());
        System.out.println("---updateByPrimaryKey--" + r.getName());
    }

    /**
     * sharding-jdbc分表模式失败，原因：不能修改sharding-key
     */
    @Test
    public void fail_updateByPrimaryKeySelective() {
        User user = new User();
        user.setId(3L);
        user.setName("20191001");
        user.setUserId(4L);
        user.setAge(10);
        this.userMapper.updateByPrimaryKeySelective(user);
        user = new User();
        user.setId(4L);
        user.setName("updateByPrimaryKeySelective");
        user.setUserId(5L);
        user.setAge(120);
        int v = this.userMapper.updateByPrimaryKeySelective(user);
        User r = this.userMapper.selectByPrimaryKey(user.getId());
        Assertions.assertEquals(user.getName(), r.getName());
        System.out.println("---updateByPrimaryKeySelective--" + r.getName());
    }

    /**
     * sharding-jdbc分表模式失败，原因：sharding-key必须填，但又不能修改sharding-key，形成冲突
     */
    @Test
    public void fail_updateListById() {
        List<User> recordList = new ArrayList<User>();
        User user = new User();
        user.setId(3L);
        user.setName("20191001");
        user.setUserId(4L);
        user.setAge(10);
        recordList.add(user);
        user = new User();
        user.setId(4L);
        user.setName("20191002");
        user.setUserId(5L);
        user.setAge(120);
        recordList.add(user);
        this.userMapper.updateListById(recordList);
    }

    @Test
    public void selectCountByExample() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("id", 1);
        int count = this.userMapper.selectCountByExample(example);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }


    /**
     * 这个扩展功能失败，在sharding-jdbc模式下失败
     */
    @Test
    public void fail_selectCountExistByExample() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", 100L);

        int count = this.userMapper.selectCountExistByExample(example);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void insert() {
        int v = 0;
        User user = new User();
        user.setId(1L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(4L);
        user.setAge(10);
        v += userMapper.insert(user);

        user = new User();
        user.setId(2L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(5L);
        user.setAge(120);
        v += userMapper.insert(user);

        Assertions.assertTrue(v > 0);
    }

    @Test
    public void insert2() {
        int v = 0;
        User user = new User();
        user.setId(3L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(4L);
        user.setAge(10);
        v += userMapper.insert(user);

        user = new User();
        user.setId(4L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(5L);
        user.setAge(120);
        v += userMapper.insert(user);

        Assertions.assertTrue(v > 0);
    }

    /**
     * ID 100以上的数据测试
     */
    @Test
    public void insertList100() {
        List<User> list = new ArrayList<>();
        int v = 0;
        User user = new User();
        user.setId(100L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(100L);
        user.setAge(10);
        list.add(user);

        user = new User();
        user.setId(101L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(101L);
        user.setAge(120);
        list.add(user);

        user = new User();
        user.setId(102L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(102L);
        user.setAge(10);
        list.add(user);

        user = new User();
        user.setId(103L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(103L);
        user.setAge(120);
        list.add(user);

        user = new User();
        user.setId(104L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(100L);
        user.setAge(10);
        list.add(user);

        user = new User();
        user.setId(105L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(101L);
        user.setAge(120);
        list.add(user);

        user = new User();
        user.setId(106L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191001");
        user.setUserId(102L);
        user.setAge(10);
        list.add(user);

        user = new User();
        user.setId(107L);
        userMapper.deleteByPrimaryKey(user.getId());
        user.setName("20191002");
        user.setUserId(103L);
        user.setAge(120);
        list.add(user);
        v += userMapper.insertListById(list);
        Assertions.assertTrue(v > 0);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", 100L);//shardingkey
        List<User> userList = this.userMapper.selectByExample(example);
        List<String> strings = userList.stream().map(t -> t.toString()).collect(Collectors.toList());
        System.out.println(strings);
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    public void insertList20() {
        List<User> recordList = new ArrayList<User>();
        User user = null;
        for (int i = 0; i < 20; i++) {
            user = new User();
            user.setId(50L + i);
            this.userMapper.deleteByPrimaryKey(user.getId());
            user.setName("20191001");
            user.setUserId(4L + i % 8);
            user.setAge(10 + i % 8);
            recordList.add(user);
        }
        int v = this.userMapper.insertList(recordList);
        Assertions.assertTrue(v > 0);
        System.out.println("insert count=" + v);
    }

    /**
     * 在sharding-jdbc模式下失败，sharding-key不能用范围查询
     */
    @Test
    public void fail_selectByExample_greaterThan() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("userId", 1L);//shardingkey不能用范围查询
        List<User> userList = this.userMapper.selectByExample(example);
        List<String> strings = userList.stream().map(t -> t.toString()).collect(Collectors.toList());
        System.out.println(strings);
        Assertions.assertTrue(userList != null);
    }

    @Test
    public void selectByExample() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
//		criteria.andCondition("bid_status in (" + 0 + ")");
        criteria.andEqualTo("userId", 4L);
        criteria.andGreaterThan("age", 10);
//		criteria.andLessThan("createTime", new Date());
        List<User> userList = this.userMapper.selectByExample(example);
        List<String> strings = userList.stream().map(t -> t.toString()).collect(Collectors.toList());
        System.out.println(strings);
        Assertions.assertTrue(userList != null);
    }
//
//    @Test
//    public void updateList() {
//        List<User> recordList = new ArrayList<User>();
//        User user = new User();
//        user.setId(3L);
//        user.setName("20191001");
//        user.setUserId(4L);
//        user.setAge(10);
//        recordList.add(user);
//        user = new User();
//        user.setId(4L);
//        user.setName("20191002");
//        user.setUserId(5L);
//        user.setAge(120);
//        recordList.add(user);
//        int insertList = this.userMapper.updateList(recordList);
//        Assertions.assertTrue(insertList > 0);
//    }
//
//    @Test
//    public void updateCaseWhen() {
//        List<User> recordList = new ArrayList<User>();
//        User user = new User();
//        user.setId(3L);
//        user.setName("20191001");
//        user.setUserId(4L);
//        user.setAge(10);
//        recordList.add(user);
//        user = new User();
//        user.setId(4L);
//        user.setName("20191002");
//        user.setUserId(5L);
//        user.setAge(120);
//        recordList.add(user);
//        int insertList = this.userMapper.updateCaseWhen(recordList);
//        Assertions.assertTrue(insertList > 0);
//    }

}
