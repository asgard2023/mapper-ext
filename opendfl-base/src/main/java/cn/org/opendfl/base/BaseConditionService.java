package cn.org.opendfl.base;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Map;

/**
 * 查询条件处理
 *
 * @author chenjh
 */
public interface BaseConditionService<T> {
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
            JSONObject jsonFilter = JSONUtil.parseObj(filters);
            String groupOp = jsonFilter.getStr("groupOp");
            JSONArray rules = jsonFilter.getJSONArray("rules");
            int rulesCount = rules.size();
            if ("AND".equals(groupOp)) {
                for (int i = 0; i < rulesCount; i++) {
                    JSONObject rule = rules.getJSONObject(i);
                    String field = rule.getStr("field");
                    Object data = rule.get("data");
                    String op = rule.getStr("op");
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
