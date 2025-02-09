import Cards.Card;

import java.util.ArrayList;

public class Deck {

    private String deckName; //ex Deck Hand Prize Discard
    private ArrayList<Card> cards;

    public Card draw(){
        Card c = cards.getLast();
        cards.removeLast();
        return c;
    }

    public Card reveal(){
        return cards.getLast();
    }

    public void multiShuffle(int iterations){
        for (int i = 0; i < iterations; i++)
            shuffle();
    }

    public void shuffle(){
        ArrayList<Card> split1 = new ArrayList<>();
        ArrayList<Card> split2 = new ArrayList<>();

        for (Card card : cards) {
            if (Math.random() > .5)
                split2.add(card);
            else
                split1.add(card);
        }

        if (Math.random() > .5){
            split2.addAll(split1);
            cards = split2;
        }
        else {
            split1.addAll(split2);
            cards = split1;
        }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

}
