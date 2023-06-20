package cn.org.opendfl.sharding.config.utils;

/**
 * mybatis类型
 * @author chenjh
 */
public enum MybatisType {
    /**
     * mybatis plus
     *
     */
    MYBATIS_PLUS("mybatisPlus"),
    /**
     * tk mybatis mapper方式
     */
    MYBATIS_MAPPER("mybatisMapper");
    private final String type;
    MybatisType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static MybatisType parse(String type){
        MybatisType mybatisType = null;
        switch (type){
            case "mybatisPlus":
                mybatisType = MYBATIS_PLUS;
                break;
            case "mybatisMapper":
                mybatisType = MYBATIS_MAPPER;
                break;
            default:
                mybatisType = null;
        }
        return mybatisType;
    }
}
