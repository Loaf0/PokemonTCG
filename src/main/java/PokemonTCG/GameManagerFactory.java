package PokemonTCG;

/*
 * Factory of design pattern for Game manager that allows the same static object to be obtained by other classes.
 * @author Tyler Snyder
 */
public class GameManagerFactory {

    private static GameManager gm;

    public GameManagerFactory() {
        gm = new GameManager();
    }

    public static GameManager getGameManager(){
        if (gm == null)
            gm = new GameManager();
        return gm;
    }

}
