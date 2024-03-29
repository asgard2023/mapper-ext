#  支持sharding-jdbc注解

## 目标
    让sharding-jdbc支持注解方式使用，以简化配置。

## 功能
* 1,支持单个分片@ShardingKey的处理，多个会取第一个
* 2,只是辅助sharding-jdbc，sharding-jdbc本身的配置还是需要的
* 3,不涉及分库，即分库仍然走原来的配置方式
* 4,actual-data-nodes的配置
  * 真实分片配置：如果查询分片key没值，会把所有分片查一遍
  * 指向空表：如果查询分片key没值，则查不到数据，即分片key的值必填。
* 4,主要用于tk.mybatis mapper

## 使用
* 1, PO配置
```java
@Table(name = "t_user")
@Data
public class UserPo implements Serializable {
    static {
        AnnotationUtils.initShardkingKey(UserPo.class);
    }
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * userId
     */
    @ShardingKey(shardTableCount = 4, tablePrefix = "t_user_")
    private Long userId;
}
```

* 2，Sharding-jdbc配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:sharding="http://shardingsphere.apache.org/schema/shardingsphere/sharding"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
                        http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://shardingsphere.apache.org/schema/shardingsphere/sharding 
                        http://shardingsphere.apache.org/schema/shardingsphere/sharding/sharding.xsd 
                        ">
  <bean id="preciseModuloTableShardingAlgorithm" class="cn.org.opendfl.sharding.config.algorithm.TbShardingKeyAlgorithm"/>
  <sharding:standard-strategy id="tableShardingStrategy" sharding-column="user_id"
                              precise-algorithm-ref="preciseModuloTableShardingAlgorithm"/>

  <sharding:key-generator id="orderKeyGenerator" type="SNOWFLAKE" column="id"/>

  <sharding:data-source id="shardingDataSource">
    <sharding:sharding-rule data-source-names="ds0,ds1">
      <sharding:table-rules>
        <sharding:table-rule logic-table="t_user"
             actual-data-nodes="ds0.t_user_empty" <!-- 用@ShardingKey，建议指向空表，即在没有分片key的查询时，查不到任何数据，分片key必填 -->
        table-strategy-ref="tableShardingStrategy"
        key-generator-ref="orderKeyGenerator" />
      </sharding:table-rules>
      <sharding:binding-table-rules>
        <sharding:binding-table-rule logic-tables="t_user"/>
      </sharding:binding-table-rules>
    </sharding:sharding-rule>
  </sharding:data-source>


  <bean id="sqlSessionFactorySharding" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="shardingDataSource"/>
    <property name="mapperLocations" value="classpath*:mapper/*.xml"/>
    <property name="plugins">
      <array>
        <ref bean="pageInterceptor"></ref>
      </array>
    </property>
  </bean>

  <bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="cn.org.opendfl.sharding.user.mapper"/>
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactorySharding"/>
  </bean>
</beans>
```