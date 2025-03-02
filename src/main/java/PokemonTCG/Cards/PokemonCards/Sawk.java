package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Fighting;
import PokemonTCG.Cards.EnergyCards.Fire;
import PokemonTCG.Cards.EnergyCards.Water;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.GameManager;
import PokemonTCG.GameManagerFactory;
import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.ArrayList;

public class Sawk extends Pokemon {

    public Sawk() {
        super("Fighting", 90, "None", "Psychic", 1, "Sweep the Leg", "");
        setName("Sawk");
        setAttack1Desc("Deal 30 Damage. Flip a coin. If heads discard an energy from your opponents active Pokemon. Req [FGTG]");
    }

    public boolean attack1(Pokemon target) {
        int attackDamage = 30;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Fighting());

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

        if (Math.random() > .5 && !target.getEnergy().isEmpty()){
            GameManager gm = GameManagerFactory.getGameManager();
            Player opponent = this == gm.getP1().getBench().getActiveCard() ? gm.getP2() : gm.getP1();
            Energy tempCard = target.getEnergy().removeFirst();
            opponent.getDiscard().add(tempCard);
            Log.message("This attack removed " + tempCard.getName() + " from " + target.getName() + " \n");
        }
        return true;
    }
}
