//package com.tencent.wxcloudrun.strategy;
//
//import com.tencent.wxcloudrun.config.ApiResponse;
//import com.tencent.wxcloudrun.constants.CmdRegexConstant;
//
//import com.tencent.wxcloudrun.dto.LoginInfo;
//import com.tencent.wxcloudrun.service.PunchCardService;
//import com.tencent.wxcloudrun.util.CmdRegexUtil;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 删除打卡
// * @Author：zhoutao
// * @Date：2023/3/6 19:32
// */
//@Component
//public class DeletePunchcardCmd implements Command {
//    @Resource
//    PunchCardService punchCardService;
//
//    @Override
//    public String commandName() {
//        return "删除打卡";
//    }
//
//    @Override
//    public String commandReg() {
//        return null;
//    }
//
//    @Override
//    public String prompt(String commandInput) {
//        return null;
//    }
//
//    @Override
//    public List<String> examples() {
//        return null;
//    }
//
//    @Override
//    public List<Integer> authUserTypes() {
//        return null;
//    }
//
//    @Override
//    public ApiResponse execute(String commandRequest, LoginInfo loginInfo) {
//
//        // 提取日期信息
//        ApiResponse result = CmdRegexUtil.extractDate(commandRequest, type(), CmdRegexConstant.dateRegex);
//        if(!result.isSuccess()){
//            return result;
//        }
//        String date = (String)result.getData();
//
//        // 删除打卡
//        punchCardService.delete(loginInfo.getCampId(), date, loginInfo.getWxId());
//
//        return ApiResponse.ok();
//    }
//
//    @Override
//    public CommandEnum type() {
//        return CommandEnum.delete_punchcard;
//    }
//}
