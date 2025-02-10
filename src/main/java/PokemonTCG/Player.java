package PokemonTCG;

public class Player {

    String name;
    Deck deck;
    Deck discard;
    Hand hand;
    boolean botFlag;

    public Player(boolean flag){
        deck = new Deck();
        discard = new Deck();
        hand = new Hand();
        botFlag = flag;
    }

    public void drawCard(){
        hand.addCard(deck.draw());
    }

    // only ran on turn one
    public void fillHand(){}

    public void fillPrize(){}

    public void checkMulligan(){}

    public void discard(int slot){
        discard.add(hand.removeCard(slot));
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Deck getDiscard() {
        return discard;
    }

    public void setDiscard(Deck discard) {
        this.discard = discard;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
