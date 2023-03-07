package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.constants.CommonConstants;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;

import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.strategy.Command;
import com.tencent.wxcloudrun.strategy.CommandFatory;
import com.tencent.wxcloudrun.util.DateUtil;
import com.tencent.wxcloudrun.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动打卡页面控制器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class CommandController {

    @Resource
    CommandFatory commandFatory;

    /**
     * 机器人的统一命令接口
     *
     * @return API response json
     */
    @PostMapping(value = "/api/robot/command")
    public ApiResponse command(@RequestBody String commandRequest) {

        // 解析请求命令
        ApiResponse apiResponse = CommandEnum.getCommand(commandRequest);
        if (apiResponse.isSuccess()) {
            // 获取命令模型
            CommandEnum commandType = (CommandEnum) apiResponse.getData();

            // 路由对应的命令解析器
            Command command = commandFatory.getCommandModel(commandType);

            // 执行命令
            return command.excute(commandRequest, LoginContext.getLoginInfo());

        } else {
            return apiResponse;
        }
    }
}
