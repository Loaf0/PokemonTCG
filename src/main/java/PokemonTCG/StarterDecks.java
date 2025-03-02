package PokemonTCG;

/*
 * @description this class creates basic decks for users to play with
 * @author Tyler Snyder
 */

import PokemonTCG.Cards.EnergyCards.*;
import PokemonTCG.Cards.PokemonCards.*;
import PokemonTCG.Cards.TrainerCards.*;

import java.util.ArrayList;

public class StarterDecks {

    /**
     * generate and return preset decks
     */
    public ArrayList<Deck> getStarterDecks(){
        ArrayList<Deck> decks = new ArrayList<>();
        decks.add(StarterDeckA());
        decks.add(StarterDeckB());
        decks.add(StarterDeckC());
        decks.add(StarterDeckD());
        decks.add(StarterDeckE());

        return decks;
    }

    private Deck StarterDeckA(){
        Deck d = new Deck();
        d.setName("Starter Deck");

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
            d.add(new PokemonCatcher());
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

    private Deck StarterDeckC(){
        Deck d = new Deck();
        d.setName("Sawk & Throh Deck");

        // add 4 of each card type
        for (int i = 0; i < 4; i++) {
            d.add(new Sawk());
            d.add(new Throh());
            d.add(new Duraludon());
            d.add(new Cynthia());
            d.add(new ProfessorsResearch());
            d.add(new Bill());
            d.add(new RareCandy());
            d.add(new PokemonCatcher());
            d.add(new EnergySearch());
            d.add(new Switch());
        }

        for (int i = 0; i < 10; i++) {
            d.add(new Fighting());
            d.add(new Metal());
        }

        while(d.size() < 60){
            d.add(new Colorless());
        }

        d.shuffle();
        return d;
    }

    private Deck StarterDeckD(){
        Deck d = new Deck();
        d.setName("Heatmor Deck");

        // add 4 of each card type
        for (int i = 0; i < 4; i++) {
            d.add(new Heatmor());
            d.add(new Arcanine());
            d.add(new MrMime());
            d.add(new Cynthia());
            d.add(new ProfessorsResearch());
            d.add(new Bill());
            d.add(new RareCandy());
            d.add(new PokemonCatcher());
            d.add(new EnergySearch());
            d.add(new Switch());
            d.add(new Psychic());
            d.add(new Psychic());
            d.add(new Fire());
            d.add(new Fire());
            d.add(new Fire());
        }


        while(d.size() < 60){
            d.add(new Colorless());
        }

        d.shuffle();
        return d;
    }

    private Deck StarterDeckE(){
        Deck d = new Deck();
        d.setName("The Mystery Deck");

        // add 4 of each card type
        for (int i = 0; i < 4; i++) {
            d.add(new Bruxish());
            d.add(new Cradily());
            d.add(new MrMime());
            d.add(new Bill());
            d.add(new RareCandy());
            d.add(new ProfessorsResearch());
            d.add(new Psychic());
            d.add(new Psychic());
            d.add(new Grass());
            d.add(new Grass());
            d.add(new Water());
            d.add(new Water());
        }
        while(d.size() < 60){
            d.add(new Colorless());
        }

        d.shuffle();
        return d;
    }


}
