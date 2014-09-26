package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

import java.util.ArrayList;

/**
 * Class describing the Initializer object
 * The initializer is in charge of initializing a chessboard
 * with a set list of pieces
 * Created by Zack Berman on 9/12/2014.
 */
public abstract class Initializer {
    Board chessboard; // reference to the Master's chessboard

    /**
     * Constructor for the initializer, which is solely responsible
     * for initializing the chess pieces for the Master class
     * @param chessboard Reference to the initially empty board
     */
    public Initializer(Board chessboard) { this.chessboard = chessboard; }

    /**
     * Populates an ArrayList of pieces, and returns it to the caller
     * @return initial ArrayList of pieces
     */
    public ArrayList<Piece> initializePieces() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        initializeWhitePieces(pieces);
        initializeBlackPieces(pieces);
        return pieces;
    }

    /**
     * Places the initial set of white pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    public abstract void initializeWhitePieces(ArrayList<Piece> pieces);

    /**
     * Places the initial set of black pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    public abstract void initializeBlackPieces(ArrayList<Piece> pieces);

}
