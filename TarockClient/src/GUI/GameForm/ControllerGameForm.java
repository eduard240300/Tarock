package GUI.GameForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGameForm {
    public ControllerGameForm(){
        GameForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Submit Button");
                GameForm.the1of2Button.setEnabled(true);
                GameForm.passButton.setEnabled(true);
                GameForm.cancelGameButton.setEnabled(true);
            }
        });
        GameForm.the1of2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed 1 of 2 Button");
            }
        });
        GameForm.passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Pass Button");
            }
        });
        GameForm.cancelGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Cancel Game Button");
            }
        });
    }

    public static void pressedCard(int CardPosition)
    {
        System.out.println("Pressed : " + CardPosition);
    }
}
