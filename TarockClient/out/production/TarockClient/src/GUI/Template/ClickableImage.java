package GUI.Template;

import GUI.GameForm.ControllerGameForm;
import GUI.TalonSelectionForm.ControllerTalonSelectionForm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ClickableImage extends JImage implements MouseListener {
    public int CardPosition;
    public String GUI;

    public ClickableImage(int CardID, String GUI, int CardPosition, int width, int height) throws IOException {
        super(CardID, width, height);
        super.addMouseListener(this);
        this.CardPosition = CardPosition;
        this.GUI = GUI;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
            if (GUI == "GameForm")
                ControllerGameForm.pressedCard(CardPosition);
            else if (GUI == "TalonSelectionForm")
                ControllerTalonSelectionForm.pressedCard(CardPosition);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
