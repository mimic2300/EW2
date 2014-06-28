package cz.ophite.ew2.game;

import java.util.HashMap;
import java.util.Map;

import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.game.json.Resource;

public final class Player
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private String name;
    private long money;
    private float income;
    private Map<String, Integer> resources = new HashMap<String, Integer>();

    public Player()
    {
        clear();
    }

    public void clear()
    {
        name = null;
        money = CONF.getInitialMoney();
        income = CONF.getInitialIncome();
        resources.clear();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getMoney()
    {
        return money;
    }

    public void setMoney(long money)
    {
        this.money = money;
    }

    public float getIncome()
    {
        return income;
    }

    public void setIncome(float income)
    {
        this.income = income;
    }

    public Map<String, Integer> getResources()
    {
        return resources;
    }

    public void addResource(Resource res)
    {
        if (!resources.containsKey(res.getCode())) {
            resources.put(res.getCode(), 1);
        } else {
            int count = resources.remove(res.getCode());
            resources.put(res.getCode(), count + 1);
        }
    }

    public boolean removeResource(Resource res)
    {
        if (resources.containsKey(res.getCode())) {
            int count = resources.remove(res.getCode());

            if (count > 1) {
                resources.put(res.getCode(), count - 1);
            }
            return true;
        }
        return false;
    }
}
