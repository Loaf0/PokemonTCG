import Cards.Energy;
import Cards.Pokemon;
import Cards.PokemonCards.MrMime;

import java.util.ArrayList;

public class StartGame {
    public static void main(String[] args){
        Pokemon anthony = new MrMime();

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Psychic"));
        requirements.add(new Energy("Colorless"));

        System.out.println("Can anthony attack? " + anthony.checkEnergy(requirements));

        anthony.attachEnergy(new Energy("Psychic"));
        anthony.attachEnergy(new Energy("Colorless"));

        System.out.println("Can anthony attack? " + anthony.checkEnergy(requirements));

        //new GameManager().run();
    }
}