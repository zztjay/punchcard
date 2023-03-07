package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CampQuery;
import com.tencent.wxcloudrun.model.Camp;
import com.tencent.wxcloudrun.service.CampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 活动的页面控制器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class CampController {
    
    @Resource
    CampService campService;

    /**
     * 生成活动信息
     *
     * @return API response json
     */
    @PostMapping(value = "/api/camp/robot/save")
    public ApiResponse save(@RequestBody Camp camp) {
       return campService.save(camp);
    }


}
