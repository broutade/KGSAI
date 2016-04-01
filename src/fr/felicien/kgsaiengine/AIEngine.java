package fr.felicien.kgsaiengine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brochu
 */
public interface AIEngine extends CommandRequestListener {
    default List<String> getSupportedCommands() {
        ArrayList<String> supportedCommands = new ArrayList<>();
        supportedCommands.add(RequestCommand.LIST_COMMANDS);
        supportedCommands.add(RequestCommand.NAME);
        supportedCommands.add(RequestCommand.VERSION);
        supportedCommands.add(RequestCommand.BOARDSIZE);
        supportedCommands.add(RequestCommand.KOMI);
        supportedCommands.add(RequestCommand.PLAY);
        supportedCommands.add(RequestCommand.GENMOVE);
        
        return supportedCommands;
    }
}
