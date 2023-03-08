package com.tencent.wxcloudrun.strategy;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.dto.LoginInfo;

/**
 * 抽象命令实现类
 *
 * @Author：zhoutao
 * @Date：2023/3/8 21:41
 */
public abstract class AbstractCommand<T,D> implements Command {
    public abstract ApiResponse<D> doExecute(String commandRequest, LoginInfo loginInfo);

    public abstract T format(D data);

    @Override
    public ApiResponse<T> execute(String commandRequest, LoginInfo loginInfo) {
        ApiResponse<D> response = doExecute(commandRequest, loginInfo);
        if(response.isSuccess()){
            T result = format(response.getData());
            return ApiResponse.ok(result);
        }  else {
            return ApiResponse.error(response.getCode(),response.getErrorMsg());
        }
    }

    public String generalResult(){
        return type().getCommand() + "执行成功！";
    }
}
