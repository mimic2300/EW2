package cz.ophite.ew2.game;

import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;

public final class Player
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private String name;
    private int money;

    public Player()
    {
        clear();
    }

    public void clear()
    {
        name = null;
        money = CONF.getInitialMoney();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }
}
