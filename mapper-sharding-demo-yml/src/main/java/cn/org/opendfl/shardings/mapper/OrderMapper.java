package cn.org.opendfl.shardings.mapper;


import cn.org.opendfl.shardings.po.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.MapperMy;

/**
 * @author chenjh
 */
public interface OrderMapper extends MapperMy<Order> {

    @Select("select EXISTS(select 1  FROM t_order  WHERE  user_id = #{userId} limit 1) cout")
    Integer countExistOrder(@Param("userId") Long userId);

    @Select("select 1  FROM t_order  WHERE  user_id = #{userId} limit 1")
    Integer countExistOrder2(@Param("userId") Long userId);

    @Select("select ifnull((select 1  FROM t_order  WHERE  user_id = #{userId} limit 1),0) cout")
    Integer countExistOrder3(@Param("userId") Long userId);

    @Select("select count(*) cout from (select 1  FROM t_order  WHERE  user_id = #{userId}) t")
    Integer countOrder(@Param("userId") Long userId);

}  