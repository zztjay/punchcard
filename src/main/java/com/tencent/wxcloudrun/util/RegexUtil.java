package com.tencent.wxcloudrun.util;

import com.tencent.wxcloudrun.constants.CommandEnum;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @Author：zhoutao
 * @Date：2023/3/7 09:53
 */
public class RegexUtil {

    /**
     * 按正则提取字符串
     */
    public static List<String> extract(String source, String regex){
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 提取日期字符串
     * @param commandInput
     * @param command
     * @return YYYYMMDD
     */
    public static String extracCommandDate(String commandInput, CommandEnum command){
        List<String> substrings =  RegexUtil.extract(commandInput, command.getDateReg());
        if(!CollectionUtils.isEmpty(substrings)){
            String year = DateUtil.getYear();
            String monthDay = substrings.get(0);
            return  year + monthDay;
        }
        return DateUtil.getToday();
    }


    public static void main(String[] args) {
        System.out.println(extract(CommandEnum.weight_punchcard.getExample().get(0)
                ,CommandEnum.weight_punchcard.getReg()));
    }
}
