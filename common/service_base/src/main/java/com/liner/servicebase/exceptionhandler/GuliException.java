package com.liner.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Administrator
 * @date: 2022/3/27 17:55
 * @description:
 */

@Data
@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
public class GuliException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息


}
