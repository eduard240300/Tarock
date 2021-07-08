package GUI.GameForm;

import Domain.Declaration;
import GUI.Main;
import Repository.Repository;
import Service.CommunicationService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControllerGameForm {

    public ControllerGameForm(){
        GameForm.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameForm.typeOfActivation == "Declaration")
                {
                    boolean popeAtFinish = Main.gameForm.popeAtFinishCheckBox.isSelected();
                    boolean pagatAtFinish = Main.gameForm.pagatAtFinishCheckBox.isSelected();
                    boolean allPopes = Main.gameForm.allPopesCheckBox.isSelected();
                    boolean trull = Main.gameForm.trullCheckBox.isSelected();
                    int numberOfTarocks = 0;
                    String numberOfTarocksString = Main.gameForm.numberOfTarocksComboBox.getSelectedItem().toString();
                    if (!numberOfTarocksString.equals("No"))
                        numberOfTarocks = Integer.parseInt(numberOfTarocksString);

                    String message = "";
                    if ((popeAtFinish) && (!Repository.hasChosenPope()))
                        message += "You don't have the requested pope ! ";
                    if ((pagatAtFinish) && (!Repository.hasPagat()))
                        message += "You don't have the pagat ! ";
                    if (Repository.getNumberOfTarocks() < numberOfTarocks)
                        message += "You don't have " + numberOfTarocksString + " tarocks !";
                    if (message.equals(""))
                    {
                        Declaration declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
                        CommunicationService.declaration = declaration;
                    }
                    else
                    {
                        GameForm.popUpMessage(message);
                    }
                }
                else if (GameForm.typeOfActivation == "Pope")
                {
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
                    if ((Repository.isRequestPlayer) && (pope == -1))
                        message += "You didn't select a pope !";

                    if (message.equals(""))
                    {
                        CommunicationService.pope = pope;
                    }
                    else
                    {
                        GameForm.popUpMessage(message);
                    }
                }
            }
        });
        GameForm.the1of2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommunicationService.playerMode = "1/2";
                Main.gameForm.the1of2Button.setEnabled(false);
                Main.gameForm.passButton.setEnabled(false);
                Main.gameForm.cancelGameButton.setEnabled(false);
            }
        });
        GameForm.passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommunicationService.playerMode = "pass";
                Main.gameForm.the1of2Button.setEnabled(false);
                Main.gameForm.passButton.setEnabled(false);
                Main.gameForm.cancelGameButton.setEnabled(false);
            }
        });
        GameForm.cancelGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Repository.canCancelGame())
                {
                    CommunicationService.playerMode = "cancel";
                    Main.gameForm.the1of2Button.setEnabled(false);
                    Main.gameForm.passButton.setEnabled(false);
                    Main.gameForm.cancelGameButton.setEnabled(false);
                }
            }
        });
    }

    public static void pressedCard(int cardPosition) throws IOException {
        if ((GameForm.canGiveCard) && (Repository.canGiveCard(cardPosition)))
        {
            GameForm.canGiveCard = false;
            CommunicationService.cardGiven = Repository.cards.get(cardPosition);
            Repository.giveCard(cardPosition);
            Main.gameForm.updateCards();
        }
    }
}
