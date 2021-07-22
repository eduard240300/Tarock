package com.Tarock.Client.GUI.Template;

import com.Tarock.Client.GUI.GameForm.ControllerGameForm;
import com.Tarock.Client.GUI.TalonSelectionForm.ControllerTalonSelectionForm;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ClickableImage extends JImage implements MouseListener {
    public int CardPosition;
    public String GUI;

    public ClickableImage(int CardID, String GUI, int CardPosition) {
        super(CardID);
        super.addMouseListener(this);
        this.CardPosition = CardPosition;
        this.GUI = GUI;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
            if (GUI.equals("GameForm")) {
                try {
                    ControllerGameForm.pressedCard(CardPosition);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            else if (GUI.equals("TalonSelectionForm")) {
                try {
                    ControllerTalonSelectionForm.pressedCard(CardPosition);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) { }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }
    @Override
    public void mouseExited(MouseEvent mouseEvent) { }
}
