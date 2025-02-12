package PokemonTCG;

/*
 * uses factory so specific cards can interact with the game in any way.
 * @author Tyler Snyder
 *
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
