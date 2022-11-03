package org.ccs.opendfl.mysql.dflsystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ccs.opendfl.mysql.dflsystem.po.DflRolePo;
import tk.mybatis.mapper.common.MapperMy;

import java.util.Date;
import java.util.List;

/**
 * @Version V1.0
 * @Description: 角色表 Mapper
 * @Author: Created by chenjh
 * @Date: 2022-5-3 20:25:42
 */
public interface DflRoleMapper extends MapperMy<DflRolePo> {
    /**
     * 用于异常测试，在sharding-jdbc下，广播模式会出错
     * @param status
     * @return
     */
    @Select("select EXISTS(select 1  FROM dfl_role  WHERE  status = #{status} limit 1) cout")
    Integer countExistRole(@Param("status") Integer status);

    Integer countExistRoleXml(@Param("status") Integer status);

    @Select("select 1 FROM dfl_role  WHERE  status = #{status} limit 1")
    Integer countExistRole2(@Param("status") Integer status);

    @Select("select ifnull((select 1  FROM dfl_role  WHERE  status = #{status} limit 1),0) cout")
    Integer countExistRole3(@Param("status") Integer status);


    /**
     * 用于异常测试  时间格式判断不对
     * @param startTime
     * @param endTime
     * @return
     */
    List<DflRolePo> findRoleListByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<DflRolePo> findRoleListByDates(@Param("startTime") String startTime, @Param("endTime") String endTime);
}