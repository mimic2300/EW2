package cz.ophite.ew2.game.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public final class DifficultyJson
{
    @SerializedName("difficulty")
    private List<Difficulty> difficulty;

    public List<Difficulty> getDifficulty()
    {
        return difficulty;
    }
}
