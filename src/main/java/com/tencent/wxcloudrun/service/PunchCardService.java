package com.tencent.wxcloudrun.service;

import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CoachEnum;
import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 打卡业务逻辑处理
 *
 * @Author：zhoutao
 * @Date：2023/1/26 19:58
 */
@Service
public class PunchCardService {

    @Resource
    PunchCardMapper punchCardMapper;

    private void delete(Long campId, String punchCardDate, String wxId, int type) {
        Record record = getRecord(campId, wxId, punchCardDate, type);
        if (null != record) {
            punchCardMapper.deleteByPrimaryKey(record.getId());
        }
    }

    /**
     * 删除用户的所有打卡记录
     *
     * @param campId
     * @param punchCardDate
     * @param wxId
     * @return
     */
    public void delete(Long campId, String punchCardDate, String wxId) {

        // 删除食物打卡
        delete(campId, wxId, punchCardDate, Record.PUNCHCARD_TYPE_FOOD);

        // 删除运动打卡
        delete(campId, wxId, punchCardDate, Record.PUNCHCARD_TYPE_SPORTS);

        // 删除体重打卡
        delete(campId, wxId, punchCardDate, Record.PUNCHCARD_TYPE_WEIGHT);

    }



    /**
     * 打卡服务
     *
     * @return API response json
     */
    public ApiResponse punchcard(String content, String punchCardTime, Long campId, String wxId, int type) {
        Record record = getRecord(campId, wxId, punchCardTime, type);
        if (null == record) {
            record = new Record();
            record.setContent(content);
            record.setType(type);
            record.setPunchCardTime(punchCardTime);
            record.setMemberWxId(wxId);
            record.setCampId(campId);
            punchCardMapper.insert(record);
        } else {
            record.setContent(content);
            record.setUpdatedAt(LocalDateTime.now());
            punchCardMapper.updateByPrimaryKeySelective(record);
        }
        return ApiResponse.ok(record.getId());
    }


    /**
     * 获取用户打卡记录
     *
     * @param wxId
     * @param punchCardTime
     * @param type
     * @return
     */
    public Record getRecord(Long campId, String wxId, String punchCardTime, int type) {
        List<Record> records = getRecords(campId, wxId, punchCardTime);
        if (!CollectionUtils.isEmpty(records)) {
            for (Record record : records) {
                if (record.getType() == type) {
                    return record;
                }
            }
        }
        return null;
    }

    public List<Record> getRecords(Long campId, String wxId, String punchcardTime) {
        Preconditions.checkArgument(StringUtil.isNotEmpty(wxId) && StringUtil.isNotEmpty(punchcardTime));
        PunchCardQuery query = new PunchCardQuery();
        query.setPunchCardTime(punchcardTime);
        query.setWxId(wxId);
        query.setCampId(campId);
        return punchCardMapper.query(query);
    }

    /**
     * 查询训练营打卡
     *
     * @return API response json
     */
    public int count(String wxId, Long campId, String startTime, String endTime, Integer type) {
       PunchCardQuery query = new PunchCardQuery();
       query.setType(type);
       query.setStartTime(startTime);
       query.setEndTime(endTime);
       query.setCampId(campId);
       query.setWxId(wxId);
       return punchCardMapper.count(query);
    }
//
//    public
//
//    /**
//     * 查询打卡记录
//     *
//     * @return API response json
//     */
//    public PunchCardDTO getPunchCardRecord(long recordId) {
//        Record record = punchCardMapper.selectByPrimaryKey(recordId);
//        if (!record.getMemberOpenId().equals(LoginContext.getOpenId())) {
//            ApiResponse.error("OPEN_ID_NOT_MATCH", "您没有权限");
//        }
//
//        PunchCardDTO punchCardDTO = new PunchCardDTO();
//        punchCardDTO.setCreateAt(DateUtil.getDate2Str(DateUtil.asDate(record.getCreatedAt())));
//        punchCardDTO.setPunchCardTime(record.getPunchCardTime());
//        punchCardDTO.setContent(record.getContent());
//        Member member = membersMapper.selectByOpenId(record.getMemberOpenId(), record.getActivityId());
//        punchCardDTO.setUserName(member.getMemberName());
//        punchCardDTO.setDeptName(member.getDeptName());
//        punchCardDTO.setPositionName(member.getPositionName());
//        punchCardDTO.setGroupIdentifier(member.getGroupIdentifier());
//
//        // 是否为本人
//        punchCardDTO.setCanEdit(LoginContext.getOpenId().equals(record.getMemberOpenId()));
//        punchCardDTO.setCoach(CoachEnum.isCoach(LoginContext.getOpenId()));
//
//        RewardQuery bestQuery = new RewardQuery();
//        bestQuery.setRecordId(recordId);
//        bestQuery.setType(Reward.REWARD_TYPE_BEST);
//        if(rewardMapper.count(bestQuery) > 0){
//            punchCardDTO.setBest(true); // 优选
//        }
//
//        RewardQuery thumbsupQuery = new RewardQuery();
//        thumbsupQuery.setRecordId(recordId);
//        thumbsupQuery.setType(Reward.REWARD_TYPE_THUMBS_UP);
//        punchCardDTO.setThumbsUp(rewardMapper.count(thumbsupQuery)); // 总点赞数
//        thumbsupQuery.setGiveRewardUserId(LoginContext.getOpenId()); // 当前用户是否点赞
//        if(rewardMapper.count(thumbsupQuery) > 0){
//            punchCardDTO.setUserThumbsup(true);
//        }
//
//        List<Reward> levelRecords = rewardMapper.getByRecordId(recordId, Reward.REWARD_TYPE_LEVE);
//        if (!CollectionUtils.isEmpty(levelRecords)) {
//            Integer level = levelRecords.get(0).getRewardLevel();
//            punchCardDTO.setLevel(level != null ? level : 0); // 等级
//        }
//        punchCardDTO.setRecordId(recordId);
//
//        Activity activity = activityMapper.selectByPrimaryKey(record.getActivityId());
//        punchCardDTO.setPunchCardType(activity.getPunchCardType());
//
//        User user = userService.getUser(record.getMemberOpenId());
//        punchCardDTO.setAvtar(user.getAvator());
//        punchCardDTO.setActivityId(record.getActivityId());
//
//        punchCardDTO.setComments(rewardService.getComments(recordId)); // 评论列表
//        return punchCardDTO;
//    }

}
