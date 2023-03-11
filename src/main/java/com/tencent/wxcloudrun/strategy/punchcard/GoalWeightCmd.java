package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

/**
 * 体重打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class GoalWeightCmd implements PunchCardCmd {
    public static final String heightNumRegex = "([0-9]|[1-9][0-9]{1,3})" + "(\\.\\d{1,2})?"; // 验证体重的数字,11.01

    public static void main(String[] args) {
        System.out.println("0000.01".matches(heightNumRegex));
        System.out.println("10010.01".matches(heightNumRegex));
        System.out.println("10010.011".matches(heightNumRegex));
        System.out.println("01.011".matches(heightNumRegex));
        System.out.println("199.01".matches(heightNumRegex));
    }
    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        return null;
    }

    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        return null;
    }

    @Override
    public String type() {
        return "目标体重";
    }

    @Override
    public String cmdReg() {
        return new StringBuilder().append(cmdPrexReg())
                .append(heightNumRegex).toString();
    }

    @Override
    public String cmdPrexReg() {
       return "[\\s]*目标(体重)?" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex; }

    @Override
    public String dataReg() {
        return heightNumRegex;
    }


}
