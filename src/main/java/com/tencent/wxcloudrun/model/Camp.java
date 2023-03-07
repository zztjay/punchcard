package com.tencent.wxcloudrun.model;

import lombok.Data;
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
    public static final int IS_SUPPORT_REPUNCHCRD = 1;
    public static final int IS_NOT_SUPPORT_REPUNCHCRD = 0;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String  campName; // 训练营名称
    private String groupId; // 归属群id
    private String  createrName; // 活动创建者名称
    private String  createrWxId; // 活动创建者的wxId
    private String  createrOpenId; // 活动创建者的openId
    private String  ext;// 扩展字段
}
