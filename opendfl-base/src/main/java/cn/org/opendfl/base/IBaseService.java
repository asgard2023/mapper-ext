package cn.org.opendfl.base;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
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
    public int updateByPrimaryKeySelectiveExample(T entity, Map<String, Object> paramsMap, Class<?> entityClass);


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
    public List<T> findByIds(List<Object> ids, Class<?> entityClass);

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


    List<T> findByPropotys(String propName, List<Object> propotys, Class<?> entityClass, String orderByClause) throws Exception;

    List<T> findByPropotys(String propName, List<Object> propotys, Class<?> entityClass) throws Exception;

    /**
     * 通过ids转成Map
     *
     * @param ids
     * @return
     * @throws Exception
     */
    Map<String, T> findMapByIds(List<Object> ids, Class<T> entity) throws Exception;


    public default void addEqualByKey(Example.Criteria criteria, String key, Map<String, Object> otherParams) {
        Object object = otherParams.get(key);
        if (object != null) {
            if (object instanceof String) {
                String value = (String) object;
                if (value.length() > 0) {
                    criteria.andEqualTo(key, value);
                }
            } else {
                criteria.andEqualTo(key, object);
            }
        }
    }

    /**
     * 支持JqGrid的动态查询filters参数处理
     *
     * @param criteria
     * @param otherParams
     */
    public default void addFilters(Example.Criteria criteria, Map<String, Object> otherParams) {
        String filters = (String) otherParams.get("filters");
        if (!CharSequenceUtil.isEmpty(filters)) {
            JSONObject jsonFilter = (JSONObject) JSON.toJSON(filters);
            String groupOp = jsonFilter.getString("groupOp");
            JSONArray rules = jsonFilter.getJSONArray("rules");
            int rulesCount = rules.size();
            if ("AND".equals(groupOp)) {
                for (int i = 0; i < rulesCount; i++) {
                    JSONObject rule = rules.getJSONObject(i);
                    String field = rule.getString("field");
                    Object data = rule.get("data");
                    String op = rule.getString("op");
                    if ("eq".equals(op)) {//等于
                        criteria.andEqualTo(field, data);
                    } else if ("ne".equals(op)) {//不等于
                        criteria.andNotEqualTo(field, data);
                    } else if ("nn".equals(op)) {//存在
                        criteria.andIsNotNull(field);
                    } else if ("nu".equals(op)) {//不存在
                        criteria.andIsNull(field);
                    } else if ("in".equals(op)) {//属于
                        String datas = (String) data;
                        criteria.andIn(field, Arrays.asList(datas.split(",")));
                    } else if ("ni".equals(op)) {//不属于
                        String datas = (String) data;
                        criteria.andNotIn(field, Arrays.asList(datas.split(",")));
                    } else if ("cn".equals(op)) {//包含
                        String datas = (String) data;
                        criteria.andLike(field, datas);
                    } else if ("nc".equals(op)) {//不包含
                        String datas = (String) data;
                        criteria.andNotLike(field, datas);
                    } else if ("bw".equals(op)) {//开始于
                        criteria.andGreaterThanOrEqualTo(field, data);
                    } else if ("gt".equals(op)) {
                        criteria.andGreaterThan(field, data);
                    } else if ("lt".equals(op)) {
                        criteria.andLessThan(field, data);
                    } else if ("ew".equals(op)) {//结束于
                        criteria.andLessThanOrEqualTo(field, data);
                    }
                }
            }
        }
    }
}
