package cn.org.opendfl.sharding.exceptions;


public class ShardingNotSupportException extends ShardingException {
    public static final String ERR_CODE = "ShardingNotSupport";
    public static final String ERR_TYPE = "ShardingKey";

    public ShardingNotSupportException(String errorMsg) {
        super(ERR_CODE, ERR_TYPE, errorMsg);
    }

}
