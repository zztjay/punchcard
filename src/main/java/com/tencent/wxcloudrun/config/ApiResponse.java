package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.constants.CommonConstants;
import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse<T> {

  private String code;
  private String errorMsg;
  private T data;


  public boolean isFail(){
    return !isSuccess();
  }


  public boolean isSuccess(){
    return code.equals(CommonConstants.SUCCESS);
  }

  private ApiResponse(String code, String errorMsg, T data) {
    this.code = code;
    this.errorMsg = errorMsg;
    this.data = data;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse(CommonConstants.SUCCESS, "成功", new HashMap<>());
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse(CommonConstants.SUCCESS, "成功", data);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(CommonConstants.ERROR, errorMsg, new HashMap<>());
  }

  public static ApiResponse error(String erroCode, String errorMsg) {
    return new ApiResponse(erroCode, errorMsg, new HashMap<>());
  }
}
