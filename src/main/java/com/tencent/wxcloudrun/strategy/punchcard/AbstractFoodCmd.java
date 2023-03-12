package com.tencent.wxcloudrun.strategy;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
            "((\\s*[,，])|(\\s+))" + ")*"; // 验证内容的字符串

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        JSONObject result = new JSONObject();
        List<String> datas = RegexUtils.getMatches(dataReg(),inputCmd);
        result.put("foods", datas);
        return ApiResponse.ok(result);
    }


    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        Record record = punchCardService.getRecord(loginInfo.getCampId(),loginInfo.getWxId(), date,
                Record.PUNCHCARD_TYPE_FOOD);

        JSONArray weightContents = new JSONArray();
        if(record != null){
            // 获取现有的体重消息
            String content = record.getContent();
            if(!StringUtils.isEmpty(content)){
                weightContents = JSONArray.parseArray(content);
            }
            // 增加本次体重信息
            if(!data.isEmpty()){
                JSONObject weightContent = new JSONObject();
                weightContent.put(type(), data.getString("food"));
                weightContents.add(weightContent);
            }
        } else {
            // 增加本次体重信息
            if(!data.isEmpty()){
                JSONObject weightContent = new JSONObject();
                weightContent.put(type(), data.getString("weight"));
                weightContents.add(weightContent);
            }
        }

        // 执行打卡
        punchCardService.punchcard(weightContents.toJSONString(),date,loginInfo.getCampId(),
                loginInfo.getWxId(),Record.PUNCHCARD_TYPE_FOOD);
        return ApiResponse.ok();
    }

    @Override
    public abstract String type() ;

    @Override
    public String cmdReg() {
        return new StringBuilder(cmdPrexReg()).append(dataReg()).toString();
    }

    @Override
    public  String cmdPrexReg(){
        return new StringBuilder("[\\s]*").append(type()).append(CmdRegexConstant.inputRegex)
                .append(CmdRegexConstant.mutipleSpaceRegex).toString();
    }

    @Override
    public String dataReg() {
        return normalContentRegex;
    }

}
