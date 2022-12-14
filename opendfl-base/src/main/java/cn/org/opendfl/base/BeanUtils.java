package cn.org.opendfl.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BeanUtils {

    /**
     * 首字母大写
     *
     * @param value
     * @return
     */
    public static String upperCaseFirst(String value) {
        if (CharSequenceUtil.isEmpty(value)) {
            return value;
        }
        char[] cs = value.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static void main(String[] args) {
        class Abc {
            String giftId;

            public String getGiftId() {
                return giftId;
            }

            public void setGiftId(String giftId) {
                this.giftId = giftId;
            }

        }
        Abc vo = new Abc();
        setValue(vo, "giftId", "123");
    }

    /**
     * 通过反射设置实体的某属性值
     *
     * @param entity
     * @param propertyName
     * @param value
     */
    public static void setValue(Object entity, String propertyName, Object value) {
        if (value == null) {
            return;
        }
        Method method = null;
        try {
            String methodName = "set" + upperCaseFirst(propertyName);
            method = entity.getClass().getDeclaredMethod(methodName, value.getClass());
            method.invoke(entity, value);
        } catch (Exception e) {
            log.error("----{} setValue--propertyName={}", e.getClass().getSimpleName(), propertyName, e);
        }

    }

    /**
     * 通过返回执行某个单参数方法
     *
     * @param entity
     * @param methodName
     * @param params
     */
    public static Object executeMethod(Object entity, String methodName, Object params) {
        Method method = null;
        try {
            method = entity.getClass().getDeclaredMethod(methodName, params.getClass());
            return method.invoke(entity, params);
        } catch (Exception e) {
            log.error("----{} executeMethod--propertyName={}", e.getClass().getSimpleName(), methodName, e);
        }
        return null;
    }

    /**
     * 获取实体的@Id 主键栏位名称
     *
     * @param entityClass
     * @return
     * @throws Exception
     */
    public static String getPKPropertyName(Class<?> entityClass) {
        EntityColumn pkColumn=getPkColumn(entityClass);
        if(pkColumn!=null){
            return pkColumn.getProperty();
        }
        return null;
    }

    private static Map<String, EntityColumn> pkColumnMap=new ConcurrentHashMap<>();

    /**
     * 获取PO的主键字段
     * @param entityClass
     * @return
     */
    public static EntityColumn getPkColumn(Class<?> entityClass){
        String className= entityClass.getName();
        return pkColumnMap.computeIfAbsent(className, t->{
            Set<EntityColumn> columnSet = EntityHelper.getPKColumns(entityClass);
            if(columnSet.size() == 1) {
                return columnSet.iterator().next();
            }
            else{
                throw new MapperException("实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
            }
        });
    }

    /**
     * 通过反射获取实体某属性值
     *
     * @param entity
     * @param propertyName
     */
    public static Object getValue(Object entity, String propertyName) {
        Method method = null;
        try {
            String methodName = "get" + upperCaseFirst(propertyName);
            method = entity.getClass().getDeclaredMethod(methodName);
            return method.invoke(entity);
        } catch (Exception e) {
            log.error("----{} getValue--propertyName={}", e.getClass().getSimpleName(), propertyName, e);
        }
        return null;
    }

    public static Map<Object, Object> getMapProps(Collection<?> list, String keyProp, String valueProp) {
        Map<Object, Object> result = new HashMap<>();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        for (Object obj : list) {
            result.put(getValue(obj, keyProp), getValue(obj, valueProp));
        }
        return result;
    }

    /**
     * 属性为空不返回
     *
     * @param list
     * @param propName
     * @return
     * @throws Exception
     */
    public static List<Object> getPropsByName(Collection<?> list, String propName) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<Object> propDataList = new ArrayList<>(list.size());
        for (Object obj : list) {
            Object o = getValue(obj, propName);
            if (o != null) {
                propDataList.add(o);
            }
        }
        return propDataList;
    }

    /**
     * 属性为空不返回
     *
     * @param list
     * @param propName
     * @return
     * @throws Exception
     */
    public static List<String> getStrPropsByName(Collection<?> list, String propName) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> propDataList = new ArrayList<>(list.size());
        String str = null;
        for (Object obj : list) {
            Object o = getValue(obj, propName);
            if (o != null) {
                if (o instanceof String) {
                    str = (String) o;
                } else {
                    str = "" + o;
                }
                propDataList.add(str);
            }
        }
        return propDataList;
    }

    public static List<String> toStringList(List<Object> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> list2 = new ArrayList<>(list.size());
        String str = null;
        for (Object o : list) {
            str = null;
            if (o != null) {
                if (o instanceof String) {
                    str = (String) o;
                } else {
                    str = "" + o;
                }
            }
            list2.add(str);
        }
        return list2;
    }

    /**
     * 从对象中获取指定的属性
     *
     * @param bean
     * @param property
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getObjectByProperty(Object bean, String property)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if (CharSequenceUtil.isEmpty(property)) {
            return null;
        }
        property = property.trim();
        Class<?> type = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class") && propertyName.equals(property)) {
                Method readMethod = descriptor.getReadMethod();
                return readMethod.invoke(bean, new Object[0]);
            }
        }
        return null;
    }

    public static String getAllProperties(Class<?> entityClass, String ignoreProperties) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        String[] ignores = new String[0];
        if (ignoreProperties != null) {
            if (ignoreProperties.contains(",")) {
                ignores = ignoreProperties.split(",");
            } else {
                ignores = new String[]{ignoreProperties};
            }
        }
        boolean isIgnore = false;
        for (EntityColumn entityColumn : columnList) {
            isIgnore = false;
            for (String ignore : ignores) {
                if (entityColumn.getProperty().equals(ignore)) {
                    isIgnore = true;
                    break;
                }
            }
            if (isIgnore) {
                continue;
            }
            sql.append(entityColumn.getProperty()).append(",");
        }
        String rSql = sql.toString();
        if (rSql.endsWith(",")) {
            rSql = rSql.substring(0, rSql.length() - 1);
        }
        return rSql;
    }
}
