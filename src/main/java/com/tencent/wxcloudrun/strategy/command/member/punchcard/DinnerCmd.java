package com.tencent.wxcloudrun.strategy.command.member.punchcard;

import org.springframework.stereotype.Component;

/**
 * @Author：zhoutao
 * @Date：2023/3/12 10:07
 */

@Component
public class DinnerCmd extends AbstractFoodCmd {
    @Override
    public String type() {
        return "晚餐";
    }
}
