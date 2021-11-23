package serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameContext {

    private static final Logger log = LoggerFactory.getLogger(GameContext.class);

    private char[][] battleFieldPlayer1;
    private char[][] battleFieldPlayer2;
    private char[][] hitFieldPlayer1;
    private char[][] hitFieldPlayer2;
    private int currPlayer;

    public char[][] getBattleFieldPlayer1() {
        return battleFieldPlayer1;
    }

    public char[][] getBattleFieldPlayer2() {
        return battleFieldPlayer2;
    }

    public char[][] getHitFieldPlayer1() {
        return hitFieldPlayer1;
    }

    public char[][] getHitFieldPlayer2() {
        return hitFieldPlayer2;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public GameContext(char[][] battleFieldPlayer1, char[][] battleFieldPlayer2, char[][] hitFieldPlayer1, char[][] hitFieldPlayer2, int currPlayer) {
        this.battleFieldPlayer1 = battleFieldPlayer1;
        this.battleFieldPlayer2 = battleFieldPlayer2;
        this.hitFieldPlayer1 = hitFieldPlayer1;
        this.hitFieldPlayer2 = hitFieldPlayer2;
        this.currPlayer = currPlayer;
    }

    public GameContext() {

    }

    public static void save(String saveName, GameContext gc) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File savesDir = FileUtils.getAbsolutePathOfSavesDirectory().toFile();
        if (savesDir.mkdir()) {
            log.info("Saves directory " + savesDir.getAbsolutePath() + " was made");
        }
        File save = new File(savesDir.getAbsolutePath(), saveName + ".json");
        if (save.createNewFile()) {
            log.info("Save file " + save.getAbsolutePath() + " was made");
        }
        objectMapper.writeValue(save, gc);
    }

    public static GameContext read(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return objectMapper.readValue(file, GameContext.class);
    }

}
