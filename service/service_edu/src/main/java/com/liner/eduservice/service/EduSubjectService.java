package com.liner.eduservice.service;

import com.liner.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liner.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    //课程分类列表功能 树形
    List<OneSubject> getAllOneTwoSubject();
}
