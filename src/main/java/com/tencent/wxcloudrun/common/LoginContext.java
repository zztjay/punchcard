package com.tencent.wxcloudrun.common;

import com.tencent.wxcloudrun.dto.LoginInfo;
import lombok.Data;

/**
 * 通用的登陆上下文
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:56
 */
@Data
public class LoginContext {

    public static ThreadLocal<LoginInfo> holder = new ThreadLocal<>();

    LoginInfo loginInfo;

    public static void createLoginContext(LoginInfo loginInfo) {
        holder.set(loginInfo);
    }

    public static LoginInfo getLoginInfo() {
        return holder.get();
    }

    public static String getOpenId(){
        return holder.get().getOpenId();
    }

    public static String getWxId(){
        return holder.get().getWxId();
    }

    public static String getWxName(){
        return holder.get().getWxName();
    }

    public static String getGroupName(){
        return holder.get().getGroupName();
    }

    public static String getGroupId(){
        return holder.get().getGroupId();
    }

    public static String getWxGroupName(){
        return holder.get().getWxGroupName();
    }

    /**
     * 销毁threadlocal
     */
    public static void destoryLoginContext() {
        holder.remove();
    }



}
