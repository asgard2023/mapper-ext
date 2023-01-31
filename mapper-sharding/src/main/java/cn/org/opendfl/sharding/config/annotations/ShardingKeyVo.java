package cn.org.opendfl.sharding.config.annotations;

public class ShardingKeyVo {
    public ShardingKeyVo() {

    }

    public ShardingKeyVo(ShardingKey shardingKey, String field) {
        this.field = field;
        this.shardCount = shardingKey.shardCount();
        this.tablePrefix = shardingKey.tablePrefix();
        this.name = shardingKey.name();
        this.shardingType=shardingKey.shardingType().getType();
        this.dateFormat= shardingKey.dateFormat();
    }

    private String field;
    private int shardingType;
    private int shardCount;
    private String dateFormat;
    private String tablePrefix;
    private String name;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getShardCount() {
        return shardCount;
    }

    public void setShardCount(int shardCount) {
        this.shardCount = shardCount;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShardingType() {
        return shardingType;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
