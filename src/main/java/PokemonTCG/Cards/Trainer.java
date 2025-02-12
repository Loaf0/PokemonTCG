package PokemonTCG.Cards;

public class Trainer extends Card {

    String description;

    public Trainer(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
