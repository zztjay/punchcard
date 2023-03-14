package com.tencent.wxcloudrun.constants;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */
public enum PunchCardType {
//
//    exercise("运动", CmdRegexConstant.sportsRegex(),"11","11");
//    ,
//    todayWeight("体重打卡", CmdRegexConstant.todayWeightRegex(),
//            Arrays.asList("体重打卡：64公斤", "体重打卡：0301，64公斤", "体重打卡：121斤", "体重打卡：0301，121斤"),
//            Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),
//    food_punchcard("饮食打卡",  RegexUtils.or(CmdRegexConstant.doSportsRegex(),CmdRegexConstant.notDoSportsRegex()),
//            Arrays.asList("食物打卡：牛奶1ml，鸡蛋1个", "食物打卡：0301，牛奶1ml，鸡蛋1个"),
//            Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),
    ;
//    delete_punchcard("删除打卡", "^删除" + CmdRegexConstant.dateRegex
//            + "的打卡", Arrays.asList("删除0301的打卡"),
//            Arrays.asList(Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER)),
//
//    punchcard_total("我的打卡统计", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的打卡统计"
//            , Arrays.asList("我今天的打卡统计", "我近一周的打卡统计", "我近一月的打卡统计")
//            , Arrays.asList(Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER)),
//
//    weight_punchcard_record("查看我的体重记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
//            + "体重记录", Arrays.asList("我近一周的体重记录", "我近一周的体重记录", "我近一月的体重记录")
//            , Arrays.asList(Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER)),
//
//    sports_punchcard_record("查看我的运动记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
//            + "运动记录", Arrays.asList("我近一周的运动记录", "我近一周的运动记录", "我近一月的运动记录")
//            , Arrays.asList(Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER)),

//    food_punchcard_record("查看我的饮食记录", "\\s?" + "我" + "(近一周|近一月|近1周|近1月|今天)" + "的"
//            + "饮食记录", Arrays.asList("我近一周的饮食记录", "我近一周的饮食记录", "我近一月的饮食记录")
//            ,Arrays.asList(Member.ROLE_TYPE_NORMAL,Member.ROLE_TYPE_CREATER)),


    /******************团队相关命令************************/
//    create_camp("开启打卡统计功能", "开启打卡统计功能", Arrays.asList("开启打卡统计功能")
//            , Arrays.asList(Member.ROLE_TYPE_NO_JOIN, Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER)),
//
//    close_camp("关闭减脂营", "关闭减脂营", Arrays.asList("关闭减脂营"),
//            Arrays.asList(Member.ROLE_TYPE_CREATER)),
//    join_camp("参加减脂营", "参加减脂营", Arrays.asList("参加减脂营"),
//            Arrays.asList(Member.ROLE_TYPE_NO_JOIN, Member.ROLE_TYPE_NORMAL, Member.ROLE_TYPE_CREATER));
    private String desc; // 命令名称
    private String cmdReg; // 命令正则

    private String cmdPrexReg; // 数据正则
    private Class commandType; // 命令类型

    PunchCardType(String desc, String cmdReg, String cmdPrexReg, Class commandType) {
        this.desc = desc;
        this.cmdReg = cmdReg;
        this.cmdPrexReg = cmdPrexReg;
        this.commandType = commandType;
    }

    public String getDesc() {
        return desc;
    }

    public String getCmdReg() {
        return cmdReg;
    }

    public String getCmdPrexReg() {
        return cmdPrexReg;
    }

    public Class getCommandType() {
        return commandType;
    }

}
