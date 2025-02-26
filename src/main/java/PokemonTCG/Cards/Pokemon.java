package PokemonTCG.Cards;

import PokemonTCG.Log;
import PokemonTCG.Player;

import java.util.ArrayList;

/*
Pokemon updates 2 and 3

add evolutions
tier 1 2 & 3 pokemon (unsure about 2)
add rare candy

find chances of rare candy falling into prize pile
draw a hand and make sure you don't mulligan Then check find the probability of bricking from [1-4] rare candy

third check up task
1-4 different pokemon cards
1-5 different trainer cards
as much energy as you choose otherwise

sample deck : 20 pokemon - 30 trainer - 10 energy

make the game playable against yourself or an ai (not needed to think about outcomes, but uses all options)
Document everything in comments / Java doc / Human instruction manual
+ List of all things that should be extra credit
 */

public class Pokemon extends Card {

    private int hp;
    private int maxHp;
    private String resistance;
    private String weakness;
    private String attack1Name;
    private String attack2Name;
    private String attack1Desc;
    private String attack2Desc;
    private int retreatCost;

    private ArrayList<Energy> energy;

    public Pokemon() {
        energy = new ArrayList<>();
    }

    public Pokemon(String type, int hp, String resistance, String weakness, int retreatCost, String attack1Name, String attack2Name) {
        setType(type);
        this.hp = hp;
        maxHp = hp;
        this.resistance = resistance;
        this.weakness = weakness;
        this.retreatCost = retreatCost;
        this.attack1Name = attack1Name;
        this.attack2Name = attack2Name;
        energy = new ArrayList<>();
    }

    public boolean playCard(Card c, Player p) {
        if (c instanceof Pokemon && p.getBench().hasOpenSlots()){
            p.getBench().addToBench((Pokemon) c);
            Log.message(p.getName() + " Played " + c.getName() + "\n");
            return true;
        }
        return false;
    }

    /**
     * temporary method used to create attack methods based on
     *
     * @param target pokemon who is target of attack
     */
    public boolean attack1(Pokemon target) {
        // weakness * 2 resist - 20 flat
        int attackDamage = 10;

        ArrayList<Energy> requirements = new ArrayList<>();

        if (!checkEnergyRequirements(requirements)) {
            return false;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        target.takeDamage(attackDamage);
        return true;
    }

    public boolean attack2(Pokemon target) {
        if (!attack2Name.isEmpty())
            return attack1(target);
        return false;
    }

    public boolean checkEnergyRequirements(ArrayList<Energy> requirements) {
        ArrayList<Energy> currEnergy = new ArrayList<>(energy);

        //indexing of arraylists start from the top to avoid race condition as things are removed
        for (int i = requirements.size() - 1; i >= 0; i--) {
            if (!requirements.get(i).getType().equals("Colorless")) {
                for (int j = currEnergy.size() - 1; j >= 0; j--) {
                    if (currEnergy.get(j).getType().equals(requirements.get(i).getType())) {
                        currEnergy.remove(j);
                        requirements.remove(i);
                    }
                }
            }
        }

        return currEnergy.size() >= requirements.size();
    }

    public boolean discardEnergy(String type) {
        for (int i = 0; i < energy.size(); i++) {
            if (energy.get(i).getType().equals(type)) {
                energy.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean canRetreat() {
        return retreatCost >= energy.size();
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0){
            hp = 0;
        }
    }

    public void attachEnergy(Energy e) {
        energy.add(e);
    }

    public ArrayList<Energy> getEnergy() {
        return energy;
    }

    public void setEnergy(ArrayList<Energy> attachedEnergy) {
        this.energy = attachedEnergy;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getAttack1Name() {
        return attack1Name;
    }

    public String getAttack2Name() {
        return attack2Name;
    }

    public void setAttack1Name(String attack1Name) {
        this.attack1Name = attack1Name;
    }

    public void setAttack2Name(String attack2Name) {
        this.attack2Name = attack2Name;
    }

    public int getRetreatCost() {
        return retreatCost;
    }

    public void setRetreatCost(int retreatCost) {
        this.retreatCost = retreatCost;
    }

    public String getAttack1Desc() {
        return attack1Desc;
    }

    public void setAttack1Desc(String attack1Desc) {
        this.attack1Desc = attack1Desc;
    }

    public String getAttack2Desc() {
        return attack2Desc;
    }

    public void setAttack2Desc(String attack2Desc) {
        this.attack2Desc = attack2Desc;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
