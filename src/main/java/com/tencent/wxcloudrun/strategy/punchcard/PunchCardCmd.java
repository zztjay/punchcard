package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;

import java.util.List;

/**
 * 打卡命令接口定义
 * 1. 检查大概率是什么命令，通过match方法，比如是否有"打卡字样"等来匹配
 * 2. 提取命令需要的数据，若提取不成功，提示用户错误
 * 3. 执行命令
 */
public interface PunchCardCmd {


    ApiResponse<JSONObject> extractData(String inputCmd);

    ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo); // 执行命令

    String type(); // 命令名称

    String cmdReg(); // 命令正则

    String cmdPrexReg(); // 前缀正则

    String dataReg(); // 数据正则

}
