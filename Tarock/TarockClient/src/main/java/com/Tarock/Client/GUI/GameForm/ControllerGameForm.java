package com.Tarock.Client.GUI.GameForm;

import com.Tarock.Common.Domain.Declaration;
import com.Tarock.Client.Main;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.CommunicationService;

import java.io.IOException;
import java.util.Objects;

public class ControllerGameForm {
    public void actionSubmitButtonDeclaration() {
        boolean popeAtFinish = GameForm.popeAtFinishCheckBox.isSelected();
        boolean pagatAtFinish = GameForm.pagatAtFinishCheckBox.isSelected();
        boolean allPopes = GameForm.allPopesCheckBox.isSelected();
        boolean trull = GameForm.trullCheckBox.isSelected();
        int numberOfTarocks = 0;
        String numberOfTarocksString = Objects.requireNonNull(GameForm.numberOfTarocksComboBox.getSelectedItem()).toString();
        if (!numberOfTarocksString.equals("No"))
            numberOfTarocks = Integer.parseInt(numberOfTarocksString);

        String message = "";
        if ((popeAtFinish) && (!Repository.hasChosenPope()))
            message += "You don't have the requested pope ! ";
        if ((pagatAtFinish) && (!Repository.hasPagat()))
            message += "You don't have the pagat ! ";
        if (Repository.getNumberOfTarocks() < numberOfTarocks)
            message += "You don't have " + numberOfTarocksString + " tarocks !";
        if (message.equals("")) {
            CommunicationService.declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
        } else {
            GameForm.popUpMessage(message);
        }
    }

    public void actionSubmitButtonPope() {
        int pope = -1;
        if (GameForm.heartRadio.isSelected())
            pope = 0;
        else if (GameForm.diamondRadio.isSelected())
            pope = 1;
        else if (GameForm.clubRadio.isSelected())
            pope = 2;
        else if (GameForm.spadeRadio.isSelected())
            pope = 3;
        if (pope != -1)
            Repository.chosenPope = pope;

        String message = "";
        if ((Repository.isRequestPlayer) && (pope == -1))
            message += "You didn't select a pope !";

        if (message.equals("")) {
            CommunicationService.pope = pope;
        } else {
            GameForm.popUpMessage(message);
        }
    }

    public void listenerSubmitButton() {
        GameForm.submitButton.addActionListener(e -> {
            if (GameForm.typeOfActivation.equals("Declaration")) {
                actionSubmitButtonDeclaration();
            } else if (GameForm.typeOfActivation.equals("Pope")) {
                actionSubmitButtonPope();
            }
        });
    }

    public void listenerThe1of2Button() {
        GameForm.the1of2Button.addActionListener(e -> {
            CommunicationService.playerMode = "1/2";
            GameForm.the1of2Button.setEnabled(false);
            GameForm.passButton.setEnabled(false);
            GameForm.cancelGameButton.setEnabled(false);
        });
    }

    public void listenerPassButton() {
        GameForm.passButton.addActionListener(e -> {
            CommunicationService.playerMode = "pass";
            GameForm.the1of2Button.setEnabled(false);
            GameForm.passButton.setEnabled(false);
            GameForm.cancelGameButton.setEnabled(false);
        });
    }

    public void listenerCancelGameButton() {
        GameForm.cancelGameButton.addActionListener(e -> {
            if (Repository.canCancelGame()) {
                CommunicationService.playerMode = "cancel";
                GameForm.the1of2Button.setEnabled(false);
                GameForm.passButton.setEnabled(false);
                GameForm.cancelGameButton.setEnabled(false);
            }
        });
    }

    public ControllerGameForm() {
        listenerSubmitButton();
        listenerThe1of2Button();
        listenerPassButton();
        listenerCancelGameButton();
    }

    public static void pressedCard(int cardPosition) throws IOException {
        if ((GameForm.canGiveCard) && (Repository.canGiveCard(cardPosition))) {
            GameForm.canGiveCard = false;
            CommunicationService.cardGiven = Repository.cards.get(cardPosition);
            Repository.giveCard(cardPosition);
            Main.gameForm.updateCards();
        }
    }
}
