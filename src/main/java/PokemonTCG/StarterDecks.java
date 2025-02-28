package PokemonTCG;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.EnergyCards.Lightning;
import PokemonTCG.Cards.EnergyCards.Psychic;
import PokemonTCG.Cards.PokemonCards.Arcanine;
import PokemonTCG.Cards.PokemonCards.MrMime;
import PokemonTCG.Cards.PokemonCards.Voltorb;
import PokemonTCG.Cards.TrainerCards.Bill;
import PokemonTCG.Cards.TrainerCards.ProfessorsResearch;
import PokemonTCG.Cards.TrainerCards.RareCandy;

import java.util.ArrayList;

public class StarterDecks {

    /**
     * generate and return preset decks
     */
    public ArrayList<Deck> getStarterDecks(){
        ArrayList<Deck> decks = new ArrayList<>();
        decks.add(StarterDeckA());
        decks.add(StarterDeckB());

        return decks;
    }

    private Deck StarterDeckA(){
        Deck d = new Deck();
        d.setName("Starter Deck A");

        // add 4 of each card type
        for (int i = 0; i < 4; i++) {
            d.add(new Arcanine());
            d.add(new MrMime());
            d.add(new Voltorb());
            d.add(new Bill());
            d.add(new ProfessorsResearch());
            d.add(new RareCandy());
            d.add(new Psychic());
            d.add(new Psychic());
            d.add(new Lightning());
            d.add(new Lightning());
            d.add(new Fire());
            d.add(new Fire());
        }
        while(d.size() < 60){
            d.add(new Colorless());
        }

        d.shuffle();
        return d;
    }

    private Deck StarterDeckB(){
        Deck d = new Deck();
        d.setName("Voltorb Candy Deck");

        // add 4 of each card type
        for (int i = 0; i < 4; i++) {
            d.add(new Voltorb());
            d.add(new RareCandy());
            d.add(new Bill());
            d.add(new ProfessorsResearch());
            d.add(new Lightning());
            d.add(new Lightning());
            d.add(new Lightning());
            d.add(new Lightning());
        }
        while(d.size() < 60){
            d.add(new Colorless());
        }

        d.shuffle();
        return d;
    }

}
