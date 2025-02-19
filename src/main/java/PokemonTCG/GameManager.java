package PokemonTCG;
/*
 * Due Tuesday
 * determine odds of mulligan for each number of pokemon run 10,000 times
 * ex 1 pokemon 59 energies
 */

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.PokemonCards.MrMime;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Cards.TrainerCards.Bill;

import java.io.IOException;

public class GameManager {

    private boolean running;
    private int turnCount;
    private Player p1;
    private Player p2;

    public void run() throws IOException {
        // check if user wants to run deck builder
        setupGame(true, true);
        setUpTurnOrder();
        setupHands();
        startGameLoop();

        Log.saveLog();
    }

    public void setupGame(boolean p1AI, boolean p2AI) {
        turnCount = 0;
        running = true;

        p1 = new Player(p1AI);
        p1.setName("Player 1");
        p2 = new Player(p2AI);
        p2.setName("Player 2");

        Deck testDeck = new Deck();

        for (int i = 0; i < 20; i++) {
            Card c = new MrMime();
            testDeck.add(c);
        }
        for (int i = 0; i < 20; i++) {
            Card c = new Bill();
            testDeck.add(c);
        }
        for (int i = 0; i < 20; i++) {
            Card c = new Energy("Psychic");
            testDeck.add(c);
        }
        p1.setDeck(testDeck);
        p2.setDeck(testDeck);
    }


    public void setUpTurnOrder(){
        Log.message("Deciding what player goes first... \nHeads : " + p1.getName() + " - Tails : " + p2.getName() + "\n");
        if (flipCoin()){
            Player temp = p1;
            p1 = p2;
            p2 = temp;
        }
        Log.message(p1.getName() + " goes first!\n");
    }

    public void startGameLoop(){
        while(running){
            turn(p1);
            if (checkWinner())
                return;

            turn(p2);
            if (checkWinner())
                return;

            turnCount++;
        }
    }

    public void turn(Player p) {
        if (p1.getPrize().getCards().isEmpty()){
            p1.setLostFlag(true);
            return;
        }
        // draw and check if deck out

        // play 1 energy and any number of other cards if desired

        // if turn count 1 can not attack
    }

    public void setupHands(){
        int mulliganCounter = 0;
        Player[] players = {p1, p2};

        for (Player p : players){
            while (p.checkMulligan()){
                p.drawCards(7 + mulliganCounter); // give the mulligan cards to the player
                if (p.checkMulligan()){
                    for (Card c : p.getHand().getCards()){
                        p.getDeck().add(c);
                    }
                    p.shuffle();
                    p.setHand(new Hand()); // reset players hand after moving back to deck
                }
            }
        }

    }

    public boolean checkWinner() {
        return !p1.getLostFlag() || !p2.getLostFlag();
    }

    public boolean flipCoin() {
        boolean flip = Math.random() >= 0.5;
        if (flip)
            Log.message("Tails!\n");
        else
            Log.message("Heads!\n");
        return flip;
    }

    public boolean playCard(Card c, Player p) {
        if (c instanceof Pokemon || c instanceof Energy || c instanceof Trainer) {
            return c.playCard(c, p);
        }
        Log.message(c.getName() + "ERROR : Object is not a card");
        return false;
    }

}
