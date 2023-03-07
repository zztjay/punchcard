package com.tencent.wxcloudrun.util;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommandEnum;
import com.tencent.wxcloudrun.constants.RegexConstant;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @Author：zhoutao
 * @Date：2023/3/7 09:53
 */
public class RegexUtil {

    /**
     * 按正则提取字符串
     */
    public static List<String> extract(String source, String regex) {
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
     *
     * @param commandInput
     * @param command
     * @return YYYYMMDD
     */
    public static ApiResponse extractDate(String commandInput, CommandEnum command) {
        List<String> substrings = RegexUtil.extract(commandInput, command.getDateReg());
        if (!CollectionUtils.isEmpty(substrings)) {
            if (substrings.size() > 1) {
                return ApiResponse.error("DATE_TOO_MATCH", "解析出多个日期，请检查命令行");
            } else {
                String year = DateUtil.getYear();
                String monthDay = substrings.get(0);
                String dateStr = year + monthDay;
                Date date = DateUtil.getStrToDate(DateUtil.DATE_FORMAT_PATTERN, dateStr);
                Date now = new Date();
                if (date.after(now)) {
                    return ApiResponse.error("DATE_OVER_NOW", "解析命令日期异常，日期超过当前时间");
                }
                return ApiResponse.ok(dateStr);
            }
        }
        return ApiResponse.ok(DateUtil.getToday());
    }

    /**
     * 根据中文提取日期
     *
     * @param commandInput
     * @param command
     * @return YYYYMMDD
     */
    public static ApiResponse extractDateFromCh(String commandInput, CommandEnum command) {
        ApiResponse result = extractDateCh(commandInput, command);
        if (!result.isSuccess()) {
            return result;
        }
        String dateStr = (String) result.getData();
        if (RegexConstant.chTodayStrs.contains(dateStr)) {
            return ApiResponse.ok(DateUtil.getDate2Str(DateTime.now().withTimeAtStartOfDay().toDate()));
        } else if (RegexConstant.chNearlyWeekStrs.contains(dateStr)) {
            return ApiResponse.ok(DateUtil.getDate2Str(DateTime.now().minusDays(7).withTimeAtStartOfDay().toDate()));
        } else if (RegexConstant.chNearlyMonthStrs.contains(dateStr)) {
            return ApiResponse.ok(DateUtil.getDate2Str(DateTime.now().minusDays(30).toDate()));
        } else if (RegexConstant.chNearlyYearStrs.contains(dateStr)) {
            return ApiResponse.ok(DateUtil.getDate2Str(DateTime.now().minusDays(365).toDate()));
        } else {
            return ApiResponse.error("CH_DATE_NOT_SUPPORT", "中文日期格式不支持");
        }
    }

    /**
     * 根据中文提取日期
     *
     * @param commandInput
     * @param command
     * @return YYYYMMDD
     */
    public static ApiResponse extractDateCh(String commandInput, CommandEnum command) {
        List<String> substrings = RegexUtil.extract(commandInput, command.getDateReg());

        if (!CollectionUtils.isEmpty(substrings)) {
            if (substrings.size() > 1) {
                return ApiResponse.error("DATE_TOO_MATCH", "解析出多个时间，请检查命令行");
            } else {
                String dateStr = substrings.get(0);
                if (RegexConstant.chTodayStrs.contains(dateStr) || RegexConstant.chNearlyWeekStrs.contains(dateStr)
                        || RegexConstant.chNearlyMonthStrs.contains(dateStr) || RegexConstant.chNearlyYearStrs.contains(dateStr)) {
                    return ApiResponse.ok(dateStr);
                }
            }
        }
        return ApiResponse.error("DATE_IS_EMPTY", "解析命令日期异常，未提取到合法日期，请检查输入");
    }


    public static void main(String[] args) {
        System.out.println(extract(CommandEnum.weight_punchcard.getExample().get(0)
                , CommandEnum.weight_punchcard.getReg()));
    }
}
