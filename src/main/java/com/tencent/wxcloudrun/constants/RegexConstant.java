package com.tencent.wxcloudrun.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 正则表达式常量
 * @Author：zhoutao
 * @Date：2023/3/7 14:11
 */
public interface RegexConstant {
    List<String> chTodayStrs = Arrays.asList("今天","今日");
    List<String> chNearlyWeekStrs = Arrays.asList("近一周","近1周");
    List<String> chNearlyMonthStrs = Arrays.asList("近一月","近1月");
    List<String> chNearlyYearStrs = Arrays.asList("近一年","近1年");
}
