package tk.mybatis.mapper.common;

import tk.mybatis.mapper.common.special.InsertListByIdMapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;
import tk.mybatis.mapper.common.special.UpdateListByIdSelectiveMapper;

public interface MapperById<T> extends Mapper<T>, InsertUseGeneratedKeysMapper<T>, InsertListByIdMapper<T>, UpdateListByIdSelectiveMapper<T> {
}
