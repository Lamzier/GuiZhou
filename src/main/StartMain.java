package main;

import main.java.business.FinalAll;
import main.java.mysql.Start;
import main.java.request.Email;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动官被类
 */
public class StartMain implements ServletContextListener {


    /**
     * 启动时
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (Start.Mysql_Start()) {//启动mysql
            System.out.println("MYSQl连接正常!");
        }else {
            System.out.println("MYSQl连接异常!");
            return;
        }
        if (Email.Start()){
            System.out.println("邮件服务启动正常!");
        }else{
            System.out.println("邮件服务启动异常!");
            return;
        }
        main.java.business.FinalAll.init();//初始化常量
        System.out.println("启动完成!!!");
    }

    /**
     * 关闭时
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        System.out.println("后端正在关闭！");
        Start.disConnection();//断开数据库连接
        System.out.println("后端已经关闭！");
    }
}
