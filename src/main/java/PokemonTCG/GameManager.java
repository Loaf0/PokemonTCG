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

public class GameManager {

    private boolean running;
    private int turnCount;
    private Player p1;
    private Player p2;

    public void run() {
        // check if user wants to run deck builder
        setupGame(true, true);
        setUpTurnOrder();
        startGameLoop();

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
        Log.message("Deciding what player goes first\n");
        if (flipCoin()){
            Player temp = p1;
            p1 = p2;
            p2 = temp;
            Log.message("Player 2 Goes first!\n");
        }
        else
            Log.message("Player 1 Goes first!\n");

    }

    public void startGameLoop(){
        while(running){
            turn(p1);
            if (checkWinner())
                break;
            turn(p2);
            if (checkWinner())
                break;
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

    public boolean checkWinner() {
        return !p1.getLostFlag() || !p2.getLostFlag();
    }

    public boolean flipCoin() {
        return Math.random() >= 0.5;
    }

    public boolean playCard(Card c, Player p) {
        if (c instanceof Pokemon || c instanceof Energy || c instanceof Trainer) {
            return c.playCard(c, p);
        }
        System.out.println(c.getName() + "ERROR : Object is not a card");
        return false;
    }

}
