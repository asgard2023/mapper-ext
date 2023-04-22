package cn.org.opendfl.shardings.mapper;

import cn.org.opendfl.shardings.po.UserLogin;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MapperMy;
import tk.mybatis.mapper.common.special.InsertListByIdMapper;

import java.util.Date;
import java.util.List;

/**
 * @author chenjh
 */
public interface UserLoginMapper extends MapperMy<UserLogin>, InsertListByIdMapper<UserLogin> {
    List<UserLogin> selectByTime(@Param("start") Date start, @Param("end") Date end);
}  