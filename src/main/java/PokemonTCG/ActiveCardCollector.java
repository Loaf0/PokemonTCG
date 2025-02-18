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

    /**
     * This method searches through the Cards Package and grabs all the cards in the active card pool
     * This allows the ability to add cards dynamically and create "Card Expansions" easily in the future
     */
    private void collectAllActiveCards(){
        Reflections reflections = new Reflections("PokemonTCG.Cards"); // Create reflection object to search within package PokemonTCG.Cards
        Set<Class<? extends Card>> allClasses = reflections.getSubTypesOf(Card.class); // Fetch list of all classes that extend Card in package
        ArrayList<Class<? extends Card>> cardList = new ArrayList<>();

        for (Class<? extends Card> cardClass : allClasses) { // Loop through list of all classes while filtering superclasses of cards
            try {
                if (cardClass != Pokemon.class && cardClass != Energy.class && cardClass != Trainer.class)
                    cardList.add(cardClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activeCards = cardList; // set list of active cards to variable to be accessed later
    }

    public ArrayList<Class<? extends Card>> getActiveCards() {
        return this.activeCards;
    }
    
}
