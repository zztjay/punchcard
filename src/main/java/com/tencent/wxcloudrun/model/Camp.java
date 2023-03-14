package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 训练营表
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Camps")
@Data
@NameStyle(Style.normal)
public class Camp implements Serializable {
    public static final int PUNCHCARD_TYPE_LOSE_WEIGHT = 1;
    public static final int PUNCHCARD_TYPE_EXERCISE = 2;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String  campName; // 训练营名称
    private String groupId; // 归属群id
    private int type = PUNCHCARD_TYPE_LOSE_WEIGHT; // 1: 减肥，2: 运动健身
    private String  createrName; // 活动创建者名称
    private String  createrWxId; // 活动创建者的wxId
    private String  createrOpenId; // 活动创建者的openId
    private String  ext;// 扩展字段
    @LogicDelete(notDeletedValue = 0, isDeletedValue = 1)
    private int deleted; // 是否删除，0：开启，1：关闭（逻辑删除）
}
