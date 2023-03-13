package com.tencent.wxcloudrun.strategy.command;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 创建减脂营
 *
 * @Author：zhoutao
 * @Date：2023/3/7 12:19
 */
@Component
public class CreateLwCampCmd implements Command<String> {
    @Resource
    CampService campService;

    @Override
    public boolean isMatch(String inputCmd) {
        if (RegexUtils.hasMatchParts(inputCmd, commandReg())) {
            return true;
        }
        return false;
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        return ApiResponse.ok(new JSONObject());
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        Camp camp = new Camp();
        camp.setCampName(loginInfo.getGroupName());
        camp.setGroupId(loginInfo.getGroupId());
        camp.setCreaterWxId(loginInfo.getWxId());
        camp.setCreaterName(loginInfo.getWxGroupName());
        camp.setType(Camp.PUNCHCARD_TYPE_LOSE_WEIGHT);
        return campService.save(camp);
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {

        return new StringBuilder("“群减肥打卡统计功能”开启成功，以下是打卡帮助：\n\n" +
                "Step1：输入“我的原始体重为xx斤”和“我的目标体重为xx斤”，并@打卡助手，进行原始和目标体重信息设置\n\n" +
                "Step2：按打卡模版进行打卡，并@打卡助手，请勿改变每一行的打卡标题（比如：“运动：”），打卡内容请用“，”分隔\n\n" +
                "今日体重：138斤\n" +
                "运动：走路5000步，力量训练60分钟\n" +
                "早餐：豆浆，包子，全麦面包\n" +
                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                "晚餐：咖喱鸡肉饭\n" +
                "加餐：水果").toString();
    }

    public static void main(String[] args) {
        System.out.println(new CreateLwCampCmd().resultFormat(null,null));
    }

    @Override
    public String commandName() {
        return "开启减肥打卡统计";
    }

    @Override
    public String commandReg() {
        return "[\\s]*开启减肥打卡统计[\\s]*";
    }


    @Override
    public List<String> examples() {
        return Arrays.asList("开启减肥打卡统计");
    }

    @Override
    public List<Integer> authUserTypes() {
        return Arrays.asList(Member.ROLE_TYPE_NO_JOIN, Member.ROLE_TYPE_CREATER);
    }
}
