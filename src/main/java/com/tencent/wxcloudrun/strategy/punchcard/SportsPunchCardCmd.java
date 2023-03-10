package com.tencent.wxcloudrun.strategy.punchcard;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CmdRegexConstant;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.strategy.punchcard.PunchCardCmd;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 运动打卡
 * @Author：zhoutao
 * @Date：2023/3/6 19:32
 */
@Component
public class SportsPunchCardCmd implements PunchCardCmd {
    @Resource
    PunchCardService punchCardService;

    @Override
    public ApiResponse<JSONObject> extractData(String inputCmd) {
        List<String> exerciseDatas  = new ArrayList<>();

        String cmdData = inputCmd.replaceFirst(cmdPrexReg(),"");
        if(!RegexUtils.hasMatchParts(cmdData,CmdRegexConstant.nodoRegex)){
            exerciseDatas.addAll(RegexUtils.matchParts(cmdData, dataReg()));
        }

        JSONObject data = new JSONObject();
        data.put("exerciseDatas",exerciseDatas);
        return ApiResponse.ok(data);
    }

    @Override
    public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {
        JSONArray exerciseDatas = data.getJSONArray("exerciseDatas");
        if( exerciseDatas != null &&  exerciseDatas.size() > 0) {
            return punchCardService.punchcard(commandRequest,exerciseDatas.toJSONString(), date, loginInfo.getCampId(),
                    loginInfo.getWxId(), Record.PUNCHCARD_TYPE_SPORTS);
        }
        return ApiResponse.ok();
    }

    @Override
    public String dataReg() {
        return CmdRegexConstant.onlyNormalContentRegex;
    }



    @Override
    public String type() {
        return "运动打卡";
    }

    @Override
    public String cmdReg() {
        return RegexUtils.or(doSportsRegex(), notDoSportsRegex());
    }

    @Override
    public String cmdPrexReg() {
        return "[\\s]*运动" + CmdRegexConstant.inputRegex + CmdRegexConstant.mutipleSpaceRegex;
    }

    protected String doSportsRegex(){
        return new StringBuilder().append(cmdPrexReg())
                .append(CmdRegexConstant.normalContentRegex).toString();
    }

    protected String notDoSportsRegex(){
        return new StringBuilder().append(cmdPrexReg())
                .append(CmdRegexConstant.nodoRegex).toString();
    }
}
