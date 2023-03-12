package com.tencent.wxcloudrun.strategy.punchcard;

import com.tencent.wxcloudrun.strategy.punchcard.AbstractFoodCmd;
import org.springframework.stereotype.Component;

/**
 * @Author：zhoutao
 * @Date：2023/3/12 10:07
 */
@Component
public class BreakfastCmd extends AbstractFoodCmd {
    @Override
    public String type() {
        return "早餐";
    }
}