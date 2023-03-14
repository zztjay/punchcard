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


    // 以群为主，后续改为以小程序为主
    public int getRoleType(Long campId, String wxId) {

        // 是否为创建者角色
        Camp camp = getCampById(campId);
        if(null != camp && camp.getCreaterWxId().equals(wxId)){
            return Member.ROLE_TYPE_CREATER;
        }
        // 其他角色
        Member member = membersMapper.selectByWxId(wxId, campId);
        if (null != member) {
            return member.getRoleType();
        }
        return Member.ROLE_TYPE_NO_JOIN;
    }

    public ApiResponse save(Camp camp) {
        if (camp.getId() != null && camp.getId() > 0L) {
            campMapper.updateByPrimaryKey(camp);
        } else {
            campMapper.insert(camp);
        }
        return ApiResponse.ok(camp.getId());
    }


    public ApiResponse isUserJoinCamp(String groupId, String wxId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId));
        Camp groupCamp = getOpenCampByGid(groupId);
        if (null == groupCamp) {
            return ApiResponse.error("GROUP_NO_CAMP", "本群还未开启打卡统计功能，请管理员创建");
        }
        boolean isJoin = membersMapper.selectByWxId(wxId, groupCamp.getId()) != null;
        if (isJoin) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error("USER_NOT_JOIN_CAMP", "您还未加入减脂营，请回复\"参加减脂营\"加入");
        }
    }

    public Camp getCampByGid(String groupId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(groupId));
        List<Camp> camps = campMapper.query(new CampQuery(groupId));
        if (!CollectionUtils.isEmpty(camps)) {
            return camps.get(0);
        }
        return null;
    }

    public Camp getOpenCampByGid(String groupId){
        Camp camp = getCampByGid(groupId);
        if(camp!=null && camp.getDeleted() == Camp.OPEN){
            return camp;
        }
        return null;
    }


    public Camp getCampById(Long campId) {
        return campMapper.selectByPrimaryKey(campId);
    }

    /**
     * 报名训练营
     *
     * @return API response json
     */
    public ApiResponse joinCamp(Long campId, String memberName, String wxId) {

        Camp camp = getCampById(campId);

        // 生成报名信息
        Member member = new Member();
        member.setCampId(camp.getId());
        member.setMemberName(memberName);
        member.setMemberWxId(wxId);
        // 是否为创建者角色
        if(null != camp && camp.getCreaterWxId().equals(wxId)){
            member.setRoleType(Member.ROLE_TYPE_CREATER);
        } else {
            member.setRoleType(Member.ROLE_TYPE_NORMAL);
        }

        membersMapper.insert(member);

        return ApiResponse.ok(member.getId());
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal((double) 2.111).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
    }
}
