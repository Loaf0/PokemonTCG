package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Log;
import PokemonTCG.Player;

public class Cynthia extends Trainer {

    public Cynthia() {
        super("Cynthia", "Shuffle your hand into your deck. Then, Draw 6 Cards");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        if (p.canDrawCards(7)){
            Log.message(p.getName() + " Played " + getName() + "!\n");

            boolean discardedSelf = false;

            for (int i = 0; i < p.getHand().getCards().size(); i++){
                if(!discardedSelf && p.getHand().getCards().get(i).getName().equals(getName())){ // check if card played was discarded
                    p.getDiscard().add(p.getHand().getCards().get(i));
                    discardedSelf = true;
                }
                else {
                    p.getDeck().add(p.getHand().getCards().get(i));
                }
            }
            p.getHand().getCards().clear();
            Log.message("Shuffling Hand into deck!\n");
            p.shuffle();
            Log.message("Drawing 6 Cards!\n");
            p.drawCards(6);
        }
        return false; // return statement it should delete card this card deletes itself if ran
    }

}
