package com.tencent.wxcloudrun.strategy.punchcard;


import com.alibaba.fastjson.JSONArray;
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
 * 运动打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class ExcerciseCmd implements PunchCardCmd {
    public static final String exerciseOnlyNormalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s+))" + ")"; // 验证内容的字符串

    public static final String exerciseNormalContentRegex =  "([\\u4E00-\\u9FA5A-Za-z0-9\\+]+" +
            "((\\s*[,，])|(\\s*))" + ")+";

    @Resource
    PunchCardService punchCardService;

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        List<String> exerciseDatas  = new ArrayList<>();
        String cmdData = inputCmd.replaceFirst(cmdPrexReg(),"");
        if(!RegexUtils.hasMatchParts(cmdData,CmdRegexConstant.nodoRegex)){
            List<String> matchParts = RegexUtils.matchParts(cmdData, dataReg());
            for (String matchPart : matchParts) {
                exerciseDatas.add(matchPart.replaceAll(",","").replaceAll("，","")
                        .replaceAll("\\s",""));
            }
        }

        JSONObject data = new JSONObject();
        data.put("exerciseDatas",exerciseDatas);
        return ApiResponse.ok(data);
    }

    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        JSONArray exerciseDatas = data.getJSONArray("exerciseDatas");
        if( exerciseDatas != null &&  exerciseDatas.size() > 0) {
            return punchCardService.punchcard(exerciseDatas.toJSONString(), date, loginInfo.getCampId(),
                    loginInfo.getWxId(), Record.PUNCHCARD_TYPE_SPORTS);
        }
        return ApiResponse.ok();
    }

    @Override
    public String dataReg() {
        return exerciseOnlyNormalContentRegex;
    }

    @Override
    public String type() {
        return "运动打卡";
    }

    @Override
    public String cmdReg() {
        return doSportsRegex();
    }

    @Override
    public String cmdPrexReg() {
        return "[\\s]*运动" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex;
    }

    protected String doSportsRegex(){
        return new StringBuilder().append(cmdPrexReg())
                .append(exerciseNormalContentRegex).toString();
    }

    protected String notDoSportsRegex(){
        return new StringBuilder().append(cmdPrexReg())
                .append(CmdRegexConstant.nodoRegex).toString();
    }
}
