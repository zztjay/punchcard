//package com.tencent.wxcloudrun.controller;
//
//import com.tencent.wxcloudrun.common.LoginContext;
//import com.tencent.wxcloudrun.config.ApiResponse;
//
//import com.tencent.wxcloudrun.service.CampService;
//import com.tencent.wxcloudrun.strategy.command.Command;
//import com.tencent.wxcloudrun.strategy.ModelFatory;
//import com.tencent.wxcloudrun.strategy.command.CreateCampCmd;
//import com.tencent.wxcloudrun.util.CmdRegexUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 活动打卡页面控制器
// *
// * @Author：zhoutao
// * @Date：2023/1/17 16:37
// */
//@RestController
//@Slf4j
//public class CommandController {
//    private static final double MINIMAL_SIMILARY = 0.5;
//
//    @Resource
//    ModelFatory modelFatory;
//
//    @Resource
//    CampService campService;
//
//
//
//    /**
//     * 机器人的统一命令接口
//     *
//     * @return API response json
//     */
//    @GetMapping(value = "/api/robot/command")
//    public ApiResponse command(@RequestParam String inputCmd) {
//
//        // 解析请求命令
//        List<Command> commandModels = modelFatory.getModels(Command.class);
//
//        // 匹配命令
//        if (StringUtils.isNotEmpty(inputCmd)) {
//            for (Command commandModel : commandModels) {
//                if (inputCmd.matches(commandModel.commandReg())) {
//                    // 一开始还没有减脂营，需要开启
//                    if (commandModel.getClass() == CreateCampCmd.class && LoginContext.getCampId() == null) {
//                        return ApiResponse.error("GROUP_NO_CAMP", "本群还未创建减脂营，请管理员创建");
//                    }
//
//                    // 检查命令权限
//                    Integer roleType = campService.getRoleType(LoginContext.getCampId(), LoginContext.getWxId());
//                    List<Integer> authTypes = commandModel.authUserTypes();
//                    if (!authTypes.contains(roleType)) {
//                        return ApiResponse.error("NO_PERMISSION_EXCUTE", "您没有权限执行" +
//                                commandModel.commandName() + "命令！");
//                    }
//
//                    // 执行命令
//                    return commandModel.execute(inputCmd, LoginContext.getLoginInfo());
//                }
//            }
//
//            // 没有匹配命令行，根据字符串相似度，寻找用户意图输入的命令，给予提示
//            Command userDesireCommand = null;
//            double maxSimilarity = MINIMAL_SIMILARY;
//            for (Command commandModel : commandModels) {
//                List<String> examples = commandModel.examples();
//                for (String example : examples) {
//                    double commandSimilarity = CmdRegexUtil.getSimilarity(example, inputCmd);
//                    if (commandSimilarity > maxSimilarity) {
//                        userDesireCommand = commandModel;
//                        maxSimilarity = commandSimilarity;
//                        continue;
//                    }
//                }
//            }
//            if (userDesireCommand != null) {
//                return ApiResponse.error("COMMAND_FORMAT_ERROR",userDesireCommand.prompt(inputCmd));
//            }
//        }
//
//        return ApiResponse.error("COMMAND_NOT_FOUND", "输入格式有误，请检查!");
//    }
//}
