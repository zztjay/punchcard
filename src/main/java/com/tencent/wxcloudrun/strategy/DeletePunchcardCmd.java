package com.tencent.wxcloudrun.strategy;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.DateUtil;
import com.tencent.wxcloudrun.util.RegexUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 删除打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class DeletePunchcardCmd implements Command {
    @Resource
    PunchCardService punchCardService;

    @Override
    public ApiResponse excute(String commandRequest, LoginInfo loginInfo) {

        // 提取日期
        String date = RegexUtil.extracCommandDate(commandRequest,type());

        // 删除打卡
        punchCardService.delete(loginInfo.getCampId(), date, loginInfo.getWxId());

        return ApiResponse.ok();
    }

    @Override
    public CommandEnum type() {
        return CommandEnum.delete_punchcard;
    }
}
