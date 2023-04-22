package cn.org.opendfl.shardings.biz;

import cn.org.opendfl.sharding.base.IBaseDateServiceSharding;
import cn.org.opendfl.shardings.po.UserLogin;

import java.util.Date;
import java.util.List;


public interface IUserLoginBiz extends IBaseDateServiceSharding<UserLogin> {

    public int saveUserLogin(UserLogin entity);

    public int updateUserLogin(UserLogin entity, Date searchDate);

    public int deleteUserLogin(Long id, Date searchDate, String operUserLogin, String remark);

    public List<UserLogin> selectByTime(Date start, Date end);

    public List<UserLogin> selectByExample(Date start, Date end);
}
