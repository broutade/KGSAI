package fr.felicien.kgsaiengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author brochu
 */
public class RequestCommand {
    
    // Administrative Commands
    
    /**
     * Recommended - Sent when first connecting to the client. kgsGtp will never
     * send nonstandard commands unless the engine states that they are
     * supported. kgsGtp may send standard commands even though the engine does
     * not claim to support them. If you do not support list_commands then only
     * very basic functionality will be used in your engine.
     */
    public final static String LIST_COMMANDS = "list_commands";
    
    /**
     * Optional
     */
    public final static String NAME = "name";
    
    /**
     * Optional - The name and version are added as comments every time that
     * kgsGtp starts a game. If the engine does not provide a name and version,
     * then nothing will be written. If you use non-ASCII characters in the name
     * or version of your engine, these characters must be encoded using UTF-8.
     */
    public final static String VERSION = "version";
    
    /**
     * Optional - If clear_board is not supported, then we send a quit after the
     * ame ends because there is nothing else we can do. If the quit fails, then
     * we just close everything up with an error.
     */
    public final static String QUIT = "quit";
    
    /**
     * Optional - This extension may be used for the engine to be able to chat
     * with opponents and other players on the server. The format of this
     * command will be "kgs-chat (game|private) Name Message".
     * The first parameter indicates whether the message came from the engine's
     * opponent in the game or from a private chat. Name is the name of the user
     * who sent the message. Message (which may include spaces but will not
     * include line feeds or any other escape characters at or below character
     * code 31) is the message itself. If the engine responds with a non-error
     * value, then the response will be sent back to the user who sent the
     * message. If the engine responsds with an error, the default message
     * (as specified by the talk option) is sent instead. Messages in chat rooms
     * and from game observers are always ignored.
     */
    public final static String KGS_CHAT = "kgs-chat";
    
    
    //Setup Commands
    
    /**
     * Required - This will be sent before the game starts. If you cannot play
     * the size specified, reply with an error. Any challenges that come with
     * board sizes that your engine cannot support will not be accepted. Note
     * that you will often get multiple boardsize commands before the game start
     * - one or more as each challenge comes in to ensure that your engine can
     * play the board size specified, then a final boardsize when the game
     * actually starts.
     */
    public final static String BOARDSIZE = "boardsize";
    
    /**
     * Recommended - Used before each game to ensure a clean starting point.
     * If you don't support this, then you will be limited to one game for each
     * time you run kgsGtp.
     */
    public final static String CLEAR_BOARD = "clear_board";
    
    /**
     * Optional - Sent when each game starts up.
     */
    public final static String KOMI = "komi";
    
    /**
     * Recommended
     */
    public final static String PLACE_FREE_HANDICAP = "place_free_handicap";
    
    /**
     * Recommanded - Used to set or ask for handicap stone positions. The fixed
     * handicap GTP command is not used. If the game is fixed handicap, you will
     * get the set_free_handicap command instead. GTP does not have enough
     * flexibility in its fixed handicap command to support KGS. If your engine
     * does not support these commands then handicap games will be refused.
     */
    public final static String SET_FREE_HANDICAP = "set_free_handicap";
    
    
    // Core Play Commands
    
    /**
     * Required
     */
    public final static String PLAY = "play";
    
    /**
     * Required
     */
    public final static String GENMOVE = "genmove";
    
    /**
     * Optional - If your engine supports this command, you will get it as
     * normal. If your engine does not include undo as a response to
     * list_commands, then KGS will use clear_board followed by a series of play
     * commands when an undo is required. If your engine supports neither of
     * these commands, then any undos that your opponents request will be
     * denied.
     */
    public final static String UNDO = "undo";
    
    /**
     * Optional - If your engine supports this command, it will be sent whenever
     * a game ends, either through scoring or due to your opponent leaving. This
     * can be used as a cue that it is a good time for the engine to log out
     * (usually done by the engine exiting after receiving this command).
     */
    public final static String KGS_GAME_OVER = "kgs-game_over";
    
    /**
     * Optional - If your engine supports this command, it will be sent each
     * time a game starts. It has one parameter, which is the rules of the game;
     * one of japanese, chinese, aga, or new_zealand. The response to this
     * command is ignored. In the future, this command may be extended by adding
     * more parameters, so please code your engine to treat "kgs-rules japanese"
     * and "kgs-rules japanese blah blah blah" as equivalent.
     */
    public final static String KGS_RULES = "kgs-rules";
    
    
    // Tournament commands
    
    /*
     * time_settings — Optional
     * kgs-time_settings — Optional
     *  Similar to the boardsize command, you may get several time settings commands; any settings that your engine cannot play with, you should return an error. If your engine supports the kgs-time_settings command, then you will get that instead of the standard time_settings command. The reson for the KGS-specific time setting is that GTP's standard time settings has no way to describe the byo-yomi time system. This system is quite popular in tournaments and is used often on KGS. The format for the kgs-time_settings command is the same as time_settings, but another parameter is inserted as the first parameter, which describes the time system as either none, absolute, byoyomi, or canadian. For the none and absolute time systems there will be fewer than two options. If you do not support kgs-time_settings and your engine is put into a byo-yomi game, kgsGtp will tell your engine that it is an absolute time game with no byo-yomi periods and a total time equal to the main time and all of the periods.
     * time_left — Optional
     *  Used as normal.
     * final_status_list — Optional, required for rated games
     *  After the game enters scoring kgsGtp will send this command, asking for dead stones. kgsGtp will never ask questions about seki, only about dead stones. If you successfully return a list of dead stones, then kgsGtp will mark those on the board. If you return an error, then kgsGtp will not mark any stones dead and your opponent will have to do that instead. In tournament games, this command is only used if you also support kgs-genmove_cleanup; otherwise it is assumed that your engine believes all stones on the board to be alive.
     * kgs-genmove_cleanup — Optional, recommended for playing ranked or tournament games
     *  This is a kgsGtp-specific command. Engines that support it should treat it the same as the genmove command, except that they should not pass until all dead stones are removed from the board. This is used by kgsGtp when the engine and the opponent disagree on which stones are dead at the end of the game. In tournament games, if you do not support this command, then you must play until all dead stones are removed from the board - that is, regular genmove must behave the way that kgs-genmove_cleanup is described.
     */
    
    Integer id;
    String command;
    List<String> arguments;
    
    public RequestCommand(String rawCommand) {
        this.id = -1;
        this.command = null;
        this.arguments = new ArrayList<>();
        
        Scanner scanner = new Scanner(rawCommand);
        boolean firstToken = true;
        
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (firstToken) {
                firstToken = false;
                
                try {
                    this.id = Integer.valueOf(token);
                }
                catch (NumberFormatException ex) {
                    this.id = -1;
                    this.command = token;
                }
            }
            else {
                if (this.command == null) {
                    this.command = token;
                }
                else {
                    this.arguments.add(token);
                }
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "RequestCommand{" + "id=" + id + ", command=" + command + ", arguments=" + arguments + '}';
    }
    
}
