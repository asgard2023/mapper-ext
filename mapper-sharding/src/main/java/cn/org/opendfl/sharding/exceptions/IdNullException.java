package cn.org.opendfl.sharding.exceptions;

/**
 * id为空异常
 *
 * @author chenjh
 */
public class IdNullException extends ShardingException {
    public static final String ERR_CODE = "IdNull";
    public static final String ERR_TYPE = "INPUT";

    public IdNullException(String errorMsg) {
        super(ERR_CODE, ERR_TYPE, errorMsg);
    }


}
