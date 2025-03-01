package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fighting;
import PokemonTCG.Cards.EnergyCards.Psychic;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Log;

import java.util.ArrayList;

public class Throh extends Pokemon {

    public Throh() {
        super("Fighting", 100, "None", "Psychic", 3, "Freestyle Strike", "Shoulder Throw");
        setName("Throh");
        setAttack1Desc("Deal 30x Damage. Flip 2 coins. This attack does 30 damage times the number of heads. Req [FGTG, COLR]");
        setAttack1Desc("Deal 80 Damage. Does 80 damage minus 20 for each energy in the defending Pokemon's retreat cost. Req [FGTG, COLR, COLR]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 30;
        int dmgMultiplier = 0;

        for (int i = 0; i < 2; i++) // flip 2 coins multiply by dmg done on heads
            if (Math.random() > .5)
                dmgMultiplier++;

        attackDamage *= dmgMultiplier;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fighting());
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
        int attackDamage = 80 - (20 * target.getRetreatCost());

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fighting());
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

        Log.message(getName() + " used " + getAttack2Name() + " on " + target.getName() + " dealing " + attackDamage + " damage! \n");
        target.takeDamage(attackDamage);
        return true;
    }
}
