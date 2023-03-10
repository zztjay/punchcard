//package com.tencent.wxcloudrun.strategy.command;
//
//import com.alibaba.fastjson.JSONObject;
//import com.tencent.wxcloudrun.config.ApiResponse;
//import com.tencent.wxcloudrun.dto.LoginInfo;
//
///**
// * 抽象命令实现类
// *
// * @Author：zhoutao
// * @Date：2023/3/8 21:41
// */
//public abstract class AbstractCommand<T> implements Command {
//
//    public abstract ApiResponse<JSONObject> doExecute(String commandRequest, LoginInfo loginInfo);
//
//    public abstract T resultFormat(JSONObject data);
//
//    @Override
//    public ApiResponse<T> execute(String commandRequest, LoginInfo loginInfo) {
//
//        // 命令执行
//        ApiResponse<JSONObject> response = doExecute(commandRequest, loginInfo);
//        if(response.isSuccess()){
//            // 返回数据格式化
//            T result = resultFormat(response.getData());
//            return ApiResponse.ok(result);
//        }  else {
//            return ApiResponse.error(response.getCode(),response.getErrorMsg());
//        }
//    }
//
//    public String generalResult(){
//        return commandName() + "成功！";
//    }
//}
