package com.liner.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liner.commonutils.R;
import com.liner.eduservice.config.vo.TeacherQuery;
import com.liner.eduservice.entity.EduTeacher;
import com.liner.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */


@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表所有数据
    @ApiOperation("查询讲师表所有数据")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用Service方法实现查询
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }


    //2.讲师逻辑删除数据
    @ApiOperation("根据ID逻辑删除讲师数据")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {  //@PathVariable 获得路径中的id值
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3.分页查询讲师数据
    @ApiOperation("分页查询讲师数据")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

       /* try {
            int i = 10/0;
        } catch (Exception e) {
           //执行自定义异常
            throw new GuliException(2000,"执行了自定义异常了");
        }*/


        //调用方法实现分页
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据list集合
        //方法一
        /*Map map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);*/
        //方法二
        return R.ok().data("total", total).data("rows", records);

    }

    //4.条件分页查询讲师数据
    @ApiOperation("条件查询带分页讲师数据")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建Wrapper条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //判断条件值是否为空
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            //构建条件
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //构建条件
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            //构建条件
            wrapper.le("gmt_create", end);
        }


        //调用方法实现分页
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据list集合

        return R.ok().data("total", total).data("rows", records);
    }


    //5.添加讲师数据
    @ApiOperation("添加讲师数据")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        //调用方法实现添加
        boolean save = teacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    //5.修改讲师数据

    //根据讲师id进行查询
    @ApiOperation("根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @ApiOperation("修改讲师数据")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        //调用方法实现修改
        boolean flag = teacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }


}

