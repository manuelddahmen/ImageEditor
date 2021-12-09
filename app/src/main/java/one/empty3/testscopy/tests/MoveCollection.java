package one.empty3.testscopy.tests;

import java.util.Collection;
import java.util.HashMap;

public class MoveCollection {
    private HashMap<String, Move> moves;
    private HashMap<String,Double> tStart = new HashMap<>();
    private HashMap<String,Double> tEnd = new HashMap<>();
    public MoveCollection(double tStart, double tEnd) {
        this.moves = new HashMap<>();
    }

    public void addAll(String humanWalks, double tStart, double tEnd,
                       Move... movesAdd) {
        for (Move move : movesAdd) {
            moves.put(move.getMoveName(), move);
            this.tStart.put(humanWalks, tStart);
            this.tEnd.put(humanWalks, tEnd);
        }
    }

    public Collection<Move> getMoves() {
        return moves.values();
    }
}
