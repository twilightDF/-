package com.mikey.design.service;

import com.github.pagehelper.PageInfo;
import com.mikey.design.entity.Teacher;
import com.mikey.design.entity.TitleOfStudent;

import java.util.List;

/**
 * @author Mikey
 * @Title:
 * @Description:
 * @Email:1625017540@qq.com
 * @date 2018/11/28 18:45
 * @Version 1.0
 */
public interface TeacherService {
    public int getTeacherNum();//获取教师人数
    public void addTeacher(Teacher teacher);//添加教师
    public void updateTeacherNum(Teacher teacher);
    public void deleteTeacherNum(int teacherId);
    public Teacher getTeacher(int teacherId);
    List<Teacher> getAllTeacher();
    PageInfo getAllTeacherByPage(int currentPage, int pageSize);//分页获取所有教师
    PageInfo<TitleOfStudent> getWillAdmitStudentMes(Integer teacherId, int currentPage, int pageSize);//通过教师id获取全部志愿信息分页
    //获取当前教师已经录取的学生信息
    PageInfo<TitleOfStudent>  getAllAlreayAdmitStudent(Integer teacherId, int currentPage, int pageSize);
    //获取学生填报志愿状态
    PageInfo<TitleOfStudent>  getAllStudentWishStateForAdmin(int currentPage, int pageSize);
    //通过手机号回显新增教师
    Teacher getStudentByPhone(String teacherPhone);
}
