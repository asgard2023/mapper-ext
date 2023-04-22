package cn.org.opendfl.sharding.exceptions;

/**
 * 分表shardingKey值为空
 *
 * @author chenjh
 */
public class ShardingKeyNullException extends ShardingException {

    public static final String ERR_CODE = "ShardingKeyNull";
    public static final String ERR_TYPE = "INPUT";

    public ShardingKeyNullException(String errorMsg) {
        super(ERR_CODE, ERR_TYPE, errorMsg);
    }

}
