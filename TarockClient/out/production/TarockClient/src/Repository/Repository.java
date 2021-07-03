package Repository;

import Domain.Round;
import Domain.User;
import Exception.RepositoryException;

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
        if ((cardID >= 1) && (cardID <= 22))
            return true;
        return false;
    }

    public static boolean isPope(int cardID)
    {
        if ((cardID == 30) || (cardID == 38) || (cardID == 46) || (cardID == 54))
            return true;
        return false;
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
        for(int i=0;i<cards.size();i++)
        {
            if (isTarock(cards.get(i)))
                result++;
        }
        return result;
    }

    public static int getNumberOfType(int type)
    {
        int result = 0;
        for(int i=0;i<cards.size();i++)
        {
            if (Round.isType(cards.get(i), type)) {
                result++;
            }
        }
        return result;
    }

    public static void initRepository()
    {
        rounds = new ArrayList<Round>();
        loggedIn = false;
        loggedUser = null;
        teams = new ArrayList<List<Integer>>();
        givenCards = new ArrayList<Integer>();
        cardsWon = new ArrayList<List<Integer>>();
        for(int i=0;i<2;i++) {
            teams.add(new ArrayList<Integer>());
            cardsWon.add(new ArrayList<Integer>());
        }
        players = new ArrayList<String>();
        cards = new ArrayList<Integer>();
        talon = new ArrayList<Integer>();
        for(int i=0;i<6;i++)
            talon.add(0);
        players.add("Player 1");
        players.add("Player 2");
        players.add("Player 3");
        players.add("Player 4");
    }

    public static void login(User newUser)
    {
        if (loggedIn == true)
            throw new RepositoryException("Already logged in !");
        else
        {
            loggedIn = true;
            loggedUser = newUser;
        }
    }

    public static void logout()
    {
        if (loggedIn == false)
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
            if (cards.size() == getNumberOfTarocks())
            {
                return true;
            }
            return false;
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

    public static boolean canGiveCard(int position)
    {
        int cardID = cards.get(position);
        if (!(rounds.size() == round+1)) {
            System.out.println("First card");
            return true;
        }
        int firstPlayer = rounds.get(rounds.size()-1).getFirstPlayer();
        System.out.println("FirstPlayer = " + firstPlayer);
        if (firstPlayer == chair)
        {
            System.out.println("FirstPlayer is chair");
            return true;
        }
        else
        {
            int firstCardID = rounds.get(rounds.size()-1).getCard(firstPlayer);
            if (Round.isTarock(firstCardID))
            {
                System.out.println("FirstCard is tarock");
                if (getNumberOfTarocks() == 0) {
                    System.out.println("You don't have any tarocks !");
                    return true;
                }
                else
                {
                    if (Round.isTarock(cardID)) {
                        System.out.println("You have given a tarock !");
                        return true;
                    }
                    else
                    {
                        System.out.println("You didn't give a tarock !");
                        return false;
                    }
                }
            }
            else
            {
                int type = Round.getCardType(firstCardID);
                System.out.println("Type is : " + type);
                if (getNumberOfType(type) == 0)
                {
                    System.out.println("You don't have any cards of type " + type);
                    if (getNumberOfTarocks() == 0) {
                        System.out.println("You have given any card !");
                        return true;
                    }
                    else
                    {
                        System.out.println("You have tarocks !");
                        if (Round.isTarock(cardID)) {
                            System.out.println("You have given a tarock !");
                            return true;
                        }
                        else
                        {
                            System.out.println("You didn't give a tarock !");
                            return false;
                        }
                    }
                }
                else
                {
                    System.out.println("You have cards of type " + type);
                    if (Round.isType(cardID, type)) {
                        System.out.println("You have given a card of type " + type);
                        return true;
                    }
                    else
                    {
                        System.out.println("You didn't give a card of type " + type);
                        return false;
                    }
                }
            }
        }
    }

    public static void giveCard(int position)
    {
        cards.remove(position);
    }
}
