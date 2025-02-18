package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;

import java.util.ArrayList;

public class Voltorb extends Pokemon {

    public Voltorb() {
        super("Lightning", 90, "None", "Fighting", 1, "Sonic Boom", "Explosion");
        setName("Voltorb");
    }

    public void attack1(Pokemon target) {
        int attackDamage = 40;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Colorless"));

        if (!checkEnergyRequirements(requirements)) {
            return;
        }

        //this attack ignores resistance & weakness

        target.takeDamage(attackDamage);
    }

    public void attack2(Pokemon target) {
        int attackDamage = 120;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Colorless"));

        if (!checkEnergyRequirements(requirements)) {
            return;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        takeDamage(90); //deal 90 self dmg

        target.takeDamage(attackDamage);
    }

}
