package fr.felicien.kgsaiengine;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author brochu
 */
public class GtpCommandWriter extends OutputStream {
    
    String buffer;
    CommandRequestListener commandInputListener;

    public GtpCommandWriter(CommandRequestListener commandInputListener) {
        this.buffer = "";
        this.commandInputListener = commandInputListener;
    }

    @Override
    public void write(int octet) throws IOException {
        buffer += (char) octet;
        if (octet == '\n') {
            RequestCommand command = new RequestCommand(buffer);
            commandInputListener.onCommandRequest(command);
            buffer = "";
        }
    }
    
}
