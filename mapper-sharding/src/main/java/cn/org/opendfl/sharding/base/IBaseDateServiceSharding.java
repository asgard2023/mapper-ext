package cn.org.opendfl.sharding.base;

import cn.org.opendfl.base.MyPageInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBaseDateServiceSharding<T> extends BaseShardingConditionService<T> {
    public T findById(Object id, Date searchDate, Class<T> entityClass);

    /**
     * 按id查询
     *
     * @param ids
     * @param searchDate
     * @param entityClass
     * @return
     */
    public List<T> findByIds(List<Object> ids, Date searchDate, Class<T> entityClass, String includeFields);

    public List<T> findBy(T entity, Class<T> entityClass);

    /**
     * 分页查询
     * shardingkey不能为空
     *
     * @param entity
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public MyPageInfo<T> findPageBy(T entity, Class<T> entityClass, MyPageInfo<T> pageInfo, Map<String, Object> otherParams);

    /**
     * 支持sharding-jdbc分表模式
     *
     * @param entity
     * @param paramsMap   要求shardingKey的属性值必填
     * @param entityClass
     * @return
     */
    public int updateByPrimaryKeySelectiveExample(T entity, Map<String, Object> paramsMap, Class<T> entityClass);


    /**
     * 用于物理删除
     *
     * @param id
     * @param searchDate
     * @param entityClass
     * @return
     */
    public int delete(Object id, Date searchDate, Class<T> entityClass);
}
