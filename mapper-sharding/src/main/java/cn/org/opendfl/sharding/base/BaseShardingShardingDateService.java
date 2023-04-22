package cn.org.opendfl.sharding.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.org.opendfl.base.BeanUtils;
import cn.org.opendfl.base.MyPageInfo;
import cn.org.opendfl.sharding.config.annotations.ShardingKeyVo;
import cn.org.opendfl.sharding.config.utils.ShardingTableUtils;
import cn.org.opendfl.sharding.config.utils.ShardingType;
import cn.org.opendfl.sharding.exceptions.IdNullException;
import cn.org.opendfl.sharding.exceptions.ShardingKeyException;
import cn.org.opendfl.sharding.exceptions.ShardingKeyNullException;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * 用于按时间精确分片，比如按月，按天等
 *
 * @author chenjh
 */
@Slf4j
public abstract class BaseShardingShardingDateService<T> implements IBaseDateServiceSharding<T> {
    public abstract Mapper<T> getMapper();

    public static final String NOT_SUPPORT_SHARDING_JDBC_MSG = "not support on sharding-jdbc table";


    public void checkPageLimit(Class<T> clazz, MyPageInfo<T> pageInfo, Map<String, Object> otherParams) {
        this.checkPageLimit(clazz, pageInfo, otherParams, 1000);
    }

    public void checkPageLimit(Class<T> clazz, MyPageInfo<T> pageInfo, Map<String, Object> otherParams, int maxRowCount) {
        String shardingKeyField = getShardingKeyField(clazz);
        Object shardingKeyValue = otherParams.get(shardingKeyField);
        if (shardingKeyValue == null && pageInfo.getPageNum() * pageInfo.getPageSize() > maxRowCount) {
            throw new ShardingKeyException(SHARDING_KEY_FIELD + "参数:" + shardingKeyField + "为空，超出翻页限制");
        }
    }


    /**
     * 如果时间分表，shardingField有值，则按这个值的前后各加1天查数据; shardingField没值按startTime,endTime参数查询(startTime默认当前时间-1月,endTime默认取当前时间)
     *
     * @param criteria
     * @param paramsMap
     * @param entityClass
     * @return
     */
    public boolean shardingDateCondition(Example.Criteria criteria, Map<String, Object> paramsMap, Class<T> entityClass) {
        ShardingKeyVo shardingKeyVo = getShardingKey(entityClass);
        boolean isDateSharding = ShardingType.isDateSharding(shardingKeyVo.getShardingType());

        //如果是日期，自动给前后加1天
        if (isDateSharding) {
            String shardingKeyField = shardingKeyVo.getField();
            ShardingType shardingType = ShardingType.parse(shardingKeyVo.getShardingType());
            Object shardingValue = paramsMap.get(shardingKeyField);
            if (shardingValue instanceof Date) {
                Date shardingDate = (Date) shardingValue;
                Date startDate = ShardingTableUtils.addDateByType(shardingDate, -1, shardingType);
                Date endDate = ShardingTableUtils.addDateByType(shardingDate, 1, shardingType);
                criteria.andGreaterThan(shardingKeyField, startDate);
                criteria.andLessThan(shardingKeyField, endDate);
            }
            if (shardingValue == null) {
                String startTime = (String) paramsMap.get("startTime");
                if (StringUtil.isEmpty(startTime)) {
                    Date date = new Date();
                    date = ShardingTableUtils.addDateByType(date, -1, shardingType);
                    startTime = DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
                }


                String endTime = (String) paramsMap.get("endTime");
                if (StringUtil.isEmpty(endTime)) {
                    endTime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
                }

                criteria.andGreaterThanOrEqualTo("createTime", DateUtil.parseDateTime(startTime));
                criteria.andLessThan("createTime", DateUtil.parseDateTime(endTime));
            }
        }
        return isDateSharding;
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

        String idField = getIdField(entityClass);
        if (paramsMap.get(idField) == null) {
            throw new ShardingKeyException("idField" + ":param " + idField + " is null");
        }
        this.checkShardingKey(entityClass, paramsMap);
        boolean isDateSharding = this.shardingDateCondition(criteria, paramsMap, entityClass);
        String shardingKeyField = getShardingKeyField(entityClass);
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (isDateSharding && entry.getKey().equals(shardingKeyField)) {
                continue;
            }
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }
        return getMapper().updateByExampleSelective(entity, example);
    }

    public int delete(Object id, Date searchDate, Class<T> entityClass) {
        if (id == null) {
            throw new IdNullException("id is null");
        }
        if (searchDate == null) {
            throw new ShardingKeyNullException("searchDate is null");
        }
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();

        String idField = getIdField(entityClass);
        String shardingKeyField = getShardingKeyField(entityClass);
        Map<String, Object> paramsMap = ImmutableMap.of(shardingKeyField, searchDate, idField, id);
        this.shardingDateCondition(criteria, paramsMap, entityClass);
        criteria.andEqualTo(idField, id);
        return getMapper().deleteByExample(example);
    }

    public int updateData(T entity, Date searchDate, Class<T> entityClass) {
        String shardingKeyField = getShardingKeyField(entityClass);
        String idField = getIdField(entityClass);
        BeanUtil.setFieldValue(entity, shardingKeyField, null);
        Object id = BeanUtil.getFieldValue(entity, idField);
        //用于指定shardingKey对应的属性名及属性值
        Map<String, Object> paramsMap = ImmutableMap.of(shardingKeyField, searchDate, idField, id);
        return this.updateByPrimaryKeySelectiveExample(entity, paramsMap, entityClass);
    }

    /**
     * 检查参数中shardingKey是否有值
     *
     * @param entityClass
     * @param paramsMap
     */
    public void checkShardingKey(Class<T> entityClass, Map<String, Object> paramsMap) {
        String shardingKeyField = getShardingKeyField(entityClass);
        if (paramsMap.get(shardingKeyField) == null) {
            throw new ShardingKeyException(SHARDING_KEY_FIELD + ":param " + shardingKeyField + " is null");
        }
    }

    private void addEntityParam(T entity, Map<String, Object> otherParams) {
        Map<String, Object> paramsMap = BeanUtil.beanToMap(entity);
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null || "class".equals(entry.getKey())) {
                continue;
            }
            otherParams.put(entry.getKey(), entry.getValue());
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
    public MyPageInfo<T> findPageBy(T entity, Class<T> entityClass, MyPageInfo<T> pageInfo, Map<String, Object> otherParams) {
        this.addEntityParam(entity, otherParams);
        Example example = createConditions(entity, otherParams);
        this.checkPageLimit(entityClass, pageInfo, otherParams);
        if (StringUtil.isNotEmpty(pageInfo.getOrderBy()) && StringUtil.isNotEmpty(pageInfo.getOrder())) {
            example.setOrderByClause(StringUtil.camelhumpToUnderline(pageInfo.getOrderBy()) + " " + pageInfo.getOrder());
        }
        PageMethod.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isCountTotal());
        List<T> list = this.getMapper().selectByExample(example);
        boolean isCountTotal = pageInfo.isCountTotal();
        pageInfo = new MyPageInfo<>(list);
        pageInfo.setCountTotal(isCountTotal);
        if (!pageInfo.isCountTotal()) {
            pageInfo.setPages(100);
        }
        return pageInfo;
    }

    public List<T> findBy(T entity, Class<T> entityClass) {
        return this.findBy(entity, entityClass, null, null);
    }

    public List<T> findBy(T entity, Class<T> entityClass, Date startDate, Date endDate) {
        Map<String, Object> paramsMap = BeanUtil.beanToMap(entity);
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        String shardingKeyField = getShardingKeyField(entityClass);
        boolean isDateSharding = this.shardingDateCondition(criteria, paramsMap, entityClass);
        if (isDateSharding) {
            if (startDate == null || endDate == null) {
                this.checkShardingKey(entityClass, paramsMap);
            } else {
                criteria.andGreaterThanOrEqualTo(shardingKeyField, startDate);
                criteria.andLessThan(shardingKeyField, endDate);
            }
        }
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null || "class".equals(entry.getKey()) || isDateSharding && entry.getKey().equals(shardingKeyField)) {
                continue;
            }
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }


        //最多返回100行
        PageMethod.startPage(1, 100, false);
        return this.getMapper().selectByExample(example);
    }

    public T findById(Object id, Date searchDate, Class<T> entityClass) {
        if (id == null) {
            log.warn("----findById id is null");
            return null;
        }
        List<T> list = findByIds(Arrays.asList(id), searchDate, entityClass);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public List<T> findByIds(List<Object> ids, Date searchDate, Class<T> entityClass) {
        return findByIds(ids, searchDate, entityClass, null);
    }

    public List<T> findByIds(List<Object> ids, Date searchDate, Class<T> entityClass, String includeFields) {
        Example example = new Example(entityClass);
        if (CharSequenceUtil.isNotBlank(includeFields)) {
            example.selectProperties(includeFields.split(","));
        }
        Example.Criteria criteria = example.createCriteria();
        if (CollUtil.isEmpty(ids)) {
            throw new IdNullException("ids empty");
        }
        if (searchDate == null) {
            throw new ShardingKeyNullException("searchDate is null");
        }
        String shardingKeyField = getShardingKeyField(entityClass);
        Date start = DateUtil.offsetDay(searchDate, -1);
        Date end = DateUtil.offsetDay(searchDate, 1);

        criteria.andGreaterThanOrEqualTo(shardingKeyField, start);
        criteria.andLessThan(shardingKeyField, end);
        criteria.andIn(BeanUtils.getPKPropertyName(entityClass), ids);//只取一个主键
        return this.getMapper().selectByExample(example);
    }

    public abstract Example createConditions(T entity, Map<String, Object> otherParams);
}
