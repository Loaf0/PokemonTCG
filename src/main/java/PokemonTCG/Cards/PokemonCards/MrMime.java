package PokemonTCG.Cards.PokemonCards;

import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;

import java.util.ArrayList;

public class MrMime extends Pokemon {

    public MrMime() {
        super("Psychic", 80, "None", "Psychic", 1, "Juggling", "");
        setName("Mr.Mime");
    }

    public void attack1(Pokemon target) {
        int attackDamage = 20;
        int dmgMultiplier = 0;

        for (int i = 0; i < 4; i++) // flip 4 coins multiply by dmg done on heads
            if (Math.random() > .5)
                dmgMultiplier++;

        attackDamage *= dmgMultiplier;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Psychic"));
        requirements.add(new Energy("Colorless"));

        if (!checkEnergyRequirements(requirements)) {
            return;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        target.takeDamage(attackDamage);
    }

}
