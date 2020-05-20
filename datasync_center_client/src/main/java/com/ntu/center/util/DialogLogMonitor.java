package com.ntu.center.util;

import javax.swing.*;

public class DialogLogMonitor extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextArea txtLogInfo;
    public static void main(String[] args) {
        try {
            //设置系统观感器
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            DialogLogMonitor dialog = new DialogLogMonitor();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * 日志信息变更监听处理（关键点）
     */
    private void init() {
        LogMonitor.addLogChangedListener(new LogChangedListener() {
            @Override
            public void EventActivated(LogChangedEvent me) {
                txtLogInfo.setText(LogMonitor.getLogs().toString());
                System.out.println("日志："+LogMonitor.getLogs().toString());
                txtLogInfo.setCaretPosition(txtLogInfo.getText().length());
                txtLogInfo.paintImmediately(txtLogInfo.getBounds());
            }
        });
    }
    public DialogLogMonitor() {
        setResizable(false);
        setTitle("\u540E\u53F0\u65E5\u5FD7\u76D1\u63A7\u5668");
        setBounds(100, 100, 439, 274);
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addContainerGap())
        );
        txtLogInfo = new JTextArea();
        txtLogInfo.setEditable(false);
        txtLogInfo.setLineWrap(true);
        scrollPane.setViewportView(txtLogInfo);
        getContentPane().setLayout(groupLayout);
        this.init();
    }
}
