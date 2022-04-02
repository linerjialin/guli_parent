package com.liner.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022-03-31 18:18
 * @description:
 */

//一级分类
@Data
public class OneSubject {

    private String id;

    private String title;

    //一个一级分类有多个二级类型
    private List<TwoSubject> children = new ArrayList<>();


}
