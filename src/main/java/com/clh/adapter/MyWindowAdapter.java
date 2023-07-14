package com.clh.adapter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author cailinhu
 * @Description TODO
 * @Date 2023/7/13 11:25
 * @Version 1.0
 */
@Deprecated
public class MyWindowAdapter extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        //退出程序
        System.exit(0);
    }
}
