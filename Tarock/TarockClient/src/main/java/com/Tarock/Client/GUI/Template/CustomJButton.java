package com.Tarock.Client.GUI.Template;

import javax.swing.*;
import java.awt.*;

public class CustomJButton extends JButton {
    public CustomJButton(String text, Font font) {
        setText(text);
        setFont(font);
        setBackground(new Color(231, 236, 240));
        setFocusPainted(false);
        setMargin(new Insets(5, 0, 5, 0));
    }
}
