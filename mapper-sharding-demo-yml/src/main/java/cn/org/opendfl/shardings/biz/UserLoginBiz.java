package cn.org.opendfl.shardings.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.org.opendfl.sharding.base.BaseShardingShardingDateService;
import cn.org.opendfl.shardings.mapper.UserLoginMapper;
import cn.org.opendfl.shardings.po.UserLogin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.MapperMy;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * biz测试
 *
 * @author chenjh
 */
@Service
public class UserLoginBiz extends BaseShardingShardingDateService<UserLogin> implements IUserLoginBiz {

    @Resource
    private UserLoginMapper mapper;
    private Snowflake snowflake = IdUtil.createSnowflake(0, 0);

    @Override
    public MapperMy getMapper() {
        return mapper;
    }

    @Override
    public Example createConditions(UserLogin entity, Map otherParams) {
        Example example = new Example(entity.getClass());
        Example.Criteria criteria = example.createCriteria();
        searchCondition(entity, otherParams, criteria);
        addFilters(criteria, otherParams);
        return example;
    }

    private void searchCondition(UserLogin entity, Map<String, Object> otherParams, Example.Criteria criteria) {
        this.shardingDateCondition(criteria, otherParams, UserLogin.class);
        if (entity.getUserId() != null) {
            otherParams.put("userId", entity.getUserId());
        }
        this.addEqualByKey(criteria, "id", otherParams);
        //用于指定shardingKey对应的属性名
        this.addEqualByKey(criteria, "userId", otherParams);
        this.addEqualByKey(criteria, "age", otherParams);
        this.addEqualByKey(criteria, "name", otherParams);
        this.addEqualByKey(criteria, "createTime", otherParams);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int saveBatch(List<UserLogin> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        list.stream().forEach(t -> {
            if (t.getId() == null) {
                t.setId(snowflake.nextId());
            }
        });
        return this.mapper.insertListById(list);
    }

    /**
     * 必须指定事务id，在混合模式下（一个走普通事务，一个走sharding-jdbc事务），事务也不起作用
     *
     * @param entity
     * @return
     */
//    @Transactional(transactionManager = "transactionManagerSharding")
//    @Transactional
    public int saveUserLogin(UserLogin entity) {
        if(entity.getCreateTime()==null){
            entity.setCreateTime(new Date());
        }
        if(entity.getIfDel()==null){
            entity.setIfDel(0);
        }
        return this.mapper.insert(entity);
    }

    public int updateUserLogin(UserLogin entity, Date searchDate) {
        entity.setUpdateTime(new Date());
        //用于指定shardingKey对应的属性名及属性值
        return this.updateData(entity, searchDate, UserLogin.class);
    }

    public int deleteUserLogin(Long id, Date searchDate, String operUserLogin, String remark) {
        UserLogin po = new UserLogin();
        po.setId(id);
        po.setUpdateTime(new Date());
        po.setResult(remark);
        po.setIfDel(1); // 0未删除,1已删除
        return this.updateUserLogin(po, searchDate);
    }

    public List<UserLogin> selectByTime(Date start, Date end) {
        return this.mapper.selectByTime(start, end);
    }

    public List<UserLogin> findByIds(List<Long> ids, Date createTime) {
        List<Object> objIds = Arrays.asList(ids.toArray());
        return super.findByIds(objIds, createTime, UserLogin.class, null);
    }


    public List<UserLogin> selectByExample(Date start, Date end) {
        Example example = new Example(UserLogin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("createTime", start);
        criteria.andLessThan("createTime", end);
        return this.mapper.selectByExample(example);
    }
}
