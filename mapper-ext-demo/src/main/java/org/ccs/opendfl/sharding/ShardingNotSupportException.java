package org.ccs.opendfl.sharding;


public class ShardingNotSupportException extends ShardingException {
    private String resultCode = "405";
    private String title = "Error";

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ShardingNotSupportException(String resultCode, String errorMsg) {
        super(errorMsg);
        this.resultCode = resultCode;
    }

    public ShardingNotSupportException(String errorMsg) {
        super(errorMsg);
    }

}
