//package com.tencent.wxcloudrun.strategy.command;
//
//import com.tencent.wxcloudrun.constants.*;
//import com.alibaba.fastjson.JSONObject;
//import com.tencent.wxcloudrun.config.ApiResponse;
//import com.tencent.wxcloudrun.constants.CmdRegexConstant;
//import com.tencent.wxcloudrun.dto.LoginInfo;
//import com.tencent.wxcloudrun.model.Member;
//import com.tencent.wxcloudrun.strategy.ModelFatory;
//import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
//import com.tencent.wxcloudrun.util.CmdRegexUtil;
//import com.tencent.wxcloudrun.util.RegexUtils;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.*;
//
//
//
//*
// * 打卡命令模型
// *
// * @Author：zhoutao
// * @Date：2023/3/9 09:16
//
//
//@Component
//public class PunchCardCmdModel extends AbstractCommand<String> {
//
//    public static final String cmdPrefixRegex = "[\\s\\S]*[今日|今天|" + CmdRegexConstant.dateRegex + "]打卡\\s*";
//
//    @Resource
//    ModelFatory modelFatory;
//
//    Map<String, String> checkMap = new HashMap<String, String>(){{
//        put(CmdRegexConstant.originWeightRegex(),"原始体重：158斤");
//        put(CmdRegexConstant.goalWeightRegex(),"目标体重：110斤");
//        put(CmdRegexConstant.todayWeightRegex(),"今日体重：138斤");
//        put(CmdRegexConstant.weightThanYestodayRegex(),"比昨天瘦：0.1斤");
//        put(CmdRegexConstant.sportsRegex(),"运动：走路5000步，力量训练60分钟");
//        put(CmdRegexConstant.foodConstrictRegex(),"饮食控制：轻断食16+8，戒油腻");
//    }};
//
//    @Override
//    public ApiResponse<JSONObject> doExecute(String commandRequest, LoginInfo loginInfo) {
//
//        String date = CmdRegexUtil.getSimilarity()
//
//        String[] commands = commandRequest.split("\\n");
//
//        // 提取所有的命令行，并执行
//        for (int i = 0; i < commands.length; i++) {
//            String command = commands[i];
//
//            // 命中命令行前缀，忽略
//            if(command.matches(cmdPrefixRegex)){
//                continue;
//            }
//
//            // 匹配命令行
//            List<PunchCardCmd> punchCardCmds = modelFatory.getModels(PunchCardCmd.class);
//            for (PunchCardCmd punchCardCmd : punchCardCmds) {
//                if (command.matches(punchCardCmd.cmdReg())) {
//
//                   ApiResponse apiResponse = punchCardCmd.execute();
//                   if(apiResponse.isSuccess()){
//                       break;
//                   } else {
//                       return apiResponse;
//                   }
//                }
//            }
//        }
//        return ApiResponse.ok();
//    }
//
//    @Override
//    public String resultFormat(JSONObject data) {
//        return null;
//    }
//
//    @Override
//    public String commandName() {
//        return "打卡";
//    }
//
//    @Override
//    public String commandReg() {
//        return CmdRegexConstant.punchCardRegex();
//    }
//
//    @Override
//    public String prompt(String commandInput) {
//        List<String> wrongCommands = new ArrayList<>();
//        String[] commands = commandInput.split("\\n");
//
//        // 检查不匹配的行
//        for (int i = 0; i < commands.length; i++) {
//            String currentCommand = commands[i];
//            boolean notMatch = true;
//            for (String regex : checkMap.keySet()) {
//                if(currentCommand.matches(regex)){
//                    notMatch = false;
//                    break;
//                }
//            }
//            // 未匹配，缺存在相似度很高的行
//            if(notMatch ){
//                wrongCommands.add(currentCommand);
//            }
//        }
//
//        // 构建输入提示文案
//        if(CollectionUtils.isNotEmpty(wrongCommands)){
//            StringBuilder prompt = new StringBuilder();
//            prompt.append("\"");
//            prompt.append(StringUtils.join(wrongCommands,"\n"));
//            prompt.append("\"");
//            prompt.append("\n");
//            prompt.append("以上输入格式有误，请检查修改！");
//            return prompt.toString();
//        }
//
//        return null;
//
//    }
//
//    @Override
//    public List<String> examples() {
//        return Arrays.asList("原始体重：158斤\n" +
//                "目标体重：110斤\n" +
//                "今日体重：138斤\n" +
//                "比昨天瘦：0.1斤\n" +
//                "运动：走路5000步，力量训练60分钟\n" +
//                "饮食控制：轻断食16+8，戒油腻");
//    }
//
//
//
//
//    @Override
//    public List<Integer> authUserTypes() {
//        return Arrays.asList(Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER);
//    }
//
//}
