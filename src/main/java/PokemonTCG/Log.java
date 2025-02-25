package PokemonTCG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Log {

    private static StringBuilder log;

    /**
     * prints and appends messages to be outputted to a battle log text file
     *
     * @param message will print and save the message.
     */
    public static void message(String message){
        if (log == null)
            log = new StringBuilder();

        System.out.print(message);
        log.append(message);
    }

    public static void saveLog() {
        if (log != null)
            exportBattleLog(log, "");
    }

    public static void exportBattleLog(StringBuilder sb, String path) { // print sys time and append to path
        path = path.isEmpty() ? "../PokemonTCGExports/BattleLog.txt" : path;
        File f = new File(path);

        try (PrintWriter pw = new PrintWriter(f)){
            pw.write(sb.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

