package com.tencent.wxcloudrun.aspect;

import com.alibaba.fastjson.JSON;
import com.github.jsonzou.jmockdata.util.StringUtils;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommonConstants;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CampService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yaml.snakeyaml.util.UriEncoder;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author：zhoutao
 * @Date：2023/2/18 21:54
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Resource
    UserService userService;

    @Resource
    CampService campService;
    /**
     * 定义一个切点，后续通知方法将会使用该节点来进行获取
     * 将Controller层中的所有方法作为切面与业务逻辑交互点
     */
    @Pointcut("execution(public * com.tencent.wxcloudrun.controller.*.*(..))")
    public void controllerPointcut() {

    }


    /**
     * 环绕通知
     * 业务内容前面执行一些信息、业务内容后面再执行一些信息
     *
     * @param proceedingJoinPoint
     * @return 返回请求方法返回的结果
     */
    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 设置登陆态
//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setGroupName(UriEncoder.decode(request.getHeader(CommonConstants.GROUP_NAME)));
//        loginInfo.setGroupId(request.getHeader(CommonConstants.GROUP_ID));
//        loginInfo.setWxId(request.getHeader(CommonConstants.WX_ID));
//        loginInfo.setWxName(UriEncoder.decode(request.getHeader(CommonConstants.USER_WX_NAME)));
//        loginInfo.setWxGroupName(UriEncoder.decode(request.getHeader(CommonConstants.USER_WX_GROUP_NAME)));

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setGroupName("周末减脂小分队");
        loginInfo.setWxId("zztjay");
        loginInfo.setWxName("韬（微信名称）");
        loginInfo.setWxGroupName("韬合（微信群名称）");
        loginInfo.setGroupId("周末减脂小分队id");

        // 设置减脂营信息
        Camp camp = campService.getCampByGid(loginInfo.getGroupId());
        if(null != camp) {
            loginInfo.setCampId(camp.getId());

            //  静默用户加入减脂营
            ApiResponse apiResponse = campService.isUserJoinCamp(loginInfo.getGroupId(),loginInfo.getWxId());
            if(apiResponse.getCode().equals("USER_NOT_JOIN_CAMP") ){
                campService.joinCamp(loginInfo.getCampId(),loginInfo.getWxName(),loginInfo.getWxId());
            }
        }

        //  静默用户注册
        if(!userService.isUserRegister(loginInfo.getWxId())){
            User user = new User();
            user.setMemberName(loginInfo.getWxGroupName());
            user.setMemberWxNick(loginInfo.getWxName());
            user.setMemberWxId(loginInfo.getWxId());
            userService.save(user);
        }

        LoginContext.createLoginContext(loginInfo);

        // 获取方法签名
        Signature signature = proceedingJoinPoint.getSignature();
        String name = signature.getName();
        Object[] objects = proceedingJoinPoint.getArgs();

        // 打印请求信息
        StringBuilder logInfo = new StringBuilder("-----Aspect 开始-----").append("\n");
        log.warn("登陆信息:" + loginInfo );
        logInfo.append("类名方法：").append(signature.getDeclaringTypeName()).append(",").append(name).append("\n");
        logInfo.append("请求参数：").append(JSON.toJSONString(objects)).append("\n");
        try {
            // 获取返回结果
            Object result = proceedingJoinPoint.proceed();
            logInfo.append("返回结果:").append(JSONObject.toJSONString(result)).append("\n");
            return result;
        } catch (Throwable e) {
            log.error("方法调用异常", e);
            return ApiResponse.error("EXCEPTION", "系统异常");
        } finally {
            LoginContext.destoryLoginContext();
            logInfo.append("-----Aspect 结束 耗时：").append(System.currentTimeMillis() - startTime).append(" ms-----");
            log.info(logInfo.toString());
        }
    }
}
