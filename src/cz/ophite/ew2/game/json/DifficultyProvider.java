package cz.ophite.ew2.game.json;

import java.util.List;

import cz.ophite.ew2.Game;
import cz.ophite.lib.json.Json;

public final class DifficultyProvider
{
    private static final String RESOURCES_FILE = "data/config/difficulty.json";
    private static final String DEFAULT_DIFFICULTY = "NORMAL";

    private static DifficultyProvider instance;

    private DifficultyJson difficultyJson;

    private DifficultyProvider()
    {}

    public static DifficultyProvider getInstance()
    {
        if (instance == null) {
            instance = new DifficultyProvider();
            instance.difficultyJson = Json.smartLoadSingle(Game.class, RESOURCES_FILE, DifficultyJson.class);
            System.out.printf("Load (%s) = %s%n", DifficultyJson.class.getSimpleName(), instance.difficultyJson != null);
        }
        return instance;
    }

    public DifficultyJson getJson()
    {
        return difficultyJson;
    }

    public List<Difficulty> getDifficulty()
    {
        return difficultyJson.getDifficulty();
    }

    public Difficulty getByCode(String difficultyCode)
    {
        for (Difficulty diff : getDifficulty()) {
            if (diff.getCode().equalsIgnoreCase(difficultyCode)) {
                return diff;
            }
        }
        return null;
    }

    public Difficulty getDefaultDifficulty()
    {
        return getByCode(DEFAULT_DIFFICULTY);
    }

    public boolean isDefaultDifficulty(Difficulty diff)
    {
        return diff.getCode().equalsIgnoreCase(DEFAULT_DIFFICULTY);
    }
}
