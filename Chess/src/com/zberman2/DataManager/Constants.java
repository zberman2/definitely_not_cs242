package com.zberman2.DataManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class containing constants used across the Chess project
 * Created by Zack Berman on 9/11/2014.
 */
public final class Constants {
    // chars for representing pieces in the terminal window
    public static final char PAWN = 'P', ROOK = 'R', KNIGHT = 'N', // K is taken by King
            BISHOP = 'B', KING = 'K', QUEEN = 'Q', DUKE = 'D', ZACK = 'Z';

    // indexes of the piece images in the URL below
    public static final int KING_IMAGE_INDEX = 0, QUEEN_IMAGE_INDEX = 1,
            ROOK_IMAGE_INDEX = 2, KNIGHT_IMAGE_INDEX = 3,
            BISHOP_IMAGE_INDEX = 4, PAWN_IMAGE_INDEX = 5;

    public static final int BLACK = 0, WHITE = 1;

    public static final Color illiniOrange = new Color(244, 127, 36);
    public static final Color illiniBlue = new Color(0, 60, 125);

    // the following Constants are utilized by the GUI class
    public static final int FONT_SIZE = 37;
    public static final int TOOLBAR_FONT_SIZE = 14;
    public static final String FONT = "MONOSPACED";
    public static final int EMPTY_BORDER_THICKNESS = 15;
    public static final int LINE_BORDER_THICKNESS = 10;
    public static final int IMAGE_DIMENSION = 64;
    public static final int HIGHLIGHT_FACTOR = 100;
    public static final int LOWLIGHT_FACTOR = 50;

    // location of the chess piece images for the GUI
    public static final String CHESS_PIECES_URL = "http://i.stack.imgur.com/memI0.png";

    // files corresponding to images used in the game
    public static File chessPieces = new File("C:/cs242/Chess/Docs/chesspieces.png");
    public static File victoryBadgeFile = new File("C:/cs242/Chess/Docs/victorybadge.jpg");
    public static File blockIFile = new File("C:/cs242/Chess/Docs/block-i.jpg");
    public static File blockI2File = new File("C:/cs242/Chess/Docs/block-i-2.png");
    public static File chiefFile = new File("C:/cs242/Chess/Docs/chief.jpg");
    public static File zackFile = new File("C:/cs242/Chess/Docs/zack.jpg");
    public static File dukeFile = new File("C:/cs242/Chess/Docs/duke.jpg");

    // images for logos in the corners of the GUI
    public static Image victoryBadge;
    public static Image blockI;
    public static Image blockI2;
    public static Image chief;

    /**
     * Method used for dividing up the image of all 12 chess pieces into
     * individual 64 pixel x 64 pixel images of each piece
     *
     * New: set up logo images for the corners of the frame
     * Function credit:
     * http://stackoverflow.com/questions/21142686/making-a-robust-resizable-chess-gui
     */
    public static void createImages() {
        try {
            // set up victory badge image
            victoryBadge = ImageIO.read(victoryBadgeFile);
            victoryBadge = victoryBadge.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

            // set up new block I image
            blockI = ImageIO.read(blockIFile);
            blockI = blockI.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

            // set up alternative block I image
            blockI2 = ImageIO.read(blockI2File);
            blockI2 = blockI2.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

            // set up chief image
            chief = ImageIO.read(chiefFile);
            chief = chief.getScaledInstance(64, 64, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Static method used to convert an image file to its negative rgb
     * components
     * @param imageFile image to manipulate
     * @param color either BLACK or WHITE
     * @return the negative version of the image
     */
    public static BufferedImage getNegative(File imageFile, int color) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (color == BLACK) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    int rgba = bufferedImage.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    col = new Color(255 - col.getRed(),
                            255 - col.getGreen(),
                            255 - col.getBlue());
                    bufferedImage.setRGB(x, y, col.getRGB());
                }
            }
        }
        return bufferedImage;
    }

    private Constants() {
        // this prevents even the native class from calling
        // this constructor
        throw new AssertionError();
    }
}
