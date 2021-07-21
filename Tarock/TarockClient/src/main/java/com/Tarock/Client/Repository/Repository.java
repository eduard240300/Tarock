package com.Tarock.Client.Repository;

import com.Tarock.Client.Domain.Round;
import com.Tarock.Client.Domain.User;
import com.Tarock.Client.Exception.RepositoryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;
    public static int sessionID;
    public static List<String> players;
    public static List<Integer> cards;
    public static List<List<Integer>> cardsWon;
    public static List<Integer> talon;
    public static List<Integer> givenCards;
    public static boolean givenCardsCompleted = false;
    public static int chair;
    public static int playerRequest = 0;
    public static int the1of2 = 0;
    public static int talonPart = -1;
    public static int chosenPope = -1;
    public static List<Round> rounds;
    public static int round = -1;
    public static boolean isRequestPlayer = false;
    public static List<List<Integer>> teams;

    public static boolean isTarock(int cardID)
    {
        return (cardID >= 1) && (cardID <= 22);
    }

    public static boolean isPope(int cardID)
    {
        return (cardID == 30) || (cardID == 38) || (cardID == 46) || (cardID == 54);
    }

    public static boolean hasChosenPope()
    {
        for(int i=0;i<12;i++)
        {
            if (cards.get(i) == 30+chosenPope*8)
                return true;
        }
        return false;
    }

    public static boolean hasPagat()
    {
        for(int i=0;i<12;i++)
        {
            if (cards.get(i) == 1)
                return true;
        }
        return false;
    }

    public static int getNumberOfTarocks()
    {
        int result = 0;
        for (Integer card : cards) {
            if (isTarock(card))
                result++;
        }
        return result;
    }
    public static int getNumberOfSpecialCards()
    {
        int result = 0;
        for (Integer card : cards) {
            if (isTarock(card))
                result++;
            else if ((card == 30) || (card == 38) || (card == 46) || (card == 54))
                result++;
        }
        return result;
    }

    public static int sizeWithout0(List<Integer> list)
    {
        int size = 0;
        for (Integer integer : list) {
            if (integer != 0)
                size++;
        }
        return size;
    }

    public static int getNumberOfType(int type)
    {
        int result = 0;
        for (Integer card : cards) {
            if (Round.isType(card, type)) {
                result++;
            }
        }
        return result;
    }

    public static void initRepository()
    {
        loggedIn = false;
        loggedUser = null;
        players = new ArrayList<>();
        players.add("Player 1");
        players.add("Player 2");
        players.add("Player 3");
        players.add("Player 4");
        resetRepository();
        resetCardsWon();
    }

    public static void resetRepository()
    {
        givenCardsCompleted = false;
        rounds = new ArrayList<>();
        teams = new ArrayList<>();
        givenCards = new ArrayList<>();
        for(int i=0;i<2;i++) {
            teams.add(new ArrayList<>());
        }
        cards = new ArrayList<>();
        talon = new ArrayList<>();
        for(int i=0;i<6;i++)
            talon.add(0);
        playerRequest = 0;
        the1of2 = 0;
        talonPart = -1;
        chosenPope = -1;
        round = -1;
        isRequestPlayer = false;
    }

    public static void resetCardsWon(){
        cardsWon = new ArrayList<>();
        for(int i=0;i<2;i++) {
            cardsWon.add(new ArrayList<>());
        }
    }

    public static void login(User newUser)
    {
        if (loggedIn)
            throw new RepositoryException("Already logged in !");
        else
        {
            loggedIn = true;
            loggedUser = newUser;
        }
    }

    public static void logout()
    {
        if (!loggedIn)
            throw new RepositoryException("Not logged in !");
        else
        {
            loggedIn = false;
            loggedUser = null;
        }
    }

    public static boolean canPutCardDown(int cardPosition) {
        if ((cards.get(cardPosition) == 1) || (cards.get(cardPosition) == 21) ||
                (cards.get(cardPosition) == 22))
            return false;
        if (isPope(cards.get(cardPosition)))
        {
            return false;
        }
        else if (isTarock(cards.get(cardPosition)))
        {
            return sizeWithout0(cards) == getNumberOfSpecialCards();
        }
        return true;
    }

    public static void addToGivenCards(int cardPosition) {
        int cardID = cards.get(cardPosition);
        cards.remove(cardPosition);
        cards.add(0);
        givenCards.add(cardID);
        if (givenCards.size() == 2)
            givenCardsCompleted = true;
    }

    public static void addTalonPartToCards() {
        for(int i=0;i<2;i++)
        {
            cards.add(talon.get(talonPart*2+i));
        }
        Collections.sort(cards);
    }

    public static boolean canCancelGame()
    {
        int sumTarocks = 0;
        for (Integer card : cards) {
            if ((card == 30) || (card == 38) || (card == 46) || (card == 54))
                return false;
            else if (isTarock(card)) {
                sumTarocks += card;
            }
        }
        return sumTarocks < 11;
    }

    public static boolean canGiveCard(int position)
    {
        int cardID = cards.get(position);
        if (!(rounds.size() == round+1)) {
            return true;
        }
        int firstPlayer = rounds.get(rounds.size()-1).getFirstPlayer();
        if (firstPlayer == chair)
        {
            return true;
        }
        else
        {
            int firstCardID = rounds.get(rounds.size()-1).getCard(firstPlayer);
            if (Round.isTarock(firstCardID))
            {
                if (getNumberOfTarocks() == 0) {
                    return true;
                }
                else
                {
                    return Round.isTarock(cardID);
                }
            }
            else
            {
                int type = Round.getCardType(firstCardID);
                if (getNumberOfType(type) == 0)
                {
                    if (getNumberOfTarocks() == 0) {
                        return true;
                    }
                    else
                    {
                        return Round.isTarock(cardID);
                    }
                }
                else
                {
                    return Round.isType(cardID, type);
                }
            }
        }
    }

    public static void giveCard(int position)
    {
        cards.remove(position);
    }
}
