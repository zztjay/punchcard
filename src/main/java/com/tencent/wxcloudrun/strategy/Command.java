package com.tencent.wxcloudrun.strategy;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;

/**
 * 命令接口
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
public interface Command {

    /**
     * 执行命令
     * @param commandRequest
     * @param loginInfo
     * @return
     */
    public ApiResponse excute(String commandRequest,  LoginInfo loginInfo);


    /**
     * 命令类型
     * @return
     */
    public CommandEnum type();
}
