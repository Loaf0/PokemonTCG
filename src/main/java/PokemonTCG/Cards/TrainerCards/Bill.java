package PokemonTCG.Cards.TrainerCards;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.GameManager;
import PokemonTCG.Player;

public class Bill extends Trainer {

    public Bill() {
        super("Bill", "Draw 2 Cards");
    }

    @Override
    public void playCard(Card c, GameManager gm, Player p) {
        p.drawCard();
    }

}
