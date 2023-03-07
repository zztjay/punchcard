package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.CampQuery;
import com.tencent.wxcloudrun.model.Camp;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CampMapper extends Mapper<Camp> {
    List<Camp> query(CampQuery query);
    int count(CampQuery query);
}
