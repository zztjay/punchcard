package com.tencent.wxcloudrun.strategy.command.creater;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.CampMapper;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 关闭打卡统计功能
 *
 * @Author：zhoutao
 * @Date：2023/3/7 12:19
 */
@Component
public class CloseLwCampCmd implements Command<String> {
    @Resource
    CampService campService;

    @Resource
    CampMapper campMapper;

    @Override
    public boolean isMatch(String inputCmd) {
        return RegexUtils.hasMatchParts(inputCmd, commandReg());
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        return ApiResponse.ok(new JSONObject());
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        campMapper.deleteByPrimaryKey(loginInfo.getCampId());
        return ApiResponse.ok();
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {
        return new StringBuilder("已关闭打卡统计功能，如需重新开启，请输入\"开启打卡统计\"，并@打卡小助手").toString();
    }

    public static void main(String[] args) {
        System.out.println(new CloseLwCampCmd().resultFormat(null, null));
    }

    @Override
    public String commandName() {
        return "关闭打卡统计";
    }

    @Override
    public String commandReg() {
        return "关闭打卡统计";
    }


    @Override
    public String examples() {
        return "关闭打卡统计";
    }

    @Override
    public ApiResponse<String> roleCheck(LoginInfo loginInfo) {
        if(loginInfo.getCampId() == null){
            return ApiResponse.error("CAMP_NOT_CREAT", "打卡统计功能未开启，请联系管理员开启！");
        }
        int roleType = campService.getRoleType(loginInfo.getCampId(),loginInfo.getWxId());
        if(roleType != Member.ROLE_TYPE_CREATER){
            return ApiResponse.error("NOLY_CREATER_CAN_CLOSE","您没有权限关闭打卡统计功能，请联系管理员!");
        }
        return ApiResponse.ok();
    }
}
