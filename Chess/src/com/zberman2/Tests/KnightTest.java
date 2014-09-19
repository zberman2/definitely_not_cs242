package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.Knight;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Knight class
 */
public class KnightTest {
    Knight knight;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Knight and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        knight = new Knight(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(knight);
        pieces.add(new Pawn(BLACK, 'f', 3));
        pieces.add(new Pawn(WHITE, 'f', 5));
        pieces.add(new Pawn(BLACK, 'd', 5));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move in L shapes where one leg is
     * 2 sides long and the other is 1 side long
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = knight.getFile();
        int rank = knight.getRank();

        // show that the Knight can move in L shape moves where one leg
        // is 2 squares and the other is 1 square
        assertEquals(true,
                knight.validMotion((char) (file + 2), rank + 1, chessboard));
        assertEquals(true,
                knight.validMotion((char)(file + 1), rank + 2, chessboard));
        assertEquals(false,
                knight.validMotion(file, rank + 2, chessboard));
    }

    /**
     * Test that it can only move in L shapes to open spaces
     * on the board
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = knight.getFile();
        int rank = knight.getRank();

        // move in L shapes as described above, but to empty spaces, or
        // spaces occupied by the opponent
        assertEquals(true,
                knight.canMove((char) (file + 2), rank - 1, chessboard));
        assertEquals(true,
                knight.canMove((char)(file + 1), rank + 2, chessboard));
        assertEquals(false,
                knight.canMove(file, rank + 2, chessboard));
        // can jump over pieces in the way
        assertEquals(true,
                knight.canMove('e', 6, chessboard));

        // can capture opponent's piece
        assertEquals(true,
                knight.canMove('f', 3, chessboard));

        // cannot move to a space occupied by same color
        assertEquals(false,
                knight.canMove('f', 5, chessboard));
    }
}