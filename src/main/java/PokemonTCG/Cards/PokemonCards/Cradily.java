package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Grass;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Log;

import java.util.ArrayList;

public class Cradily extends Pokemon {

    public Cradily() {
        super("Grass", 120, "Water", "Fire", 2, "Spiral Drain", "");
        setName("Cradily");
        setAttack1Desc("Deal 60 Damage. Heal 20 damage from this Pokemon. Req [GRAS, COLR, COLR]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 60;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Grass());
        requirements.add(new Colorless());
        requirements.add(new Colorless());

        if (!checkEnergyRequirements(requirements)) {
            System.out.println(getName() + " is missing the required energy!");
            return false;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        Log.message(getName() + " used " + getAttack1Name() + " on " + target.getName() + " dealing " + attackDamage + " damage! \n");

        target.takeDamage(attackDamage);
        Log.message(getName() + " gained 20 HP!");
        takeDamage(-20);
        return true;
    }

}
