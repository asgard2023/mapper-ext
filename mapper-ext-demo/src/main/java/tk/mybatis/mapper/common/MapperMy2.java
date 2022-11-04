package tk.mybatis.mapper.common;

import tk.mybatis.mapper.common.special.InsertListByIdMapper;
import tk.mybatis.mapper.common.special.UpdateListByIdSelectiveMapper;
public interface MapperMy2<T> extends MapperMy<T>, InsertListByIdMapper<T>, UpdateListByIdSelectiveMapper<T> {
}
