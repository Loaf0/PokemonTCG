package PokemonTCG;

import java.io.IOException;

import static PokemonTCG.GameManagerFactory.getGameManager;

public class StartGame {
    public static void main(String[] args) throws IOException {
        getGameManager().run();
    }
}