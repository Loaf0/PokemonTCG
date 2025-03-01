package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Fighting;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.GameManager;
import PokemonTCG.GameManagerFactory;
import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.ArrayList;

public class Heatmor extends Pokemon {

    public Heatmor() {
        super("Fire", 110, "None", "Water", 1, "Energy Burner", "");
        setName("Heatmor");
        setAttack1Desc("Deal 30 Damage. This attack deals 30 more damage for each energy attached to your opponents Active Pokemon. Req [FIRE, FIRE]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 30 + (target.getEnergy().size() * 30);

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fire());
        requirements.add(new Fire());

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
}
