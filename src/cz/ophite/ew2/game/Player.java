package cz.ophite.ew2.game;

public final class Player
{
    private String name;

    public Player()
    {
        clear();
    }

    public void clear()
    {
        name = null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
