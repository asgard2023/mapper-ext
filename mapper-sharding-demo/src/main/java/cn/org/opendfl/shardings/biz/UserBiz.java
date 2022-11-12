package cn.org.opendfl.shardings.biz;

import cn.org.opendfl.sharding.base.BaseShardingService;
import cn.org.opendfl.shardings.mapper.UserMapper;
import cn.org.opendfl.shardings.po.User;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.MapperMy;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * biz测试
 *
 * @author chenjh
 */
@Service
public class UserBiz extends BaseShardingService<User> implements IUserBiz {

    @Resource
    private UserMapper mapper;

    @Override
    public MapperMy getMapper() {
        return mapper;
    }

    @Override
    public Example createConditions(User entity, Map otherParams) {
        Example example = new Example(entity.getClass());
        Example.Criteria criteria = example.createCriteria();
        searchCondition(entity, otherParams, criteria);
        addFilters(criteria, otherParams);
        return example;
    }

    private void searchCondition(User entity, Map<String, Object> otherParams, Example.Criteria criteria) {
        String startTime = (String) otherParams.get("startTime");
        if (StringUtil.isNotEmpty(startTime)) {
            criteria.andGreaterThanOrEqualTo("createTime", startTime);
        }
        String endTime = (String) otherParams.get("endTime");
        if (StringUtil.isNotEmpty(endTime)) {
            criteria.andLessThanOrEqualTo("createTime", endTime);
        }


        if (entity.getUserId() != null) {
            otherParams.put("userId", entity.getUserId());
        }
        this.addEqualByKey(criteria, "id", otherParams);
        //用于指定shardingKey对应的属性名
        this.addEqualByKey(criteria, "userId", otherParams);
        this.addEqualByKey(criteria, "age", otherParams);
        this.addEqualByKey(criteria, "name", otherParams);
    }

    /**
     * 必须指定事务id，在混合模式下（一个走普通事务，一个走sharding-jdbc事务），事务也不起作用
     *
     * @param entity
     * @return
     */
//    @Transactional(transactionManager = "transactionManagerSharding")
//    @Transactional
    public int saveUser(User entity) {
        int v = this.mapper.insert(entity);
        return v;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int saveBatch(List<User> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return this.mapper.insertListById(list);
    }


    public int updateUser(User entity) {
        //用于指定shardingKey对应的属性名及属性值
        Map<String, Object> paramsMap = ImmutableMap.of("userId", entity.getUserId(), "id", entity.getId());
        entity.setUserId(null);
        int v = this.updateByPrimaryKeySelectiveExample(entity, paramsMap, User.class);

        return v;
    }

    public int deleteUser(Long id, String operUser, String remark) {
        User exist = this.findById(id);

        User po = new User();
        po.setId(id);
        po.setUserId(exist.getUserId());//需要补充shardingkey
        po.setName("delete");
//        po.setIfDel(1); // 0未删除,1已删除
        int v = this.updateUser(po);
        return v;
    }
}
