package com.tencent.wxcloudrun.constants;

import com.github.jsonzou.jmockdata.util.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.util.DateUtil;
import com.tencent.wxcloudrun.util.RegexUtil;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */
public enum CommandEnum {
    sports_punchcard("运动打卡", "^运动打卡[:：]" + "\\s?" + "((0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)[,，]?){0,1}"
            + "([\\u4E00-\\u9FA5A-Za-z0-9]+[,，]?)+", Arrays.asList("运动打卡：跳绳3小时，骑行1小时", "运动打卡：0301，跳绳3小时，骑行1小时"),
            "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),
    weight_punchcard("体重打卡", "^体重打卡[:：]" + "\\s?" + "((0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)[,，]?){0,1}"
            + "(\\d{1,4})" + "(公斤|斤)$", Arrays.asList("体重打卡：64公斤", "体重打卡：0301，64公斤", "体重打卡：121斤", "体重打卡：0301，121斤"),
            "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),
    food_punchcard("食物打卡", "^食物打卡[:：]" + "\\s?" + "((0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)){0,1}"
            + "([\\u4E00-\\u9FA5A-Za-z0-9]+[,，]?)+", Arrays.asList("食物打卡：牛奶1ml，鸡蛋1个", "食物打卡：0301，牛奶1ml，鸡蛋1个"),
            "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),
    delete_punchcard("删除打卡", "^删除" + "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)"
            + "的打卡$", Arrays.asList("删除0301的打卡"), "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)",
            Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),

    punchcard_total("我的打卡统计", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的打卡统计"
            , Arrays.asList("我今天的打卡统计", "我近一周的打卡统计", "我近一月的打卡统计"),
            "(近一周|近一月|近1周|近1月|今天|近一年|近1年)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),

    weight_punchcard_record("查看我的体重记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
            + "体重记录", Arrays.asList("我近一周的体重记录", "我近一周的体重记录", "我近一月的体重记录"),
            "(近一周|近一月|近1周|近1月|今天|近一年|近1年)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),

    food_punchcard_record("查看我的运动记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
            + "运动记录", Arrays.asList("我近一周的运动记录", "我近一周的运动记录", "我近一月的运动记录"),
            "(近一周|近一月|近1周|近1月|今天|近一年|近1年)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),

    sports_punchcard_record("查看我的饮食记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
            + "饮食记录", Arrays.asList("我近一周的饮食记录", "我近一周的饮食记录", "我近一月的饮食记录"),
            "(近一周|近一月|近1周|近1月|今天|近一年|近1年)",Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),


    /******************团队相关命令************************/
    create_camp("创建减脂营", "创建减脂营",Arrays.asList("创建减脂营") ,
            "",Arrays.asList(Member.ROLE_TYPE_NO_JOIN,Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),

    join_camp("参加减脂营", "参加减脂营",Arrays.asList("参加减脂营") ,
            "",Arrays.asList(Member.ROLE_TYPE_NO_JOIN,Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER));
    private String command;
    private String reg;
    private List<String> example;
    private String dateReg;
    private List<Integer> authUserTypes;

    CommandEnum(String command, String reg, List<String> example, String dateReg, List<Integer> authUserTypes) {
        this.command = command;
        this.reg = reg;
        this.example = example;
        this.dateReg = dateReg;
        this.authUserTypes = authUserTypes;
    }

    /**
     * 获取命令行
     * @param commandInput
     * @return
     */
    public static ApiResponse getCommand(String commandInput){
        // 匹配命令
        if(StringUtils.isNotEmpty(commandInput)){
            for (CommandEnum command : CommandEnum.values()) {
                if(commandInput.matches(command.getReg())){
                    return ApiResponse.ok(command);
                }
            }
        }
        // 匹配失败
        return ApiResponse.error("COMMAND_NOT_FOUND","输入有误，没有匹配到命令行!");
    }



    public String getCommand() {
        return command;
    }

    public String getReg() {
        return reg;
    }

    public List<String> getExample() {
        return example;
    }

    public String getDateReg() {
        return dateReg;
    }

    public List<Integer> getAuthUserTypes() {
        return authUserTypes;
    }
}
