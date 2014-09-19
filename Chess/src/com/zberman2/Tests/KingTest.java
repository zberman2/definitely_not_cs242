package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.King;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the King class
 */
public class KingTest {
    King king;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Bishop and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        king = new King(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(king);
        pieces.add(new Pawn(BLACK, 'd', 5));
        pieces.add(new Pawn(WHITE, 'e', 4));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move 1 space horizontally, vertically
     * or diagonally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = king.getFile();
        int rank = king.getRank();

        // King can only move one space horizontally, vertically or
        // diagonally
        assertEquals(true,
                king.validMotion((char) (file + 1), rank + 1, chessboard));
        assertEquals(true,
                king.validMotion((char)(file - 1), rank, chessboard));
        assertEquals(false,
                king.validMotion(file, rank + 2, chessboard));
    }

    /**
     * Test that it can only move 1 space horizontally, vertically
     * or diagonally to open spaces on the board
     *
     * note that the canMove method does not determine if the king is left
     * in check after the move. That is the job of the move method in Master
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = king.getFile();
        int rank = king.getRank();

        assertEquals(true,
                king.canMove((char)(file + 1), rank + 1, chessboard));
        assertEquals(true,
                king.canMove(file, rank-1, chessboard));
        assertEquals(false,
                king.canMove(file, rank + 2, chessboard));

        // white piece is in the way
        assertEquals(false,
                king.canMove('e', 4, chessboard));
        // can capture the black piece in front of it
        assertEquals(true,
                king.canMove('d', 5, chessboard));
    }
}