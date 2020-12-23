package com.wisdomcommand.communication.exception;

import com.communication.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 对全局异常进行统一的处理
 * @Auther ChenShuHong
 * @Date 2020-12-23 14:46
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
  /**
   * 默认全局异常处理。
   * @return ResultData
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public CommonResult<String> exception(Exception e) {
    log.error("全局异常信息 ex={}", e.getMessage(), e);
    return new CommonResult(cn.hutool.http.HttpStatus.HTTP_INTERNAL_ERROR,e.getMessage());
  }
}
