package GUI.TalonSelectionForm;

import GUI.GameForm.GameForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerTalonSelectionForm {
    public ControllerTalonSelectionForm(){
        TalonSelectionForm.takeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed takeButton Button");
            }
        });
        TalonSelectionForm.nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed nextButton Button");
            }
        });
        TalonSelectionForm.giveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed giveButton Button");
            }
        });
    }

    public static void pressedCard(int CardPosition)
    {
        System.out.println("Pressed : " + CardPosition + " Talon");
    }
}