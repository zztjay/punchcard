package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动请求对象
 * @Author：zhoutao
 * @Date：2023/1/22 19:45
 */
@Data
public class ActivityRequest {
    private String teamCode; // 企业组织唯一标识码
    private String  activityName; // 活动名称
    private String  activityDesc;// 活动描述
    private String  activityPic;// 活动图片
    private String activityStartTime;// 开始生效日期
    private String activityEndTime;// 结束生效日期
    private int punchCardType;// 打卡类型，1：“整体文本法” 2.“标准造句法”
    private int punchCardFrequecy;// 打卡频次，1：“每天1次”，2. “每周6天”，3. “每周5天”
    private int canRepunchCard;// 是否允许补卡，0：不允许，1：允许
    private int  repunchCardDays;// 允许补卡天数
    private String  members;// 全部学生名单
    private String  coachs;// 全部教练列表，包含openId和名称, {"openId":"1212","nick":"国晖"}
    private String  rewardRule;// 奖励规则，json结构
    // "{
    // type：积分类型，1.打卡 2.点赞、3.评级 4. 优选
    // startTime：开始时间
    // basePioint：基础值
    // limit：积分上限
    // }"
    private String  ext;// 扩展字段
}
