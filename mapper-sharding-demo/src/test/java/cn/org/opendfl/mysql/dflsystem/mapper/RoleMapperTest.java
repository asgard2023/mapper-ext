package cn.org.opendfl.mysql.dflsystem.mapper;


import cn.org.opendfl.MysqlApplication;
import cn.org.opendfl.mysql.dflsystem.po.DflRolePo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjh
 */
@SpringBootTest(classes = MysqlApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class RoleMapperTest {

    @Autowired
    private DflRoleMapper dflRoleMapper;


    @Test
    public void selectOne() {
        DflRolePo queryWrapper = new DflRolePo();
        queryWrapper.setId(1);
        DflRolePo selectById = this.dflRoleMapper.selectOne(queryWrapper);
        Assertions.assertTrue(selectById != null);
    }

    @Test
    public void selectByPrimaryKey() {
        DflRolePo selectById = this.dflRoleMapper.selectByPrimaryKey(1);
        Assertions.assertTrue(selectById != null);
    }

    @Test
    public void select() {
        DflRolePo queryWrapper = new DflRolePo();
        queryWrapper.setStatus(1);
        List<DflRolePo> list = this.dflRoleMapper.select(queryWrapper);
        Assertions.assertTrue(list.size() > 0);

        queryWrapper = new DflRolePo();
        queryWrapper.setCode("test");
        list = this.dflRoleMapper.select(queryWrapper);
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void selectCount() {
        DflRolePo role = new DflRolePo();
        int count = this.dflRoleMapper.selectCount(role);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void countExistRole() {
        int count = this.dflRoleMapper.countExistRole(1);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void countExistRoleXml() {
        int count = this.dflRoleMapper.countExistRoleXml(1);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void fail_findRoleListByDate() {
        Date now = new Date();
        Date startDate = DateUtils.addYears(now, -10);
        Date endDate = now;
        List<DflRolePo> list = this.dflRoleMapper.findRoleListByDate(startDate, endDate);
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void findRoleListByDates() {
        Date now = new Date();
        Date startDate = DateUtils.addYears(now, -10);
        Date endDate = now;
        List<DflRolePo> list = this.dflRoleMapper.findRoleListByDates(DateFormatUtils.format(startDate, "yyyy-MM-dd HH:mm:ss"), DateFormatUtils.format(endDate, "yyyy-MM-dd HH:mm:ss"));
        Assertions.assertTrue(list.size() > 0);
    }

    /**
     * 这个功扩展功能，在sharding-jdbc模式下失败
     */
    @Test
    public void fail_selectCountExist() {
        DflRolePo role = new DflRolePo();
        role.setStatus(1);
        int count = this.dflRoleMapper.selectCountExist(role);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }


    /**
     * sharding-jdbc模式下失败，原因：不能修改shardingkey，且roleId不能为空
     */
    @Test
    public void fail_updateByExample() {
        DflRolePo role = new DflRolePo();
        role.setId(5);
        role.setIfDel(0);
        role.setStatus(1);
        role.setName("updateByExample_fail");
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", role.getId());
        int v = this.dflRoleMapper.updateByExample(role, example);
        DflRolePo r = this.dflRoleMapper.selectByPrimaryKey(role.getId());
        Assertions.assertEquals(role.getName(), r.getName());
        System.out.println("---updateByExampleSelective--v=" + v + " name=" + r.getName());
    }

    @Test
    public void updateByExampleSelective_shardingkey() {
        Integer id = 3;
        Long roleId = 4L;
        DflRolePo role = new DflRolePo();
        role.setName("updateByExampleSelective");
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", 3);
        int v = this.dflRoleMapper.updateByExampleSelective(role, example);
        DflRolePo r = this.dflRoleMapper.selectByPrimaryKey(id);
        System.out.println("---updateByExampleSelective_shardingkey--v=" + v + " r=" + r);
        Assertions.assertEquals(role.getName(), r.getName());
        System.out.println("---updateByExampleSelective--" + r.getName());
    }


    /**
     * sharding-jdbc模式下失败，原因：空指针
     * 正常模式下，roleId为空异常
     */
    @Test
    public void updateByPrimaryKey() {
        DflRolePo role = new DflRolePo();
        role.setId(9);
        role.setIfDel(0);
        role.setStatus(1);
        role.setName("updateByPrimaryKey3");
//        role.setDflRolePoId(4L);//shardingkey
        this.dflRoleMapper.updateByPrimaryKey(role);
        role = new DflRolePo();
        role.setId(8);
        role.setIfDel(0);
        role.setStatus(1);
        role.setName("updateByPrimaryKey4");
//        role.setDflRolePoId(5L);//shardingkey
        this.dflRoleMapper.updateByPrimaryKey(role);
        DflRolePo r = this.dflRoleMapper.selectByPrimaryKey(role.getId());
        Assertions.assertEquals(role.getName(), r.getName());
        System.out.println("---updateByPrimaryKey--" + r.getName());
    }

    /**
     * sharding-jdbc分表模式失败，原因：不能修改sharding-key
     */
    @Test
    public void updateByPrimaryKeySelective() {
        DflRolePo role = new DflRolePo();
        role.setId(18);
        role.setName("updateByPrimaryKeySelective3");
        this.dflRoleMapper.updateByPrimaryKeySelective(role);
        role = new DflRolePo();
        role.setId(19);
        role.setName("updateByPrimaryKeySelective4");
        int v = this.dflRoleMapper.updateByPrimaryKeySelective(role);
        DflRolePo r = this.dflRoleMapper.selectByPrimaryKey(role.getId());
        Assertions.assertEquals(role.getName(), r.getName());
        System.out.println("---updateByPrimaryKeySelective--" + r.getName());
    }

    /**
     * sharding-jdbc分表模式失败，原因：sharding-key必须填，但又不能修改sharding-key，形成冲突
     */
    @Test
    public void updateListById() {
        List<DflRolePo> recordList = new ArrayList<DflRolePo>();
        DflRolePo role = new DflRolePo();
        role.setId(13);
        role.setIfDel(0);
        role.setStatus(1);
        role.setName("updateListById1");
        recordList.add(role);
        role = new DflRolePo();
        role.setId(14);
        role.setName("updateListById2");
        role.setIfDel(0);
        role.setStatus(1);
        recordList.add(role);
        this.dflRoleMapper.updateListById(recordList);
    }

    @Test
    public void selectCountByExample() {
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("id", 1);
        int count = this.dflRoleMapper.selectCountByExample(example);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }


    /**
     * 这个扩展功能失败，在sharding-jdbc模式下失败
     */
    @Test
    public void fail_selectCountExistByExample() {
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", 100);

        int count = this.dflRoleMapper.selectCountExistByExample(example);
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void insert() {
        int v = 0;
        DflRolePo role = new DflRolePo();
        role.setCode("test");
        role.setId(1);
        role.setStatus(1);
        role.setIfDel(0);
        dflRoleMapper.deleteByPrimaryKey(role.getId());
        role.setName("20191001");
        v += dflRoleMapper.insert(role);

        role = new DflRolePo();
        role.setCode("test2");
        role.setId(2);
        role.setStatus(1);
        role.setIfDel(0);
        dflRoleMapper.deleteByPrimaryKey(role.getId());
        role.setName("20191002");
        v += dflRoleMapper.insert(role);

        Assertions.assertTrue(v > 0);
    }

    @Test
    public void insert2() {
        int v = 0;
        DflRolePo role = new DflRolePo();
        role.setCode("test3");
        role.setId(23);
        role.setStatus(1);
        role.setIfDel(0);
        dflRoleMapper.deleteByPrimaryKey(role.getId());
        role.setName("20191001");
        v += dflRoleMapper.insert(role);

        role = new DflRolePo();
        role.setId(24);
        role.setStatus(1);
        role.setIfDel(0);
        dflRoleMapper.deleteByPrimaryKey(role.getId());
        role.setName("20191002");
        v += dflRoleMapper.insert(role);

        Assertions.assertTrue(v > 0);
    }

    @Test
    public void insertList20() {
        List<DflRolePo> recordList = new ArrayList<DflRolePo>();
        DflRolePo role = null;
        for (int i = 0; i < 5; i++) {
            role = new DflRolePo();
            role.setIfDel(0);
            role.setStatus(1);
            role.setId(50 + i);
            this.dflRoleMapper.deleteByPrimaryKey(role.getId());
            role.setName("test20"+i);
            recordList.add(role);
        }
//        int v = this.dflRoleMapper.insertList(recordList);
        int v = this.dflRoleMapper.insertListById(recordList);
        Assertions.assertTrue(v > 0);
        System.out.println("insert count=" + v);
    }

    /**
     * 在sharding-jdbc模式下失败，sharding-key不能用范围查询
     */
    @Test
    public void fail_selectByExample_greaterThan() {
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("id", 1);//shardingkey不能用范围查询
        List<DflRolePo> roleList = this.dflRoleMapper.selectByExample(example);
        List<String> strings = roleList.stream().map(t -> t.toString()).collect(Collectors.toList());
        System.out.println(strings);
        Assertions.assertTrue(roleList != null);
    }

    @Test
    public void selectByExample() {
        Example example = new Example(DflRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("id", 10);
        List<DflRolePo> roleList = this.dflRoleMapper.selectByExample(example);
        List<String> strings = roleList.stream().map(t -> t.toString()).collect(Collectors.toList());
        System.out.println(strings);
        Assertions.assertTrue(roleList != null);
    }

}
