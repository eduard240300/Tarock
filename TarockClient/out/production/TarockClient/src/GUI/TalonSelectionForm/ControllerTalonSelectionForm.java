package GUI.TalonSelectionForm;

import GUI.Main;
import Service.CommunicationService;
import Repository.Repository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControllerTalonSelectionForm {

    public ControllerTalonSelectionForm(){
        TalonSelectionForm.takeButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
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
            }
        });
        TalonSelectionForm.nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommunicationService.talonTakeDecision = "next";
                Repository.talonPart++;
                Repository.the1of2++;
                Repository.talonPart = Repository.talonPart % 3;
            }
        });
        TalonSelectionForm.giveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Repository.givenCardsCompleted)
                {
                    String message = "";
                    for(int i=0;i<2;i++)
                        message += " " + Repository.givenCards.get(i);
                    CommunicationService.talonGiveDecision = message;
                    TalonSelectionForm.giveButton.setEnabled(false);
                }
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