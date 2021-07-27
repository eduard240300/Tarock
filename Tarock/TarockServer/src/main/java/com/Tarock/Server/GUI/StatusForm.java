package com.Tarock.Server.GUI;

import com.Tarock.Common.Service.DataManipulationService;

import javax.swing.*;
import java.awt.*;

public class StatusForm extends JFrame {
    public static Font arialBig = new Font("Arial", Font.PLAIN, 30);
    public static Font arialDefault = new Font("Arial", Font.BOLD, 16);
    public static int sizeX = 900;
    public static int sizeY = 540;
    public static JLabel statusLabel;
    public static JTextArea statusTextArea;
    public static JScrollPane statusScrollPane;

    public static void addToStatusTextArea(String line) {
        statusTextArea.append(DataManipulationService.eliminateNewLines(line) + '\n');
        statusScrollPane.getVerticalScrollBar().setValue(statusScrollPane.getVerticalScrollBar().getMaximum());
    }

    public void initStatusLabel() {
        statusLabel = new JLabel("Server log : ", SwingConstants.CENTER);
        statusLabel.setFont(arialBig);
        statusLabel.setBounds(40, 20, 820, 40);
        add(statusLabel);
    }

    public void initStatusTextArea() {
        statusTextArea = new JTextArea();
        statusTextArea.setFont(arialDefault);
        statusTextArea.setText("");
        statusTextArea.setLineWrap(true);
        statusScrollPane = new JScrollPane(statusTextArea);
        statusScrollPane.setBounds(40, 80, 820, 400);
        statusTextArea.setEditable(false);
        add(statusScrollPane);
    }

    public StatusForm() {
        setLayout(null);

        initStatusLabel();
        initStatusTextArea();

        if (System.getProperty("os.name").equals("Windows 10"))
            sizeX += 18;
        setSize(sizeX, sizeY);
        setTitle("Tarock Server");

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        getContentPane().setBackground(DataManipulationService.backgroundColor);
        addWindowListener(new MyWindowListener());
    }
}