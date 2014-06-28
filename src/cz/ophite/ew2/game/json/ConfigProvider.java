package cz.ophite.ew2.game.json;

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
            instance.gameConfigJson = Json.smartLoadSingle(ConfigProvider.class, CONFIG_FILE, ConfigJson.class);
        }
        return instance;
    }

    public ConfigJson getGameConfig()
    {
        return gameConfigJson;
    }
}
