package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 打卡查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class PunchCardQuery extends QueryBase {
    private Long campId; //活动id
    private String punchCardTime; // 打卡日期
    private String openId; // 用户的openId
    private String wxId; // 用户的wxId
    private Integer type; // 打卡类型
    private String startTime; // 开始打卡时间
    private String endTime; // 结束打卡时间
}
