package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.model.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PunchCardMapper extends Mapper<Record> {
    List<Record> query(PunchCardQuery query);
    int count(PunchCardQuery query);
    String getUserWeight(@Param("wxId")String wxId,@Param("campId") Long campId,
                              @Param("punchCardDate") String punchCardDate);

    String getUserWeightLastTime(@Param("wxId")String wxId,@Param("campId") Long campId,
                         @Param("today") String today);

    String getUserWeightLastWeek(@Param("wxId")String wxId,@Param("campId") Long campId,
                              @Param("start") String start, @Param("end") String end);
}
