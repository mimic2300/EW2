package cz.ophite.ew2.game;

public enum Difficulty
{
    NEWBIE("Newbie"),
    EASY("Easy"),
    NORMAL("Normal", true),
    HARD("Hard"),
    BRUTAL("Brutal");

    private String name;
    private boolean initial;

    private Difficulty(String name, boolean initial)
    {
        this.name = name;
        this.initial = initial;
    }

    private Difficulty(String name)
    {
        this(name, false);
    }

    public static Difficulty getInitial()
    {
        for (Difficulty diff : values()) {
            if (diff.isInitial()) {
                return diff;
            }
        }
        return null;
    }

    public static Difficulty getByName(String name)
    {
        for (Difficulty diff : values()) {
            if (diff.name.equals(name)) {
                return diff;
            }
        }
        return null;
    }

    public String getName()
    {
        return name;
    }

    public boolean isInitial()
    {
        return initial;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
