package tk.mybatis.mapper.common.base.select;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.provider.base.BaseSelectExtProvider;

import java.util.List;

/**
 * @author chenjh
 * @param <T>
 */
public interface SelectCountExtMapper<T> {

    /**
     * 主要用于检查数据是否存在，直接取第一条，免count
     * select exists(select 1 from table_name limit 1)
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectExtProvider.class, method = "dynamicSQL")
    Integer selectCountExist(T record);

    @SelectProvider(type = BaseSelectExtProvider.class, method = "dynamicSQL")
    Long selectMaxId(T record);

    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = BaseSelectExtProvider.class, method = "dynamicSQL")
    List<Long> selectIds(T record);
}
