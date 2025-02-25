package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Log;

import java.util.ArrayList;

public class Voltorb extends Pokemon {

    public Voltorb() {
        super("Lightning", 90, "None", "Fighting", 1, "Sonic Boom", "Explosion");
        setName("Voltorb");
        setAttack1Desc("Deal 40 Damage. This attack damage isn't affected by weakness or resistances. Req [COLR]");
        setAttack2Desc("Deal 120 Damage. This attack deals 90 damage to itself. Req [COLR]");
    }

    public void attack1(Pokemon target) {
        int attackDamage = 40;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Colorless"));

        if (!checkEnergyRequirements(requirements)) {
            return;
        }

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

        Log.message(getName() + " used " + getAttack1Name() + " dealing " + attackDamage + " damage to itself! \n");
        takeDamage(90);
        Log.message(getName() + " used " + getAttack1Name() + " on " + target.getName() + " dealing " + attackDamage + " damage! \n");
        target.takeDamage(attackDamage);
    }

}
