package com.zberman2.Game;

import com.zberman2.DataManager.Master;
import com.zberman2.DataManager.Move;
import com.zberman2.Pieces.Piece;
import javafx.util.Pair;

import java.util.Scanner;
import java.util.Stack;

import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Class describing a game object, in particular, a game run through the
 * terminal window. This game loop was previously stored in the Main file, but
 * it has been moved here.
 * Created by Zack Berman on 9/24/2014.
 */
public class TerminalWindowGame {
    // a terminal window game requires a master
    // move stack, color variable, gameOver variable and a scanner
    Master chessmaster;
    Stack<Move> moveStack;
    int color = WHITE;
    boolean gameOver = false;
    Scanner sc;

    /**
     * No args constructor which sets up a Standard master,
     * a new scanner and an empty move stack
     */
    public TerminalWindowGame() {
        chessmaster = new Master(false);
        moveStack = new Stack<Move>();
        sc = new Scanner(System.in);
    }

    /**
     * Print the board and start the game loop
     */
    public void startGame() {
        chessmaster.printBoard();
        while (!gameOver) {
            System.out.flush();

            Piece piece = promptPiece();
            promptMove(piece);
            color = 1 - color;

            evaluateBoard();
            chessmaster.printBoard();
        }
    }

    /**
     * Prints out the color who is currently up
     */
    public void printColor() {
        if (color == WHITE) {
            System.out.println("Turn: white");
        } else {
            System.out.println("Turn: black");
        }
    }

    public Piece handlePieceInput(String input, Piece piece) {
        if (input.equalsIgnoreCase("q")) {
            // quit, exit with return value 0
            System.exit(0);
        } else if (input.equalsIgnoreCase("u")) {
            if (moveStack.empty()) {
                System.out.println("No moves to undo");
            } else {
                Move lastMove = moveStack.pop();
                lastMove.undo();
                color = 1 - color;
                chessmaster.printBoard();
            }
        } else if (isValidInput(input)) {
            piece = chessmaster.findPiece(input);
            if (piece == null) {
                System.out.println("No piece there, try again...");
            } else if (piece.getColor() != color) {
                System.out.println("This is not your piece...");
                piece = null;
            }
        } else {
            System.out.println("Invalid input");
        }
        return piece;
    }

    public int handleMoveInput(String input, Piece piece) {
        if (!isValidInput(input)) {
            System.out.println("Invalid input");
            return -1;
        }

        // change turns if a valid move was made
        if (chessmaster.canMove(piece, input)) {
            Pair<Character, Integer> oldPosition = piece.getPosition();
            Piece captured = chessmaster.findPiece(input);
            chessmaster.move(piece, input);
            Pair<Character, Integer> newPosition = piece.getPosition();
            moveStack.push(new Move(piece, captured, oldPosition, newPosition));
            return 1;
        } else {
            System.out.println("Illegal move, try again");
            return 0;
        }
    }

    public boolean isValidInput(String input) {
        return input.length() == 2;
    }

    public Piece promptPiece() {
        String input;
        Piece piece = null;
        while (piece == null) {
            // loop until player selects a valid piece
            printColor();

            // prompt player to select a piece to move
            System.out.print("Enter the coordinates of a piece you" +
                    " would like to move. ('q' to quit, 'u' to undo)");
            System.out.println(" (example: a1)");
            input = sc.nextLine();
            piece = handlePieceInput(input, null);
        }
        return piece;
    }

    public void promptMove(Piece piece) {
        String input;
        boolean done = false;

        while (!done) {
            // prompt player to move the selected piece
            System.out.println("Enter the coordinates of where you would" +
                    " like to move your piece.");
            input = sc.nextLine();
            int moveResult = handleMoveInput(input, piece);
            switch (moveResult) {
                case -1:
                    break;
                case 0:
                    piece = promptPiece();
                    break;
                default:
                    done = true;
            }
        }
    }

    public void evaluateBoard() {
        // check for check, checkmate, stalemate
        if (chessmaster.isCheckmate(color)) {
            System.out.println("Checkmate");
            gameOver = true;
        } else if (chessmaster.isStalemate(color)) {
            System.out.println("Stalemate");
            gameOver = true;
        } else if (chessmaster.isCheck(color) > 0) {
            System.out.println("King is in check");
        }
    }
}
