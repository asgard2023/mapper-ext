package cn.org.opendfl.shardings.biz;

import cn.org.opendfl.base.IBaseService;
import cn.org.opendfl.shardings.po.UserLogin;


public interface IUserLoginBiz extends IBaseService<UserLogin> {

    public int saveUserLogin(UserLogin entity);

    public int updateUserLogin(UserLogin entity);

    public int deleteUserLogin(Long id, String operUserLogin, String remark);
}
