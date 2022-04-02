package com.liner.eduservice.controller;

import com.liner.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Administrator
 * @date: 2022/3/29 15:47
 * @description:
 */


@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域
public class EduLoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/de47ee9b-7fec-43c5-8173-13c5f7f689b2.png");
    }



}
