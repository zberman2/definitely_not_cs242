package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import com.zberman2.Pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Rook class
 */
public class RookTest {
    Rook rook;
    Board chessboard;
    ArrayList<Piece> pieces;

    /**
     * Create a single Rook and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        rook = new Rook(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(rook);
        pieces.add(new Pawn(BLACK, 'g', 4));
        pieces.add(new Pawn(WHITE, 'd', 6));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move horizontally or vertically
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = rook.getFile();
        int rank = rook.getRank();

        assertEquals(true,
                rook.validMotion((char)(file+2), rank, chessboard));
        assertEquals(true,
                rook.validMotion(file, rank-3, chessboard));
        assertEquals(false,
                rook.validMotion((char)(file+2), rank+1, chessboard));
    }

    /**
     * Test that it can only move horizontally or vertically to open
     * spaces on the board with no pieces in between the starting
     * and ending points
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = rook.getFile();
        int rank = rook.getRank();

        assertEquals(true,
                rook.canMove((char) (file - 2), rank, chessboard));
        assertEquals(true,
                rook.canMove(file, rank - 3, chessboard));
        assertEquals(false,
                rook.canMove((char) (file - 2), rank + 1, chessboard));

        // can capture opponent's piece
        assertEquals(true,
                rook.canMove('g', 4, chessboard));

        // can't capture own piece
        assertEquals(false,
                rook.canMove('d', 6, chessboard));

        // can't jump pieces
        assertEquals(false,
                rook.canMove('d', 8, chessboard));
    }
}