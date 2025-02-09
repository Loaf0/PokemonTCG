import Cards.Card;

public class Field {
    Card[] field = new Card[6]; //Slot 0 is active pokemon 1-5 is bench


    public Card getActivePokemon(){
        return field[0];
    }

    public Card knockout(){
        Card c = field[0];
        field[0] = null;
        return c;
    }

    public void setActive(int slot){
        if (slot <= 0 || slot > field.length){
            System.out.println("ERROR : INVALID SLOT");
            return;
        }
        if (field[slot] == null){
            System.out.println("ERROR : Field Spot Empty");
        }
        Card temp = field[0];
        field[0] = field[slot];
        field[slot] = temp;
    }

    public Card getSlot(int slot){
        return field[slot];
    }
}
