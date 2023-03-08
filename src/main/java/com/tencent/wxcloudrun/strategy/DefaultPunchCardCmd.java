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
public abstract class DefaultPunchCardCmd extends AbstractCommand<String,Long>{

    @Resource
    PunchCardService punchCardService;

    @Override
    public ApiResponse<Long> doExecute(String commandRequest, LoginInfo loginInfo) {

        // 提取日期信息
        ApiResponse result = RegexUtil.extractDate(commandRequest, type());
        if(!result.isSuccess()){
            return result;
        }
        String date = (String)result.getData();

        // 提取食物信息 todo
        String content = commandRequest;

        return punchCardService.punchcard(content, date, loginInfo.getCampId(),
                loginInfo.getWxId(), punchCardType());
    }

    @Override
    public String format(Long data) {
        return generalResult();
    }

    @Override
    public abstract CommandEnum type() ;

    public abstract Integer punchCardType();
}
