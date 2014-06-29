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

    @SerializedName("incomeModifier")
    private Double incomeModifier;

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

    public Double getIncomeModifier()
    {
        return incomeModifier;
    }

    @Override
    public String toString()
    {
        return code;
    }
}
