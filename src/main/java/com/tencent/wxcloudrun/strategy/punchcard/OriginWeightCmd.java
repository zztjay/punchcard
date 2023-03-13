package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dao.MembersMapper;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.MemberService;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.tencent.wxcloudrun.constants.CmdRegexConstant.*;

/**
 * 体重打卡
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class OriginWeightCmd extends AbstractWeightCmd {

    @Resource
    MemberService memberService;

    @Override
    public String type() {
        return "原始体重";
    }


    @Override
    public String cmdPrexReg() {
        return "[\\s]*原始(体重)?" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex;
    }


    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {

        memberService.updateOriginWeight(loginInfo.getWxId(),loginInfo.getCampId(),data.getString("weight"));

        return super.execute(date, commandRequest, data, loginInfo);
    }

}
