package com.liner.eduservice.controller;


import com.liner.commonutils.R;
import com.liner.eduservice.service.EduSubjectService;
import com.liner.eduservice.entity.subject.OneSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表功能 树形
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //上传excel文件
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

