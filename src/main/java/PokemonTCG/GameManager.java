package PokemonTCG;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.EnergyCards.Psychic;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.PokemonCards.Arcanine;
import PokemonTCG.Cards.PokemonCards.MrMime;
import PokemonTCG.Cards.PokemonCards.Voltorb;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Cards.TrainerCards.Bill;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {

    private boolean running;
    private int turnCount;
    private boolean canPlayEnergy;
    private Player p1;
    private Player p2;
    private Deck[] usableDecks;
    private Scanner input;

    public void run() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // check if user wants to run deck builder - GIVE STATISTICS ON MULLIGAN BASED ON THE DECK WHEN COMPLETED
        if (!mainMenu())
            return;
        setupGame(false, false);
        setUpTurnOrder();
        setupHands();
        startGameLoop();
        endingGame();
    }

    // replace with deck builder temp deck or choice of decks
    public void setupGame(boolean p1AI, boolean p2AI) {
        turnCount = 0;
        running = true;

        p1 = new Player(p1AI);
        p1.setName("Player 1");
        p2 = new Player(p2AI);
        p2.setName("Player 2");

        Deck testDeck = new Deck();

        for (int i = 0; i < 10; i++) {
            Card c = new MrMime();
            Card d = new Voltorb();
            testDeck.add(c);
            testDeck.add(d);
        }
        for (int i = 0; i < 20; i++) {
            Card c = new Bill();
            testDeck.add(c);
        }
        for (int i = 0; i < 10; i++) {
            Card c = new Psychic();
            Card d = new Colorless();
            testDeck.add(c);
            testDeck.add(d);
        }
        p1.setDeck(testDeck);
        p2.setDeck(testDeck);
    }


    public void setUpTurnOrder(){
        Log.message("Deciding what player goes first... \nHeads : " + p1.getName() + "  -  Tails : " + p2.getName() + "\n");
        if (flipCoin()){
            Player temp = p1;
            p1 = p2;
            p2 = temp;
        }
        Log.message(p1.getName() + " goes first!\n");
    }

    public void startGameLoop(){
        while(running){
            printGameState();
            turn(p1);
            if (checkWinner())
                return;

            printGameState();
            turn(p2);
            if (checkWinner())
                return;
        }
    }

    public void endingGame(){
        Log.saveLog();
        System.out.println("Someone wins return to menu or quit game? Restart menu not added yet");
        // on return to menu restart gameManager
        // on quit scan.close(); and end
        input.close();
    }

    /**
     * will execute all the per turn logic and choices made by the player
     *
     * @param p the player taking the turn.
     */
    public void turn(Player p) {
        // check if active pokemon is alive
        if (p.getBench().getActiveCard() != null && p.getBench().getActiveCard().getHp() == 0){
            Log.message(p.getBench().getActiveCard().getName() + " has fainted! \n");

            Player opponent = p == p1 ? p2 : p1;
            opponent.collectPrize();

            if(opponent.getPrize().getCards().isEmpty()){
                Log.message(opponent.getName() + " has beaten all of " + p.getName() + "'s Pokemon and won! \n");
                p.setLostFlag(true);
                return;
            }

            if(p.getBench().getCards().isEmpty()){
                Log.message(p.getName() + " has no active pokemon and loses the battle! \n");
                p.setLostFlag(true);
                return;
            }
            else {
                promptNewActive(p);
            }
        }

        if (p.getPrize().getCards().isEmpty()){
            p.setLostFlag(true);
            return;
        }
        canPlayEnergy = true;

        System.out.println();
        Log.message(p.getName() + "'s Turn : \n");
        Log.message(p.getName() + " draws 1 card \n");
        p.drawCard();

        boolean playingTurn = true;
        printTurnMenu();
        while(playingTurn){
            int option = input.nextInt();

            switch(option){
                case 1:
                    promptPlayCard(p);
                    printTurnMenu();
                    break;
                case 2:
                    if (turnCount == 0) {
                        System.out.println("You cannot attack on the first turn!");
                        break;
                    }
                    // if the attack is successful stop loop
                    playingTurn = !promptAttack(p);
                    break;
                case 3:
                    p.showHand();
                    break;
                case 4:
                    promptRetreat(p);
                    break;
                case 5:
                    promptInspect(p);
                    break;
                case 6:
                    printGameState();
                    break;
                case 7:
                    if(p.getBench().getActiveCard() == null){
                        System.out.println("You must have an active pokemon before ending your turn!");
                        break;
                    }
                    playingTurn = false;
                    break;
                default:
                    System.out.println("The option that was selected is not available please Input [1 - 6]");
                    break;
            }
        }

        Log.message("Ending Turn! \n\n");
        // play 1 energy and any number of other cards if desired

        turnCount++;
    }

    public void setupHands(){
        int p1MulliganCounter = 0;
        int p2MulliganCounter = 0;
        Player[] players = {p1, p2};

        for (int i = 0; i < players.length; i++){
            while (players[i].passMulliganCheck()){
                players[i].drawCards(7); // give the mulligan cards to the player
                if (players[i].passMulliganCheck()){
                    for (Card c : players[i].getHand().getCards()){
                        players[i].getDeck().add(c);
                    }
                    players[i].shuffle();
                    players[i].setHand(new Hand()); // reset players hand after moving back to deck
                }
                else
                    if(i == 0)
                        p1MulliganCounter++;
                    else
                        p2MulliganCounter++;
            }
        }

        if(p1MulliganCounter > p2MulliganCounter) // mulligans cancel out and are evaluated after hands are set up
            p2.drawCards(p1MulliganCounter - p2MulliganCounter);
        else if (p1MulliganCounter < p2MulliganCounter)
            p1.drawCards(p2MulliganCounter - p1MulliganCounter);

        p1.fillPrize();
        p2.fillPrize();
    }

    public boolean checkWinner() {
        return p1.getLostFlag() || p2.getLostFlag();
    }

    public boolean flipCoin() {
        boolean flip = Math.random() >= 0.5;
        if (flip)
            Log.message("Tails!\n");
        else
            Log.message("Heads!\n");
        return flip;
    }

    public boolean playCard(Card c, Player p) {
        if (c instanceof Pokemon || c instanceof Trainer)
            return c.playCard(c, p);
        if (c instanceof Energy)
            return attachEnergy((Energy) c, p);
        Log.message(c.getName() + "ERROR : Object is not a card");
        return false;
    }

    /**
     * allows choice of attaching energy cards to pokemon
     *
     * @param e the energy card to be attached.
     * @param p the player taking the turn.
     */
    private boolean attachEnergy(Energy e, Player p) {
        if (p.getBench().getActiveCard() == null){
            System.out.println("You must have a Pokemon in play!");
            return false;
        }
        if (!canPlayEnergy){
           System.out.println("You can only play 1 energy per turn!");
           return false;
        }

        int size = p.getBench().getCards().size();

        System.out.println("Which pokemon do you want to attach " + e.getName() + " to?");
        System.out.println("  0. " + p.getBench().getActiveCard().getName() + " (Active Pokemon)");
        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getBench().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt() - 1;
            if (userInput >= -1 && userInput < size)
                looping = false;
            else
                System.out.println("Please enter a valid option");
        }

        if(userInput == -1){
            p.getBench().getActiveCard().attachEnergy(e);
        }
        else {
            p.getBench().getCards().get(userInput).attachEnergy(e);
        }
        canPlayEnergy = false;
        return true;
    }

    /**
     * handles the choosing and playing of cards
     *
     * @param p the player taking the turn.
     */
    public void promptPlayCard(Player p){
        int size = p.getHand().getCards().size();

        System.out.println("Which card will you play?");
        System.out.println("  0. Return to menu");
        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getHand().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt() - 1;
            if (userInput >= -1 && userInput < size)
                looping = false;
            else
                System.out.println("Please enter a valid option");
        }

        if (userInput == -1){
            return;
        }

        Card c = p.getHand().getCards().get(userInput);

        if(playCard(c, p))
            p.getHand().getCards().remove(userInput);
    }

    /**
     * the attacking logic and player choice of move
     *
     * @param p the player taking the turn.
     */
    public boolean promptAttack(Player p){
        if(p.getBench().getActiveCard() == null){
            System.out.println(p.getName() + " doesn't have an active Pokemon");
            return false;
        }
        System.out.println("Choose Attack :");

        Pokemon c = p.getBench().getActiveCard();
        Player opponent = p == p1 ? p2 : p1;

        if(!c.getAttack1Name().isEmpty()){
            System.out.println("  1. " + c.getAttack1Name() + " - " + c.getAttack1Desc());
        }
        if(!c.getAttack2Name().isEmpty()){
            System.out.println("  2. " + c.getAttack2Name() + " - " + c.getAttack2Desc());
        }

        boolean looping = true;
        int userInput;
        while (looping){
            userInput = input.nextInt();
            if (userInput == 1 && !c.getAttack1Name().isEmpty()){
                if (!c.attack1(opponent.getBench().getActiveCard()))
                    return false;
                looping = false;
            }
            else if (userInput == 2 && !c.getAttack2Name().isEmpty()) {
                if (!c.attack2(opponent.getBench().getActiveCard()))
                    return false;
                looping = false;
            }
            else
                System.out.println("Please enter a valid option");
        }

        // check if pokemon was killed during own attack ex. self dmg voltorb dies first
        if (p.getBench().getActiveCard() != null && p.getBench().getActiveCard().getHp() == 0){
            Log.message(p.getBench().getActiveCard().getName() + " has fainted! \n");

            opponent.collectPrize();

            if(opponent.getPrize().getCards().isEmpty()){
                Log.message(opponent.getName() + " has beaten all of " + p.getName() + "'s Pokemon and won! \n");
                p.setLostFlag(true);
            }
            else if(p.getBench().getCards().isEmpty()){
                Log.message(p.getName() + " has no active pokemon and loses the battle! \n");
                p.setLostFlag(true);
            }
            else {
                promptNewActive(p);
            }
        }
        return true;
    }

    public void promptRetreat(Player p){
        if(p.getBench().getActiveCard() == null){
            System.out.println(p.getName() + " doesn't have an active Pokemon");
            return;
        }
        System.out.println("Choose Attack :");
    }

    public void promptInspect(Player p){
        // ask user where the card you want to inspect is
        // options bench hand active card
        // stats would be
    }

    /**
     * change active pokemon after retreat or faint
     *
     * @param p the player taking the turn.
     */
    public void promptNewActive(Player p){
        int size = p.getBench().getCards().size();

        System.out.println("Which pokemon will be your new active pokemon?");

        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getBench().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt() - 1;
            if (userInput >= 0 && userInput < size)
                looping = false;
            else
                System.out.println("Please enter a valid option");
        }

        p.getBench().setActiveCard(userInput);
    }

    /**
     * displays all relevant player information between turns
     */
    public void printGameState(){
        System.out.printf("\n%-50s | %-50s", p1.getName() + "'s Game State", p2.getName() + "'s Game State");

        String p1ActivePkm = p1.getBench().getActiveCard() == null ? "Active Pokemon : None" : "Active Pokemon : " + p1.getBench().getActiveCard().getName() + " - " + p1.getBench().getActiveCard().getHp() + "/" + p1.getBench().getActiveCard().getMaxHp() + " HP ";
        String p2ActivePkm = p2.getBench().getActiveCard() == null ? "Active Pokemon : None" : "Active Pokemon : " + p2.getBench().getActiveCard().getName() + " - " + p2.getBench().getActiveCard().getHp() + "/" + p2.getBench().getActiveCard().getMaxHp() + " HP ";

        System.out.printf("\n%-50s | %-50s", p1ActivePkm, p2ActivePkm);

        System.out.printf("\n%-50s | %-50s", getBenchRows(0, 3, p1), "Bench : " + getBenchRows(0, 3, p2));
        if(p1.getBench().getCards().size() > 3 || p2.getBench().getCards().size() > 3 )
            System.out.printf("\n%-50s | %-50s", getBenchRows(1, 3, p1), "Bench : " + getBenchRows(1, 3, p2));

        System.out.printf("\n%-50s | %-50s", "Hand Size : " + p1.getHand().getCards().size() + "  - Cards Left : " + p1.getDeck().getCards().size(), "Hand Size : " + p2.getHand().getCards().size() + "  - Cards Left : " + p2.getDeck().getCards().size());
        System.out.printf("\n%-50s | %-50s \n", "Prize Cards : " + p1.getPrize().getCards().size(), "Prize Cards : " + p2.getPrize().getCards().size());
    }

    /**
     * retrieve string of items from a players bench
     *
     * @param rowNumber the row number
     * @param valuesPerRow amount of items per row
     * @param p player whose bench is being retrieved
     */
    private String getBenchRows(int rowNumber, int valuesPerRow, Player p) {
        ArrayList<Pokemon> benchCards = p.getBench().getCards();
        int startIndex = rowNumber * valuesPerRow;

        StringBuilder row = new StringBuilder();

        for (int i = startIndex; i < startIndex + valuesPerRow && i < benchCards.size(); i++) {
            if (i > startIndex) row.append(", ");
            row.append(benchCards.get(i).getName());
        }

        if (row.isEmpty()) {
            return "Bench: None";
        } else {
            return "Bench: " + row;
        }
    }



    public void printTurnMenu(){
        System.out.println("What will you do : " +
                "\n  1. Play Card    4. Retreat           7. End turn " +
                "\n  2. Attack       5. Inspect Card" +
                "\n  3. Show Hand    6. Show Game State");
    }

    /**
     * Print the page of cards for the deck builder
     *
     * @param cardList the array of cards to be used
     * @param currPage page you wish to display
     * @return int the highest option on each page (used to limit play choices)
     */
    private int showCardPage(ArrayList<Class<? extends Card>> cardList, int currPage){
        int pages =  cardList.size() % 8 == 0 ? cardList.size() / 8 : (cardList.size() / 8) + 1;
        int options = currPage == pages ? cardList.size() % 8 : 8;
        int choice = 0;
        int max = (currPage * 8) + options;

        for (int i = currPage * 8; i < max; i++){
            try {
                System.out.println("  " + choice + ". " + cardList.get(i).getDeclaredConstructor().newInstance().getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            choice++;
        }
        if (currPage > 0)
            System.out.println("  8. Previous Page");
        if (currPage < pages)
            System.out.println("  9. Next Page");

        return options;
    }

    public void createDeck() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // uses reflection to get all card classes from pokemon energy and trainer packages
        ArrayList<Class<? extends Card>> cardList = new ActiveCardCollector().getActiveCards();
        Deck newDeck = new Deck();

        int maxPage = cardList.size() % 8 == 0 ? cardList.size() / 8 : (cardList.size() / 8) + 1;
        int curPage = 0;

        while (newDeck.size() < 60){
            System.out.println("Enter the Card # [0-7] or input [8-9] to change pages");
            int options = showCardPage(cardList, curPage);

            int userInputCard = input.nextInt();
            int userInputQuantity = 0;

            if (userInputCard > 0 && userInputCard <= options - 1){
                System.out.println("Enter the quantity [1-4]");
                userInputQuantity = input.nextInt();

                if (userInputQuantity <= 4 && userInputQuantity > 0){
                    if(userInputQuantity == 1){
                        newDeck.add(cardList.get(userInputCard + (8 * (curPage))).getDeclaredConstructor().newInstance());
                        System.out.println(newDeck.getCards().get(newDeck.size()-1).getName() + " was added");
                    }
                    else {
                        if(newDeck.size() + userInputQuantity <= 60){
                            for (int i = 0; i < userInputQuantity; i++) {
                                newDeck.add(cardList.get(userInputCard + (8 * (curPage))).getDeclaredConstructor().newInstance());
                            }
                            System.out.println(userInputQuantity + " " + newDeck.getCards().get(newDeck.size()-1).getName() + "s were added");
                        }
                        else
                            System.out.println("You are adding to many cards " + (newDeck.size() + userInputQuantity) + " / 60");
                    }
                }
                else
                    System.out.println("Invalid Quantity try [1-4]");
            }

            if(userInputCard == 8){
                if (curPage > 0)
                    curPage--;
                else
                    System.out.println("There are no previous pages");
            }

            if(userInputCard == 9){
                if (curPage < maxPage)
                    curPage++;
                else
                    System.out.println("There are no more page");
            }
            // make the user only be able to add 4 of each card that isnt energy
        }

        System.out.println("Name your deck : ");
        String name = "";
        while(name.isEmpty()){
            name = input.nextLine();
        }
        newDeck.setName(name);

        // when saving deck prompt user to name the deck
        // save the deck to list of usable decks

        // when its done add choosing a deck to the start up game method
    }

    public boolean mainMenu() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        input = new Scanner(System.in);

        System.out.println(
                " _____     _                                _____ _____ _____          \n" +
                "| ___ \\   | |                              |_   _/  __ \\  __ \\      \n" +
                "| |_/ /__ | | _____ _ __ ___   ___  _ __     | | | /  \\/ |  \\/       \n" +
                "|  __/ _ \\| |/ / _ \\ '_ ` _ \\ / _ \\| '_ \\    | | | |   | | __     \n" +
                "| | | (_) |   <  __/ | | | | | (_) | | | |   | | | \\__/\\ |_\\ \\     \n" +
                "\\_|  \\___/|_|\\_\\___|_| |_| |_|\\___/|_| |_|   \\_/  \\____/\\____/ \n");
        System.out.println(" 1. Play Game \n" +
                " 2. Build Deck \n" +
                " 3. Monte Carlo Simulations \n" +
                " 4. Exit Game \n");

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt();
            if (userInput > 0 && userInput <= 4)
                looping = false;
            else
                System.out.println("Please enter a valid option [1 - 4]");
        }

        switch (userInput){
            case 1:
                System.out.println("Starting Game...");
                return true;
            case 2:
                System.out.println("Starting Deck builder");
                createDeck();
                return false; // temp for testing
            case 3:
                System.out.println("Starting Monte Carlo Simulations");
                break;
            case 4:
                System.out.println("Exiting Game...");
                return false;
        }

        return true;
    }
}
