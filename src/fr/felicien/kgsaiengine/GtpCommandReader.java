package fr.felicien.kgsaiengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brochu
 */
class GtpCommandReader extends InputStream implements CommandResponseListener {
    int lastReadIndex;
    String command;
    AIEngine engine;
    
    public GtpCommandReader(AIEngine engine) {
        this.engine = engine;
        this.lastReadIndex = 1;
        this.command = "";
    }

    @Override
    public int read() throws IOException {
        int byyte = readByte();
        System.out.println("read: " + (char) byyte);
        return byyte;
    }

    private boolean hasCommand() {
        return lastReadIndex < command.length();
    }

    private int readByte() {
        while (true) {
            if (hasCommand()) {
                return command.getBytes()[lastReadIndex++];
            }
            else if (lastReadIndex == command.length()) {
                lastReadIndex++;
                return -1;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(GtpCommandReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void onCommandResponse(ResponseCommand command) {
        System.out.println("Send command: " + command);
        this.command = command.computeCommand();
        this.lastReadIndex = 0;
    }
}
