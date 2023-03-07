package com.tencent.wxcloudrun.strategy;

import com.tencent.wxcloudrun.constants.CommandEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 命令模型工厂
 * @Author：zhoutao
 * @Date：2023/3/6 19:36
 */
@Component
public class CommandFatory implements ApplicationContextAware {
    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Command getCommandModel(CommandEnum type){
        Map<String, Command> beansMap = applicationContext.getBeansOfType(Command.class);
        for (Command command : beansMap.values()) {
                if(command.type() == type){
                    return command;
                }
        }
        return null;
    }
}
