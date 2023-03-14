package com.tencent.wxcloudrun.strategy.command.creater;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 加为好友的帮助提示
 *
 * @Author：zhoutao
 * @Date：2023/3/13 16:52
 */
@Component
public class BeFriendHelpCmd implements Command<String> {

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
        return ApiResponse.ok();
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {
        return new StringBuilder("很高兴认识您，我是打卡小助手，以下是step by step帮助：\n" +
                "  Step1:  把“打卡小助手”加入减肥群\n" +
                "  Step2:  群内输入“开启打卡统计功能”，并@打卡小助手\n" +
                "  Step3:  群内输入“导出excel打卡统计（3.1～3.30）”命令，并@打卡小助手，打卡小助手会私聊发送excel给您，" +
                "日期支持“近7天、近30天、上一周、上个月、自定义日期区间”\n").toString();
    }

    @Override
    public String commandName() {
        return "新朋友帮助";
    }

    @Override
    public String commandReg() {
        return "新朋友帮助";
    }

    @Override
    public String examples() {
        return "新朋友帮助";
    }

    @Override
    public List<Integer> authUserTypes() {
        return Arrays.asList(Member.ROLE_TYPE_NO_JOIN,Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER);
    }
}
