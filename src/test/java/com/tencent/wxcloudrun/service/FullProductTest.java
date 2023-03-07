package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.adaptor.MessageSendAdaptor;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.constants.MessageTemplateConstant;
import com.tencent.wxcloudrun.constants.MsgArgumentEnum;
import com.tencent.wxcloudrun.controller.CommandController;
import com.tencent.wxcloudrun.dao.UsersMapper;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.strategy.Command;
import com.tencent.wxcloudrun.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Test
    public void test() {

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

        for (String command : CommandEnum.punchcard_total.getExample()) {
            System.out.println(commandController.command(command));
        }



    }

}
