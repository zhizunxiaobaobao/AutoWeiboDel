package com.huang.WeiboTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static com.huang.WeiboTest.TestWeibo.delete;
import static com.huang.WeiboTest.TestWeibo.getWeiboPage;
import static com.huang.WeiboTest.TestWeibo.login;

public class SwingTest {

    private static int count=0;
    private static JButton bt1;//登陆按钮
    private static JButton bt2;//忘记密码按钮
    private static JLabel jl_1;//登录的版面
    private static JFrame jf_1;//登陆的框架
    private static JTextField jtext1;//用户名
    private static JPasswordField jtext2;//密码
    private static JLabel jl_admin;
    private static JLabel jl_password;


    public static void main(String[] args) {
/*
        JFrame jf = new JFrame();
        jf.setVisible(true);
        jf.setSize(500,300);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = jf.getContentPane();
        container.setBackground(Color.GRAY);
        JLabel label = new JLabel("请输入微博账号和密码");
        label.setHorizontalTextPosition(SwingConstants.TRAILING);
        container.add(label);*/
        try{
            init();
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "网络波动较大，程序内部异常", "错误信息", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void init(){
        Font font =new Font("黑体", Font.PLAIN, 20);//设置字体
        jf_1=new JFrame("微博清空程序--powered by huang.zh");
        jf_1.setSize(450, 400);
        //给登陆界面添加背景图片
        ImageIcon bgim = new ImageIcon("/ocean.jpg") ;//背景图案
        bgim.setImage(bgim.getImage().
                getScaledInstance(bgim.getIconWidth(),
                        bgim.getIconHeight(),
                        Image.SCALE_DEFAULT));
        jl_1=new JLabel();
        jl_1.setIcon(bgim);

        jl_admin=new JLabel("账号");
        jl_admin.setBounds(20, 50, 60, 50);
        jl_admin.setFont(font);

        jl_password=new JLabel("密码");
        jl_password.setBounds(20, 120, 60, 50);
        jl_password.setFont(font);

        bt1=new JButton("删除");         //更改成loginButton
        bt1.setBounds(90, 250, 100, 50);
        bt1.setFont(font);
        bt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jtext1.getText().length() != 0 && jtext2.getPassword().length != 0){
                    weiboDel(jtext1.getText(),new String(jtext2.getPassword()));
                } else {
                    JOptionPane.showMessageDialog(null, "账号或密码不能为空", "错误信息", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        bt2=new JButton("退出");
        bt2.setBounds(250, 250, 100, 50);
        bt2.setFont(font);
        bt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf_1.dispatchEvent(new WindowEvent(jf_1,WindowEvent.WINDOW_CLOSING) );
            }
        });

        //加入文本框
        jtext1=new JTextField("root");
        jtext1.setBounds(150, 50, 250, 50);
        jtext1.setFont(font);

        jtext2=new JPasswordField("123456");//密码输入框
        jtext2.setBounds(150, 120, 250, 50);
        jtext2.setFont(font);

        jl_1.add(jtext1);
        jl_1.add(jtext2);

        jl_1.add(jl_admin);
        jl_1.add(jl_password);
        jl_1.add(bt1);
        jl_1.add(bt2);

        jf_1.add(jl_1);
        jf_1.setVisible(true);
        jf_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf_1.setLocation(300,400);

    }

    public static void weiboDel(String userName,String password){
        ChromeOptions options = new ChromeOptions();
        options.setBinary("chrome/chrome.exe");
        //测试目录
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        //maven编译目录
        System.setProperty("webdriver.chrome.driver", "classes/chromedriver.exe");
        WebDriver driver = null;
        try{
            driver = new ChromeDriver(options);
        }catch (Exception e){
            options.setBinary("D:\\chrome\\chrome.exe");
            driver = new ChromeDriver(options);
        }
        try {
            driver = login(userName,password,driver);
            driver = getWeiboPage(driver);
            delete(driver);
        } catch (Exception e){
            e.printStackTrace();
            options.setBinary("D\\chrome\\chrome.exe");
        }
    }

}
