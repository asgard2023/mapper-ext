package cn.org.opendfl.shardings.biz;


import cn.org.opendfl.base.IBaseService;
import cn.org.opendfl.shardings.po.User;

public interface IUserBiz extends IBaseService<User> {

    public int saveUser(User entity);

    public int updateUser(User entity);

    public int deleteUser(Long id, String operUser, String remark);
}
