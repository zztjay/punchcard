package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;

import com.tencent.wxcloudrun.model.Camp;
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
    @GetMapping(value = "/api/robot/command")
    public ApiResponse command(@RequestParam String commandRequest) {

        // 解析请求命令
        ApiResponse commandResult = CommandEnum.getCommand(commandRequest);
        if (commandResult.isSuccess()) {

            // 获取命令模型
            CommandEnum commandType = (CommandEnum) commandResult.getData();

            // 检查命令权限
            Integer roleType = LoginContext.getRoleType();
            List<Integer> authTypes = commandType.getAuthUserTypes();
            if(!authTypes.contains(roleType) ){
                  return ApiResponse.error("NO_PERMISSION_EXCUTE","您没有权限执行"+
                          commandType.getCommand() +  "命令！");
            }

            // 减脂营检查
            if(commandType != CommandEnum.create_camp && LoginContext.getCampId()==null){
                return ApiResponse.error("GROUP_NO_CAMP","本群还未创建减脂营，请管理员创建");
            }

            // 路由对应的命令解析器
            Command command = commandFatory.getCommandModel(commandType);

            // 执行命令
            ApiResponse result =  command.execute(commandRequest, LoginContext.getLoginInfo());

            return result;

        } else {
            return commandResult;
        }
    }
}
