package PokemonTCG;

/*
 * @description Custom log class to print, log and save the battle logs
 * @author Tyler Snyder
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    /**
     * Helper method for saveLog()
     *
     * @param sb String builder containing full battle log
     * @param path save location for file
     */
    private static void exportBattleLog(StringBuilder sb, String path) { // print sys time and append to path
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dateTime = currentDateTime.format(formatter);

        path = path.isEmpty() ? "../PokemonTCGExports/" + dateTime  + "-BattleLog.txt" : path;
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(f)){
            pw.write(sb.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

