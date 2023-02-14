package com.mikey.design.views.teacher;

import com.mikey.design.listerner.common.LoginOut;
import com.mikey.design.views.common.UpPwdPanel;
import com.mikey.design.views.common.UpdatePanel;
import com.mikey.design.views.common.WelcomeJpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Mikey
 * @Title:
 * @Description:
 * @Email:1625017540@qq.com
 * @date 2018/11/30 19:37
 * @Version 1.0
 */
public class TeacherMainView extends JFrame {

    public TeacherMainView(JFrame login) {
        JPanel Menu=new JPanel();
        Menu.setLayout(new GridLayout(10,1,3,3));
        JButton menuChild1 = new JButton("添加毕业设计");
        JButton menuChild2 = new JButton("我的毕设题目");
        JButton menuChild3 = new JButton("录取报名学生");
        JButton menuChild4 = new JButton("查看已录学生");
        JButton menuChild5 = new JButton("修改个人信息");
        JButton menuChild6 = new JButton("修改登入密码");
        JButton menuChild7 = new JButton("退出登入");
        Menu.add(menuChild1);
        Menu.add(menuChild2);
        Menu.add(menuChild3);
        Menu.add(menuChild4);
        Menu.add(menuChild5);
        Menu.add(menuChild6);
        Menu.add(menuChild7);

        Menu.setVisible(true);
        /**
         * 菜单栏
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenu editMenu = new JMenu("编辑");
        JMenu viewMenu = new JMenu("视图");
        JMenu aboutMenu = new JMenu("关于");
        // 一级菜单添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(aboutMenu);
        /**
         * logo
         */
        Toolkit took = Toolkit.getDefaultToolkit(); //设置窗口图标 //要用绝对路径
        Image image = took.getImage("src/main/resources/Images/logo.png");
        setIconImage(image);
        /**
         * 中间布局
         */
        JPanel center=new JPanel();
        center.setVisible(true);
        CardLayout cardLayout=new CardLayout(10,10);
        center.setLayout(cardLayout);

        JPanel addTitlePanel=new AddTitlePanel();//添加毕业设计题目

        JPanel selfDesignJpanel  = new SelfDesignJpanel();//查看我的毕设题目

        JPanel admitStudentsJpanel=new AdmitStudentsJpanel();//录取报名学生

        JPanel alrealyAdmitJpanel=new AlrealyAdmitJpanel();//课设题目

        JPanel updatePanel=new UpdatePanel();//更新个人信息

        UpPwdPanel upPwdPanel=new UpPwdPanel();//修改密码

        menuChild1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(center,"addTitlePanel");
            }
        });
        menuChild2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((SelfDesignJpanel) selfDesignJpanel).refreshData();
                cardLayout.show(center,"selfDesignJpanel");
            }
        });
        menuChild3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ((AdmitStudentsJpanel) admitStudentsJpanel).listenerPageButton();//添加监听按钮
                ((AdmitStudentsJpanel) admitStudentsJpanel).refreshData();//刷新数据
                cardLayout.show(center,"admitStudentsJpanel");
            }
        });
        menuChild4.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((AlrealyAdmitJpanel) alrealyAdmitJpanel).refreshData();//刷新数据
                cardLayout.show(center,"alrealyAdmitJpanel");
            }
        });
        menuChild5.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(center,"updatePanel");
            }
        });
        menuChild6.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((UpPwdPanel) upPwdPanel).refreshData();//刷新数据
                cardLayout.show(center,"upPwdPanel");
            }
        });
        menuChild7.addActionListener(new LoginOut(login,this));

        center.add(new WelcomeJpanel(),"welcomeJpanel");
        center.add(selfDesignJpanel,"selfDesignJpanel");
        center.add(addTitlePanel,"addTitlePanel");
        center.add(admitStudentsJpanel,"admitStudentsJpanel");
        center.add(alrealyAdmitJpanel,"alrealyAdmitJpanel");
        center.add(updatePanel,"updatePanel");
        center.add(upPwdPanel,"upPwdPanel");

        this.add(center, BorderLayout.CENTER);  //中间
        this.add(menuBar, BorderLayout.NORTH);  //北边
        this.add(Menu, BorderLayout.WEST);   //西边

        //设置窗体属性
        this.setTitle("毕业设计选题系统");
        this.setSize(700, 500);
        this.setLocation(600, 300);
        this.setVisible(true);
        setResizable(false);//禁用最大化
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        TeacherMainView testBorderLayout = new TeacherMainView(null);

    }
}