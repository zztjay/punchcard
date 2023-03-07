package com.tencent.wxcloudrun.strategy;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import org.springframework.stereotype.Component;

/**
 * 体重打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class WeightPunchcardCmd extends DeletePunchcardCmd {
    @Override
    public CommandEnum type() {
        return CommandEnum.weight_punchcard;
    }
}
