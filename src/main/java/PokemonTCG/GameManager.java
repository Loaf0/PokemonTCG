package PokemonTCG;

/*
 * @description The class that handles the majority of game logic and turn structure
 * @author Tyler Snyder
 */

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.Trainer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameManager {

    private boolean running;
    private int turnCount;
    private boolean canPlayEnergy;
    private Player p1;
    private Player p2;
    private ArrayList<Deck> usableDecks;
    private Scanner input;

    /**
     * Start the Pokemon TCG Simulator
     */
    public void run() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        if (!mainMenu())
            return;
        setupGame(false, false);
        setUpTurnOrder();
        setupHands();
        startGameLoop();
        endingGame();
    }

    /**
     * set up the players and decks
     *
     * @param p1AI if the associated player is AI
     * @param p2AI if the associated player is AI
     */
    public void setupGame(boolean p1AI, boolean p2AI) {
        // Verify that it isn't overwriting deck list from deck builder
        if (usableDecks == null){
            usableDecks = new ArrayList<>();
        }

        // Populate Starter Decks
        usableDecks.addAll(new StarterDecks().getStarterDecks());

        turnCount = 0;
        running = true;

        p1 = new Player(p1AI);
        p1.setName("Player 1");
        p2 = new Player(p2AI);
        p2.setName("Player 2");

        initializePlayer(p1);
        initializePlayer(p2);
    }

    /**
     * Helper method for setup game that initializes the players name and deck
     * @param p Player to be initialized
     */
    private void initializePlayer(Player p){
        System.out.println("Enter " + p.getName() +  "'s Name : ");
        String name = "";

        while(name.isEmpty()){
            name = input.nextLine();
        }
        p.setName(name.trim());

        System.out.println("Pick a Deck :");
        for (int i = 0; i < usableDecks.size(); i++) {
            System.out.println("  " + i + ". " + usableDecks.get(i).getName());
        }

        int userInput = -1;
        while (userInput >= usableDecks.size() || userInput < 0) {
            if(input.hasNextInt())
                userInput = input.nextInt();
            else{
                userInput = -1;
                input.next();
            }
            if (userInput >= usableDecks.size() || userInput < 0){
                System.out.println("Please enter a number [0-" + (usableDecks.size()-1) + "]");
            }
        }
        p.setDeck(usableDecks.remove(userInput));
    }

    /**
     * Randomize what player moves first
     */
    public void setUpTurnOrder(){
        Log.message("Deciding what player goes first... \nHeads : " + p1.getName() + "  -  Tails : " + p2.getName() + "\n");
        if (flipCoin()){
            Player temp = p1;
            p1 = p2;
            p2 = temp;
        }
        Log.message(p1.getName() + " goes first!\n");
    }

    /**
     * run looping turn structure
     */
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

    /**
     * Clean up game
     */
    public void endingGame(){
        System.out.println("Saving Battle Log...");
        Log.saveLog();

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

            //remove all energy and add to discard when fainting
            for (Card c : p.getBench().getActiveCard().getEnergy())
                p.getDiscard().add(c);
            p.getBench().getActiveCard().setEnergy(new ArrayList<Energy>());
            p.getDiscard().add(p.getBench().getActiveCard());

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

        // check if the player has lost
        if (p.getPrize().getCards().isEmpty()){
            p.setLostFlag(true);
            return;
        }
        canPlayEnergy = true;

        System.out.println();
        Log.message(p.getName() + "'s Turn : \n");
        Log.message(p.getName() + " draws 1 card \n");
        p.drawCard();

        // user input for turn structure
        boolean playingTurn = true;
        printTurnMenu();
        while(playingTurn){
            int option = 0;
            if(input.hasNextInt())
                option = input.nextInt();
            else
                input.next();

            switch(option){
                case 1: // play card
                    promptPlayCard(p);
                    printTurnMenu();
                    break;
                case 2: // attack
                    if (turnCount == 0) {
                        System.out.println("You cannot attack on the first turn!");
                        break;
                    }
                    // if the attack is successful end turn
                    playingTurn = !promptAttack(p);
                    break;
                case 3: // show hand
                    p.showHand();
                    break;
                case 4: // retreat
                    promptRetreat(p);
                    break;
                case 5: // inspect card
                    promptInspect(p);
                    printTurnMenu();
                    break;
                case 6: // show game state
                    printGameState();
                    break;
                case 7: // end turn
                    if(p.getBench().getActiveCard() == null){
                        System.out.println("You must have an active pokemon before ending your turn!");
                        break;
                    }
                    playingTurn = false;
                    break;
                default:
                    System.out.println("The option that was selected is not available please Input [1 - 7]");
                    break;
            }
        }
        Log.message("Ending Turn! \n\n");
        turnCount++;
    }

    /**
     * handle drawing initial cards and mulligan checking / comparisons
     */
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

    /**
     * handle playing card of any type
     *
     * @param c the card to be played
     * @param p owner of card played
     */
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
            if (input.hasNextInt())
                userInput = input.nextInt() - 1;
            if (userInput >= -1 && userInput < size)
                looping = false;
            else{
                input.next();
                System.out.println("Please enter a valid option");
            }
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
            if(input.hasNextInt())
                userInput = input.nextInt() - 1;
            else{
                userInput = -2;
                input.next();
            }
            if (userInput >= -1 && userInput < size)
                looping = false;
            else{
                System.out.println("Please enter a valid option");
            }
        }

        if (userInput == -1){
            return;
        }

        Card c = p.getHand().getCards().get(userInput);

        if(playCard(c, p))
            p.getDiscard().add(p.getHand().getCards().remove(userInput));
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
            userInput = 0;
            if(input.hasNextInt())
                userInput = input.nextInt();
            else
                input.next();

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

        // check if pokemon was killed during own attack ex. self dmg dies first
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

    /**
     * handle playing card of any type
     *
     * @param p player whose turn it is
     */
    public void promptRetreat(Player p){
        if (p.getBench().getActiveCard() == null){
            System.out.println("You need an active Pokemon first!");
            return;
        }
        if (p.getBench().getCards().isEmpty()){
            System.out.println("You need a Pokemon in your bench!");
            return;
        }
        if (!p.getBench().getActiveCard().canRetreat()){
            System.out.println("Your active Pokemon doesn't have enough energy to retreat!");
            return;
        }

        int size = p.getBench().getCards().size();

        System.out.println("Which pokemon will be your new active pokemon?");

        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getBench().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            if (input.hasNextInt())
                userInput = input.nextInt() - 1;
            if (userInput >= 0 && userInput < size)
                looping = false;
            else{
                input.next();
                System.out.println("Please enter a valid option");
            }
        }
        Pokemon c = p.getBench().getActiveCard();
        p.getBench().setActiveCard(userInput);
        p.getBench().addToBench(c);
        Log.message(c.getName() + " retreated!\n");
        Log.message(p.getBench().getActiveCard().getName() + " came out to fight!\n");

    }

    /**
     * handling inspection of cards of each type from hand
     *
     * @param p player inspect cards in hand
     */
    public void promptInspect(Player p){
        int size = p.getHand().getCards().size();

        System.out.println("Which card will you inspect?");
        System.out.println("  0. Return to menu");
        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getHand().getCards().get(i).getName());
        }

        boolean looping = true;
        int userInput = 0;
        while(looping){
            if (input.hasNextInt())
                userInput = input.nextInt() - 1;
            else{
                input.next();
                userInput = -2;
            }

            if (userInput >= -1 && userInput < size)
                looping = false;
            else
                System.out.println("Please enter a valid option");
        }

        if (userInput == -1){
            return;
        }

        Card c = p.getHand().getCards().get(userInput);

        if (c instanceof Energy){
            System.out.println("It is a/an " + c.getName() + " you can attach this to Pokemon to use attacks!");
        }
        else if (c instanceof Trainer){
            System.out.println("This is " + c.getName() + " when played the following effect plays out \n" + ((Trainer) c).getDescription());
        }
        else if (c instanceof Pokemon){
            int hp = ((Pokemon) c).getHp();
            int maxHp = ((Pokemon) c).getMaxHp();
            String resist = ((Pokemon) c).getResistance();
            String weakness = ((Pokemon) c).getWeakness();
            int retreatCost = ((Pokemon) c).getRetreatCost();
            String attack1Name = ((Pokemon) c).getAttack1Name();
            String attack2Name = ((Pokemon) c).getAttack2Name();

            System.out.println(c.getName() + "\n  Stats :\n    HP:" + hp + "/" + maxHp + "\n    Resistant : " + resist + "\n    Weakness : " + weakness + "\n    Retreat Cost : " + retreatCost);
            System.out.println("  Attacks :");
            if(!attack1Name.isEmpty()){
                String attackDescription = ((Pokemon) c).getAttack1Desc();
                System.out.println("    " + attack1Name + " - " + attackDescription);
            }
            if(!attack2Name.isEmpty()){
                String attackDescription = ((Pokemon) c).getAttack2Desc();
                System.out.println("    " + attack2Name + " - " + attackDescription);
            }

        }

    }

    /**
     * change active pokemon after faint
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
            else{
                input.next();
                System.out.println("Please enter a valid option");
            }
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

        System.out.printf("\n%-50s | %-50s", "Bench : " + getBenchRows(0, 3, p1), "Bench : " + getBenchRows(0, 3, p2));
        if(p1.getBench().getCards().size() > 3 || p2.getBench().getCards().size() > 3 )
            System.out.printf("\n%-50s | %-50s", getBenchRows(1, 3, p1), "        " + getBenchRows(1, 3, p2));

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
            return "None";
        } else {
            return "" + row;
        }
    }


    /**
     * print the turn options
     */
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
        int options = currPage < pages - 1 ? 8 : cardList.size() % 8;
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
        if (currPage < pages - 1)
            System.out.println("  9. Next Page");

        return options;
    }

    /**
     * the deck builder menu that pulls all cards from card package with reflection and prompts the user for creation of decks
     */
    public void createDeck() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // uses reflection to get all card classes from pokemon energy and trainer packages
        ArrayList<Class<? extends Card>> cardList = new ActiveCardCollector().getActiveCards();
        Map<Class<? extends Card>, Integer> cardQuantityMap = new HashMap<>();
        Deck newDeck = new Deck();

        if (usableDecks == null) {
            usableDecks = new ArrayList<>();
        }

        int maxPage = cardList.size() % 8 == 0 ? cardList.size() / 8 : (cardList.size() / 8) + 1;
        int curPage = 0;

        while (newDeck.size() < 60){
            System.out.println("Enter the Card # [0-7] or input [8-9] to change pages");
            int options = showCardPage(cardList, curPage);

            int userInputCard = -1;
            if (input.hasNextInt())
                userInputCard = input.nextInt();
            else
                input.next();

            int userInputQuantity = 0;

            if (userInputCard >= 0 && userInputCard < options){
                System.out.println("Enter the quantity [1-4]");
                if (input.hasNextInt())
                    userInputQuantity = input.nextInt();
                else
                    input.next();

                Class<? extends Card> cardClass = cardList.get(userInputCard + (8 * curPage));
                Card tempCard = cardClass.getDeclaredConstructor().newInstance();

                // limit all cards but energy
                if (!(tempCard instanceof Energy)) {
                    int currentCount = cardQuantityMap.getOrDefault(cardClass, 0);
                    // check if the user will add more then 4 cards
                    if (currentCount + userInputQuantity > 4) {
                        System.out.println("You cannot add more than 4 copies of " + tempCard.getName());
                        continue;
                    } else {
                        // update amount of cards in deck per class
                        cardQuantityMap.put(cardClass, currentCount + userInputQuantity);
                    }
                }
                if (userInputQuantity <= 4 && userInputQuantity > 0){
                    if(userInputQuantity == 1){
                        newDeck.add(tempCard);
                        System.out.println(tempCard.getName() + " was added");
                    }
                    else {
                        if(newDeck.size() + userInputQuantity <= 60){
                            for (int i = 0; i < userInputQuantity; i++) {
                                newDeck.add(tempCard);
                            }
                            System.out.println(userInputQuantity + " " + tempCard.getName() + "s were added");
                        }
                        else
                            System.out.println("You are adding to many cards " + (newDeck.size() + userInputQuantity) + " / 60");
                    }
                }
                else
                    System.out.println("Invalid Quantity try [1-4]");
            }

            // handle page swapping input handling
            if(userInputCard == 8){
                if (curPage > 0)
                    curPage--;
                else
                    System.out.println("There are no previous pages");
            }
            if(userInputCard == 9){
                if (curPage < maxPage - 1)
                    curPage++;
                else
                    System.out.println("There are no more page");
            }
        }

        // name deck and add to usable decks
        System.out.println("Name your deck : ");
        String name = "";
        while(name.isEmpty()){
            name = input.nextLine();
        }
        newDeck.setName(name);
        usableDecks.add(newDeck);
    }

    /**
     * calling the Monte Carlo simulators that export data for users
     */
    public void launchSimulator() throws IOException {
        System.out.println("Simulations : ");
        System.out.println("  1. Export Chance of drawing a Mulligan");
        System.out.println("  2. Export Chance of drawing a Mulligan OR Bricking");

        boolean looping = true;
        int userInput = 0;
        while(looping){
            userInput = input.nextInt();
            if (userInput > 0 && userInput < 3)
                looping = false;
            else{
                input.next();
                System.out.println("Please enter a valid option [1-2]");
            }
        }

        switch (userInput){
            case 1:
                new MonteCarlo().exportAllMulliganDataAsCSV("", 10000);
                break;
            case 2:
                System.out.println("How many Pokemon are in the deck?");
                looping = true;
                userInput = 0;
                while(looping){
                    userInput = input.nextInt();
                    if (userInput > 0 && userInput <= 56)
                        looping = false;
                    else{
                        input.next();
                        System.out.println("Please enter a valid option [1-2]");
                    }
                }
                new MonteCarlo().exportMulliganBrickedAsCSV("", userInput);
                break;
        }
    }

    /**
     * Call, display and accept user input for the main menu
     */
    public boolean mainMenu() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        input = new Scanner(System.in);

        System.out.println(
                """
                         _____     _                                _____ _____ _____         \s
                        | ___ \\   | |                              |_   _/  __ \\  __ \\     \s
                        | |_/ /__ | | _____ _ __ ___   ___  _ __     | | | /  \\/ |  \\/      \s
                        |  __/ _ \\| |/ / _ \\ '_ ` _ \\ / _ \\| '_ \\    | | | |   | | __    \s
                        | | | (_) |   <  __/ | | | | | (_) | | | |   | | | \\__/\\ |_\\ \\    \s
                        \\_|  \\___/|_|\\_\\___|_| |_| |_|\\___/|_| |_|   \\_/  \\____/\\____/\s
                        """);
        System.out.println("""
                 1. Play Game\s
                 2. Build Deck\s
                 3. Monte Carlo Simulations\s
                 4. Exit Game\s
                """);

        boolean looping = true;
        int userInput = 0;
        while(looping){
            if (input.hasNextInt())
                userInput = input.nextInt();
            else
                input.next();

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
                return mainMenu(); // allow more selections while still keeping boolean output
            case 3:
                System.out.println("Starting Monte Carlo Simulations");
                try {
                    launchSimulator();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            case 4:
                System.out.println("Exiting Game...");
                return false;
        }
        return true;
    }

    public Scanner getInput() {
        return input;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }
}
