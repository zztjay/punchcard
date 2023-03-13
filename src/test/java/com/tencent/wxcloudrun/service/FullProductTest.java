package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.controller.CommandControllerNew;
import com.tencent.wxcloudrun.strategy.punchcard.AbstractWeightCmd;
import com.tencent.wxcloudrun.strategy.punchcard.OriginWeightCmd;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static com.tencent.wxcloudrun.constants.CmdRegexConstant.normalContentRegex;

/**
 * @Author：zhoutao
 * @Date：2023/1/31 17:03
 */
@SpringBootTest
public class FullProductTest {

    @Resource
    CommandControllerNew commandController;

    @Resource
    PunchCardService punchCardService;

    @Test
    public void test() {


        System.out.println(commandController.command("3.1日\n" + "原始体重：158斤，\n" +
                "目标体重：110斤\n" +
                "今日体重：138斤\n" +
                "比昨天瘦：0.1斤\n" +
                "运动： 走路5000步 ，力量训练60分钟 全身运动\n" +
                "早餐：豆浆，包子，全麦面包\n" +
                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                "晚餐：咖喱鸡肉饭\n" +
                "加餐：水果 @韬合"));
//
//        System.out.println(RegexUtils.getMatches(AbstractWeightCmd.heightNumRegex,"🦅3.1日\n" + "原始体重：158斤，\n" +
//                "目标体重：110斤🦅\n" +
//                "今日体重：138斤🦅\n" +
//                "比昨天瘦：0.1斤🦅\n" +
//                "运动：无\n" +
//                "早餐：豆浆，包子，全麦面包 🦅 \n" +
//                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
//                "晚餐：咖喱鸡肉饭\n" +
//                "加餐：无"));

        System.out.println(punchCardService.getRecords(3l,"zztjay","20230301"));
//        System.out.println(commandController.command("创建减脂营"));
//
//        System.out.println(commandController.command("加入减脂营"));
//
//        // 执行命令
//        for (String command : CommandEnum.food_punchcard.getExample()) {
//            System.out.println(commandController.command(command));
//        }
//
//        for (String command : CommandEnum.sports_punchcard.getExample()) {
//            System.out.println(commandController.command(command));
//        }
//
//        for (String command : CommandEnum.weight_punchcard.getExample()) {
//            System.out.println(commandController.command(command));
//        }

//        for (String command : CommandEnum.punchcard_total.getExample()) {
//            System.out.println(commandController.command(command));
//        }




    }

    public static void main(String[] args) {
        System.out.println(RegexUtils.getMatches(new OriginWeightCmd().cmdReg(),"🦅3.1日\n" + "原始：158斤，\n" +
                "目标体重：110斤🦅\n" +
                "今日体重：138斤🦅\n" +
                "比昨天瘦：0.1斤🦅\n" +
                "运动：无\n" +
                "早餐：豆浆，包子，全麦面包 🦅 \n" +
                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                "晚餐：咖喱鸡肉饭\n" +
                "加餐：无"));

        System.out.println(new OriginWeightCmd().cmdReg());
    }

}
