package cz.ophite.ew2.game.json;

import java.util.Collections;
import java.util.List;

import cz.ophite.ew2.Game;
import cz.ophite.ew2.util.Utils;
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

            if (instance.resourceJson != null) {
                List<Resource> uniqueCheck = Utils.uniqueCheck(instance.getResources());

                if (uniqueCheck.isEmpty()) {
                    Collections.sort(instance.resourceJson.getResources());
                } else {
                    Utils.print(uniqueCheck);
                }
            }
        }
        return instance;
    }

    public ResourceJson getJson()
    {
        return resourceJson;
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
