package com.clh.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @Author cailinhu
 * @Description TODO
 * @Date 2023/7/17 15:33
 * @Version 1.0
 */
public class PropertiesUtil {
    public static Properties properties;
    static {
        File cfgFile = new File("config/ocr_config.properties");
        try(FileInputStream fis = new FileInputStream(cfgFile);){
            properties = new Properties();
            properties.load(fis);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
