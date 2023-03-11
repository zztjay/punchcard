    package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

/**
 * 比昨天瘦-体重打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class WeightLessYstdCmd implements PunchCardCmd {
    public static final String heightNumRegex = "([0-9]|[1-9][0-9]{1,3})" + "(\\.\\d{1,2})?"; // 验证体重的数字

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
        return "比昨天瘦";
    }

    @Override
    public String cmdReg() {
        return new StringBuilder().append(cmdPrexReg())
                .append(heightNumRegex).toString();
    }

    @Override
    public String cmdPrexReg() {
        return "[\\s]*比昨天瘦" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex; }

    @Override
    public String dataReg() {
        return heightNumRegex;
    }
}
