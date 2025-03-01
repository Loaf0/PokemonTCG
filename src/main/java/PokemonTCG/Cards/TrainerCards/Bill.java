package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Log;
import PokemonTCG.Player;

public class Bill extends Trainer {

    public Bill() {
        super("Bill", "Draw 2 Cards");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(2)){
            Log.message(p.getName() + " Played " + getName() + "!\n");
            Log.message("Drawing 2 Cards\n");
            p.drawCards(2);
            return true;
        }
        return false;
    }

}
