package com.tencent.wxcloudrun.strategy;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.RegexUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 运动打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class SportsPunchcardCmd extends DefaultPunchCardCmd {

    @Override
    public CommandEnum type() {
        return CommandEnum.sports_punchcard;
    }

    @Override
    public Integer punchCardType() {
        return Record.PUNCHCARD_TYPE_SPORTS;
    }
}
