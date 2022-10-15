package tk.mybatis.mapper.common.insert;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.provider.base.BaseInsertExtProvider;

/**
 * insert 扩展功能
 * @author chenjh
 * @param <T>
 */
public interface InsertExtMapper<T> {
    /**
     * 插入数据并返回id
     *
     * @param record
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = BaseInsertExtProvider.class, method = "dynamicSQL")
    int insertById(T record);
}
