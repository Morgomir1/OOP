package main.java.game;

import game.Game;

import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Game.initializeGame();
    }


}
