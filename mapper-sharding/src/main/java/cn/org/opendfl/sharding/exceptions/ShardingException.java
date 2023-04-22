package cn.org.opendfl.sharding.exceptions;


public class ShardingException extends RuntimeException {
    public static final String ERR_CODE = "Sharding";
    public static final String ERR_TYPE = "ERR";

    private String resultCode = ERR_CODE;

    private String errorType = ERR_TYPE;

    public String getResultCode() {
        return resultCode;
    }

    public String getErrorType() {
        return errorType;
    }


    public ShardingException(String resultCode, String errorType, String errorMsg) {
        super(errorMsg);
        this.errorType = errorType;
        this.resultCode = resultCode;
    }

    public ShardingException(String errorMsg) {
        super(errorMsg);
    }

}
