package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.controller.CommandControllerNew;
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

    @Test
    public void test() {


        System.out.println(commandController.command("3.1日运动打卡原始体重：158斤，\n" +
                "目标体重：110斤\n" +
                "今日体重：138斤\n" +
                "比昨天瘦：0.1斤\n" +
                "原始体重运动  ：   走路5000步 ，力量训练60分钟 全身运动\n" +
                "饮食控制：轻断食16+8，戒油腻\n" +
                "早餐：豆浆，包子，全麦面包\n" +
                "午餐：西红柿炒鸡蛋，炒牛肉，米饭\n" +
                "晚餐：咖喱鸡肉饭\n" +
                "加餐：水果"));

        System.out.println("运动：   走路5000步 ，力量训练60分钟 全身运动".matches(normalContentRegex));

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
        String regex = "((\\s*[,，])|(\\s+))";
        System.out.println(" ，".matches(regex));
    }
}
