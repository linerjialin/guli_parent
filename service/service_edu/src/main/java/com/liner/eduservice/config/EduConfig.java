package com.liner.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Administrator
 * @date: 2022/3/27 11:20
 * @description:
 */

@Configuration
@MapperScan("com.liner.eduservice.mapper")
public class EduConfig {

    //逻辑删除的插件
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }


    //MP分页的插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }



}
