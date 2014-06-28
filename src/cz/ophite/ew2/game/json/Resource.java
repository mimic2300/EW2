package cz.ophite.ew2.game.json;

import com.google.gson.annotations.SerializedName;

public final class Resource implements Comparable<Resource>
{
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Integer price;

    @SerializedName("energyPerTick")
    private Float energyPerTick;

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }

    public float getEnergyPerTick()
    {
        return energyPerTick;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Resource o)
    {
        return name.compareTo(o.name);
    }
}
