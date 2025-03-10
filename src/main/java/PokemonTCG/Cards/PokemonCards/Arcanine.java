package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.GameManager;
import PokemonTCG.GameManagerFactory;
import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Arcanine extends Pokemon {

    public Arcanine() {
        super("Fire", 130, "None", "Water", 3, "Crunch", "Fire Mane");
        setName("Arcanine");
        setAttack1Desc("Deal 30 Damage. Flip a coin. If heads discard an energy from your opponents active Pokemon. Req [FIRE, COLR]");
        setAttack2Desc("Deal 120 Damage. Req [FIRE, FIRE, COLR]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 20;
        int dmgMultiplier = 0;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fire());
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
            GameManager gm = GameManagerFactory.getGameManager();
            Player opponent = this == gm.getP1().getBench().getActiveCard() ? gm.getP2() : gm.getP1();
            Energy tempCard = target.getEnergy().removeFirst();
            opponent.getDiscard().add(tempCard);
            Log.message("This attack removed " + tempCard.getName() + " from " + target.getName() + " \n");
        }

        return true;
    }

    public boolean attack2(Pokemon target) {
        int attackDamage = 120;
        int dmgMultiplier = 0;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fire());
        requirements.add(new Fire());
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
}
