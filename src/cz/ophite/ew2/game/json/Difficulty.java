package cz.ophite.ew2.game.json;

import com.google.gson.annotations.SerializedName;

import cz.ophite.ew2.game.json.base.UniqueCode;

public final class Difficulty implements UniqueCode
{
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("initial_income")
    private Double initialIncome;

    @SerializedName("initial_money")
    private Double initialMoney;

    @SerializedName("income_modifier")
    private Double incomeModifier;

    @Override
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

    public Double getInitialIncome()
    {
        return initialIncome;
    }

    public Double getInitialMoney()
    {
        return initialMoney;
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
