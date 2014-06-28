package cz.ophite.ew2.game.json;

import java.util.Collections;
import java.util.List;

import cz.ophite.ew2.Game;
import cz.ophite.lib.json.Json;

public final class ResourceProvider
{
    private static final String RESOURCES_FILE = "data/config/resource.json";

    private static ResourceProvider instance;

    private ResourceJson resourceJson;

    private ResourceProvider()
    {}

    public static ResourceProvider getInstance()
    {
        if (instance == null) {
            instance = new ResourceProvider();
            instance.resourceJson = Json.smartLoadSingle(Game.class, RESOURCES_FILE, ResourceJson.class);
            System.out.printf("Load (%s) = %s%n", ResourceJson.class.getSimpleName(), instance.resourceJson != null);
            Collections.sort(instance.resourceJson.getResources());
        }
        return instance;
    }

    public List<Resource> getResources()
    {
        return resourceJson.getResources();
    }

    public Resource getResourceByCode(String resourceCode)
    {
        for (Resource res : resourceJson.getResources()) {
            if (res.getCode().equalsIgnoreCase(resourceCode)) {
                return res;
            }
        }
        return null;
    }
}
