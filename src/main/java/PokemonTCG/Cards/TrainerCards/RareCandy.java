package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Player;

public class RareCandy extends Trainer {

    public RareCandy() {
        super("Rare Candy", "Draw 2 Cards");
    }

    @Override
    public boolean playCard(Card c, Player p) {
        System.out.println("rare candy used");
        return false;
    }

}
