package PokemonTCG;

import PokemonTCG.Cards.Card;

import java.util.ArrayList;

public class Deck {

    private String name; // only for premade decks
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }

    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
    }

    public Card draw() {
        if (!cards.isEmpty()) {
            Card c = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            return c;
        }
        return new Card();
    }

    public void add(Card c) {
        cards.add(c);
    }

    public Card reveal() {
        return cards.get(cards.size() - 1);
    }

    public void multiShuffle(int iterations) {
        for (int i = 0; i < iterations; i++)
            shuffle();
    }

    public void shuffle() {
        ArrayList<Card> split1 = new ArrayList<>();
        ArrayList<Card> split2 = new ArrayList<>();

        for (Card card : cards) {
            if (Math.random() > .5)
                split2.add(card);
            else
                split1.add(card);
        }

        if (Math.random() > .5) {
            split2.addAll(split1);
            cards = split2;
        } else {
            split1.addAll(split2);
            cards = split1;
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int size(){
        return cards.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
