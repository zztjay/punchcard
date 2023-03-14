package com.tencent.wxcloudrun.strategy.command.creater;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;

import com.tencent.wxcloudrun.dto.CampQuery;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 开启打卡统计功能
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
        return RegexUtils.hasMatchParts(inputCmd, commandReg());
    }

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        return ApiResponse.ok(new JSONObject());
    }

    @Override
    public ApiResponse execute(String commandRequest, JSONObject cmdData, LoginInfo loginInfo) {
        Camp camp = campService.getCampByGid(loginInfo.getGroupId());
        if(null == camp){
            camp = new Camp();
        }
        camp.setCampName(loginInfo.getGroupName());
        camp.setGroupId(loginInfo.getGroupId());
        camp.setCreaterWxId(loginInfo.getWxId());
        camp.setCreaterName(loginInfo.getWxGroupName());
        camp.setType(Camp.PUNCHCARD_TYPE_LOSE_WEIGHT);
        camp.setDeleted(Camp.OPEN);
        return campService.save(camp);
    }

    @Override
    public String resultFormat(JSONObject data, LoginInfo loginInfo) {

        return new StringBuilder("“群打卡统计功能”开启成功，以下是打卡帮助：\n\n" +
                "Step1：输入输入“原体重xx斤”和“目标xx斤”，并@打卡小助手，进行初始体重信息设置\n\n" +
                "Step2：按打卡模版进行打卡，并@打卡小助手，请勿改变每一行的打卡标题（比如：“运动：”），打卡内容请用“，”分隔\n\n" +
                "\uD83D\uDC49今日体重：138斤\n" +
                "⛹️\u200D♀️运动：走路5000步，力量训练60分钟\n" +
                "\uD83C\uDF5E早餐：豆浆，包子，全麦面包\n" +
                "\uD83C\uDF5A午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                "\uD83C\uDF75晚餐：咖喱鸡肉饭\n" +
                "\uD83C\uDF49加餐：无").toString();
    }

    public static void main(String[] args) {
        System.out.println(new CreateLwCampCmd().resultFormat(null, null));
    }

    @Override
    public String commandName() {
        return "开启打卡统计";
    }

    @Override
    public String commandReg() {
        return "[\\s]*开启打卡统计[\\s]*";
    }


    @Override
    public String examples() {
        return "开启打卡统计";
    }

    @Override
    public ApiResponse<String> roleCheck(LoginInfo loginInfo) {
        // 检查群里是否重复创建
        Camp camp = campService.getOpenCampByGid(loginInfo.getGroupId());
        if (camp != null) {
            return ApiResponse.error("CAMP_REPEATED_CREAT", "打卡统计功能已开启");
        }
        return ApiResponse.ok();
    }

}
