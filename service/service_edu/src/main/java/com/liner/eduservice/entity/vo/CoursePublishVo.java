package com.liner.eduservice.entity.vo;

import lombok.Data;

import javax.annotation.security.DenyAll;

/**
 * @author: Administrator
 * @date: 2022-04-01 20:10
 * @description:
 */
@Data
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
