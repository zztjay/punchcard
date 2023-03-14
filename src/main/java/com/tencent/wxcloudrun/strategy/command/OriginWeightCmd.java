package com.tencent.wxcloudrun.strategy.command;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dao.MembersMapper;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.MemberService;
import com.tencent.wxcloudrun.strategy.punchcard.AbstractWeightCmd;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static com.tencent.wxcloudrun.constants.CmdRegexConstant.*;

/**
 * 体重打卡
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class OriginWeightCmd implements Command<String> {
    public static final String heightNumRegex = "((([1-9][0-9]{1,3})(\\.\\d{1,2})?)|([0-9](\\.\\d{1,2})?))"; // 验证体重的数字
    @Resource
    MemberService memberService;

    @Override
    public boolean isMatch(String inputCmd) {
        return RegexUtils.hasMatchParts(inputCmd, commandReg());
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        JSONObject data = new JSONObject();

        // 提取命令
        List<String> matcheCmds = RegexUtils.getMatches(heightNumRegex, inputCmd);
        if(matcheCmds.size() > 1){
            return ApiResponse.error("EXTRACT_TOO_MUCH_WEIGHT_CMD", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples().get(0) + "\"");
        }
        // 提取数据
        List<String> matcheDatas = RegexUtils.getMatches(heightNumRegex, matcheCmds.get(0));
        if (matcheDatas.size() == 0) {
            return ApiResponse.error("EXTRACT_NO_WEIGHT", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples().get(0) + "\"");
        } else if (matcheDatas.size() > 1) {
            return ApiResponse.error("EXTRACT_TOO_MUCH_WEIGHT", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples().get(0) + "\"");
        } else if (matcheDatas.size() == 1) {
            data.put("weight", matcheDatas.get(0));
        }
        return ApiResponse.ok(data);
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        memberService.updateOriginWeight(loginInfo.getWxId(),loginInfo.getCampId(),cmdData.getString("weight"));
        return ApiResponse.ok();
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {
        return "设置成功，您的原始体重为" + data.getString("weight") + "斤!\n";
    }

    @Override
    public String commandName() {
        return "我的原始体重";
    }

    @Override
    public String commandReg() {
        return "(我的)?原始体重[为是:：]?" + mutipleSpaceRegex + heightNumRegex + "[斤]?";
    }

    @Override
    public List<String> examples() {
        return Arrays.asList("我的原始体重为110斤");
    }

    @Override
    public List<Integer> authUserTypes() {
        return Arrays.asList(Member.ROLE_TYPE_NO_JOIN, Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER);
    }

    public static void main(String[] args) {
        System.out.println(RegexUtils.getMatches(new OriginWeightCmd().commandReg(), "我的原始体重112\n我的目标体重129斤" ));
        System.out.println(RegexUtils.getMatches( new OriginWeightCmd().commandReg(), "我的原始体重为112斤"));
        System.out.println(RegexUtils.getMatches( new OriginWeightCmd().commandReg(), "原始体重是112斤"));
    }

}
