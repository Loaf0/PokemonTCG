package PokemonTCG;
/*
 * Due Tuesday
 * determine odds of mulligan for each number of pokemon run 10,000 times
 * ex 1 pokemon 59 energies
 */


import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;

public class GameManager {

    int turnCount;
    Player p1;
    Player p2;

    public void run(){
        setupGame(true, true);

    }

    public void setupGame(boolean p1AI, boolean p2AI){
        p1 = new Player(p1AI);
        p2 = new Player(p2AI);
        turnCount = 0;
    }

    public void turn(){
        // draw and check if deck out

        // play 1 energy and any number of other cards if desired

        // if turn count 1 can not attack
    }

    public void checkWinner(){}

    public void flipCoin(){}

    public void playCard(Card c, Player p){
        if (c instanceof Pokemon || c instanceof Energy || c instanceof Trainer){
            c.playCard(c, this, p);
        }
        else {
            System.out.println(c.getName() + "ERROR : Object is not a card");
        }
    }

}
