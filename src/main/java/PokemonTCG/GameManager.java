package PokemonTCG;
/*
 * Due Tuesday
 * determine odds of mulligan for each number of pokemon run 10,000 times
 * ex 1 pokemon 59 energies
 */

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.PokemonCards.MrMime;
import PokemonTCG.Cards.Trainer;
import PokemonTCG.Cards.TrainerCards.Bill;

import java.io.IOException;
import java.util.Scanner;

public class GameManager {

    private boolean running;
    private int turnCount;
    private boolean canPlayEnergy;
    private Player p1;
    private Player p2;
    private Scanner input;

    public void run() throws IOException {
        // check if user wants to run deck builder
        setupGame(true, true);
        setUpTurnOrder();
        setupHands();
        startGameLoop();

        Log.saveLog();
    }

    public void setupGame(boolean p1AI, boolean p2AI) {
        turnCount = 0;
        running = true;

        input = new Scanner(System.in);
        p1 = new Player(p1AI);
        p1.setName("Player 1");
        p2 = new Player(p2AI);
        p2.setName("Player 2");

        Deck testDeck = new Deck();

        for (int i = 0; i < 20; i++) {
            Card c = new MrMime();
            testDeck.add(c);
        }
        for (int i = 0; i < 20; i++) {
            Card c = new Bill();
            testDeck.add(c);
        }
        for (int i = 0; i < 20; i++) {
            Card c = new Energy("Psychic");
            testDeck.add(c);
        }
        p1.setDeck(testDeck);
        p2.setDeck(testDeck);
    }


    public void setUpTurnOrder(){
        Log.message("Deciding what player goes first... \nHeads : " + p1.getName() + " - Tails : " + p2.getName() + "\n");
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

            turnCount++;
        }
    }

    public void turn(Player p) {
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
                    promptAttack(p);
                    printTurnMenu();
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

                    Log.message("Ending Turn! \n\n");
                    playingTurn = false;
                    break;
                default:
                    System.out.println("The option that was selected is not available please Input [1 - 6]");
                    break;
            }
        }

        // play 1 energy and any number of other cards if desired

        // if turn count 1 can not attack
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
        return !p1.getLostFlag() && !p2.getLostFlag();
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
        if (c instanceof Pokemon || c instanceof Energy || c instanceof Trainer) {
            return c.playCard(c, p);
        }
        Log.message(c.getName() + "ERROR : Object is not a card");
        return false;
    }

    public void promptPlayCard(Player p){
        int size = p.getHand().getCards().size();

        System.out.println("Which card will you play?");

        for (int i = 0; i < size; i++) {
            System.out.println("  " + (i + 1) + ". " + p.getHand().getCards().get(i).getName());
        }

        boolean looping = true;
        while(looping){
            int userInput = input.nextInt() - 1;
            if (userInput >= 0 && userInput < size)
                looping = false;
            else
                System.out.println("Please enter a valid option");
        }

    }

    public void promptAttack(Player p){
        if(p.getBench().getActiveCard() == null){
            System.out.println(p.getName() + " doesn't have an active Pokemon");
            return;
        }
        System.out.println("Choose Attack :");


    }

    public void promptRetreat(Player p){
        if(p.getBench().getActiveCard() == null){
            System.out.println(p.getName() + " doesn't have an active Pokemon");
            return;
        }
        System.out.println("Choose Attack :");
    }

    public void promptInspect(Player p){
        if(p.getBench().getActiveCard() == null){
            System.out.println(p.getName() + " doesn't have an active Pokemon");
            return;
        }
        System.out.println("Choose Attack :");
    }

    public void printPlayerState(Player p){
        System.out.println();
        System.out.println(p.getName() + "'s gameState");
        p.showBench();
        System.out.println("Hand Size : " + p.getHand().getCards().size());
        System.out.println("Prize Cards : " + p.getPrize().getCards().size());
    }

    public void printGameState(){
        System.out.printf("\n%-50s | %-50s", p1.getName() + "'s Game State", p2.getName() + "'s Game State");
        System.out.printf("\n%-50s | %-50s", p1.getBench().getActiveCard() == null ? "Active Pokemon : None" : "Active Pokemon : " + p1.getBench().getActiveCard(), p2.getBench().getActiveCard() == null ? "Active Pokemon : None" : "Active Pokemon : " + p2.getBench().getActiveCard());
        System.out.printf("\n%-50s | %-50s", "Bench : " + p1.getBench().getCards().toString(), "Bench : " + p2.getBench().getCards().toString());
        System.out.printf("\n%-50s | %-50s", "Hand Size : " + p1.getHand().getCards().size() + "  - Cards Left : " + p1.getDeck().getCards().size(), "Hand Size : " + p2.getHand().getCards().size() + "  - Cards Left : " + p2.getDeck().getCards().size());
        System.out.printf("\n%-50s | %-50s \n", "Prize Cards : " + p1.getPrize().getCards().size(), "Prize Cards : " + p2.getPrize().getCards().size());
    }

    public void printTurnMenu(){
        System.out.println("What will you do : " +
                "\n  1. Play Card    4. Retreat           7. End turn " +
                "\n  2. Attack       5. Inspect Card" +
                "\n  3. Show Hand    6. Show Game State");
    }

}
