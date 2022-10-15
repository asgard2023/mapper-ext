package tk.mybatis.mapper.common;

import tk.mybatis.mapper.common.base.select.SelectCountExtMapper;
import tk.mybatis.mapper.common.example.SelectExtByExampleMapper;
import tk.mybatis.mapper.common.insert.InsertExtMapper;
import tk.mybatis.mapper.common.special.UpdateListByIdMapper;
import tk.mybatis.mapper.common.special.UpdateListByIdSelectiveMapper;

public interface MapperExt<T> extends SelectCountExtMapper<T>, SelectExtByExampleMapper<T>
        , UpdateListByIdMapper<T>, UpdateListByIdSelectiveMapper<T>
        , InsertExtMapper<T> {
}
