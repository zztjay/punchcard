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
public class TodayWeightCmd extends AbstractWeightCmd {

    @Override
    public String type() {
        return "今日体重";
    }

    @Override
    public String cmdPrexReg() {
        return "[\\s]*今日(体重)?" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex; }

}
