package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.MembersMapper;
import com.tencent.wxcloudrun.model.Member;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author：zhoutao
 * @Date：2023/3/12 21:52
 */
@Component
public class MemberService {

    @Resource
    MembersMapper membersMapper;

    public void updateOriginWeight(String wxId, Long campId, String originWeight){
        Member member = membersMapper.selectByWxId(wxId,campId);
        member.setOriginWeight(originWeight);
        membersMapper.updateByPrimaryKey(member);
    }

    public void updateGoalWeight(String wxId, Long campId, String goalWeight){
        Member member = membersMapper.selectByWxId(wxId,campId);
        member.setGoalWeight(goalWeight);
        membersMapper.updateByPrimaryKey(member);
    }
}
