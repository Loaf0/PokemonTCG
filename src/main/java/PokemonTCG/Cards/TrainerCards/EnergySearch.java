package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Log;
import PokemonTCG.Player;

public class EnergySearch extends Trainer {

    public EnergySearch() {
        super("Energy Search", "Draw the first energy card found in your deck. Shuffle Deck");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(1)){
            Log.message(p.getName() + " Played " + getName() + "!\n");
            for (int i = 0; i < p.getDeck().getCards().size(); i++) {
                if (p.getDeck().getCards().get(i) instanceof Energy){
                    Log.message("Drawing Energy and shuffling deck\n");
                    p.getHand().addCard(p.getDeck().getCards().remove(i));
                    p.shuffle();
                    return true;
                }
            }
        }
        return false;
    }

}
