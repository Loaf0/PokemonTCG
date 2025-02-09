import Cards.Card;

import java.util.ArrayList;

public class Hand {

    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Card c){
        hand.add(c);
    }

    public Card playCard(int slot){
        return hand.remove(slot);
    }

    public void discard(int slot){
        hand.remove(slot);
    }

    public void showHand(){
        System.out.println("Hand :");
        for (Card c : hand){
            System.out.printf(" %s ", c.getName());
        }
    }
}
