package cn.org.opendfl.shardings.po;

import cn.org.opendfl.sharding.config.annotations.ShardingKey;
import cn.org.opendfl.sharding.config.utils.AnnotationUtils;
import cn.org.opendfl.sharding.config.utils.ShardingType;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
    private String funcCode;
    private String loginType;
    private String ip;
    private String deviceId;
    private String sysVersion;
    @ShardingKey(shardingType = ShardingType.DATE, tablePrefix = "t_user_login_", dateFormat = "yyMM")
    private Date createTime;
    private String result;
    private String qd;
}
