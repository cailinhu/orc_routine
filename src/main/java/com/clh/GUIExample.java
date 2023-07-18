package com.clh;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.clh.bean.TranslateResult;
import com.clh.capture.CaptureTool;
import com.clh.util.PropertiesUtil;
import com.clh.util.TranslateUtil;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.apache.log4j.Logger;

import javax.swing.*;
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
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font(null,Font.PLAIN, 20));
        textArea.setLineWrap(true); // 自动换行
        textArea.setForeground(Color.DARK_GRAY);
        //翻译的文本区域
        JTextArea translateText = new JTextArea();
        translateText.setLineWrap(true);
        translateText.setWrapStyleWord(true);
        translateText.setFont(new Font(null,Font.PLAIN, 20));
        translateText.setForeground(Color.DARK_GRAY);
        // 创建 JScrollPane，并将文本框添加到其中
        // 创建一个滚动面板，并将文本区域添加到其中
        JScrollPane  ocr_scrollPane = new JScrollPane(textArea);
        ocr_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollPane translate_scrollPane = new JScrollPane(translateText);
        translate_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // 创建 JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.add(ocr_scrollPane);
        splitPane.setOneTouchExpandable(true); // 允许一键展开或收起
        splitPane.setResizeWeight(0.5); // 设置初始大小比例
        new GlobalKeyListenerExample(textArea);

//        JScrollPane scrollPane = new JScrollPane(textArea);


        // 创建面板，使用 FlowLayout
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // 创建一个按钮
        JButton button = new JButton("开始截图");
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            StartCapture(textArea);
        });
        JButton button2= new JButton("开始翻译");
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.BLUE);
        button2.setFocusPainted(false);
        button2.addActionListener(e -> {
            String text = textArea.getText();
            if (StrUtil.isEmpty(text)) return;
            if (splitPane.getComponents().length==2)
            splitPane.add(translate_scrollPane);
            TranslateResult result = TranslateUtil.getResult(text);
            translateText.setText( CollUtil.join(result.getTranslation(),"\n"));
        });
        panelBtn.add(button);
        panelBtn.add(button2);

        // 添加滚动面板、按钮和标签到面板中
//        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
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
         Logger logger = Logger.getLogger(GlobalKeyListenerExample.class);
        private JTextArea textArea;

        public GlobalKeyListenerExample(JTextArea textArea ) {
            try {
                // 初始化全局键盘监听器
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException ex) {
                logger.error("无法注册全局键盘监听器: ",ex);
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
