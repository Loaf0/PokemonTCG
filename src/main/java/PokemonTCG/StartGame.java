package PokemonTCG;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static PokemonTCG.GameManagerFactory.getGameManager;

public class StartGame {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        getGameManager().run();
    }
}