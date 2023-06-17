package cn.org.opendfl.shardings.mapper;


import cn.org.opendfl.shardings.po.User;
import tk.mybatis.mapper.common.MapperMy;
import tk.mybatis.mapper.common.special.InsertListByIdMapper;

/**
 * @author chenjh
 */
public interface UserMapper extends MapperMy<User>, InsertListByIdMapper<User> {

}  