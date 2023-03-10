//package com.tencent.wxcloudrun.strategy;
//
//import com.tencent.wxcloudrun.config.ApiResponse;
//
//import com.tencent.wxcloudrun.dto.LoginInfo;
//import com.tencent.wxcloudrun.model.Record;
//import com.tencent.wxcloudrun.service.PunchCardService;
//import com.tencent.wxcloudrun.util.DateUtil;
//import com.tencent.wxcloudrun.util.CmdRegexUtil;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//
///**
// * 打卡总数统计
// *
// * 输入
// * 今天打卡总数、近一周打卡总数、近一月打卡总数
// *
// * 输出
// * 今天（3.1）：体重打卡1次、运动打卡1次、饮食打卡1次
// * 近一周（3.1～3.3）：体重打卡4次、运动打卡3次、饮食打卡2次
// * 近一月（3.1～3.31）：体重打卡4次、运动打卡3次（跑步4次，走路3次）、饮食打卡2次（米饭2次，牛奶4次）
// * @Author：zhoutao
// * @Date：2023/3/6 19:32
// */
//@Component
//public class PunchcardTotalCmd implements Command {
//    @Resource
//    PunchCardService punchCardService;
//
//
//    @Override
//    public ApiResponse execute(String commandRequest, LoginInfo loginInfo) {
//
//        ApiResponse dateChResult = CmdRegexUtil.extractDateCh(commandRequest, type());
//        if(!dateChResult.isSuccess()){
//            return dateChResult;
//        }
//        String dateCh = (String)dateChResult.getData();
//
//        ApiResponse startTimeResult = CmdRegexUtil.extractDateFromCh(commandRequest, type());
//        if(!startTimeResult.isSuccess()){
//            return startTimeResult;
//        }
//        String startTime = (String)startTimeResult.getData();
//        String endTime = DateUtil.getDate2Str(new Date());
//
//        int weightCount = punchCardService.count(loginInfo.getWxId(),loginInfo.getCampId()
//                ,startTime,endTime,Record.PUNCHCARD_TYPE_WEIGHT); // 体重打卡
//        int sportsCount = punchCardService.count(loginInfo.getWxId(),loginInfo.getCampId()
//                ,startTime,endTime,Record.PUNCHCARD_TYPE_SPORTS); // 运动打卡
//        int foodCount = punchCardService.count(loginInfo.getWxId(),loginInfo.getCampId()
//                ,startTime,endTime,Record.PUNCHCARD_TYPE_FOOD); // 食物打卡
//
//        String result = format(dateCh,DateUtil.getDate2Str("MMdd",DateUtil.getStr2SDate(startTime))
//                , DateUtil.getDate2Str("MMdd",DateUtil.getStr2SDate(endTime))
//                ,weightCount,sportsCount,foodCount);
//
//        return ApiResponse.ok(result);
//    }
//
//    public  static String format(String dateCh, String start, String end,  int weight, int sports, int food){
//        String format  = "%s(%s ~ %s) : 体重打卡%s次、运动打卡%s次、饮食打卡%s次";
//        return String.format( format,dateCh,start,end,weight,sports,food);
//    }
//
//    public static void main(String[] args) {
//        System.out.println(format("今天","2.1","3.1",1,1,1));
//    }
//}
