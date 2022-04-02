package com.liner.eduservice.service;

import com.liner.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liner.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
public interface EduChapterService extends IService<EduChapter> {

    //课程大纲列表
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除课程章节
    boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseById(String courseId);
}
