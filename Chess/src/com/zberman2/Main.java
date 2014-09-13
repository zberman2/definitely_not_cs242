package com.zberman2;

import com.zberman2.DataManager.Master;
import com.zberman2.Pieces.Piece;

import java.util.Scanner;
import static com.zberman2.DataManager.Constants.*;

/**
 * Class containing the main method (i.e. game loop)
 * Note, the following code is messy and currently for debugging purposes.
 * It will be refactored for assignments beyond 1.0
 */
public class Main {

    /**
     * Method containing the game loop
     * @param args empty String array
     */
    public static void main(String[] args) {
        // initialize a master to allow us to manipulate the game
        Master chessmaster = new Master();

        // set up a scanner to read user input
        Scanner sc = new Scanner(System.in);
        int color = WHITE; // starting color
        while (true) {
            System.out.flush();
            chessmaster.printBoard();

            Piece piece = null;
            String input;
            while (piece == null) {
                // loop until player selects a valid piece
                if (color == WHITE) {
                    System.out.println("Turn: white");
                } else {
                    System.out.println("Turn: black");
                }

                // prompt player to select a piece to move
                System.out.print("Enter the coordinates of a piece you" +
                        " would like to move. (or 'q' to quit)");
                System.out.println(" (example: a1)");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    // quit, exit with return value 0
                    System.exit(0);
                }

                piece = chessmaster.findPiece(input);
                if (piece == null) {
                    System.out.println("No piece there, try again...");
                } else if (piece.getColor() != color) {
                    System.out.println("This is not your piece...");
                    piece = null;
                }
            }

            // prompt player to move the selected piece
            System.out.println("Enter the coordinates of where you would" +
                    " like to move your piece.");
            input = sc.nextLine();
            int success = chessmaster.move(piece, input);

            // change turns if a valid move was made
            if (success == 0) {
                color = 1 - color;
            } else {
                System.out.println("Illegal move, try again");
            }

            // check for check, checkmate, stalemate
            if (chessmaster.isCheck(color) > 0) {
                System.out.println("King is in check");
            }
            if (chessmaster.isCheckmate(color)) {
                System.out.println("Checkmate");
            }
            if (chessmaster.isStalemate(color)) {
                System.out.println("Stalemate");
            }
        }
    }
}
