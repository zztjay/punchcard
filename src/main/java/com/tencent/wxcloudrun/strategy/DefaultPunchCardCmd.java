package com.tencent.wxcloudrun.strategy;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.RegexUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 打卡默认实现
 * @Author：zhoutao
 * @Date：2023/3/7 11:16
 */
@Component
public abstract class DefaultPunchCardCmd implements Command{

    @Resource
    PunchCardService punchCardService;

    @Override
    public ApiResponse excute(String commandRequest, LoginInfo loginInfo) {
        // 提取日期信息
        String date = RegexUtil.extracCommandDate(commandRequest, type());

        // 提取食物信息 todo
        String content = commandRequest;

        return punchCardService.punchcard(content, date, loginInfo.getCampId(),
                loginInfo.getWxId(), Record.PUNCHCARD_TYPE_FOOD);
    }

    @Override
    public abstract CommandEnum type() ;
}
