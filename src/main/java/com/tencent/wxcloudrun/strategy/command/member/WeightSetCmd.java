package com.tencent.wxcloudrun.strategy.command.member;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.service.MemberService;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.tencent.wxcloudrun.constants.CmdRegexConstant.mutipleSpaceRegex;

/**
 * 体重打卡
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class WeightSetCmd implements Command<String> {
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
        List<String> originCmds = RegexUtils.getMatches(originRegx(), inputCmd);
        List<String> goalCmds = RegexUtils.getMatches(goalRegx(), inputCmd);
        if (originCmds.size() > 1 || goalCmds.size() > 1) {
            return ApiResponse.error("EXTRACT_TOO_MUCH_WEIGHT_CMD", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples() + "\"");
        }

        // 提取体重数据
        if (originCmds.size() > 0) {
            ApiResponse result = extractWeight(heightNumRegex, originCmds.get(0));
            if (result.isFail()) {
                return result;
            }
            data.put("origin", result.getData());
        }
        if (goalCmds.size() > 0) {
            ApiResponse result = extractWeight(heightNumRegex, goalCmds.get(0));
            if (result.isFail()) {
                return result;
            }
            data.put("goal", result.getData());
        }

        return ApiResponse.ok(data);
    }

    private ApiResponse<String> extractWeight(String regex, String cmd) {
        List<String> weights = RegexUtils.getMatches(regex, cmd);
        if (weights.size() == 0 || CollectionUtils.isEmpty(weights)) {
            return ApiResponse.error("EXTRACT_NO_WEIGHT", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples() + "\"");
        } else if (weights.size() > 1) {
            return ApiResponse.error("EXTRACT_TOO_MUCH_WEIGHT", "格式有误，请检查！\n" +
                    "参考示例：\"" + examples() + "\"");
        }
        return ApiResponse.ok(weights.get(0));
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        if (StringUtil.isNotEmpty(cmdData.getString("goal"))) { // 目标体重
            memberService.updateGoalWeight(loginInfo.getWxId(), loginInfo.getCampId(), cmdData.getString("goal"));
        }
        if (StringUtil.isNotEmpty(cmdData.getString("origin"))) { // 原始体重
            memberService.updateOriginWeight(loginInfo.getWxId(), loginInfo.getCampId(), cmdData.getString("origin"));
        }
        return ApiResponse.ok();
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {
        StringBuilder result = new StringBuilder("设置成功");
        if (StringUtil.isNotEmpty(data.getString("origin"))) {
            result.append("，原体重").append(data.getString("origin")).append("斤");
        }
        if (StringUtil.isNotEmpty(data.getString("goal"))) {
            result.append("，目标").append(data.getString("goal")).append("斤");
        }
        result.append("\n");
        return result.toString();
    }

    @Override
    public String commandName() {
        return "体重设置";
    }

    @Override
    public String commandReg() {
        return RegexUtils.or(goalRegx(), originRegx()).toString();
    }

    public static void main(String[] args) {
        System.out.println(RegexUtils.getMatches(new WeightSetCmd().commandReg(), "我的原体重为 120斤\n" +
                "我的目标体重为 100斤"));
    }

    private String goalRegx() {
        return "目标(体重)?[为是:：]?" + mutipleSpaceRegex + heightNumRegex + "斤";
    }

    private String originRegx() {
        return "原(体重)?[为是:：]?" + mutipleSpaceRegex + heightNumRegex + "斤";
    }

    @Override
    public String examples() {
        return new StringBuilder("原体重120.6斤").append("\n").append("目标100斤").toString();
    }

    @Override
    public ApiResponse<String> roleCheck(LoginInfo loginInfo) {
        if(loginInfo.getCampId() == null){
            return ApiResponse.error("CAMP_NOT_CREAT", "打卡统计功能未开启，请联系管理员开启！");
        }
        return ApiResponse.ok();
    }
}
