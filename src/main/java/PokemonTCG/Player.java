package PokemonTCG;

/*
 * @description class containing all a players data containers
 * @author Tyler Snyder
 */

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Pokemon;

public class Player {

    private String name;
    private Bench bench;
    private Deck deck;
    private Deck discard;
    private Deck prize;
    private Hand hand;
    private boolean botFlag;
    private boolean lostFlag;

    public Player(boolean flag) {
        bench = new Bench();
        deck = new Deck();
        discard = new Deck();
        prize = new Deck();
        hand = new Hand();
        botFlag = flag;
        lostFlag = false;
    }

    public void drawCard() {
        if (!deck.getCards().isEmpty())
            hand.addCard(deck.draw());
        else
            lostFlag = true;
    }

    public void drawCards(int x) {
        for (int i = 0; i < x; i++)
            drawCard();
    }

    public boolean canDrawCard() {
        return !deck.getCards().isEmpty();
    }

    public boolean canDrawCards(int x) {
        return x <= deck.size();
    }

    public void collectPrize(){
        if (!deck.getCards().isEmpty())
            hand.addCard(prize.draw());
    }

    public void fillHand() {
        drawCards(7);
    }

    public void fillPrize() {
        for (int i = 0; i < 6; i++) {
            prize.add(deck.draw());
        }
        prize.multiShuffle(5);
    }

    public void shuffle(){
        deck.multiShuffle(5);
    }

    public boolean passMulliganCheck() {
        return hand.checkMulligan();
    }

    public void discard(int slot) {
        discard.add(hand.removeCard(slot));
    }

    public void showHand() {
        hand.showHand();
    }

    public boolean deckHasCard(String cardName){
        cardName = cardName.trim();
        for (Card c : deck.getCards()) {
            if (c.getName().trim().equalsIgnoreCase(cardName))
                return true;
        }
        return false;
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

    public Deck getPrize(){
        return prize;
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

    public boolean getLostFlag() {
        return lostFlag;
    }

    public void setLostFlag(boolean lostFlag) {
        this.lostFlag = lostFlag;
    }

    public Bench getBench() {
        return bench;
    }

    public void setBench(Bench bench) {
        this.bench = bench;
    }

    public void setPrize(Deck prize) {
        this.prize = prize;
    }

    public void showBench(){
        String activePkm = bench.getActiveCard() == null ? "No Active Pokemon" : bench.getActiveCard().toString();
        System.out.println("Active Pokemon : " + activePkm);
        System.out.println("Bench : " + bench.getCards().toString());
    }
}
