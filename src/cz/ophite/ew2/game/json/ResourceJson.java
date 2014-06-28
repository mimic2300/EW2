package cz.ophite.ew2.game.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public final class ResourceJson
{
    @SerializedName("resources")
    private List<Resource> resources;

    public List<Resource> getResources()
    {
        return resources;
    }
}
