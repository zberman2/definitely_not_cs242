package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by Zack Berman on 9/18/2014.
 * Calls the superclass constructor to make a standard
 * square, 8x8 board
 */
public class StandardBoard extends Board {
    /**
     * Blank constructor
     */
    public StandardBoard() {
        super(8, 8, 4); // defines a square 8x8 board
    }

    /**
     * For testing purposes, initializes an 8x8 board with a given
     * set of chess pieces
     * @param pieces An array list of chess pieces
     */
    public StandardBoard(ArrayList<Piece> pieces) {
        super(pieces, 8, 8, 4);
        // defines a square 8x8 board with an initial set of pieces
    }
}
