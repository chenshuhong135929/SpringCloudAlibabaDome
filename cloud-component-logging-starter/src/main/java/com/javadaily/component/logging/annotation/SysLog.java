package com.javadaily.component.logging.annotation;

import java.lang.annotation.*;

/**
 * @Auther ChenShuHong
 * @Date 2021-05-14 11:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
  /**
   * 日志内容
   * @return {String}
   */
  String value();
}
