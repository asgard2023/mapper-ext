package cn.org.opendfl.shardings.po;


import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author kelvin
 * @since 2019
 */
@Data
@Table(name = "t_user")
public class User implements Serializable {
    static {
        AnnotationUtils.initShardkingKey(User.class);
    }

    /**
     *
     */
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

    /**
     * 名字
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

}