package com.mikey.design.views.student;

import com.mikey.design.entity.Design;
import com.mikey.design.entity.Student;
import com.mikey.design.entity.Teacher;
import com.mikey.design.entity.TitleOfStudent;
import com.mikey.design.service.DesignService;
import com.mikey.design.service.StudentService;
import com.mikey.design.service.TeacherService;
import com.mikey.design.service.TitleOfStudentService;
import com.mikey.design.utils.SpringUtil;
import com.mikey.design.utils.ThreadLocalUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * @author Mikey
 * @Title: 填报志愿
 * @Description: 填报志愿面板
 * @Email:1625017540@qq.com
 * @date 2018/12/4 11:14
 * @Version 1.0
 */
public class WispPanel extends JPanel {

    private StudentService studentService;
    private TeacherService teacherService;
    private DesignService designService;
    private TitleOfStudentService titleOfStudentService;
    private  List<Teacher> teacherList=null;
    private  List<Design> titleList=null;

    /**
     * 获取数据信息
     */
    private void getData(){
        studentService = (StudentService) SpringUtil.getBean("studentServiceImpl");
        teacherService = (TeacherService) SpringUtil.getBean("teacherServiceImpl");
        designService= (DesignService) SpringUtil.getBean("designServiceImpl");
        titleOfStudentService= (TitleOfStudentService) SpringUtil.getBean("titleOfStudentServiceImpl");

        this.teacherList=teacherService.getAllTeacher();//获取全部教师信息
        this.titleList=designService.getAllDesign();//获取全部毕设题目信息
    }

    /**
     * 校验志愿提交数据
     * @return
     * @param teacherboBox
     * @param teacherboBox2
     * @param titleBox
     * @param titleBox2
     */
    private boolean checkData(JComboBox teacherboBox, JComboBox teacherboBox2, JComboBox titleBox, JComboBox titleBox2){

        //TODO:待添加限制第一第二志愿不能相同

        if(teacherboBox.getSelectedItem().toString().equals("请选择教师")||teacherboBox2.getSelectedItem().toString().equals("请选择教师")){
            JOptionPane.showMessageDialog(this,"请选择教师！","系统提示",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if (titleBox.getItemCount()==0||titleBox.getItemCount()==0){
            JOptionPane.showMessageDialog(this,"该教师无毕设题目！","系统提示",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 提交志愿
     * @param titleBox
     * @param titleBox2
     * @return
     */
    private boolean submitWish(JComboBox titleBox, JComboBox titleBox2){
        Student self = (Student) ThreadLocalUtil.get();//获取用户（学生）信息
        TitleOfStudent firstTitleOfStudent = new TitleOfStudent();
        firstTitleOfStudent.setDesOfStu(self.getStudentId());
        firstTitleOfStudent.setDesWishOrder(0);//第一志愿
        firstTitleOfStudent.setDesState(0);//待录取
        for (Design d:titleList){
            if (d.getDesignTitle().equals(titleBox.getSelectedItem().toString())){
                firstTitleOfStudent.setDesOfTitle(d.getDesignId());
            }
        }

        TitleOfStudent secondTitleOfStudent = new TitleOfStudent();
        secondTitleOfStudent.setDesOfStu(self.getStudentId());
        secondTitleOfStudent.setDesWishOrder(1);//第二志愿
        secondTitleOfStudent.setDesState(0);//待录取
        for (Design d:titleList){
            if (d.getDesignTitle().equals(titleBox2.getSelectedItem().toString())){
                secondTitleOfStudent.setDesOfTitle(d.getDesignId());
            }
        }

        titleOfStudentService.addTitleOfStudent(firstTitleOfStudent,secondTitleOfStudent);//保存到数据库

        return true;
    }
    /**
     * 构造方法初始化面板
     * @throws HeadlessException
     */
    public WispPanel(JButton menuChild1) throws HeadlessException {
        getData();//获取数据信息
        showView(menuChild1);//展现视图

    }
    public WispPanel() throws HeadlessException { }

    /**
     * 展现视图
     * @throws HeadlessException
     */
    public void showView(JButton menuChild1) throws HeadlessException {

        setLayout(new GridLayout(7,1));//七行一列网格布局
        /**
         * 标题
         */
        JPanel titleJpanel=new JPanel();
        titleJpanel.setBackground(Color.LIGHT_GRAY);
        JLabel title=new JLabel("填报志愿");
        title.setFont(new Font("宋体",Font.BOLD, 20));
        titleJpanel.setLayout(new FlowLayout());
        titleJpanel.add(title);
        add(titleJpanel);
        /**
         * 志愿一
         */
        JPanel firstWishJpanel=new JPanel();
        firstWishJpanel.setBorder(new EmptyBorder(5,5,5,5));
        firstWishJpanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        JLabel wishabel=new JLabel("志愿一:    ");
        JLabel teacherLabel=new JLabel("指导老师:");
        JComboBox teacherboBox=new JComboBox();//下拉框

        teacherboBox.addItem("请选择教师");

        for (Teacher t:teacherList){
            teacherboBox.addItem(t.getTeacherName());
        }
        firstWishJpanel.add(wishabel);
        firstWishJpanel.add(teacherLabel);
        firstWishJpanel.add(teacherboBox);



        JLabel blank1=new JLabel("    ");
        JLabel titleLabel=new JLabel("课设题目:");
        JComboBox titleBox=new JComboBox();//下拉框

        titleBox.addItem("请选择课设题目");

        firstWishJpanel.add(blank1);
        firstWishJpanel.add(titleLabel);
        firstWishJpanel.add(titleBox);

        add(firstWishJpanel);

        //设置监听达到联级更新
        teacherboBox.addItemListener(e -> {//lambda语法
           if(e.getStateChange() == ItemEvent.SELECTED){//只在确认选择时进行更新

             titleBox.removeAllItems();//移除所有下拉框内容

               for (Teacher t:teacherList){
                   if(teacherboBox.getSelectedItem().toString().equals(t.getTeacherName())){//判断教师是否
                       for (Design d:titleList)
                           if (d.getDesignOfTeacher() == t.getTeacherId())
                               titleBox.addItem(d.getDesignTitle());
                   }
               }
           }
      }
        );
        /**
         * 志愿二
         */
        JPanel secondWishJpanel=new JPanel();
        secondWishJpanel.setBorder(new EmptyBorder(5,5,5,5));
        secondWishJpanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        JLabel wishabel2=new JLabel("志愿二:    ");
        JLabel teacherLabel2=new JLabel("指导老师:");
        JComboBox teacherboBox2=new JComboBox();//下拉框

        teacherboBox2.addItem("请选择教师");

        for (Teacher t:teacherList){
            teacherboBox2.addItem(t.getTeacherName());
        }

        secondWishJpanel.add(wishabel2);
        secondWishJpanel.add(teacherLabel2);
        secondWishJpanel.add(teacherboBox2);

        JLabel blank2=new JLabel("    ");
        JLabel titleLabel2=new JLabel("课设题目:");
        JComboBox titleBox2=new JComboBox();//下拉框

        titleBox2.addItem("请选择课设题目");

        secondWishJpanel.add(blank2);
        secondWishJpanel.add(titleLabel2);
        secondWishJpanel.add(titleBox2);

        add(secondWishJpanel);

        //设置监听达到联级更新:志愿二
        teacherboBox2.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){

                titleBox2.removeAllItems();//移除下拉框内容

                for (Teacher t:teacherList){
                            if(teacherboBox2.getSelectedItem().toString().equals(t.getTeacherName())){//判断教师是否
                                for (Design d:titleList){
                                    if (d.getDesignOfTeacher()==t.getTeacherId())
                                        titleBox2.addItem(d.getDesignTitle());
                                }
                            }
                        }
                    }
                });
        /**
         * 提交按钮
         */
        JPanel btnJpanel=new JPanel();
        JButton submint=new JButton("提交");
        JButton reset=new JButton("重置");

        reset.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherboBox.setSelectedIndex(0);
                if(titleBox.getItemCount()==0)titleBox.addItem("请选择课设题目");
                titleBox.setSelectedIndex(0);
                teacherboBox2.setSelectedIndex(0);
                if(titleBox2.getItemCount()==0)titleBox2.addItem("请选择课设题目");
                titleBox2.setSelectedIndex(0);
            }
        });

        /**
         * 确认提交志愿
         */

        JPanel showTipMessageLocationJpanel=this;

        submint.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * 数据校验
                 */
                if (checkData(teacherboBox,teacherboBox2,titleBox,titleBox2)){
                /**
                 * 弹窗确认
                 */
                int result = JOptionPane.showConfirmDialog(showTipMessageLocationJpanel, "确认提交志愿？", "系统提示", JOptionPane.YES_NO_CANCEL_OPTION);
                /**
                 * 提交保存
                 */
                if (result==0){
                    //保存志愿操作
                    if (submitWish(titleBox,titleBox2)){
                        JOptionPane.showMessageDialog(showTipMessageLocationJpanel,"提交成功！","系统提示",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("志愿提交成功");
                        //TODO:隐藏填报志愿面板
                        menuChild1.doClick();
                    }else {
                        JOptionPane.showMessageDialog(showTipMessageLocationJpanel,"提交失败、请联系系统管理员！","系统提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
               }
            }
        });
        btnJpanel.add(submint);
        btnJpanel.add(reset);

        add(btnJpanel);
    }
}
