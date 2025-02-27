package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Hand;
import PokemonTCG.Player;

public class ProfessorsResearch extends Trainer {

    public ProfessorsResearch() {
        super("Professor's Research", "Discard your Hand and Draw 7 Cards");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(7)){
            p.setHand(new Hand());
            p.drawCards(7);
            return true;
        }
        return false;
    }

}
