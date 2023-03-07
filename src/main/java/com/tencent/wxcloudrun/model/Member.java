package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队成员对象
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Members")
@Data
@NameStyle(Style.normal)
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer ROLE_TYPE_MANAGER = 3;
    public static final Integer ROLE_TYPE_NORMAL = 2;
    public static final Integer ROLE_TYPE_CREATER = 1 ;
    public static final Integer ROLE_TYPE_NO_JOIN = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private Long campId; // 活动id
    private String memberWxId; // 用户的wxId，机器人以wxId为准
    private String memberOpenId; // 用户的openId，机器人以openId为准
    private String memberName; // 用户减脂营的名称（在群里，以群名称为主）
    private Integer roleType; // 用户角色,1: 成员，2:管理员
}
