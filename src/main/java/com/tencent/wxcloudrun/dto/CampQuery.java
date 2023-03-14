package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

/**
 * 活动查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class CampQuery extends QueryBase {
    private String groupId; // 群id

    private Integer deleted; // 群id


    public CampQuery(String groupId) {
        this.groupId = groupId;
    }
}
