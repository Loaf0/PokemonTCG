package PokemonTCG;

import PokemonTCG.Cards.Card;
import PokemonTCG.Cards.Energy;
import PokemonTCG.Cards.Pokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class MonteCarlo {

    public double calculateMulliganChance(int numOfPokemon, int iterations){
        int mulligans = 0;

        for (int i = 0; i < iterations; i++) {
            Player p = new Player(false);
            Deck d = new Deck();
            for (int j = 0; j < numOfPokemon; j++) {
                Card c = new Pokemon();
                d.add(c);
            }
            while (d.size() < 60) {
                Card c = new Energy();
                d.add(c);
            }

            p.setDeck(d);
            p.shuffle();
            p.drawCards(7);

            if (!p.checkMulligan())
                mulligans++;
        }
        double chance = (double) mulligans / iterations;
        System.out.printf("Pokemon in Deck : %2d, Iterations : %10d, Mulligans : %10d, Chance Of Occurrence : %.3f%% \n" , numOfPokemon, iterations, mulligans, chance*100);
        return chance;
    }

    public void exportAllMulliganDataAsCSV(int iterations, String path) throws IOException {
        path = path.isEmpty() ? "../PokemonTCGExports/export.csv" : path;
        File f = new File(path);

        if (!f.getParentFile().mkdirs()){
            System.out.println("Path could not be created");
            return;
        }

        try (PrintWriter pw = new PrintWriter(f)){
            StringBuilder sb = new StringBuilder();
            sb.append("Pokemon In Deck,").append("Chance Of Occurrence\n");

            for (int i = 0; i <= 60; i++) {
                DecimalFormat df = new DecimalFormat("#.000");
                sb.append(i).append(",").append(df.format(calculateMulliganChance(i, iterations))).append("\n");
            }

            pw.write(sb.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
