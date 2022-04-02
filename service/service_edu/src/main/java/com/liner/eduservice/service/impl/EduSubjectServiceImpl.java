package com.liner.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liner.eduservice.entity.EduSubject;
import com.liner.eduservice.entity.excel.SubjectData;
import com.liner.eduservice.listener.SubjectExcelListener;
import com.liner.eduservice.mapper.EduSubjectMapper;
import com.liner.eduservice.service.EduSubjectService;
import com.liner.eduservice.entity.subject.OneSubject;
import com.liner.eduservice.entity.subject.TwoSubject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法读取

            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //课程分类列表功能 树形
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类  ParentId = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
//        this.list(wrapperOne)

        //查询所有二级分类   ParentId ！= 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，存储最终封装的数据
        List<OneSubject> findSubjectList = new ArrayList<>();

        //封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //得到 oneSubjectList 每个 eduSubject 对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把 eduSubject 放到 oneSubject对象里

            //基本写法
            OneSubject oneSubject = new OneSubject();
            /* oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());  */

            //上述简写方式  工具类
            //eduSubject 值复制到 oneSubject对象里
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //把 oneSubject 放到 findSubjectList
            findSubjectList.add(oneSubject);

            //封装二级分类
            //在一级分类里嵌套 二级分类
            //创建list集合，存储最终封装的数据
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                //判断二级属于哪个一级
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    //tSubject 值复制到 twoSubject 放到  twoFinalSubjectList 里
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return findSubjectList;
    }
}
