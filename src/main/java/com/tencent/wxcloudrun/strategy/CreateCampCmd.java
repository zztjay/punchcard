package com.tencent.wxcloudrun.strategy;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.service.CampService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 创建减脂营
 * @Author：zhoutao
 * @Date：2023/3/7 12:19
 */
@Component
public class CreateCampCmd implements Command{
    @Resource
    CampService campService;

    @Override
    public ApiResponse execute(String commandRequest, LoginInfo loginInfo) {
        Camp camp = new Camp();
        camp.setCampName(loginInfo.getGroupName());
        camp.setGroupId(loginInfo.getGroupId());
        camp.setCreaterWxId(loginInfo.getWxId());
        camp.setCreaterName(loginInfo.getWxGroupName());
        return campService.save(camp);
    }

    @Override
    public CommandEnum type() {
        return CommandEnum.create_camp;
    }

}
