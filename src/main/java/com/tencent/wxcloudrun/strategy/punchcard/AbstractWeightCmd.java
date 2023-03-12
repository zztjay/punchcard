    package com.tencent.wxcloudrun.strategy.punchcard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginInfo;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

    /**
     * 比昨天瘦-体重打卡
     * @Author：zhoutao
     * @Date：2023/3/6 19:32
     */
    @Component
    public abstract class AbstractWeightCmd implements PunchCardCmd {
        @Resource
        PunchCardService punchCardService;

        public static final String heightNumRegex = "([0-9]|[1-9][0-9]{1,3})" + "(\\.\\d{1,2})?"; // 验证体重的数字

        @Override
        public ApiResponse<JSONObject> extractData(String inputCmd) {
            JSONObject data = new JSONObject();
            List<String> datas = RegexUtils.getMatches(dataReg(),inputCmd);
            if(datas.size() > 1){
                return ApiResponse.error("TOO_MUCH_WEIGHT","\\'" + inputCmd + "\\'格式有误，请修改！");
            }
            data.put("weight", datas.get(0));
            return ApiResponse.ok(data);
        }

        @Override
        public ApiResponse execute(String date, String commandRequest, JSONObject data, LoginInfo loginInfo) {

            Record record = punchCardService.getRecord(loginInfo.getCampId(),loginInfo.getWxId(), date, Record.PUNCHCARD_TYPE_WEIGHT);

            JSONObject weightContent = new JSONObject();
            if(record != null){
                weightContent = JSONObject.parseObject(record.getContent());
            }
            weightContent.put(type(), data.getString("weight"));

            // 执行打卡
            punchCardService.punchcard(weightContent.toJSONString(),date,loginInfo.getCampId(),
                    loginInfo.getWxId(),Record.PUNCHCARD_TYPE_WEIGHT);
            return ApiResponse.ok();
        }

        @Override
        public abstract String type();

        @Override
        public String cmdReg() {
            return new StringBuilder().append(cmdPrexReg())
                    .append(dataReg()).toString();
        }

        @Override
        public abstract String cmdPrexReg() ;
        @Override
        public String dataReg() {
            return heightNumRegex;
        }
    }
