package com.liner.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liner.eduservice.entity.EduSubject;
import com.liner.eduservice.entity.excel.SubjectData;
import com.liner.eduservice.service.EduSubjectService;
import com.liner.servicebase.exceptionhandler.GuliException;

/**
 * @author: Administrator
 * @date: 2022/3/31 8:15
 * @description:
 */

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    //不能交给Spring管理，需要自己new
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    //读取文件内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }

        //一行一行输入,每次读取两个值。先一级再二级
        //先判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        //一级分类没有重复的就添加
        if (existOneSubject == null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类名称

            subjectService.save(existOneSubject);
        }

        //取一级分类的id值
        String pid=existOneSubject.getId();
        //判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getOneSubjectName(),pid);
        //二级分类没有重复的就添加
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称

            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }


    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }


    //读取文件后内容
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
