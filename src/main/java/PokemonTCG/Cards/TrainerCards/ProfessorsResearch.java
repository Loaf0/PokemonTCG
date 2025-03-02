package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Hand;
import PokemonTCG.Log;
import PokemonTCG.Player;

public class ProfessorsResearch extends Trainer {

    public ProfessorsResearch() {
        super("Professor's Research", "Discard your Hand and Draw 7 Cards");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(7)){
            Log.message(p.getName() + " Played " + getName() + "!\n");
            for (int i = 0; i < p.getHand().getCards().size(); i++){
                p.getDiscard().add(p.getHand().getCards().get(i));
            }
            p.getHand().getCards().clear();
            Log.message("Discarding Hand and Drawing 7 Cards\n");
            p.drawCards(7);
        }
        return false; // return statement is should delete card this card deletes it self if ran
    }

}
