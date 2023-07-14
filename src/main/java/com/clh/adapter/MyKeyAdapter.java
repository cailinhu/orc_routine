package com.clh.adapter;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @Author cailinhu
 * @Description TODO
 * @Date 2023/7/13 11:27
 * @Version 1.0
 */
@Deprecated
public class MyKeyAdapter extends KeyAdapter {
    // 创建一个标志变量，表示监听器是否已完成
    public static final boolean[] listenerFinished = {false};


    private Frame frame;

    public MyKeyAdapter(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
}
