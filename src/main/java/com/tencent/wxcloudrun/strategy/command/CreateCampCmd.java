package com.tencent.wxcloudrun.strategy.command;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.service.CampService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建减脂营
 * @Author：zhoutao
 * @Date：2023/3/7 12:19
 */
@Component
public class CreateCampCmd implements Command<String> {
    @Resource
    CampService campService;

    @Override
    public boolean isMatch(String inputCmd) {
        return false;
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        return null;
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        Camp camp = new Camp();
        camp.setCampName(loginInfo.getGroupName());
        camp.setGroupId(loginInfo.getGroupId());
        camp.setCreaterWxId(loginInfo.getWxId());
        camp.setCreaterName(loginInfo.getWxGroupName());
        return campService.save(camp);
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {
        return null;
    }

    @Override
    public String commandName() {
        return null;
    }

    @Override
    public String commandReg() {
        return null;
    }


    @Override
    public List<String> examples() {
        return null;
    }

    @Override
    public List<Integer> authUserTypes() {
        return null;
    }
}
