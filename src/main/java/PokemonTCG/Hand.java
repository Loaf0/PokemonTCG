package PokemonTCG;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Pokemon;

import java.util.ArrayList;

public class Hand {

    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Card c) {
        hand.add(c);
    }

    public Card removeCard(int slot) {
        if (0 < slot && slot < hand.size())
            return hand.remove(slot);
        return null;
    }

    public Card removeCard(String name) {
        for (int i = 0; i < hand.size() - 1; i++)
            if (hand.get(i).getName().equals(name))
                return hand.remove(i);
        return null;
    }

    public boolean checkMulligan(){
        for(Card c : hand)
            if(c instanceof Pokemon)
                return true;
        return false;
    }

    public void showHand() {
        System.out.println("Hand :");
        for (Card c : hand)
            System.out.printf(" %s ", c.getName());
    }
}
