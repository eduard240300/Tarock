package com.Tarock.Server.GUI;

import com.Tarock.Common.Service.DataManipulationService;

import javax.swing.*;
import java.awt.*;

public class StatusForm extends JFrame{
    public static JTextArea statusTextArea;
    public static JScrollPane statusScrollPane;

    public static void addToStatusTextArea(String line)
    {
        statusTextArea.append(DataManipulationService.eliminateNewLines(line) + '\n');
        statusScrollPane.getVerticalScrollBar().setValue(statusScrollPane.getVerticalScrollBar().getMaximum());
    }

    public StatusForm(){
        Font arialBig = new Font("Arial", Font.PLAIN, 30);
        Font arialDefault = new Font("Arial", Font.BOLD, 16);
        setLayout(null);

        JLabel statusLabel = new JLabel("Server log : ", SwingConstants.CENTER);
        statusLabel.setFont(arialBig);
        statusLabel.setBounds(40, 20, 820,40);
        add(statusLabel);

        statusTextArea = new JTextArea();
        statusTextArea.setFont(arialDefault);
        statusTextArea.setText("");
        statusTextArea.setLineWrap(true);
        statusScrollPane = new JScrollPane(statusTextArea);
        statusScrollPane.setBounds(40, 80, 820, 400);
        statusTextArea.setEditable(false);
        add(statusScrollPane);

        if (System.getProperty("os.name").equals("Linux")) {
            setSize(900, 540);
        }
        else if (System.getProperty("os.name").equals("Windows 10"))
            setSize(918,540);
        setTitle("Tarock Server");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        getContentPane().setBackground(new Color(78, 154, 6));
        addWindowListener(new MyWindowListener());
    }
}
