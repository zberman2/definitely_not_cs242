package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

/**
 * Created by Zack Berman on 9/18/2014.
 * This class contains the methods needed to print a
 * chess board gui to the terminal window
 */
public class TerminalWindowGUI {
    private Board chessboard;

    public TerminalWindowGUI(Board chessboard) {
        this.chessboard = chessboard;
    }

    public void printBoard() {
        // print labels of the columns above the board
        printFileLabels();
        printTopBorder();
        for (int y = chessboard.getyDimension(); y >= 1; y--) {
            // spaces are 3 lines in height
            printTopOfSpaces(y);
            printMiddleOfSpaces(y);
            printBottomOfSpaces(y);
        }
        System.out.println();
        // print column labels below the board too
        printFileLabels();
    }

    /**
     * Prints characters which correspond to the x coordinates
     * of the board.
     */
    private void printFileLabels() {
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
            System.out.print("      " + x);
        }
        System.out.println(); // new line after labels are printed
    }

    /**
     * Prints a series of underscores which mark the top border of the board
     */
    private void printTopBorder() {
        System.out.print("    "); //indent
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
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
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
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
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
            Piece temp = chessboard.at(x, row);
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
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
            System.out.print("______|");
        }
        System.out.println();
    }
}
