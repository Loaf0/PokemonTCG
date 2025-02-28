package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Player;

public class RareCandy extends Trainer {

    public RareCandy() {
        // Different card effect because evolutions are not implemented yet
        super("Rare Candy", "Active Pokemon Gains 30 Hp");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        Pokemon m = p.getBench().getActiveCard();
        if (m != null){
            m.setHp(m.getHp() + 30);
            return true;
        }
        return false;
    }

}
