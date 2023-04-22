
package cn.org.opendfl.sharding.base;


import cn.hutool.core.lang.UUID;
import cn.org.opendfl.base.BeanUtils;
import cn.org.opendfl.base.IBaseService;
import cn.org.opendfl.base.MyPageInfo;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.exceptions.ShardingKeyException;
import cn.org.opendfl.sharding.exceptions.ShardingNotSupportException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * Created by chenjh
 */
@Slf4j
public abstract class BaseShardingService<T> implements IBaseService<T> {

    public abstract Mapper<T> getMapper();

    public static final String NOT_SUPPORT_SHARDING_JDBC_MSG = "not support on sharding-jdbc table";
    public static final String SHARDING_KEY_FIELD = "shardingKeyField";

    @Override
    public T selectByKey(Object key) {
        return getMapper().selectByPrimaryKey(key);
    }

    /**
     * 如果id为null的String字段，则取uuid
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int save(T entity) {
        String pkName = null;
        Object idValue = null;
        String className = null;
        try {
            className = entity.getClass().getSimpleName();
            EntityColumn pkColumn = BeanUtils.getPkColumn(entity.getClass());
            pkName = pkColumn.getProperty();
            idValue = BeanUtils.getValue(entity, pkName);
            if (pkColumn.getJavaType() == String.class && (idValue == null || idValue.toString().length() == 0)) {
                BeanUtils.setValue(entity, pkName, UUID.fastUUID());
            }
        } catch (Exception e) {
            log.error("--save {}.{}={} error={}", className, pkName, idValue, e.getMessage(), e);
            throw new RuntimeException("saveError:" + e.getMessage());
        }
        return getMapper().insert(entity);
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Object key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    /**
     * 更新对象所有属性，包括空值
     * 在sharding-jdbc分表情况下，这个功能不能用
     *
     * @param entity
     * @return
     * @Deprecated
     */
    @Override
    public int update(T entity) {
        throw new ShardingNotSupportException(NOT_SUPPORT_SHARDING_JDBC_MSG);
    }

    /**
     * 只更新有值的属性
     * shardingkey不支持：原因shardingkey必填，但又不能修改，形成矛盾，除于主键id为shardingkey
     *
     * @param entity
     * @return
     * @Deprecated
     */
    @Override
    public int updateByPrimaryKeySelective(T entity) {
        throw new ShardingNotSupportException(NOT_SUPPORT_SHARDING_JDBC_MSG);
    }

    /**
     * 支持sharding-jdbc分表模式
     *
     * @param entity
     * @param paramsMap   要求shardingKey的属性值必填
     * @param entityClass
     * @return
     */
    public int updateByPrimaryKeySelectiveExample(T entity, Map<String, Object> paramsMap, Class<T> entityClass) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();

        String idField = AnnotationUtils.getIdField(entityClass);
        if (paramsMap.get(idField) == null) {
            throw new ShardingKeyException("idField" + ":param " + idField + " is null");
        }
        this.checkShardingKey(entityClass, paramsMap);
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }
        return getMapper().updateByExampleSelective(entity, example);
    }

    /**
     * 检查参数中shardingKey是否有值
     * @param entityClass
     * @param paramsMap
     */
    public void checkShardingKey(Class entityClass, Map<String, Object> paramsMap){
        String shardingKeyField = AnnotationUtils.getShardingKeyField(entityClass);
        if (paramsMap.get(shardingKeyField) == null) {
            throw new ShardingKeyException(SHARDING_KEY_FIELD + ":param " + shardingKeyField + " is null");
        }
    }

    /**
     * 软删除
     * 在sharding-jdbc分表情况下，这个功能不能用
     *
     * @param entity
     * @param propertyName
     * @param deleteStatus
     * @return
     * @throws Exception
     * @Deprecated
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteVirtual(T entity, String propertyName, Object deleteStatus) throws Exception {
        throw new ShardingNotSupportException(NOT_SUPPORT_SHARDING_JDBC_MSG);
    }

    /**
     * 根据id查询
     */
    @Override
    public T findById(Object id) {
        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * 按id列表查询
     *
     * @param ids
     * @param entityClass
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findByIds(List<Object> ids, Class<T> entityClass) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() != 1) {
            throw new RuntimeException("主键 不唯一");
        }
        criteria.andIn(BeanUtils.getPKPropertyName(entityClass), ids);//只取一个主键
        return this.getMapper().selectByExample(example);
    }

    @Override
    public List<T> findByPropotys(String propName, List<Object> propotys, Class<T> entityClass) throws Exception {
        return findByPropotys(propName, propotys, entityClass, null);
    }

    /**
     * 按id列表查询 like ： orderByClause: example.setOrderByClause("create_time desc");
     *
     * @param propName
     * @param entityClass
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findByPropotys(String propName, List<Object> propotys, Class<T> entityClass, String orderByClause) throws Exception {
        if (propotys == null || propotys.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = new Example(entityClass);
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        Example.Criteria criteria = example.createCriteria();
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() != 1) {
            throw new Exception("主键 不唯一");
        }
        criteria.andIn(propName, propotys);
        return this.getMapper().selectByExample(example);
    }

    /**
     * 按照实体查找
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findBy(T entity) {
        return this.getMapper().select(entity);
    }

    /**
     * 按属性进行唯一查询，如果有多个则会抛出异常
     *
     * @param propoty
     * @param value
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public T findOne(String propoty, Object value, Class<T> entity) {
        List<T> list = findByPropoty(propoty, value, entity);
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new RuntimeException("多条结果");
        }
        return list.get(0);
    }

    /**
     * 按属性查询list
     *
     * @param propoty
     * @param value
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findByPropoty(String propoty, Object value, Class<T> entity) {
        return findByPropoty(propoty, value, entity, null);
    }

    /**
     * 按属性查询list
     *
     * @param propoty
     * @param value
     * @param entity
     * @param orderByClause，like ： example.setOrderByClause("create_time desc");
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findByPropoty(String propoty, Object value, Class<T> entity, String orderByClause) {
        Example example = new Example(entity);
        if (orderByClause != null) {
            example.setOrderByClause(orderByClause);
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(propoty, value);
        return this.getMapper().selectByExample(example);
    }

    public void checkPageLimit(Class clazz, MyPageInfo<T> pageInfo, Map<String, Object> otherParams) {
        this.checkPageLimit(clazz, pageInfo, otherParams, 1000);
    }

    public void checkPageLimit(Class clazz, MyPageInfo<T> pageInfo, Map<String, Object> otherParams, int maxRowCount) {
        String shardingKeyField = AnnotationUtils.getShardingKeyField(clazz);
        Object shardingKeyValue = otherParams.get(shardingKeyField);
        if (shardingKeyValue == null) {
            if (pageInfo.getPageNum() * pageInfo.getPageSize() > maxRowCount) {
                throw new ShardingKeyException(SHARDING_KEY_FIELD + "参数:" + shardingKeyField + "为空，超出翻页限制");
            }
        }
    }

    /**
     * 分页查询
     * shardingkey不能为空
     *
     * @param entity
     * @param pageInfo
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "null"})
    @Override
    public MyPageInfo<T> findPageBy(T entity, MyPageInfo<T> pageInfo, Map<String, Object> otherParams) {
        Example example = createConditions(entity, otherParams);
        this.checkPageLimit(entity.getClass(), pageInfo, otherParams);
        if (StringUtil.isNotEmpty(pageInfo.getOrderBy()) && StringUtil.isNotEmpty(pageInfo.getOrder())) {
            example.setOrderByClause(StringUtil.camelhumpToUnderline(pageInfo.getOrderBy()) + " " + pageInfo.getOrder());
        }
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isCountTotal());
        List<T> list = this.getMapper().selectByExample(example);
        boolean isCountTotal = pageInfo.isCountTotal();
        pageInfo = new MyPageInfo<>(list);
        pageInfo.setCountTotal(isCountTotal);
        if (!pageInfo.isCountTotal()) {
            pageInfo.setPages(100);
        }
        return pageInfo;
    }

    public abstract Example createConditions(T entity, Map<String, Object> otherParams);

    @Override
    public Map<String, T> findMapByIds(List<Object> ids, Class<T> entity) {
        Map<String, T> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        List<T> pos = this.findByIds(ids, entity);
        if (pos == null || pos.isEmpty()) {
            return map;
        }
        for (T po : pos) {
            Object tid = BeanUtils.getValue(po, "id");
            if (tid == null) {
                tid = BeanUtils.getValue(po, "tid");
            }
            map.put(tid.toString(), po);
        }
        return map;
    }

}
