package PokemonTCG;

/*
 * @description class containing all the monte carlo simulations along with outputting them for easy data viewing
 * @author Tyler Snyder
 */

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.EnergyCards.Colorless;
import PokemonTCG.Cards.Pokemon;
import PokemonTCG.Cards.TrainerCards.RareCandy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class MonteCarlo {

    public double calculateMulliganChance(int numOfPokemon, int iterations){
        int mulligans = 0;

        for (int i = 0; i < iterations; i++) {
            Player p = getPlayer(numOfPokemon, 0);
            if (p.passMulliganCheck())
                mulligans++;
        }
        double chance = (double) mulligans / iterations;
        System.out.printf("Pokemon in Deck : %2d, Iterations : %10d, Mulligans : %10d, Chance Of Occurrence : %.3f%% \n" , numOfPokemon, iterations, mulligans, chance*100);
        return chance;
    }

    public void exportAllMulliganDataAsCSV(String path, int iterations) throws IOException {
        path = path.isEmpty() ? "../PokemonTCGExports/mulliganExport.csv" : path;
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(f)){
            StringBuilder sb = new StringBuilder();
            sb.append("Pokemon In Deck,").append("Chance Of Occurrence\n");

            for (int i = 0; i <= 60; i++) {
                DecimalFormat df = new DecimalFormat("#.000");
                sb.append(i).append(",").append(df.format(calculateMulliganChance(i, iterations))).append("\n");
            }

            pw.write(sb.toString());
            System.out.println("Saving output to " + path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportMulliganBrickedAsCSV(String path, int numberOfPokemon) throws IOException {
        path = path.isEmpty() ? "../PokemonTCGExports/mulliganBrickExport.csv" : path;
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(f)){
            StringBuilder sb = new StringBuilder();
            sb.append("Rare Candy #,").append("Chance Of Occurrence\n");

            for (int i = 1; i <= 4; i++) {
                DecimalFormat df = new DecimalFormat("#.000");
                sb.append(i).append(",").append(df.format(calculateMulliganBrickChance(numberOfPokemon, i, 10000))).append("\n");
            }

            pw.write(sb.toString());
            System.out.println("Saving output to " + path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateMulliganBrickChance(int numOfPokemon, int numOfRareCandy, int iterations){
        int brickedCount = 0;

        for (int i = 0; i < iterations; i++) {
            Player p = getPlayer(numOfPokemon, numOfRareCandy);

            if (p.passMulliganCheck() && p.deckHasCard("Rare Candy") )
                brickedCount++;
        }
        double chance = (double) brickedCount / iterations;
        System.out.printf("Pokemon in Deck : %2d, Iterations : %10d, Mulligans Or Bricked : %10d, Chance Of Occurrence : %.3f%% \n" , numOfPokemon, iterations, brickedCount, chance * 100);
        return chance;
    }

    private static Player getPlayer(int numOfPokemon, int numOfRareCandy) {
        Player p = new Player(false);
        Deck d = new Deck();
        for (int r = 0; r < numOfRareCandy; r++){
            Card c = new RareCandy();
            d.add(c);
        }
        for (int j = 0; j < numOfPokemon; j++) {
            Card c = new Pokemon();
            d.add(c);
        }
        while (d.size() < 60) {
            Card c = new Colorless();
            d.add(c);
        }

        p.setDeck(d);
        p.shuffle();
        p.drawCards(7);
        return p;
    }

}
