package com.tencent.wxcloudrun.strategy.command;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.constants.CommonConstants;
import com.tencent.wxcloudrun.constants.RegexContants;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.strategy.ModelFatory;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import com.tencent.wxcloudrun.util.CmdRegexUtil;
import com.tencent.wxcloudrun.util.DateUtil;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.tencent.wxcloudrun.constants.CmdRegexConstant.chTodayStrs;


/**
 * 打卡命令模型
 *
 * @Author：zhoutao
 * @Date：2023/3/9 09:16
 */
@Component
public class PunchCardCmdModelNew implements Command<String> {
    @Resource
    ModelFatory modelFatory;

    List<String> PUNCH_CARD_KEY_WORDS = Arrays.asList("打卡", "减肥打卡", "运动打卡", "健身打卡", "食物打卡");

    @Override
    public boolean isMatch(String inputCmd) {
        // 如果命中了打卡类型的正则，则确定是打卡
        List<PunchCardCmd> punchCardCmds = modelFatory.getModels(PunchCardCmd.class);
        for (PunchCardCmd punchCardCmd : punchCardCmds) {
            if (RegexUtils.hasMatchParts(inputCmd, punchCardCmd.cmdReg())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        JSONObject data = new JSONObject();
        List<String> matches = RegexUtils.getMatches(CmdRegexConstant.dateRegex, inputCmd);

        if (matches.size() == 0) {  // 无打卡日期，默认以今天为准
            data.put("punchcardDate", DateUtil.getToday());
        } else if (matches.size() == 1) {
            String year = String.valueOf(new DateTime().getYear());
            String dateStr = matches.get(0);
            if (dateStr.equals("今日") || dateStr.equals("今天")) {
                data.put("punchcardDate", DateUtil.getToday());
            } else if (dateStr.contains("月") && dateStr.contains("日")) {
                dateStr = year + "年" + dateStr;
                data.put("punchcardDate", DateUtil.getDate2Str(DateUtil.DATE_FORMAT_PATTERN
                        , DateUtil.getStrToDate("yyyy年MM月dd日", dateStr)));
            } else if (dateStr.contains("\\.")) {
                dateStr = year + "." + dateStr;
                data.put("punchcardDate", DateUtil.getDate2Str(DateUtil.DATE_FORMAT_PATTERN
                        , DateUtil.getStrToDate("yyyy.MM.dd", dateStr)));
            } else {
                dateStr = year + dateStr;
                data.put("punchcardDate", DateUtil.getStrToDate("yyyyMMdd", dateStr));
            }
        } else if (matches.size() > 1) {  // 存在多个日期，不合法
            return ApiResponse.error("DATE_TOO_MATCH", "您提交了多个打卡日期，请修正！");
        }

        // 提取所有合法的命令
        List<String> matchPunchCardCmds = new ArrayList<>();
        List<PunchCardCmd> punchCardCmds = modelFatory.getModels(PunchCardCmd.class);
        for (PunchCardCmd punchCardCmd : punchCardCmds) {
            if (RegexUtils.hasMatchParts(inputCmd, punchCardCmd.cmdReg())) {
                matchPunchCardCmds.add(punchCardCmd.type());
            }
        }
        data.put("matchPunchCardCmds", matchPunchCardCmds);

        return ApiResponse.ok(data);
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject data, LoginInfo loginInfo) {

        String punchcardDate = data.getString("punchcardDate");

        List<String> matchPunchCardCmds = JSONObject.parseArray(data.getJSONArray("matchPunchCardCmds").toJSONString()
                , String.class);

        List<PunchCardCmd> punchCardCmds = modelFatory.getModels(PunchCardCmd.class);
        for (PunchCardCmd punchCardCmd : punchCardCmds) {

            if (matchPunchCardCmds.contains(punchCardCmd.type())) {
                List<String> matchCmds = RegexUtils.matchParts(commandRequest, punchCardCmd.cmdReg());
                for (String matchCmd : matchCmds) {

                    System.out.println("匹配命令行：" + matchCmd);

                    // 提取命令行数据
                    ApiResponse<JSONObject> dataResult = punchCardCmd.extractData(matchCmd);
                    if (!dataResult.isSuccess()) {
                        return dataResult;
                    }

                    // 执行命令
                    ApiResponse executeResult = punchCardCmd.execute(punchcardDate, matchCmd, dataResult.getData(), loginInfo);
                    if (!executeResult.isSuccess()) {
                        return executeResult;
                    }
                }
            }
        }
        return ApiResponse.ok();
    }

    @Override
    public String resultFormat(JSONObject data) {
        return null;
    }

    @Override
    public String commandName() {
        return "打卡";
    }

    @Override
    public String commandReg() {
        return null;
    }

    @Override
    public List<String> examples() {
        return Arrays.asList("原始体重：158斤\n" +
                "目标体重：110斤\n" +
                "今日体重：138斤\n" +
                "比昨天瘦：0.1斤\n" +
                "运动：走路5000步，力量训练60分钟\n" +
                "饮食控制：轻断食16+8，戒油腻");
    }



    @Override
    public List<Integer> authUserTypes() {
        // todo 要删除
        return Arrays.asList(Member.ROLE_TYPE_NO_JOIN, Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER);
    }

}
