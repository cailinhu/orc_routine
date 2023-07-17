package com.clh;

import com.clh.capture.CaptureTool;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.*;

public class GUIExample {
    JFrame frame ;
    public GUIExample() {
        // 设置 Swing 使用 UTF-8 编码
        System.setProperty("file.encoding", "UTF-8");
        // 创建 JFrame 实例
         frame = new JFrame("文字识别");

        // 设置窗口的宽度和高度
        frame.setSize(600, 400);

        // 设置窗口关闭时的操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建一个面板
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 创建一个标签
        final JLabel label = new JLabel("按下F4截图后识别");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.BLUE);

        // 创建一个文本区域
        final JTextArea textArea = new JTextArea();

        new GlobalKeyListenerExample(textArea);


        textArea.setFont(new Font(null,Font.PLAIN, 20));
        textArea.setLineWrap(true); // 自动换行
        textArea.setForeground(Color.DARK_GRAY);
        // 创建一个滚动面板，并将文本区域添加到其中
        JScrollPane scrollPane = new JScrollPane(textArea);

//        /**
//         * 截图监听
//         */
//        textArea.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                //获得键盘按下的键是哪一个，当前的码
//                int keycode = e.getKeyCode();
//                if (keycode == 115) {
//                    //开始截屏
//                    try {
//                        frame.setVisible(false);
//                        Thread.sleep(1000);
////                        JavaScreenCaptureTool javaScreenCaptureTool = new JavaScreenCaptureTool(label);
//                        frame.setVisible(true);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            }
//
//        });

        // 创建一个按钮
        JButton button = new JButton("开始截图");
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            StartCapture(textArea);
        });

        // 添加滚动面板、按钮和标签到面板中
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);
        panel.add(label, BorderLayout.NORTH);

        // 将面板添加到窗口中
        frame.getContentPane().add(panel);

        // 设置窗口居中显示
        frame.setLocationRelativeTo(null);

        // 设置窗口可见
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUIExample();
    }

     class GlobalKeyListenerExample   implements NativeKeyListener {

        private JTextArea textArea;

        public GlobalKeyListenerExample(JTextArea textArea ) {
            try {
                // 初始化全局键盘监听器
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                System.err.println("无法注册全局键盘监听器: " + ex.getMessage());
                System.exit(1);
            }
            GlobalScreen.addNativeKeyListener(this);
            this.textArea=textArea;
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {
            // 键盘按下事件
            if (e.getRawCode()==115) {
                StartCapture(textArea);
            }
        }
     }

    private void StartCapture(JTextArea textArea) {
        frame.setVisible(false);
        new CaptureTool(frame,textArea);
    }
}
