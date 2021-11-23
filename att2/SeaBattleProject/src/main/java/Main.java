import game.Game;

import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Game game = new Game();
        game.createGameMenu();
    }
}
