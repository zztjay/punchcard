package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.controller.CommandController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author：zhoutao
 * @Date：2023/1/31 17:03
 */
@SpringBootTest
public class FullProductTest {

    @Resource
    CommandController commandController;

    @Resource
    PunchCardService punchCardService;

    @Test
    public void test() {


//        System.out.println(commandController.command("3.7日打卡\n" +
//                "\uD83D\uDC49今日体重：138斤\n" +
//                "⛹️\u200D♀️运动：走路5000步，力量训练60分钟\n" +
//                "\uD83C\uDF5E早餐：豆浆，包子，全麦面包\n" +
//                "\uD83C\uDF5A午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
//                "\uD83C\uDF75晚餐：咖喱鸡肉饭\n" +
//                "\uD83C\uDF49加餐：无  @打卡小助手"));
//
//        System.out.println(commandController.command("新人入群帮助"));
//
//        System.out.println(commandController.command("开启打卡统计功能"));
//
//        System.out.println(commandController.command("我的原始体重为150斤"));
//        System.out.println(commandController.command("我的目标体重为110斤"));
//
//        System.out.println(commandController.command("原始体重：158斤，\n" +
//                "目标体重：110斤\n" +
//                "今日体重：138斤\n" +
//                "比昨天瘦：0.1斤\n" +
//                "运动： 走路5000步 ，力量训练60分钟 全身运动\n" +
//                "早餐：豆浆，包子，全麦面包\n" +
//                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
//                "晚餐：咖喱鸡肉饭\n" +
//                "加餐：水果 @韬合"));
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

//        System.out.println(commandController.command("开启打卡统计"));
        System.out.println(commandController.command("关闭打卡统计"));


//        System.out.println(punchCardService.getRecords(3l,"zztjay","20230301"));
//        System.out.println(commandController.command("开启打卡统计功能"));
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
    }

}
