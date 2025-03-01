package PokemonTCG;

/*
 * assisting data container for Player to hold benched and active pokemon
 * @author Tyler Snyder
 */

import PokemonTCG.Cards.Pokemon;

import java.util.ArrayList;

public class Bench {

    private ArrayList<Pokemon> bench;
    Pokemon activeCard;

    public Bench(){
        bench = new ArrayList<>();
    }

    public void addToBench(Pokemon p){
        if (activeCard == null)
            activeCard = p;
        else
            bench.add(p);
    }

    public boolean hasOpenSlots(){
        return bench.size() <= 5;
    }

    public boolean setActiveCard(int input){
        if(0 <= input && input < bench.size() && activeCard == null)
            return false;
        activeCard = bench.remove(input);
        return true;
    }

    public ArrayList<Pokemon> getCards() {
        return bench;
    }

    public void setBench(ArrayList<Pokemon> bench) {
        this.bench = bench;
    }

    public Pokemon getActiveCard() {
        return activeCard;
    }
}
