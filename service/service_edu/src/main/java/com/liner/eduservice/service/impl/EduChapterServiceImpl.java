package com.liner.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liner.eduservice.entity.EduChapter;
import com.liner.eduservice.entity.EduVideo;
import com.liner.eduservice.entity.chapter.ChapterVo;
import com.liner.eduservice.entity.chapter.VideoVo;
import com.liner.eduservice.mapper.EduChapterMapper;
import com.liner.eduservice.service.EduChapterService;
import com.liner.eduservice.service.EduVideoService;
import com.liner.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //课程大纲列表 根据课程id查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id 查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //根据课程id 查询课程里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideosList = videoService.list(wrapperVideo);

        //创建list集合，存储最终封装的数据
        List<ChapterVo> finalList = new ArrayList<>();

        //遍历查询章节 list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            //创建list集合，存储封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //遍历查询小节 list集合进行封装
            for (int m = 0; m < eduVideosList.size(); m++) {
                EduVideo eduVideo = eduVideosList.get(m);
                //判断小节属于那个章节
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }

        return finalList;
    }

    //删除课程章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id 查询小节表 查询到有数据则不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //只是查询 章节下有无数据
        int count = videoService.count(wrapper);
        //判断
        if (count>0){//能查询出数据，不进行删除
            throw new GuliException(20001,"不能删除");
        }else {//没有数据，可以进行删除
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
