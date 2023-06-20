# mybatis 分库分表扩展

基于shardingjdbc的分库分表，简化部份配置
支持mybatis plus, tk.mybatis mapper

## 目标
    让sharding-jdbc支持注解方式使用，以简化配置。

## 功能
### 支持按ID取模分片
### 支持按时间(年月日)分片
#### 支持最低时间限制
#### 支持提前3天自动建表

## 使用
### 配置
```yml
db:
  minDate: 2023-01-01 00:00:00
  dbScheme: ds
  # mybatisMapper或者mybatisPlus
  mybatisType: mybatisMapper
```

### mybatisPlus需要在PO上加@TableName, @TableId
```java
@TableName(value = "t_user")
public class User implements Serializable {
    static {
        AnnotationUtils.initShardkingKey(User.class);
    }
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * userId
     */
    @ShardingKey(shardTableCount = 4, tablePrefix = "t_user_", shardDbCount = 2, dbPrefix = "ds")
    private Long userId;
    // 其他属性略
}
```
### mybatisMapper需要在PO上加@Table,@Id
```java
@Data
@Table(name = "t_user")
public class User implements Serializable {
    static {
        AnnotationUtils.initShardkingKey(User.class);
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
    // 其他属性略
}
```

### 按时间分片示例
```java
@Data
@Table(name = "t_user_login")
public class UserLogin {
    static{
        AnnotationUtils.initShardkingKey(UserLogin.class);
    }
    /**
     * 主键
     */
    @Id
    private Long id;
    private Long userId;
    private String ip;
    @ShardingKey(shardingType = ShardingType.MONTH, tablePrefix = "t_user_login_", dateFormat = "yyMM")
    private Date createTime;
    private String result;
}
```