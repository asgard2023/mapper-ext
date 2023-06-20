package cn.org.opendfl.sharding.config.annotations;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;

import java.util.Date;

@Data
public class ShardingKeyVo {
    public ShardingKeyVo() {

    }

    public ShardingKeyVo(ShardingKey shardingKey, String field) {
        this.field = field;
        this.shardTableCount = shardingKey.shardTableCount();
        this.tablePrefix = shardingKey.tablePrefix();
        this.shardDbCount = shardingKey.shardDbCount();
        this.dbPrefix = shardingKey.dbPrefix();
        this.name = shardingKey.name();
        this.shardingType = shardingKey.shardingType().getType();
        this.dateFormat = shardingKey.dateFormat();
        if (CharSequenceUtil.isNotBlank(shardingKey.minDate())) {
            Date minDateValue = DateUtil.parseDate(shardingKey.minDate());
            if (minDateValue != null) {
                this.minDate = minDateValue;
            }
        }
    }

    private String field;
    private int shardingType;
    private int shardTableCount;
    private int shardDbCount;
    private String dateFormat;
    private String tablePrefix;
    private String dbPrefix;
    private String name;

    private Date minDate;
}
