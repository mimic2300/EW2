package cz.ophite.ew2.game.json;

import com.google.gson.annotations.SerializedName;

public final class Resource implements Comparable<Resource>
{
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Double price;

    @SerializedName("income")
    private Double income;

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public Double getPrice()
    {
        return price;
    }

    public Double getIncome()
    {
        return income;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Resource o)
    {
        return price.compareTo(o.price);
    }
}
