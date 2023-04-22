package cn.org.opendfl.sharding.config.utils;

/**
 * @author chenjh
 */
public enum ShardingType {
    /**
     * ID取模
     */
    ID_MOD(1, ShardingDateType.ID.getType()),
    /**
     * 日期，按月/天等
     */
    DATE(2, ShardingDateType.DATETIME.getType()),
    MONTH(3, ShardingDateType.DATETIME.getType()),
    YEAR(4, ShardingDateType.DATETIME.getType());
    private final int type;
    private final int dateType;

    ShardingType(int type, int dateType) {
        this.type = type;
        this.dateType = dateType;
    }

    public int getType() {
        return type;
    }

    public int getDateType() {
        return dateType;
    }

    public static boolean isDateSharding(int type){
        ShardingType shardingType = parse(type);
        return shardingType!=null && shardingType.dateType==ShardingDateType.DATETIME.getType();
    }

    public static ShardingType parse(int value) {    //将数值转换成枚举值
        switch (value) {
            case 1:
                return ID_MOD;
            case 2:
                return DATE;
            case 3:
                return MONTH;
            case 4:
                return YEAR;
            default:
                return null;
        }
    }
}
