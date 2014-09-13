package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;
import javafx.util.Pair;

import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Abstract class describing a generic chess piece
 * Created by Zack Berman on 9/10/2014.
 */
public abstract class Piece {
    private int color; // 0 for white, 1 for black
    private Pair<Character, Integer> position; // (x, y) coordinate
    private boolean isCaptured = false;

    /**
     * Constructor for a Piece object. Sets the color and initial position
     * @param color 0 or 1 (white or black)
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    public Piece(int color, char x, int y) {
        this.color = color;
        this.position = new Pair(x, y);
    }

    /**
     * Called when a piece is captured in the game
     */
    public void setIsCapturedTrue() { isCaptured = true; }

    /**
     * Called to revert the capturing of a piece
     */
    public void setIsCapturedFalse() { isCaptured = false; }

    /**
     * Method that determines if the piece can make a valid move to
     * location (a, b) on the board
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @param board current instance of the chess board
     * @return true if moving to (a, b) is valid
     */
    public boolean canMove(char a, int b, Board board) {
        int xMax = board.getxDimension();
        int yMax = board.getyDimension();
        // make sure (a, b) is on the board
        if (a < 'a' || a >= ('a' + xMax) || b < 1 || b > yMax) {
            return false;
        }

        if (a == getX() && b == getY()) return false; // current location

        // call the pieces specific validMotion method
        if (!validMotion(a, b, board)) { return false; }

        // check that (a, b) is either unoccupied, or occupied
        // by one of the opponent's pieces
        Piece piece = board.at(a,b);
        if (piece == null) { return true; }
        else { return piece.getColor() != this.getColor(); }
    }

    /**
     * Boolean method used by all subclasses of Piece
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @param board current instance of the chess board
     * @return true if moving to (a, b) is valid
     */
    abstract boolean validMotion(char a, int b, Board board);

    /**
     * Changes the position of the Piece to a new space
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     */
    public void move(char a, int b) {
        position = new Pair(a,b);
    }

    /**
     * Determines if the Piece is currently located at (a, b) and
     * not yet captured by the opponent
     * @param a x coordinate
     * @param b y coordinate
     * @return true if the piece is not captured and located at (a, b)
     */
    public boolean isAt(char a, int b) {
        return (!isCaptured) && ((getX() == a) && (getY() == b));
    }

    /**
     * Getter for the Piece's color
     * @return color
     */
    public int getColor() { return color; }

    /**
     * Getter for x coordinate
     * @return x
     */
    public char getX() { return (char)position.getKey(); }

    /**
     * Getter for y coordinate
     * @return y
     */
    public int getY() { return (int)position.getValue(); }

    /**
     * Getter for isCaptured boolean variable
     * @return isCaptured
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Returns the character representing the color of the Piece
     * @return 'w' for white pieces, 'b' for black pieces
     */
    public char colorNotation() {
        if (color == WHITE) return 'w';
        else return 'b';
    }

    /**
     * Returns the character representing the type of Piece
     * @return Piece character (see Constants for possible values)
     */
    public abstract char pieceNotation();

    /**
     * Determines if (a, b) is located diagonally away from the Piece's
     * current location
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @return true if (a, b) is diagonally away from (x, y)
     */
    public boolean isDiagonalMotion(char a, int b) {
        int xDifference = xDifference(a);
        int yDifference = yDifference(b);

        return xDifference == yDifference;
    }

    /**
     * Determines if (a, b) is located vertically away from the Piece's
     * current location
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @return true if (a, b) is vertically away from (x, y)
     */
    public boolean isVerticalMotion(char a, int b) {
        return xDifference(a) == 0;
    }

    /**
     * Determines if (a, b) is located horizontally away from the Piece's
     * current location
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @return true if (a, b) is horizontally away from (x, y)
     */
    public boolean isHorizontalMotion(char a, int b) {
        return yDifference(b) == 0;
    }

    /**
     * Given a diagonal path, this method determines if there are any pieces
     * located between the Piece's current position and its destination, (a, b)
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @param board current instance of the chess board
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenDiagonalPath(char a, int b, Board board) {
        if (!isDiagonalMotion(a, b)) return false;
        int difference = xDifference(a);
        // xDifference and yDifference will return the same value for
        // diagonal paths

        // incrementX will be 1 if a > current x coordinate, and -1 if not
        int incrementX = (a - getX()) / difference;
        // incrementY will be 1 if b > current y coordinate, and -1 if not
        int incrementY = (b - getY()) / difference;

        // check all spaces in a diagonal path between (x, y) and (a, b)
        for (int i = 2; i <= difference; i++) {
            int tempX = getX() + (incrementX*i) - incrementX;
            int tempY = getY() + (incrementY*i) - incrementY;

            // return false if we find a piece at (tempX, tempY)
            if (board.at((char)tempX, tempY) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Given a vertical path, this method determines if there are any pieces
     * located between the Piece's current position and its destination, (a, b)
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @param board current instance of the chess board
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenVerticalPath(char a, int b, Board board) {
        if (!isVerticalMotion(a, b)) return false;
        // xDifference is 0 for vertical paths
        int difference = yDifference(b);

        // incrementY will be 1 if b > current y coordinate, -1 if not
        int incrementY = (b - getY()) / difference;

        // check all spaces in a vertical path between (x, y) and (a, b)
        for (int i = 2; i <= difference; i++) {
            int tempY = getY() + (incrementY*i) - incrementY;

            // return false if we find a piece at (tempX, tempY)
            if (board.at(a, tempY) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Given a horizontal path, this method determines if there are any pieces
     * located between the Piece's current position and its destination, (a, b)
     * @param a x coordinate of new space
     * @param b y coordinate of new space
     * @param board current instance of the chess board
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenHorizontalPath(char a, int b, Board board) {
        if (!isHorizontalMotion(a, b)) return false;
        // yDifference is 0 for horizontal paths
        int difference = xDifference(a);

        // incrementX will be 1 if a > current x coordinate, -1 if not
        int incrementX = (a - getX()) / difference;

        // check all spaces in a horizontal path between (x, y) and (a, b)
        for (int i = 2; i <= difference; i++) {
            int tempX = getX() + (incrementX*i) - incrementX;

            // return false if we find a piece at (tempX, tempY)
            if (board.at((char)tempX, b) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Method that determines the absolute value of the difference between
     * input a and the current x coordinate
     * @param a x coordinate of new space
     * @return difference between x and a
     */
    public int xDifference(char a) {
        int x1 = getX();
        int x2 = (int)a;
        return Math.abs(x1-x2);
    }

    /**
     * Method that determines the absolute value of the difference between
     * input b and the current y coordinate
     * @param b y coordinate of new space
     * @return difference between y and b
     */
    public int yDifference(int b) {
        int y1 = getY();
        int y2 = b;
        return Math.abs(y1 - y2);
    }

    /**
     * toString Method for a Piece
     * Prints the color and (x, y) position
     * @return A string containing the color and position of the Piece
     */
    @Override
    public String toString() {
        return "Piece{" +
                "color=" + getColor() +
                ", x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
