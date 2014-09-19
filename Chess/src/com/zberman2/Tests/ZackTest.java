package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Knight class
 */
public class ZackTest {
    Zack zack;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Knight and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        zack = new Zack(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(zack);
        pieces.add(new Pawn(BLACK, 'e', 3));
        pieces.add(new Pawn(WHITE, 'a', 1));
        pieces.add(new Pawn(BLACK, 'b', 4));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move diagonally, vertically, or horizontally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = zack.getFile();
        int rank = zack.getRank();

        // show that the Knight can move in L shape moves where one leg
        // is 2 squares and the other is 1 square
        assertEquals(true,
                zack.validMotion((char) (file + 2), rank, chessboard));
        assertEquals(true,
                zack.validMotion((char) (file + 2), rank + 2, chessboard));
        assertEquals(true,
                zack.validMotion(file, rank + 2, chessboard));
        assertEquals(true,
                zack.validMotion((char) (file - 2), rank + 2, chessboard));
        assertEquals(false,
                zack.validMotion((char) (file - 1), rank + 2, chessboard));
    }

    /**
     * Test that it can move vertically, horizontally or diagonally, and
     * can jump pieces. It can't move to spaces occupied by it's own color.
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = zack.getFile();
        int rank = zack.getRank();

        // move in L shapes as described above, but to empty spaces, or
        // spaces occupied by the opponent
        assertEquals(true,
                zack.canMove((char) (file + 2), rank, chessboard));
        assertEquals(true,
                zack.canMove((char)(file - 2), rank + 2, chessboard));
        assertEquals(true,
                zack.canMove(file, rank - 2, chessboard));
        // can jump over pieces in the way
        assertEquals(true,
                zack.canMove('f', 2, chessboard));

        // can capture opponent's piece
        assertEquals(true,
                zack.canMove('b', 4, chessboard));

        // cannot move to a space occupied by same color
        assertEquals(false,
                zack.canMove('a', 1, chessboard));
    }
}