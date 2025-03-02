package PokemonTCG.Cards;

/*
 * @description superclass base for cards to extend
 * @author Tyler Snyder
 */

import PokemonTCG.Player;

public class Card {

    private String name;
    private String type;

    public boolean playCard(Card c, Player p) {
        return false;
    }

    public String getName() {
        return name == null ? "No Name" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
