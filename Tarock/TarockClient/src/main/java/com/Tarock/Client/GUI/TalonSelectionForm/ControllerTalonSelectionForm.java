package com.Tarock.Client.GUI.TalonSelectionForm;

import com.Tarock.Client.GUI.Main;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.CommunicationService;

import java.io.IOException;

public class ControllerTalonSelectionForm {

    public ControllerTalonSelectionForm(){
        TalonSelectionForm.takeButton.addActionListener(e -> {
            CommunicationService.talonTakeDecision = "take";
            Repository.addTalonPartToCards();
            try {
                Main.talonSelectionForm.updateCards();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            TalonSelectionForm.takeButton.setEnabled(false);
            TalonSelectionForm.nextButton.setEnabled(false);
            TalonSelectionForm.giveButton.setEnabled(true);
        });
        TalonSelectionForm.nextButton.addActionListener(e -> {
            CommunicationService.talonTakeDecision = "next";
            Repository.talonPart++;
            Repository.the1of2++;
            Repository.talonPart = Repository.talonPart % 3;
        });
        TalonSelectionForm.giveButton.addActionListener(e -> {
            if (Repository.givenCardsCompleted)
            {
                StringBuilder message = new StringBuilder();
                for(int i=0;i<2;i++)
                    message.append(" ").append(Repository.givenCards.get(i));
                CommunicationService.talonGiveDecision = message.toString();
                TalonSelectionForm.giveButton.setEnabled(false);
            }
        });
    }

    public static void pressedCard(int cardPosition) throws IOException {
        if (TalonSelectionForm.giveButton.isEnabled())
        {
            if (!Repository.givenCardsCompleted)
            {
                if ((Repository.cards.size() > cardPosition) && (Repository.canPutCardDown(cardPosition)))
                    Repository.addToGivenCards(cardPosition);
                Main.talonSelectionForm.updateCards();
                Main.talonSelectionForm.updateGivenCards();
            }
        }
    }
}