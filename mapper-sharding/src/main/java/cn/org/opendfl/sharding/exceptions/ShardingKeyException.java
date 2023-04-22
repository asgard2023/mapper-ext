package cn.org.opendfl.sharding.exceptions;


public class ShardingKeyException extends ShardingException {
    public static final String ERR_CODE  = "ShardingKey";
    public static final String ERR_TYPE = "ShardingKey";


    public ShardingKeyException(String errorMsg) {
        super(ERR_CODE, ERR_TYPE, errorMsg);
    }

}
