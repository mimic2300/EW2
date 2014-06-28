package cz.ophite.ew2.game;

import cz.ophite.ew2.game.json.Difficulty;
import cz.ophite.ew2.game.json.DifficultyProvider;

public final class Player
{
    private static final DifficultyProvider DP = DifficultyProvider.getInstance();

    private String name;
    private Difficulty difficulty;

    public Player()
    {
        clear();
    }

    public void clear()
    {
        name = null;
        difficulty = DP.getDefaultDifficulty();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
}
