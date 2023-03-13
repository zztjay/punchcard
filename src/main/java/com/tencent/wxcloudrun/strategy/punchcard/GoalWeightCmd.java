package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.MemberService;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 体重打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class GoalWeightCmd extends AbstractWeightCmd {

    @Resource
    MemberService memberService;

    @Override
    public String type() {
        return "目标体重";
    }

    @Override
    public String cmdPrexReg() {
       return "目标(体重)?" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex; }


    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        memberService.updateGoalWeight(loginInfo.getWxId(),loginInfo.getCampId(),data.getString("weight"));
        return super.execute(date, commandRequest, data, loginInfo);
    }
}
