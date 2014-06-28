package cz.ophite.ew2.game.json;

import com.google.gson.annotations.SerializedName;

public final class Difficulty
{
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("energyBoostPerTick")
    private Float energyBoostPerTick;

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Float getEnergyBoostPerTick()
    {
        return energyBoostPerTick;
    }

    @Override
    public String toString()
    {
        return code;
    }
}
