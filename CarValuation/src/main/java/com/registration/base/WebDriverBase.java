package com.registration.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 *
 * Web driver base class is used to load props and launch chrome browser.
 */
public class WebDriverBase {

    public static WebDriver driver;
    public static Properties props;

    public WebDriverBase()  {
        props = new Properties();
        try {
            ClassLoader classLoader = WebDriverBase.class.getClassLoader();
            FileInputStream fis = new FileInputStream(new File(classLoader.getResource("config.properties").getFile()).getAbsolutePath());
            props.load(fis);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void initialBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(props.getProperty("websiteurl"));
    }


    public static void close(){
            driver.quit();
    }

}
