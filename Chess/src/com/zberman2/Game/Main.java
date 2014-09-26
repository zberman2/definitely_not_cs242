package com.zberman2.Game;

import com.zberman2.DataManager.Master;

import java.io.IOException;

/**
 * Class containing the main method (i.e. game loop)
 */
public class Main {

    /**
     * Method containing the game loop
     * @param args empty String array
     */
    public static void main(String[] args) {
        // initialize standard master
        Master master = new Master();
        try {
            // draw GUI, and it will handle the rest
            master.drawBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
