package PokemonTCG;

import java.util.ArrayList;
import java.util.Set;
import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;
import org.reflections.Reflections;

public class ActiveCardCollector {
    ArrayList<Class<? extends Card>> activeCards;

    public ActiveCardCollector() {
        this.activeCards = new ArrayList<>();
        collectAllActiveCards();
    }

    private void collectAllActiveCards(){
        Reflections reflections = new Reflections("PokemonTCG.Cards");
        Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class);
        
        ArrayList<Class<? extends Card>> cardList = new ArrayList<>();
        for (Class<? extends Card> cardClass : allClasses) {
            try {
                if (cardClass != Pokemon.class && cardClass != Energy.class && cardClass != Trainer.class)
                    cardList.add(cardClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activeCards = cardList;
    }

    public ArrayList<Class<? extends Card>> getActiveCards() {
        return this.activeCards;
    }
    
}
