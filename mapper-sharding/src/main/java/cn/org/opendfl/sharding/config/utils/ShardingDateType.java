package cn.org.opendfl.sharding.config.utils;

public enum ShardingDateType {
    ID(1),
    DATETIME(2);
    private final int type;
    ShardingDateType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
