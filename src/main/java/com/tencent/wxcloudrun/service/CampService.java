package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.util.StringUtils;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.SuperManagerEnum;
import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.CampQuery;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 活动业务逻辑处理器
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:11
 */
@Service
@Slf4j
public class CampService {
    @Resource
    CampMapper campMapper;
    @Resource
    MembersMapper membersMapper;

    @Deprecated
    // 以群为主，后续改为以小程序为主
    public int getRoleType(Long campId, String wxId){
       Member member = membersMapper.selectByWxId(wxId,campId);
       if(null != member){
           return member.getRoleType();
       }
       return Member.ROLE_TYPE_NO_JOIN;
    }

    public ApiResponse save(Camp camp) {
        // 检查群里是否重复创建
        List<Camp> activities =  campMapper.query(new CampQuery(camp.getGroupId()));
        if(!CollectionUtils.isEmpty(activities)){
            return ApiResponse.error("CAMP_REPEATED_CREAT","减脂营已创建");
        }
        if (camp.getId() != null && camp.getId() > 0L) {
            campMapper.updateByPrimaryKey(camp);
        } else {
            campMapper.insert(camp);
        }
        return ApiResponse.ok(camp.getId());
    }


    public ApiResponse isUserJoinCamp(String groupId){
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId));
        Camp groupCamp = getCampByGid(groupId);
        if(null == groupCamp){
            return ApiResponse.error("GROUP_NO_CAMP","本群还未创建减脂营，请管理员创建");
        }
        boolean isJoin = membersMapper.selectByWxId(LoginContext.getWxId(), groupCamp.getId()) != null;
        if(isJoin){
            return ApiResponse.ok();
        } else {
            return ApiResponse.error("USER_NOT_JOIN_CAMP","您还未加入减脂营，请回复\"参加减脂营\"加入");
        }
    }

    public Camp getCampByGid(String groupId){
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId));
        List<Camp> camps = campMapper.query(new CampQuery(groupId));
        if(!CollectionUtils.isEmpty(camps)){
            return camps.get(0);
        }
        return null;
    }
    /**
     * 报名训练营
     *
     * @return API response json
     */
    public ApiResponse joinCamp(String groupId) {

        Camp camp = getCampByGid(groupId);

        // 生成报名信息
        Member member = new Member();
        member.setCampId(camp.getId());
        member.setMemberName(LoginContext.getWxGroupName());
        member.setMemberWxId(LoginContext.getWxId());
        member.setRoleType(LoginContext.getRoleType());
        membersMapper.insert(member);

        return ApiResponse.ok(member.getId());
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal((double)2.111).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString());
    }
}
