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
    public List<Integer> authUserTypes() {
        return Arrays.asList( Member.ROLE_TYPE_CREATER);
    }
}
