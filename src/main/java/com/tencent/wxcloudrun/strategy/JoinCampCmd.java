package com.tencent.wxcloudrun.strategy;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.service.CampService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 加入减脂营
 * @Author：zhoutao
 * @Date：2023/3/7 12:19
 */
@Component
public class JoinCampCmd implements Command{
    @Resource
    CampService campService;
    
    @Override
    public ApiResponse execute(String commandRequest, LoginInfo loginInfo) {
        return campService.joinCamp(loginInfo.getCampId(),loginInfo.getWxGroupName(),loginInfo.getWxId());
    }

    @Override
    public CommandEnum type() {
        return CommandEnum.join_camp;
    }
}
