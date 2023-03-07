package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * @Author：zhoutao
 * @Date：2023/3/6 12:29
 */
@Data
public class LoginInfo {
    private String openId;  // 微信openId

    private String wxId;  // 微信Id

    private String wxGroupName; // 用户群昵称

    private String wxName ; // 微信名称

    private String groupId; // 微信群id

    private String groupName; // 微信群名称

    private Long campId; // 当前训练营id


}
