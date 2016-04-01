package fr.felicien.kgsaiengine;

/**
 *
 * @author brochu
 */
public class Move {
    int x;
    int y;
    char player;

    public Move(int x, int y, char player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }
    
    public Move(String resume, char player) {
        this.x = charToX(resume.charAt(0));
        this.y = Integer.parseInt(resume.substring(1));
        this.player = player;
    }
    
    static char xToChar(int x) {
        char xChar = (char) (0x60 + x);
        if (xChar >= 'i') {
            xChar++;
        }
        return xChar;
    }
    
    static int charToX(char xChar) {
        if (xChar >= 'i') {
            xChar--;
        }
        int x = xChar - 0x60;
        return x;
    }

    char getPlayer() {
        return this.player;
    }
    
    @Override
    public String toString() {
        return "" + xToChar(x) + y;
    }
}
