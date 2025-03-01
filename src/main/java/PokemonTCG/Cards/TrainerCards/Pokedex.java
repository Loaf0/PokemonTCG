package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Log;
import PokemonTCG.Player;

public class Pokedex extends Trainer {

    public Pokedex() {
        super("PokeDex", "Look at the top 3 cards of your deck.");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.getDeck().getCards().isEmpty()){
            System.out.println("No Cards Left in Deck!");
            return false;
        }

        Log.message(p.getName() + " Played " + getName() + "!\n");

        int size = Math.min(p.getDeck().size(), 3);

        Log.message("Top " + size + " Cards : ");
        for (int i = 0; i < size; i++) {
            Log.message(p.getDeck().getCards().get(i).getName() + " ");
        }
        Log.message("\n");

        return true;
    }

}
