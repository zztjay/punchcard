package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.strategy.ModelFatory;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.strategy.command.CreateLwCampCmd;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    ModelFatory modelFatory;

    @Resource
    CampService campService;

    /**
     * 机器人的统一命令接口
     *
     * @return API response json
     */
    @GetMapping(value = "/api/robot/command")
    public ApiResponse command(@RequestParam String inputCmd) {

        // 解析请求命令
        List<Command> commandModels = modelFatory.getModels(Command.class);

        // 匹配命令

        if (StringUtils.isNotEmpty(inputCmd)) {
            StringBuilder responseText = new StringBuilder();
            for (Command commandModel : commandModels) {
                if (commandModel.isMatch(inputCmd)) {

                    // 提取命令所需数据
                    ApiResponse<JSONObject> extractResult = commandModel.extractData(inputCmd);
                    if (!extractResult.isSuccess()) {
                        return extractResult;
                    }

                    // 一开始还没有减脂营，需要开启
                    if (commandModel.getClass() == CreateLwCampCmd.class && LoginContext.getCampId() == null) {
                        return ApiResponse.error("GROUP_NO_CAMP", "本群还未开启减肥打卡统计功能");
                    }

                    // 检查命令权限
                    Integer roleType = campService.getRoleType(LoginContext.getCampId(), LoginContext.getWxId());
                    List<Integer> authTypes = commandModel.authUserTypes();
                    if (!authTypes.contains(roleType)) {
                        return ApiResponse.error("NO_PERMISSION_EXCUTE", "您没有权限执行" +
                                commandModel.commandName() + "命令！");
                    }

                    // 执行命令
                    ApiResponse executeResult = commandModel.execute(inputCmd, extractResult.getData(), LoginContext.getLoginInfo());
                    if (!executeResult.isSuccess()) {
                        return executeResult;
                    }

                    // 构建返回数据
                    responseText.append(commandModel.resultFormat(extractResult.getData(),LoginContext.getLoginInfo()));
                }
            }
            return ApiResponse.ok(responseText.toString());
        }

        return ApiResponse.error("COMMAND_NOT_FOUND", "无法识别您的输入，请检查文本格式!");
    }
}
