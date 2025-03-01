package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.GameManager;
import PokemonTCG.GameManagerFactory;
import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.Scanner;

public class PokemonCatcher extends Trainer {

    public PokemonCatcher() {
        super("Pokemon Catcher", "Flip a coin. If heads, switch 1 of your opponent's Benched Pokemon with their Active Pokemon");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        GameManager gm = GameManagerFactory.getGameManager();
        Scanner input = gm.getInput();
        Player target = p == gm.getP1() ? gm.getP2() : gm.getP1();

        if (target.getBench().getActiveCard() == null){
            System.out.println("They need an active pokemon to use this card!");
            return false;
        }

        if (target.getBench().getCards().isEmpty()){
            System.out.println("They need a pokemon in bench to use this card!");
            return false;
        }

        Log.message(p.getName() + " Played " + getName() + "!\n");

        if (gm.flipCoin()){
            Log.message("Pokemon Catcher Failed!");
            return true;
        }

        int size = target.getBench().getCards().size();

        System.out.println("Which pokemon will you swap with " + target.getBench().getActiveCard().getName() + "?");

        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + target.getBench().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt() - 1;
            if (userInput >= 0 && userInput < size)
                looping = false;
            else{
                input.next();
                System.out.println("Please enter a valid option");
            }
        }

        Pokemon temp = target.getBench().getActiveCard();
        target.getBench().setActiveCard(userInput);
        target.getBench().addToBench(temp);

        return true;
    }

}
