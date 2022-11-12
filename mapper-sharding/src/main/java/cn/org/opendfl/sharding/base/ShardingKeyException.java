package cn.org.opendfl.sharding.base;


public class ShardingKeyException extends ShardingException {
    private String resultCode = "406";
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

    public ShardingKeyException(String resultCode, String errorMsg) {
        super(errorMsg);
        this.resultCode = resultCode;
    }

    public ShardingKeyException(String errorMsg) {
        super(errorMsg);
    }

}
