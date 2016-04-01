package fr.felicien.kgsaiengine;

import com.gokgs.client.gtp.GtpClient;
import com.gokgs.client.gtp.Options;
import java.util.AbstractList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author felicien brochu
 */
public class KGSAIEngine implements AIEngine {
    private final static String name = "felixBot";
    GtpCommandWriter commandWriter;
    GtpCommandReader commandReader;
    CommandResponseListener commandResponseListener;
    Properties properties;
    Move[][] goban;
    Group[][] groups;
    

    public KGSAIEngine() {
        this.commandWriter = new GtpCommandWriter(this);
        this.commandReader = new GtpCommandReader(this);
        this.commandResponseListener = this.commandReader;
        
        this.properties = new Properties();
        
        properties.put("name", name);
        properties.put("password", "dqnu8e");
        properties.put("room", "Salle française");
        properties.put("mode", "custom");
        properties.put("automatch.speed", "blitz,medium");
        properties.put("automatch.rank", "20k");
        properties.put("verbose", "t");
        
        this.goban  = new Move[19][19];
        this.groups = new Group[19][19];
    }

    private void start() {
        Options options = new Options(properties, "YOYOYO");
        GtpClient gtpClient = new GtpClient(this.commandReader, this.commandWriter, options);
        gtpClient.go();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KGSAIEngine engine = new KGSAIEngine();
        engine.start();
    }

    @Override
    public void onCommandRequest(RequestCommand command) {
        System.out.println("Received command: " + command);
        switch (command.getCommand()) {
            case RequestCommand.LIST_COMMANDS:
                listCommands();
                break;
            case RequestCommand.NAME:
                sendName();
                break;
            case RequestCommand.VERSION:
                sendVersion();
                break;
            case RequestCommand.BOARDSIZE:
                sendBoardSize();
                break;
            case RequestCommand.PLAY:
                saveOpponentMove(command);
                break;
            case RequestCommand.GENMOVE:
                genMove(command);
                break;
            default:
                sendCommand(new ResponseCommand(""));
        }
    }

    private void sendCommand(ResponseCommand responseCommand) {
        this.commandResponseListener.onCommandResponse(responseCommand);
    }

    private void listCommands() {
        StringBuilder computedCommand = new StringBuilder();
        
        for (String command : this.getSupportedCommands()) {
            if (!computedCommand.toString().isEmpty()) {
                computedCommand.append("\n");
            }
            computedCommand.append(command);
        }
        
        ResponseCommand responseCommand = new ResponseCommand(computedCommand.toString());
        sendCommand(responseCommand);
    }

    private void sendBoardSize() {
        sendCommand(ResponseCommand.EMPTY_RESPONSE);
    }

    private void saveOpponentMove(RequestCommand command) {
        System.out.println("Opponent played: " + command.getArguments());
        Move move = new Move(command.getArguments().get(1), command.getArguments().get(0).charAt(0));
        executeMove(move);
        sendCommand(ResponseCommand.EMPTY_RESPONSE);
    }

    private void genMove(RequestCommand command) {
        ResponseCommand responseCommand = new ResponseCommand("pass");
        
        Move move;
        char player = command.getArguments().get(0).charAt(0);
        
        do {
            move = getRandomMove(player);
        } while (!isLegalMove(move));
        
        
        executeMove(move);
        
        sendCommand(new ResponseCommand(move.toString()));
    }
    
    private Move getRandomMove(char player) {
        int x, y;
        
        x = (int) Math.floor(Math.random() * 19) + 1;
        y = (int) Math.floor(Math.random() * 19) + 1;
        
        Move move = new Move(x, y, player);
        return move;
    }

    private void sendName() {
        ResponseCommand responseCommand = new ResponseCommand(name);
        sendCommand(responseCommand);
    }

    private void sendVersion() {
        ResponseCommand responseCommand = new ResponseCommand("1.0");
        sendCommand(responseCommand);
    }

    private boolean isLegalMove(Move move) {
        return this.goban[move.x - 1][move.y - 1] == null;
    }

    private void executeMove(Move move) {
        Move[][] newGoban = new Move[19][19];
        
        if (isLegalMove(move)) {
            Set<Group> adjacentGroups = getAdjacentGroups(move);
            this.goban[move.x - 1][move.y - 1] = move;
        }
    }

    private Set<Group> getAdjacentGroups(Move move) {
        Set<Group> adjacentGroups = new HashSet<>();
        
        if (move.x - 1 >= 0 && move.y - 1 >= 0 && groups[move.x - 1][move.y - 1] != null) {
            adjacentGroups.add(groups[move.x - 1][move.y - 1]);
        }
        if (move.x - 1 >= 0 && move.y + 1 < 19 && groups[move.x - 1][move.y + 1] != null) {
            adjacentGroups.add(groups[move.x - 1][move.y + 1]);
        }
        if (move.x + 1 < 19 && move.y - 1 >= 0 && groups[move.x + 1][move.y - 1] != null) {
            adjacentGroups.add(groups[move.x + 1][move.y - 1]);
        }
        if (move.x + 1 < 19 && move.y + 1 < 19 && groups[move.x + 1][move.y + 1] != null) {
            adjacentGroups.add(groups[move.x + 1][move.y + 1]);
        }
        return adjacentGroups;
    }
}
