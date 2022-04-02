package com.liner.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: Administrator
 * @date: 2022/3/31 8:10
 * @description:
 */

@Data
public class SubjectData {
    @ExcelProperty(value = "一级分类",index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类",index = 1)
    private String twoSubjectName;
}
