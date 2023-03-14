package com.tencent.wxcloudrun.strategy.command.member.punchcard;


import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 食物打卡
 *
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public abstract class AbstractFoodCmd implements PunchCardCmd {

    @Resource
    PunchCardService punchCardService;

    public static final String normalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s*))" + ")+"; // 验证内容的字符串

    public static final String onlyNormalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s*))" + ")"; // 验证内容的字符串

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {

        JSONObject result = new JSONObject();

        List<String> foods = new ArrayList<>();
        List<String> matchParts = RegexUtils.getMatches(dataReg(), inputCmd.replaceFirst(cmdPrexReg(),""));
        for (String matchPart : matchParts) {
            foods.add(matchPart.replaceAll(",","").replaceAll("，","")
                    .replaceAll("\\s",""));
        }
        result.put("foods", foods);
        return ApiResponse.ok(result);
    }

    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        Record record = punchCardService.getRecord(loginInfo.getCampId(),loginInfo.getWxId(), date,
                Record.PUNCHCARD_TYPE_FOOD);

        JSONObject foodContents = new JSONObject();
        if(record != null){
            foodContents = JSONObject.parseObject(record.getContent());
        }
        foodContents.put(type(), data.getJSONArray("foods"));

        // 执行打卡
        punchCardService.punchcard(foodContents.toJSONString(),null, date,loginInfo.getCampId(),
                loginInfo.getWxId(),Record.PUNCHCARD_TYPE_FOOD);
        return ApiResponse.ok();
    }

    @Override
    public abstract String type() ;

    @Override
    public String cmdReg() {
        return new StringBuilder(cmdPrexReg()).append(normalContentRegex).toString();
    }

    @Override
    public String cmdPrexReg(){
        return new StringBuilder("[\\s]*").append(type()).append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex).toString();
    }

    public static void main(String[] args) {
        String a = "水果\n";
        String normalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
                "((\\s*[,，])|(\\s*))" + ")*"; // 验证内容的字符串
        System.out.println(RegexUtils.getMatches(normalContentRegex,a));
//        System.out.println(new AddFoodCmd().cmdReg());
    }
    @Override
    public String dataReg() {
        return onlyNormalContentRegex;
    }

}
