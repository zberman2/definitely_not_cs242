package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.Duke;
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
public class DukeTest {
    Duke duke;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Knight and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        duke = new Duke(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(duke);
        pieces.add(new Pawn(BLACK, 'e', 3));
        pieces.add(new Pawn(WHITE, 'd', 6));
        pieces.add(new Pawn(BLACK, 'b', 4));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move 2 spaces horizontally, vertically,
     * or horizontally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = duke.getFile();
        int rank = duke.getRank();

        assertEquals(true,
                duke.validMotion((char) (file + 2), rank, chessboard));
        assertEquals(false,
                duke.validMotion((char)(file + 1), rank + 2, chessboard));
        assertEquals(true,
                duke.validMotion(file, rank + 2, chessboard));
        assertEquals(true,
                duke.validMotion((char)(file - 2), rank + 2, chessboard));
    }

    /**
     * Test that it can only move 2 spaces vertically, horizontally,
     * or vertically to open spaces, or to opponent's spaces
     * on the board
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = duke.getFile();
        int rank = duke.getRank();

        // move in L shapes as described above, but to empty spaces, or
        // spaces occupied by the opponent
        assertEquals(true,
                duke.canMove((char) (file + 2), rank, chessboard));
        assertEquals(false,
                duke.canMove((char)(file + 1), rank + 2, chessboard));
        assertEquals(true,
                duke.canMove(file, rank - 2, chessboard));
        // can jump over pieces in the way
        assertEquals(true,
                duke.canMove('f', 2, chessboard));

        // can capture opponent's piece
        assertEquals(true,
                duke.canMove('b', 4, chessboard));

        // cannot move to a space occupied by same color
        assertEquals(false,
                duke.canMove('d', 6, chessboard));
    }
}