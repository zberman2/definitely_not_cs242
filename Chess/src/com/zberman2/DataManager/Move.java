package com.zberman2.DataManager;

import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import javafx.util.Pair;

/**
 * Class describing a Move object
 * A move stores the motion of a piece from one space on the board
 * to another. It is used for undoing moves in the game
 * Created by Zack Berman on 9/23/2014.
 */
public class Move {
    Piece piece;
    Piece captured;
    Pair<Character, Integer> startPosition;
    Pair<Character, Integer> endPosition;

    /**
     * Constructor for a Move object
     * @param piece Piece that is moving
     * @param captured Piece being captured (null if no capture
     *                 is taking place)
     * @param startPosition beginning (char, int) pair
     * @param endPosition end (char, int) pair
     */
    public Move(Piece piece, Piece captured,
                Pair<Character, Integer> startPosition,
                Pair<Character, Integer> endPosition) {
        this.piece = piece;
        this.captured = captured;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Reverts the move
     */
    public void undo() {
        // move piece to its original position
        piece.move(startPosition);
        if (captured != null) {
            // return the captured piece to the board
            captured.setIsCapturedFalse();
        }

        // if its a pawn, decrement move counter
        // if counter is 0, set isFirstMove true
        if (piece instanceof Pawn) {
            ((Pawn) piece).decrementMoveCount();
            if (((Pawn) piece).getMoveCount() == 0) {
                ((Pawn) piece).setIsFirstMove(true);
            }
        }
    }
}
