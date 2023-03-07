package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.RewardQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author：zhoutao
 * @Date：2023/1/30 15:36
 */
@SpringBootTest
public class MapperTest {
    @Resource
    CampMapper activityMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    PunchCardMapper punchCardMapper;


    @Resource
    UsersMapper usersMapper;

    @Test
    public void correctionTest(){
//        usersMapper.getByOpenId("1");
//
////        activityMapper.query(new ActivityQuery("1"));
////        activityMapper.count(new ActivityQuery("1"));
//
//        membersMapper.selectByOpenId("1",1L);
//        membersMapper.selectByUserName("周韬",1L);
//
//        commentMapper.getComments(1L);
//        commentMapper.getRootComments(1L);
//
////        punchCardMapper.query(new PunchCardQuery());
////        punchCardMapper.count(new PunchCardQuery());
//        punchCardMapper.getRepunchCount(1L, "1");
//
//        rewardMapper.getByRecordId(1L,"1",1);
//        PunchCardQuery query = new PunchCardQuery();
//        query.setActivityId(7L);
//        query.setOpenId("oOPIl45BU7yfmV-0bYYKX-Os64G0");
////        query.setPunchCardTime("2023-2-18");
//        query.setIngoreRecordIds(Arrays.asList(14l));


        RewardQuery thumbsupQuery = new RewardQuery();
        thumbsupQuery.setRecordId(11L);

    }
}
