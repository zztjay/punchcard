package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;

import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.Command;
import com.tencent.wxcloudrun.strategy.CommandFatory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    CampService campService;

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

            // 检查命令权限
            Integer roleType = campService.getRoleType(LoginContext.getCampId(),LoginContext.getWxId());
            List<Integer> authTypes = commandType.getAuthUserTypes();
            if(roleType == Member.ROLE_TYPE_NO_JOIN && !authTypes.contains(roleType) ){
                  return ApiResponse.error("NO_PERMISSION_EXCUTE","您没有权限执行命令！");
            }

            // 路由对应的命令解析器
            Command command = commandFatory.getCommandModel(commandType);

            // 执行命令
            return command.excute(commandRequest, LoginContext.getLoginInfo());

        } else {
            return apiResponse;
        }
    }
}
