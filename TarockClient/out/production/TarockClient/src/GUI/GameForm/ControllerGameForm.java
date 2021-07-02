package GUI.GameForm;

import GUI.Main;
import Service.MainService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGameForm {

    public ControllerGameForm(){
        GameForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed Submit Button");
            }
        });
        GameForm.the1of2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainService.setPlayerMode("1/2");
                Main.gameForm.the1of2Button.setEnabled(false);
                Main.gameForm.passButton.setEnabled(false);
                Main.gameForm.cancelGameButton.setEnabled(false);
                System.out.println("Pressed 1 of 2 Button");
            }
        });
        GameForm.passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainService.setPlayerMode("pass");
                Main.gameForm.the1of2Button.setEnabled(false);
                Main.gameForm.passButton.setEnabled(false);
                Main.gameForm.cancelGameButton.setEnabled(false);
                System.out.println("Pressed Pass Button");
            }
        });
        GameForm.cancelGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainService.setPlayerMode("cancel");
                Main.gameForm.the1of2Button.setEnabled(false);
                Main.gameForm.passButton.setEnabled(false);
                Main.gameForm.cancelGameButton.setEnabled(false);
                System.out.println("Pressed Cancel Button");
            }
        });
    }

    public static void pressedCard(int CardPosition)
    {
        System.out.println("Pressed : " + CardPosition);
    }
}
