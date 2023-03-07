package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
import com.tencent.wxcloudrun.model.Record;
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
    CampMapper campMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    PunchCardMapper punchCardMapper;


    @Resource
    UsersMapper usersMapper;

    @Test
    public void correctionTest(){

        usersMapper.getByWxId("1");

        PunchCardQuery query = new PunchCardQuery();
        query.setWxId("1");
        query.setCampId(111L);
        query.setType(Record.PUNCHCARD_TYPE_FOOD);
        punchCardMapper.query(query);


    }
}
