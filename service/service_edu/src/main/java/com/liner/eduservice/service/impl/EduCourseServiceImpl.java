package com.liner.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liner.eduservice.entity.EduCourse;
import com.liner.eduservice.entity.EduCourseDescription;
import com.liner.eduservice.entity.EduVideo;
import com.liner.eduservice.entity.vo.CourseInfoVo;
import com.liner.eduservice.entity.vo.CoursePublishVo;
import com.liner.eduservice.mapper.EduCourseMapper;
import com.liner.eduservice.service.EduChapterService;
import com.liner.eduservice.service.EduCourseDescriptionService;
import com.liner.eduservice.service.EduCourseService;
import com.liner.eduservice.service.EduVideoService;
import com.liner.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.ObjenesisBase;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    //课程小节注入
    @Autowired
    EduVideoService eduVideoService;
    //课程章节注入
    @Autowired
    EduChapterService eduChapterService;


    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //课程表添加课程基本信息
        //CourseInfoVo 对象转换 eduCourse 对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            //添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }

        //添加成功  获取 添加之后的id
        String cid = eduCourse.getId();


        //课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置 描述的id 就是 课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;

    }

    //根据课程id 查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程基本信息的方法
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    //根据课程id 查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用 Mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
       //根据课程id删除小节
        eduVideoService.removeVideoByCourseById(courseId);

        //根据课程id删除章节
        eduChapterService.removeChapterByCourseById(courseId);

       //根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        
       //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new GuliException(20001,"删除失败");
        }

    }


}
