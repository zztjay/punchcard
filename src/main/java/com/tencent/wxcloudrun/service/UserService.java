package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.util.StringUtils;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.dao.UsersMapper;
import com.tencent.wxcloudrun.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 团队成员业务器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:38
 */
@Service
public class UserService {
    @Resource
    UsersMapper usersMapper;

    public User getUser(String openId) {
        return usersMapper.getByOpenId(openId);
    }

    public Long save(User user) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(user.getMemberWxId()));

        // 已注册，更新用户信息
        if (isUserRegister(user.getMemberWxId())) {
            User dbUser = usersMapper.getByWxId(LoginContext.getWxId());
            user.setId(dbUser.getId());
            usersMapper.updateByPrimaryKeySelective(user);
        }
        // 未注册，插入用户信息
        else {
           usersMapper.insert(user);
        }
        return user.getId();
    }

    public boolean isUserRegister(String wxId) {
        User user = usersMapper.getByWxId(wxId);
        if (user == null) {
            return false;
        }
        return true;
    }
}
