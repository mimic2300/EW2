package cz.ophite.ew2.game;

public final class Player
{
    private String name;
    private Difficulty difficulty;

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
