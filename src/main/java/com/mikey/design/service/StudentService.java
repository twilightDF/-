package com.mikey.design.service;

import com.github.pagehelper.PageInfo;
import com.mikey.design.entity.Student;

/**
 * @author Mikey
 * @Title:
 * @Description:
 * @Email:1625017540@qq.com
 * @date 2018/11/28 18:10
 * @Version 1.0
 */
public interface StudentService {
    public void addStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(int studentId);
    public int getStudentNum();
    public Student getStudent(int studentId);

    PageInfo getAllTeacherByPage(int currentPage, int pageSize);

    /**
     * 通过手机号回显学号
     * @param studentPhone
     * @return
     */
    Student getStudentByPhone(String studentPhone);
}
