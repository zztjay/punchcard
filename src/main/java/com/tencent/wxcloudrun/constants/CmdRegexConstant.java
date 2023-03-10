package com.tencent.wxcloudrun.constants;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.util.RegexUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式常量
 * @Author：zhoutao
 * @Date：2023/3/7 14:11
 */
public class CmdRegexConstant {
   public static final List<String> chTodayStrs = Arrays.asList("今天","今日");
    public static final List<String> chNearlyWeekStrs = Arrays.asList("近一周","近1周");
    public static final List<String> chNearlyMonthStrs = Arrays.asList("近一月","近1月");
    public static final List<String> chNearlyYearStrs = Arrays.asList("近一年","近1年");

    public static final String chDateRegex = "(近一周|近一月|近1周|近1月|今天|今日)";

    public static final String numRegex = "([0-9]|[1-9][0-9]*)"; // 验证零和非零开头的数字

    public static final String dateRegex = "(0+[1-9]|1[0-2])((0+[1-9])|((1|2)[0-9])|30|31)"
            + "|(([1-9]|1[0-2])月(([1-9])|((1|2)[0-9])|30|31)日)"
            + "|(([1-9]|1[0-2])\\.(([1-9])|((1|2)[0-9])|30|31))"
            + "| (今日|今天)"; // 日期正则表达式,0301,3.1,3月1日

    public static final String heightNumRegex = numRegex + "(\\.\\d{1,2})?"; // 验证体重的数字

    public static final String incOrDecrRegex = "[+,-]?"; // 增加或者减少

    public static final String splitRegex =  "[,，]?"; // 用于分隔字段的字符

    public static final String nodoRegex = "(没|无|没有)";  // 没有做的正则表达式
    public static final String mutipleSpaceRegex = "\\s*"; // 0个或多个空格分隔符

    public static final String onlyNormalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s+))" + ")"; // 验证内容的字符串

    public static final String normalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s+))" + ")*"; // 验证内容的字符串

    public static final String inputRegex =  "(\\s*[:：])"; // 开始输入的字符，0个或1个

    public static final String weightUnit = "(公斤|斤)"; // 体重单位


    public static String originWeightRegex(){
        return new StringBuilder().append("原始体重").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex)
                .append(CmdRegexConstant.heightNumRegex).append(weightUnit).toString();
    }

    public static String goalWeightRegex(){
        return new StringBuilder().append("目标体重").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex)
                .append(CmdRegexConstant.heightNumRegex).append(weightUnit).toString();
    }

    public static String todayWeightRegex(){
        return new StringBuilder().append("今日体重").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex)
                .append(CmdRegexConstant.heightNumRegex).append(weightUnit).toString();
    }

    public static String weightThanYestodayRegex(){
        return new StringBuilder().append("比昨天瘦").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex).append(incOrDecrRegex)
                .append(CmdRegexConstant.heightNumRegex).append(weightUnit).toString();
    }


    public static String foodConstrictRegex(){
        return RegexUtils.or(doFoodConstrictRegex(),notDoFoodConstrictRegex());
    }


    public static String doFoodConstrictRegex(){
        return new StringBuilder().append("饮食控制").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex).append(CmdRegexConstant.normalContentRegex).toString();
    }

    public static String notDoFoodConstrictRegex(){
        return new StringBuilder().append("饮食控制").append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex)
                .append(CmdRegexConstant.nodoRegex).toString();
    }
//
//    public static String punchCardRegex(){
//        return originWeightRegex()+"[\\s]" + goalWeightRegex() +
//                "[\\s]*" + todayWeightRegex()
//                +"[\\s]" + weightThanYestodayRegex() +"[\\s]" + doSportsRegex() + "[\\s]" + foodConstrictRegex();
//
//    }

    public static void main(String[] args) {
        String command = "原始体重：158斤\n" +
                "目标体重：110斤\n" +
                "今日体重：138斤\n" +
                "比昨天瘦：0.1斤\n" +
                "运动：走路5000步，力量训练60分钟\n" +
                "饮食控制：轻断食16+8，戒油腻";


    }
}
