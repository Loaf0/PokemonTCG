package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.EnergyCards.Grass;
import PokemonTCG.Cards.EnergyCards.Water;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Log;

import java.util.ArrayList;

public class Bruxish extends Pokemon {

    public Bruxish() {
        super("Water", 110, "None", "Lightning", 1, "Bite", "Surf");
        setName("Bruxish");
        setAttack1Desc("Deal 20 Damage. Req [COLR]");
        setAttack2Desc("Deal 110 Damage. Req [WATR, WATR, COLR]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 20;

        ArrayList<Energy> requirements = new ArrayList<>();
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
        return true;
    }

    public boolean attack2(Pokemon target) {
        int attackDamage = 110;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Water());
        requirements.add(new Water());
        requirements.add(new Colorless());

        if (!checkEnergyRequirements(requirements)) {
            System.out.println(getName() + " is missing the required energy!");
            return false;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        Log.message(getName() + " used " + getAttack2Name() + " on " + target.getName() + " dealing " + attackDamage + " damage! \n");

        target.takeDamage(attackDamage);
        return true;
    }
}
