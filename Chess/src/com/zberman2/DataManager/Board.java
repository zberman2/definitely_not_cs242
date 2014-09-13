package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

import java.util.ArrayList;

/**
 * Class describing the functionality of a chess board
 * Created by Zack Berman on 9/10/2014.
 */
public class Board {
    private int xDimension = 8; // spaces in the x direction
    private int yDimension = 8; // spaces in the y dimension
    private int numSides = 4;   // defines the shape of the board
    private ArrayList<Piece> pieces; // list of chess pieces on the board

    /**
     * Blank constructor
     */
    public Board() {}

    /**
     * Constructor which accepts an ArrayList of pieces
     * @param pieces ArrayList of initial chess pieces to be
     *               placed on the board
     */
    public Board(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Setter for the ArrayList of pieces.
     * Used with the blank constructor to construct a blank board, and
     * later add the pieces.
     * @param pieces ArrayList of initial chess pieces to be
     *               placed on the board
     */
    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Getter for pieces
     * @return ArrayList of pieces on the board
     */
    public ArrayList<Piece> getPieces() { return pieces; }

    /**
     * Getter for y dimension of the board
     * @return yDimension
     */
    public int getyDimension() { return yDimension; }

    /**
     * Getter for x dimension of the board
     * @return xDimension
     */
    public int getxDimension() { return xDimension; }

    /**
     * Getter for number of sides of the board
     * (not used, but may be useful if game is changed from a standard
     * square board to something else)
     * @return number of sides on the chess board
     */
    public int getNumSides() { return numSides; }

    /**
     * Method which iterates through the list of pieces and
     * determines if there is a piece at (x, y) on the board.
     * If there is, it returns that piece, null otherwise.
     * @param x x coordinate
     * @param y y coordinate
     * @return The piece at (x, y), null if one doesn't exist
     */
    public Piece at(char x, int y) {
        for (Piece piece : pieces) {
            if (piece.isAt(x,y)) return piece;
        }
        return null;
    }

    /**
     * Prints terminal window chess board
     */
    public void printBoard() {
        // print labels of the columns above the board
        printColumnLabels();
        printTopBorder();
        for (int y = 1; y <= yDimension; y++) {
            // spaces are 3 lines in height
            printTopOfSpaces(y);
            printMiddleOfSpaces(y);
            printBottomOfSpaces(y);
        }
        System.out.println();
        // print column labels below the board too
        printColumnLabels();
    }

    /**
     * Prints characters which correspond to the x coordinates
     * of the board.
     */
    private void printColumnLabels() {
        for (char x = 'a'; x < ('a' + xDimension); x++) {
            System.out.print("      " + x);
        }
        System.out.println(); // new line after labels are printed
    }

    /**
     * Prints a series of underscores which mark the top border of the board
     */
    private void printTopBorder() {
        System.out.print("    "); //indent
        for (char x = 'a'; x < ('a' + xDimension); x++) {
            System.out.print("______ ");
        }
        System.out.println();
    }

    /**
     * Prints the top third of the spaces on the board.
     */
    private void printTopOfSpaces(int row) {
        boolean fill; // true if space is colored in
        // odd rows start with a black space, even with a white space
        if (row % 2 == 1) fill = true;
        else fill = false;

        System.out.print("   |"); //indent plus left border of space
        for (char x = 'a'; x < ('a' + xDimension); x++) {
            if (fill) System.out.print("______|");
            else System.out.print("      |");

            fill = !fill; // toggle fill
        }
        System.out.println();
    }

    /**
     * Prints the middle third of the spaces on the board. This section will
     * either be blank, or contain a chess piece
     * @param row y coordinate on the board
     */
    private void printMiddleOfSpaces(int row) {
        boolean fill; // true if space is colored in
        // odd rows start with a black space, even with a white space
        if (row % 2 == 1) fill = true;
        else fill = false;

        // indent plus row number plus left border of space
        System.out.print(" " + row + " |");
        for (char x = 'a'; x < ('a' + xDimension); x++) {
            Piece temp = at(x, row);
            if (temp == null) {
                // put underscores in the space if fill, spaces otherwise
                if (fill) System.out.print("______|");
                else System.out.print("      |");
            }
            else { // print the piece in chess notation
                String str;
                if (fill) str = "__";
                else str = "  ";

                // place algebraic chess notation for the piece in the
                // middle of the space
                str += temp.colorNotation();
                str += temp.pieceNotation();

                if (fill) str += "__|";
                else str += "  |";
                System.out.print(str);
            }
            fill = !fill; // toggle fill
        }
        System.out.println(" " + row);
    }

    /**
     * Prints the bottom third of the spaces on the board
     */
    private void printBottomOfSpaces(int row) {
        System.out.print("   |"); //indent plus left border of space
        for (char x = 'a'; x < ('a' + xDimension); x++) {
            System.out.print("______|");
        }
        System.out.println();
    }
}
