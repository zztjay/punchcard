package com.tencent.wxcloudrun.strategy;


import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import org.springframework.stereotype.Component;

/**
 * 食物打卡
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class FoodConstrictCmd implements PunchCardCmd {
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
        return "饮食控制";
    }

    @Override
    public String cmdReg() {
        return null;
    }

    @Override
    public String cmdPrexReg() {
        return null;
    }

    @Override
    public String dataReg() {
        return null;
    }
}
