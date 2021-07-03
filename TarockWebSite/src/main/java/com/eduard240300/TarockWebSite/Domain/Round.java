package com.eduard240300.TarockWebSite.Domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private int roundID;
    private int firstPlayer;
    private List<Integer> cards;
    private int playerThatWon = -1;

    public Round(int roundID, int firstPlayer)
    {
        this.roundID = roundID;
        this.firstPlayer = firstPlayer;
        cards = new ArrayList<Integer>();
        for(int i=0;i<4;i++)
            cards.add(0);
    }

    public void addCard(int player, int cardID)
    {
        cards.set(player, cardID);
    }

    public int getCard(int player)
    {
        return cards.get(player);
    }

    public boolean isTarock(int cardID)
    {
        if ((cardID >= 1) && (cardID <= 22))
            return true;
        return false;
    }

    public boolean isType(int cardID, int type)
    {
        if ((cardID >= 23+type*8) && (cardID <= 30+type*8))
            return true;
        return false;
    }

    public int getCardType(int cardID)
    {
        int type = cardID - 23;
        return type/8;
    }

    public void caseAreTarocks()
    {
        int numberOfTrullCards = 0;
        for(int i = 0;i < 4;i++)
        {
            if ((cards.get(i) == 1) || (cards.get(i) == 21) || (cards.get(i) == 22))
                numberOfTrullCards++;
        }
        if (numberOfTrullCards == 3) {
            for (int i = 0; i < 4; i++) {
                if (cards.get(i) == 1) {
                    playerThatWon = i;
                }
            }
        }
        else
        {
            int max = -1;
            for(int i=0;i<4;i++)
            {
                if (isTarock(cards.get(i)))
                {
                    if (max < cards.get(i))
                    {
                        max = cards.get(i);
                        playerThatWon = i;
                    }
                }
            }
        }
    }

    public void calculateWinner()
    {
        if (isTarock(cards.get(firstPlayer)))
        {
            caseAreTarocks();
        }
        else
        {
            int type = getCardType(cards.get(firstPlayer));
            for(int i=0;i<4;i++)
            {
                if (isTarock(cards.get(i))) {
                    caseAreTarocks();
                    return;
                }
            }
            int max = -1;
            for(int i=0;i<4;i++)
            {
                if (isType(cards.get(i), type))
                {
                    if (max < cards.get(i))
                    {
                        max = cards.get(i);
                        playerThatWon = i;
                    }
                }
            }
        }
    }

    public int getPlayerThatWon()
    {
        calculateWinner();
        return playerThatWon;
    }
}
