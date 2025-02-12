package PokemonTCG.Cards;

import java.util.ArrayList;

public class Pokemon extends Card {

    private int hp;
    private String resistance;
    private String weakness;
    private String attack1Name;
    private String attack2Name;
    private int retreatCost;

    private ArrayList<Energy> energy;

    public Pokemon() {
        energy = new ArrayList<Energy>();
    }

    public Pokemon(String type, int hp, String resistance, String weakness, int retreatCost, String attack1Name, String attack2Name) {
        setType(type);
        this.hp = hp;
        this.resistance = resistance;
        this.weakness = weakness;
        this.retreatCost = retreatCost;
        this.attack1Name = attack1Name;
        this.attack2Name = attack2Name;
        energy = new ArrayList<Energy>();
    }

    // Example attack method
    public void attack1(Pokemon target) {
        //weakness * 2 resist - 20 flat
        int attackDamage = 10;

        ArrayList<Energy> requirements = new ArrayList<>();

        if (!checkEnergy(requirements)) {
            return;
        }

        if (getType().equals(target.getWeakness()))
            attackDamage *= 2;
        else if (getType().equals(target.getResistance()))
            attackDamage -= 20;

        target.takeDamage(attackDamage);
    }

    // Template for attack2 only some pokemon have 1
    public void attack2(Pokemon target) {
        if (attack2Name.isEmpty())
            return;
        else
            attack1(target); //attack 2 logic
    }

    public boolean checkEnergy(ArrayList<Energy> requirements) {
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
}
