package com.tencent.wxcloudrun.strategy;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.strategy.command.Command;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import com.tencent.wxcloudrun.util.CmdRegexUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 命令模型工厂
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:36
 */
@Component
public class ModelFatory implements ApplicationContextAware {


    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> List<T> getModels(Class<T> tClass) {
        Map<String, T> beansMap = applicationContext.getBeansOfType(tClass);
        return new ArrayList<>(beansMap.values());
    }

    // todo
    public <T, D> D getModel(Class<T> tClass, D desireModel) {
        List<T> models = getModels(tClass);
        for (T model : models) {
            if (model.getClass().equals(desireModel.getClass())) {
                return desireModel;
            }
        }
        return null;
    }


}
