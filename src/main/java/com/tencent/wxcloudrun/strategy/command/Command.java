package com.tencent.wxcloudrun.strategy.command;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;

import java.util.List;


/**
 * 命令接口定义
 * 1. 检查大概率是什么命令，通过match方法，比如是否有"打卡字样"等来匹配
 * 2. 提取命令需要的数据，比如日期，针对打卡，用正则表达式提取出合法的打卡命令，并逐个进行检查，
 * 判断标准是：是否可以提取需要的数据，让后续的命令可以执行
 * 3. 执行命令
 * 4. 构建数据
 */
public interface Command<T> {

    boolean isMatch(String inputCmd);

    ApiResponse<JSONObject> extractData(String inputCmd);

    ApiResponse execute(String commandRequest, JSONObject cmdData,  LoginInfo loginInfo); // 执行命令

    T resultFormat(JSONObject data);

    String commandName(); // 命令名称

    String commandReg(); // 命令正则匹配

    List<String> examples(); // 命令示例

    List<Integer> authUserTypes(); // 授权用户类型


}
