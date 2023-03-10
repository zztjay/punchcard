package com.tencent.wxcloudrun.strategy.command.member;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 新人入群帮助
 *
 * @Author：zhoutao
 * @Date：2023/3/13 16:52
 */
@Component
public class JoinGroupHelpCmd implements Command<String> {

    @Resource
    CampService campService;

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
        // 存在减脂营的情况下才提示
        if(loginInfo.getCampId() != null){
            return new StringBuilder("欢迎加入，以下是打卡帮助：\n\n" +
                    "Step1： 输入“原体重xx斤”和“目标xx斤”，进行初始体重信息设置\n\n" +
                    "Step2：请按打卡模版进行打卡，勿改变每一行的打卡标题（比如：“运动：”），打卡内容请用“，”分隔，没有请输入“无”，或删除行\n\n" +
                    "\uD83D\uDC49今日体重：138斤\n" +
                    "⛹️\u200D♀️运动：走路5000步，力量训练60分钟\n" +
                    "\uD83C\uDF5E早餐：豆浆，包子，全麦面包\n" +
                    "\uD83C\uDF5A午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                    "\uD83C\uDF75晚餐：咖喱鸡肉饭\n" +
                    "\uD83C\uDF49加餐：无").toString();
        }
        return null;
    }


    @Override
    public String commandName() {
        return "新人入群帮助";
    }

    @Override
    public String commandReg() {
        return "新人入群帮助";
    }

    @Override
    public String examples() {
        return "新人入群帮助";
    }

    @Override
    public ApiResponse<String> roleCheck(LoginInfo loginInfo) {
        return ApiResponse.ok();
    }

    public static void main(String[] args) {
        System.out.println(new JoinGroupHelpCmd().resultFormat(null,null));
    }
}
