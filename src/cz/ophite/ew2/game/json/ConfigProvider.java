package cz.ophite.ew2.game.json;

import cz.ophite.ew2.Game;
import cz.ophite.lib.json.Json;

public final class ConfigProvider
{
    private static final String CONFIG_FILE = "data/config/game.json";

    private static ConfigProvider instance;

    private ConfigJson gameConfigJson;

    private ConfigProvider()
    {}

    public static ConfigProvider getInstance()
    {
        if (instance == null) {
            instance = new ConfigProvider();
            instance.gameConfigJson = Json.smartLoadSingle(Game.class, CONFIG_FILE, ConfigJson.class);
            System.out.printf("Load (%s) = %s%n", ConfigJson.class.getSimpleName(), instance.gameConfigJson != null);
        }
        return instance;
    }

    public ConfigJson getGameConfig()
    {
        return gameConfigJson;
    }
}
