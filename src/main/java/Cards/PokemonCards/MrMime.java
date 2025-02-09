package Cards.PokemonCards;

import Cards.Energy;
import Cards.Pokemon;

import java.util.ArrayList;

public class MrMime extends Pokemon {

    public MrMime() {
        super(80, "None", "Psychic", 1, "Juggling", "");
    }

    public void attack1(Pokemon target){
        int attackDamage = 20;
        int dmgMultiplier = 0;

        for (int i = 0; i < 4; i++) // flip 4 coins multiply by dmg done on heads
            if (Math.random() > .5)
                dmgMultiplier++;

        attackDamage *= dmgMultiplier;

        ArrayList<Energy> requirements = new ArrayList<>();
        requirements.add(new Energy("Psychic"));
        requirements.add(new Energy("Colorless"));

        if(!checkEnergy(requirements)){
            return;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        target.takeDamage(attackDamage);
    }

}
