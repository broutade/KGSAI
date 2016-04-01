package fr.felicien.kgsaiengine;

/**
 *
 * @author brochu
 */
public class ResponseCommand {
    public final static ResponseCommand EMPTY_RESPONSE = new ResponseCommand("");

    Integer id;
    String response;

    public ResponseCommand(Integer id, String response) {
        this.id = id;
        this.response = response;
    }
    
    public ResponseCommand(String response) {
        this.id = -1;
        this.response = response;
    }
    
    public String computeCommand() {
        StringBuilder computedCommand = new StringBuilder();
        
        computedCommand.append("=");
        
        if (id != -1) {
            computedCommand.append(id.toString());
        }
        
        computedCommand.append(" ")
                .append(response)
                .append("\n\n");
        return computedCommand.toString();
    }

    @Override
    public String toString() {
        return computeCommand();
    }
    
}
