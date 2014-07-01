package cz.ophite.ew2.game.json;

import java.awt.Dimension;

import com.google.gson.annotations.SerializedName;

public final class ConfigJson
{
    @SerializedName("game_title")
    private String gameTitle;

    @SerializedName("game_version")
    private String gameVersion;

    @SerializedName("window_width")
    private Integer windowWidth;

    @SerializedName("window_height")
    private Integer windowHeight;

    @SerializedName("energy_rate")
    private Double energyRate;

    public String getGameTitle()
    {
        return gameTitle;
    }

    public String getGameVersion()
    {
        return gameVersion;
    }

    public Integer getWindowWidth()
    {
        return windowWidth;
    }

    public Integer getWindowHeight()
    {
        return windowHeight;
    }

    public Dimension getWindowSize()
    {
        return new Dimension(windowWidth, windowHeight);
    }

    public Double getEnergyRate()
    {
        return energyRate;
    }
}
