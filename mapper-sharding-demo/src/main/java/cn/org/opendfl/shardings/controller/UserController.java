package cn.org.opendfl.shardings.controller;


import cn.org.opendfl.base.MyPageInfo;
import cn.org.opendfl.exception.ResultData;
import cn.org.opendfl.exception.ValidateUtils;
import cn.org.opendfl.shardings.biz.IUserBiz;
import cn.org.opendfl.shardings.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Version V1.0
 * t_user Controller
 * @author: chenjh
 * @Date: 2022-10-27 17:12:22
 * @Company: chenjh
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
@RestController
@RequestMapping("user/user")
public class UserController {

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserBiz userBiz;

    /**
     * t_user列表查询
     *
     * @param request  请求req
     * @param entity   t_user对象
     * @param pageInfo 翻页对象
     * @return MyPageInfo 带翻页的数据集
     * @author chenjh
     * @date 2022-10-27 17:12:22
     */
    @RequestMapping(value = "list", method = {RequestMethod.POST, RequestMethod.GET})
    public MyPageInfo<User> queryPage(HttpServletRequest request, User entity, MyPageInfo<User> pageInfo) {
        if (entity == null) {
            entity = new User();
        }
        pageInfo = userBiz.findPageBy(entity, pageInfo, this.createAllParams(request));
        return pageInfo;
    }


    /**
     * t_user 新增
     *
     * @param request 请求req
     * @param entity  t_user对象
     * @return ResultData 返回数据
     * @author chenjh
     * @date 2022-10-27 17:12:22
     */
    @RequestMapping(value = "save", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData edit(User entity, HttpServletRequest request) {
        if (entity.getId() != null && entity.getId() > 0) {
            return update(entity, request);
        }
//		entity.setUpdateUser(getCurrentUserId());
//		entity.setCreateUser(getCurrentUserId());
        userBiz.saveUser(entity);
        return ResultData.success();
    }

    /**
     * t_user 更新
     *
     * @param request 请求req
     * @param entity  t_user对象
     * @return ResultData 返回数据
     * @author chenjh
     * @date 2022-10-27 17:12:22
     */
    @RequestMapping(value = "update", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData update(User entity, HttpServletRequest request) {
//		entity.setUpdateUser(getCurrentUserId());
        int v = userBiz.updateUser(entity);
        return ResultData.success(v);
    }

    /**
     * t_user 删除
     *
     * @param request 请求req
     * @param id      t_userID
     * @return ResultData 返回数据
     * @author chenjh
     * @date 2022-10-27 17:12:22
     */
    @RequestMapping(value = "delete", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultData delete(@RequestParam(name = "id", required = false) Long id, HttpServletRequest request) {
        ValidateUtils.notNull(id, "id不能为空");
        String remark = request.getParameter("remark");
        int v = userBiz.deleteUser(id, this.getCurrentUserId(), remark);
        return ResultData.success(v);
    }

    private String getCurrentUserId() {
        return null;
    }

    public Map<String, Object> createAllParams(HttpServletRequest request) {
        Map properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<>();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        Pattern p = Pattern.compile("\t|\r|\n");
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            Matcher m = p.matcher(value.trim());
            value = m.replaceAll("");
            returnMap.put(name, value);
        }
        return returnMap;
    }
}