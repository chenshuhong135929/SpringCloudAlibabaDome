package com.wisdomcommand.communication.exception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.communication.common.CommonResult;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @Auther ChenShuHong
 * @Date 2021-01-12 15:03
 */
@Component
public class SentinelExceptionHandler implements BlockExceptionHandler{

  @Override
  public void handle(HttpServletRequest  request, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
    //BlockException 异常接口,包含Sentinel的五个异常
    // FlowException 限流异常
    // DegradeException 降级异常
    // ParamFlowException 参数限流异常
    // AuthorityException 授权异常
    // SystemBlockException 系统负载异常
    httpServletResponse.setContentType("application/json;charset=utf-8");
    CommonResult data = null;
    if (e instanceof FlowException) {
      data = new CommonResult(-1, "流控规则被触发......");
    } else if (e instanceof DegradeException) {
      data = new CommonResult(-2, "降级规则被触发...");
    } else if (e instanceof AuthorityException) {
      data = new CommonResult(-3, "授权规则被触发...");
    } else if (e instanceof ParamFlowException) {
      data = new CommonResult(-4, "热点规则被触发...");
    } else if (e instanceof SystemBlockException) {
      data = new CommonResult(-5, "系统规则被触发...");
    }
    httpServletResponse.getWriter().write(JSON.toJSONString(data));
  }
}


