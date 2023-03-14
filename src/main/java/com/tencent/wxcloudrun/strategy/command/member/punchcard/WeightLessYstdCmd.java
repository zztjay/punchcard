    package com.tencent.wxcloudrun.strategy.command.member.punchcard;

import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import org.springframework.stereotype.Component;

/**
 * 比昨天瘦-体重打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class WeightLessYstdCmd extends AbstractWeightCmd {

    @Override
    public String type() {
        return "比昨天瘦";
    }

    @Override
    public String cmdPrexReg() {
        return "比昨天瘦" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex; }

    @Override
    public String dataReg() {
       return CmdRegexConstant.incOrDecrRegex + heightNumRegex;
    }
}
