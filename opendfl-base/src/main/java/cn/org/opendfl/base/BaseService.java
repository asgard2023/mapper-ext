/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.org.opendfl.base;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.*;

/**
 * Created by caco on 2017/07/21.
 */
@Slf4j
public abstract class BaseService<T> implements IBaseService<T> {

    public abstract Mapper<T> getMapper();

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Object key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    /**
     * 更新对象所有属性，包括空值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int update(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    /**
     * 只更新有值的属性
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 主要用于处理shardingjdbc分表的，这里也能用
     *
     * @param entity
     * @param paramsMap
     * @param entityClass
     * @return
     */
    public int updateByPrimaryKeySelectiveExample(T entity, Map<String, Object> paramsMap, Class<T> entityClass) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }
        return getMapper().updateByExampleSelective(entity, example);
    }

    /**
     * 软删除
     *
     * @param entity
     * @param propertyName
     * @param deleteStatus
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteVirtual(T entity, String propertyName, Object deleteStatus) throws Exception {
        BeanUtils.setValue(entity, propertyName, deleteStatus);
        return this.getMapper().updateByPrimaryKeySelective(entity);
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
     * @param ids
     * @param entityClass
     * @return
     * @throws Exception
     */
    @Override
    public List<T> findByPropotys(String propName, List<Object> propotys, Class<T> entityClass, String orderByClause) throws Exception {
        if (CollUtil.isEmpty(propotys)) {
            return Collections.emptyList();
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
        if (CollUtil.isEmpty(list)) {
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

    /**
     * 分页查询
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
    public Map<String, T> findMapByIds(List<Object> ids, Class<T> entity) throws Exception {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<T> pos = this.findByIds(ids, entity);
        if (CollUtil.isEmpty(pos)) {
            return Collections.emptyMap();
        }
        Map<String, T> map = new HashMap<String, T>();
        for (T po : pos) {
            Object tid = BeanUtils.getValue(po, "id");
            map.put(tid.toString(), po);
        }
        return map;
    }

}
