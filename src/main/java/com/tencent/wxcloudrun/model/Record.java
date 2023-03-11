package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 打卡记录
 *
 * @Author：zhoutao
 * @Date：2023/1/18 13:33
 */
@Table(name = "Records")
@Data
@NameStyle(Style.normal)
public class Record {

    public static final int PUNCHCARD_TYPE_PUNCHCARD = 0;
    public static final int PUNCHCARD_TYPE_SPORTS = 1;
    public static final int PUNCHCARD_TYPE_FOOD = 2;
    public static final int PUNCHCARD_TYPE_WEIGHT = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private Long campId; // 活动id
    private String memberWxId; // 用户的wxId
    private String memberOpenId; // 用户的openId
    private Integer type; // 打卡类型，1:运动，2:食物，3：减肥
    private String content; //用户打卡文本内容，使用json结构存储
    private String punchCardTime; // 打卡日期，YYYYMMDD
    private String formatContent; // 提取核心字段，格式化后的数据
    private String quantity;  // 具体数量，运动：千卡，食物：千卡，减肥：斤数
}
