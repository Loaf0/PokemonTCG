package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.GameManagerFactory;
import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.Scanner;

public class Switch extends Trainer {

    public Switch() {
        super("Switch", "Switch your Active with 1 of your benched Pokemon");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(2)){
            Scanner input = GameManagerFactory.getGameManager().getInput();
            Log.message(p.getName() + " Played " + getName() + "!\n");

            if (p.getBench().getActiveCard() == null){
                System.out.println("You need an active Pokemon first!");
                return false;
            }
            if (p.getBench().getCards().isEmpty()){
                System.out.println("You need a Pokemon in your bench!");
                return false;
            }

            int size = p.getBench().getCards().size();

            System.out.println("Which pokemon will be your new active pokemon?");

            for (int i = 0; i < size; i++) {
                System.out.println("  " + (i + 1) + ". " + p.getBench().getCards().get(i).getName());
            }

            boolean looping = true;
            int userInput = 0;
            while(looping){
                if (input.hasNextInt())
                    userInput = input.nextInt() - 1;
                if (userInput >= 0 && userInput < size)
                    looping = false;
                else{
                    input.next();
                    System.out.println("Please enter a valid option");
                }
            }
            Pokemon tempPoke = p.getBench().getActiveCard();
            p.getBench().setActiveCard(userInput);
            p.getBench().addToBench(tempPoke);
            Log.message(tempPoke.getName() + " retreated!\n");
            Log.message(p.getBench().getActiveCard().getName() + " came out to fight!\n");

            return true;
        }
        return false;
    }

}
