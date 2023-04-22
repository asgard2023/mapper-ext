package cn.org.opendfl.base;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> extends BaseConditionService<T>  {
    public abstract Mapper<T> getMapper();

    public T selectByKey(Object key);

    /**
     * 如果id为null的String字段，则取uuid
     */
    public int save(T entity);


    /**
     * 删除
     *
     * @param key
     * @return
     */
    public int delete(Object key);

    /**
     * 更新对象所有属性，包括空值
     *
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 只更新有值的属性
     *
     * @param entity
     * @return
     */
    public int updateByPrimaryKeySelective(T entity);

    /**
     * 主要用于sharding-jdbc
     *
     * @param entity
     * @param paramsMap 要求shardingkey的属性值必填
     * @param entityClass
     * @return
     */
    public int updateByPrimaryKeySelectiveExample(T entity, Map<String, Object> paramsMap, Class<T> entityClass);


    /**
     * 通过id查找
     *
     * @param id
     * @return
     */
    public T findById(Object id);

    /**
     * 按id列表查询
     *
     * @param ids
     * @param entityClass
     * @return
     * @throws Exception
     */
    public List<T> findByIds(List<Object> ids, Class<T> entityClass);

    /**
     * 按照实体查找
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public List<T> findBy(T entity);

    public T findOne(String propoty, Object value, Class<T> entity);

    /**
     * 软删除
     *
     * @param entity
     * @param propertyName
     * @param deleteStatus
     * @return
     * @throws Exception
     */
    public int deleteVirtual(T entity, String propertyName, Object deleteStatus) throws Exception;

    /**
     * 分页查询
     *
     * @param entity
     * @param pageInfo
     * @param otherParams
     * @return
     */
    MyPageInfo<T> findPageBy(T entity, MyPageInfo<T> pageInfo, Map<String, Object> otherParams);

    /**
     * 按某属性查询list
     *
     * @param propoty
     * @param value
     * @param entity
     * @return
     */
    List<T> findByPropoty(String propoty, Object value, Class<T> entity);

    /**
     * 按属性查询list  orderByClause ： example.setOrderByClause("create_time desc");
     *
     * @param propoty
     * @param value
     * @param entity
     * @param orderByClause，
     * @return
     * @throws Exception
     */
    List<T> findByPropoty(String propoty, Object value, Class<T> entity, String orderByClause);


    List<T> findByPropotys(String propName, List<Object> propotys, Class<T> entityClass, String orderByClause) throws Exception;

    List<T> findByPropotys(String propName, List<Object> propotys, Class<T> entityClass) throws Exception;

    /**
     * 通过ids转成Map
     *
     * @param ids
     * @return
     * @throws Exception
     */
    Map<String, T> findMapByIds(List<Object> ids, Class<T> entity) throws Exception;

}
