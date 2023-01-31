package cn.org.opendfl.shardings.biz;

import cn.org.opendfl.sharding.base.BaseShardingService;
import cn.org.opendfl.shardings.mapper.UserLoginMapper;
import cn.org.opendfl.shardings.po.UserLogin;
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
public class UserLoginBiz extends BaseShardingService<UserLogin> implements IUserLoginBiz {

    @Resource
    private UserLoginMapper mapper;

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
        String startTime = (String) otherParams.get("startTime");
        if (StringUtil.isNotEmpty(startTime)) {
            criteria.andGreaterThanOrEqualTo("createTime", startTime);
        }
        String endTime = (String) otherParams.get("endTime");
        if (StringUtil.isNotEmpty(endTime)) {
            criteria.andLessThanOrEqualTo("createTime", endTime);
        }

        if (entity.getCreateTime() != null) {
            otherParams.put("createTime", entity.getCreateTime());
        }

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
        int v = this.mapper.insert(entity);
        return v;
    }

    public int updateUserLogin(UserLogin entity) {
        //用于指定shardingKey对应的属性名及属性值
        Map<String, Object> paramsMap = ImmutableMap.of("createTime", entity.getCreateTime(), "id", entity.getId());
        entity.setCreateTime(null);
        int v = this.updateByPrimaryKeySelectiveExample(entity, paramsMap, UserLogin.class);

        return v;
    }

    public int deleteUserLogin(Long id, String operUserLogin, String remark) {
        UserLogin exist = this.findById(id);

        UserLogin po = new UserLogin();
        po.setId(id);
        po.setCreateTime(exist.getCreateTime());//需要补充shardingkey
        po.setResult(remark);
//        po.setIfDel(1); // 0未删除,1已删除
        int v = this.updateUserLogin(po);
        return v;
    }
}
