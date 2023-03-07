package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.model.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MembersMapper extends Mapper<Member> {

    public Member selectByUserName(@Param("userName") String userName, @Param("campId")  Long campId);

    public Member selectByOpenId(@Param("openId")String openId,@Param("campId") Long campId);

    public Member selectByWxId(@Param("wxId")String wxId,@Param("campId") Long campId);


    public List<Member> selectByActivityId(@Param("campId") Long campId);

    public List<Long> getSignList(@Param("openId")String openId);

    public int sumMembers(@Param("campId") Long campId);

}
