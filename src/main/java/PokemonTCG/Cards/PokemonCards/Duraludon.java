package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.EnergyCards.Metal;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Log;

import java.util.ArrayList;

public class Duraludon extends Pokemon {

    public Duraludon() {
        super("Metal", 130, "Grass", "Fire", 2, "Hammer In", "Raging Hammer");
        setName("Duraludon");
        setAttack1Desc("Deal 30 Damage. Req [METL]");
        setAttack2Desc("Deal 80 Damage. This attack deals additional damage for all damage taken. Req [METL, METL, COLR]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 30;

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
        int attackDamage = 80;

        if (getHp() < getMaxHp())
            attackDamage += getMaxHp() - getHp();

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Metal());
        requirements.add(new Metal());
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

        if(!target.getEnergy().isEmpty()){
            Log.message("This attack removed " + target.getEnergy().removeFirst().getName() + " from " + target.getName() + " \n");
        }

        return true;
    }
}
