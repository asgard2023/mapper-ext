package cn.org.opendfl.sharding.config.utils;

/**
 * @author chenjh
 */
public enum ShardingType {
    /**
     * ID取模
     */
    ID_MOD(1),
    /**
     * 日期，按月/天等
     */
    DATE(2),
    /**
     * 范围分片，未实现
     */
    RANGE(3);
    private final int type;
    ShardingType(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }
}
