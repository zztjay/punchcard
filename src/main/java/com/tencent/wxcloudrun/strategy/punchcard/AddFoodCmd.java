package com.tencent.wxcloudrun.strategy.punchcard;

import com.tencent.wxcloudrun.strategy.AbstractFoodCmd;

/**
 * @Author：zhoutao
 * @Date：2023/3/12 10:07
 */
public class AddFoodCmd extends AbstractFoodCmd {
    @Override
    public String type() {
        return "加餐";
    }
}
