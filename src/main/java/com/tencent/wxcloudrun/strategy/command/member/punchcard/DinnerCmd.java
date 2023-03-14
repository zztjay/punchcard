package com.tencent.wxcloudrun.strategy.command.member.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import org.springframework.stereotype.Component;

/**
 * @Author：zhoutao
 * @Date：2023/3/12 10:07
 */

@Component
public class DinnerCmd extends AbstractFoodCmd {
    @Override
    public String type() {
        return "晚餐";
    }

    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        throw new IllegalArgumentException();
    }
}
