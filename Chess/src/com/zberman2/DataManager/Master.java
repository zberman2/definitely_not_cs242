package com.zberman2.DataManager;

import com.zberman2.Pieces.King;
import com.zberman2.Pieces.Piece;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * The master class controls the flow of the game.
 * This class describes that flow.
 * Created by Zack Berman on 9/11/2014.
 */
public class Master {
    private Board chessboard; // the master has access to the board

    /**
     * No args constructor which initializes a blank standard board, populates
     * a standard list of chess pieces, and adds them to the board.
     */
    public Master() {
        this.chessboard = new StandardBoard();
        ArrayList<Piece> pieces;
        Initializer init = new StandardInitializer(chessboard);
        pieces = init.initializePieces();
        chessboard.setPieces(pieces);
    }

    /**
     * Constructor for testing purposes. Initializes a master with
     * a starting board configuration
     * @param board chessboard to be used by the master
     */
    public Master(Board board) { this.chessboard = board; }

    /**
     * Entry method for printing the chessboard in the Board class
     */
    public void printBoard() { chessboard.printBoard(); }

    public void drawBoard() { chessboard.drawBoard(); }

    /**
     * Returns the chess piece at a certain space on the board
     * @param input string containing a pair of x and y
     *              coordinates
     * @return Piece at (file, rank) or null if none exists
     */
    public Piece findPiece(String input) {
        char file = parseFile(input);
        int rank = parseRank(input);
        return chessboard.at(file, rank);
    }

    /**
     * Entry method for moving a piece to a new space
     * @param piece Piece to be moved
     * @param input String containing an (file, rank) coordinate pair (i.e. the
     *              space where piece is going to be moved to)
     * @return 0 for successful moves, -1 otherwise
     */
    public int move(Piece piece, String input) {
        char newFile = parseFile(input);
        int newRank = parseRank(input);
        return move(piece, newFile, newRank);
    }

    /**
     * Determines if piece can move to the coordinate specified in input,
     * and moves it there if can. Otherwise, it will not move the piece,
     * and print an error report.
     * @param piece Piece to be moved
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @return 0 if successful, -1 if not
     */
    public int move(Piece piece, char newFile, int newRank) {
        // store starting position in case we need to revert to old location
        char oldFile = piece.getFile();
        int oldRank = piece.getRank();

        // test if (file, rank) is the current space
        if (oldFile == newFile && oldRank == newRank) { return -1; }

        // see if moving to (file, rank) is legal
        if (!piece.canMove(newFile, newRank, chessboard)) { return -1; }

        // locate the piece at (file, rank) if one exists, and capture it
        Piece captured = chessboard.at(newFile, newRank);
        if (captured != null) { captured.setIsCapturedTrue(); }

        // move the piece to (file, rank)
        piece.move(newFile, newRank);

        // test if moving to (file, rank) leave the King in check
        if (isCheck(piece.getColor()) > 0) {
            // return piece to its former position
            piece.move(oldFile, oldRank);
            // return the captured piece to not captured
            if (captured != null) captured.setIsCapturedFalse();
            return -1;
        }

        return 0;
    }

    /**
     * Determines if the King of a particular color is in check.
     * @param color color of the King we are checking
     * @return 0 if he is not in check. 1 for a single check, and
     *         2 for a double check (or more)
     */
    public int isCheck(int color) {
        Piece king = findKing(color);

        char file = king.getFile();
        int rank = king.getRank();
        int numberOfChecks = 0;

        // see if any piece can attack the king in 1 legal move
        for (Piece piece : chessboard.getPieces()) {
            if ((piece.getColor() != color) &&
                    piece.canMove(file, rank, chessboard)) {
                numberOfChecks++;
                if (numberOfChecks > 1) { return numberOfChecks; }
            }
        }
        // return the number of pieces checking the king
        return numberOfChecks;
    }

    /**
     * Determines if the color passed in as a parameter has
     * any legal moves for its next turn
     * @param color white or black
     * @return true if the color has legal moves available
     */
    public boolean hasLegalMoves(int color) {
        ArrayList<Piece> pieces = pieceArrayList(color);
        for (Piece piece : pieces) {
            ArrayList<Pair<Character, Integer>> moves =
                    moveList(piece);
            if (!moves.isEmpty()) return true;
        }
        return false;
    }

    /**
     * Determines if the king of the specified color has
     * any legal moves for its next turn
     * @param color white or black
     * @return true if the king has legal moves available
     */
    public boolean kingHasLegalMoves(int color) {
        Piece king = findKing(color);
        // see if the King can make any legal moves
        ArrayList<Pair<Character, Integer>> kingMoves =
                moveList(king);
        return !kingMoves.isEmpty();
    }

    /**
     * Determines if the King a particular color is in checkmate, and the
     * game is over
     * @param color color of the King we are checking
     * @return true if he is in checkmate
     */
    public boolean isCheckmate(int color) {
        int numberOfChecks = isCheck(color);

        switch (numberOfChecks) {
            case 1: // single check, see if any pieces have a legal move
                return !hasLegalMoves(color);
            case 2: // double check, see if the king has any legal moves
                return !kingHasLegalMoves(color);
            default: // king is not in check
                return false;
        }
    }

    /**
     * Determines if there is no check, but also no legal moves available
     * @param color Team we are checking
     * @return true if the game ends in a stalemate
     */
    public boolean isStalemate(int color) {
        // make sure the king is not in check, but there are no
        // legal moves available
        return isCheck(color) == 0 && !hasLegalMoves(color);
    }

    /**
     * Accumulates and returns an ArrayList of pieces for a particular
     * color that have not been captured yet
     * @param color Team
     * @return ArrayList of color pieces
     */
    public ArrayList<Piece> pieceArrayList(int color) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece piece : chessboard.getPieces()) {
            if (piece.getColor() == color && !piece.isCaptured()) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public ArrayList< Pair<Character, Integer> > moveList(Piece piece) {
        ArrayList< Pair<Character, Integer> > moves =
                new ArrayList<Pair<Character, Integer>>();

        // store coordinates for resetting after testing each move
        char oldFile = piece.getFile();
        int oldRank = piece.getRank();
        // iterate through the board and see if this piece can move to
        // each space
        for (char rank = 'a'; rank < ('b' + chessboard.getxDimension()); rank++) {
            for (int file = 1; file <= chessboard.getyDimension(); file++) {
                if (move(piece, rank, file) == 0) {
                    piece.move(oldFile, oldRank);
                    moves.add(new Pair(rank, file));
                }
            }
        }
        return moves;
    }

    /**
     * Finds and returns the King for a particular color
     * @param color color of the King we are finding
     * @return the King of a certain color
     */
    private Piece findKing(int color) {
        Piece king = null;
        for (Piece piece : chessboard.getPieces()) {
            if ((piece instanceof King) && (piece.getColor() == color)) {
                king = piece;
                break;
            }
        }
        return king;
    }

    /**
     * Determines the x coordinate defined in a string input
     * @param input String containing a (file, rank) coordinate pair (i.e. the
     *              space where piece is going to be moved to)
     * @return file value
     */
    private char parseFile(String input) {
        assert(input.length() == 2);
        char file = input.charAt(0);
        assert(file >= 'a' && file < ('a' + chessboard.getxDimension()));
        return file;
    }

    /**
     * Determines the y coordinate defined in a string input
     * @param input String containing a (file, rank) coordinate pair (i.e. the
     *              space where piece is going to be moved to)
     * @return rank value
     */
    private int parseRank(String input) {
        assert(input.length() == 2);
        int rank = Integer.parseInt(input.substring((1)));
        assert(rank >= 1 && rank <= chessboard.getyDimension());
        return rank;
    }
}
