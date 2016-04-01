package fr.felicien.kgsaiengine;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author brochu
 */
class Group {
    private Set<Move> moves;
    private Set<Move> liberties;
    
    public Group() {
        this.moves = new HashSet<>();
        this.liberties = new HashSet<>();
    }
    
    public void addMove(Move move, Set<Move> liberties) {
        this.liberties.remove(move);
        this.liberties.addAll(liberties);
    }
    
    public boolean isDead() {
        return this.liberties.isEmpty();
    }
}
