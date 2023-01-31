package cn.org.opendfl.shardings.mapper;

import cn.org.opendfl.shardings.po.UserLogin;
import tk.mybatis.mapper.common.MapperMy;
import tk.mybatis.mapper.common.special.InsertListByIdMapper;

/**
 * @author chenjh
 */
public interface UserLoginMapper extends MapperMy<UserLogin>, InsertListByIdMapper<UserLogin> {

}  