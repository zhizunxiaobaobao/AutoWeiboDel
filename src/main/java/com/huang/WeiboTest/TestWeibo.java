package com.huang.WeiboTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Scanner;

/**
 * @Auther: huang.zh
 * @Date: 2018/11/27 18:10
 * @Description:
 */
public class TestWeibo {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        String password = scanner.nextLine();
        System.setProperty("webdriver.chrome.driver", "D://Tools//chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver = login(userName,password,driver);
        driver = getWeiboPage(driver);
        delete(driver);
    }

    public static WebDriver login(String userName,String passwprd,WebDriver driver) throws Exception{
         //chrome
        driver.manage().window().maximize();
        driver.get("https://weibo.com/");
        Thread.sleep(8000);
        driver.findElement(By.id("loginname")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(passwprd);
        driver.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[6]/a")).click();
        Thread.sleep(5000);
        return driver;
    }

    public static WebDriver getWeiboPage(WebDriver driver) throws Exception{
        if (driver.findElement(By.xpath("//*[@id=\"v6_pl_rightmod_myinfo\"]/div/div/div[2]/ul/li[3]/a")) != null){
            driver.findElement(By.xpath("//*[@id=\"v6_pl_rightmod_myinfo\"]/div/div/div[2]/ul/li[3]/a")).click();
            Thread.sleep(3000);
        }
        return driver;
    }

    public static void delete(WebDriver driver) throws Exception{
        while (true) {
            try{
                if (driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[1]/div/a")) != null) {
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[1]/div/a")).click();
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[1]/div/div/ul/li[1]/a")).click();
                    Thread.sleep(1500);
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[1]/div/div[2]/div/p[2]/a")).click();
                    driver.navigate().refresh();
                    Thread.sleep(3000);
                }
            } catch (Exception e){
                if (driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[2]/div/a")) != null) {
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[2]/div/a")).click();
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[2]/div/div/ul/li[1]/a")).click();
                    Thread.sleep(1500);
                    driver.findElement(By.xpath("//*[@id=\"Pl_Official_MyProfileFeed__20\"]/div/div[2]/div[1]/div[2]/div/div[2]/div/p[2]/a")).click();
                    driver.navigate().refresh();
                    Thread.sleep(3000);
                }
            }

        }
    }

}
