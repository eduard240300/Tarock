package GUI.GameForm;

import Domain.Declaration;
import GUI.Main;
import Repository.Repository;
import Service.CommunicationService;
import Service.MainService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerGameForm {

    public ControllerGameForm(){
        GameForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                boolean popeAtFinish = Main.gameForm.popeAtFinishCheckBox.isSelected();
                boolean pagatAtFinish = Main.gameForm.pagatAtFinishCheckBox.isSelected();
                boolean allPopes = Main.gameForm.allPopesCheckBox.isSelected();
                boolean trull = Main.gameForm.trullCheckBox.isSelected();
                int numberOfTarocks = 0;
                String numberOfTarocksString = Main.gameForm.numberOfTarocksComboBox.getSelectedItem().toString();
                if (!numberOfTarocksString.equals("No"))
                    numberOfTarocks = Integer.parseInt(numberOfTarocksString);
                int pope = -1;
                if (Main.gameForm.heartRadio.isSelected())
                    pope = 0;
                else if (Main.gameForm.diamondRadio.isSelected())
                    pope = 1;
                else if (Main.gameForm.clubRadio.isSelected())
                    pope = 2;
                else if (Main.gameForm.spadeRadio.isSelected())
                    pope = 3;
                if (pope != -1)
                    Repository.chosenPope = pope;

                String message = "";
                if ((popeAtFinish) && (!Repository.hasChosenPope()))
                    message += "You don't have the requested pope ! ";
                if ((pagatAtFinish) && (!Repository.hasPagat()))
                    message += "You don't have the pagat ! ";
                if (Repository.getNumberOfTarocks() < numberOfTarocks)
                    message += "You don't have " + numberOfTarocksString + " tarocks !";
                if ((Repository.isRequestPlayer) && (pope == -1))
                    message += "You didn't select a pope !";
                if (message.equals(""))
                {
                    Declaration declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
                    if (Repository.isRequestPlayer)
                    {
                        declaration.setPope(pope);
                    }
                    CommunicationService.declaration = declaration;
                }
                else
                {
                    GameForm.popUpMessage(message);
                }

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
