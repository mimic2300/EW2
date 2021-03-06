package cz.ophite.ew2.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.ophite.ew2.game.json.Resource;
import cz.ophite.ew2.game.json.ResourceProvider;
import cz.ophite.ew2.util.EventHandler;

public final class Player
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    public final PlayerHandler playerHandler = new PlayerHandler();

    private String name;
    private double money;
    private double income;
    private Map<String, Integer> resources = new HashMap<String, Integer>();

    public Player()
    {
        clear();
    }

    public void clear()
    {
        name = "";
        money = 0;
        income = 0;
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

    public double getMoney()
    {
        return money;
    }

    public void setMoney(double money)
    {
        if (this.money != money) {
            this.money = money;
            playerHandler.fireMoneyChanged(money);
        }
    }

    public double getIncome()
    {
        return income;
    }

    public void setIncome(double income)
    {
        this.income = income;
    }

    public Map<String, Integer> getResources()
    {
        return resources;
    }

    public void buyResource(Resource res)
    {
        boolean added = false;

        if (!resources.containsKey(res.getCode())) {
            if (res.getMaxLimit() > 0) {
                resources.put(res.getCode(), 1);
                added = true;
            }
        } else {
            int count = resources.get(res.getCode());

            if (count < res.getMaxLimit()) {
                resources.remove(res.getCode());
                resources.put(res.getCode(), count + 1);
                added = true;
            }
        }
        if (added) {
            setMoney(getMoney() - res.getPrice());
            calculateIncome();
        }
    }

    public boolean sellResource(Resource res)
    {
        if (resources.containsKey(res.getCode())) {
            int count = resources.remove(res.getCode());

            if (count > 1) {
                resources.put(res.getCode(), count - 1);
            }
            setMoney(getMoney() + (res.getPrice() / 2));
            calculateIncome();
            return true;
        }
        return false;
    }

    public int getResourceCountOf(Resource res)
    {
        if (resources.containsKey(res.getCode())) {
            return resources.get(res.getCode());
        }
        return 0;
    }

    private void calculateIncome()
    {
        double total = 0;

        for (Entry<String, Integer> entry : resources.entrySet()) {
            Resource res = RP.getResourceByCode(entry.getKey());
            total += Double.valueOf(String.format("%.3f", res.getIncome() * entry.getValue()));
        }
        setIncome(total);
    }

    public class PlayerHandler extends EventHandler<PlayerListener>
    {
        private void fireMoneyChanged(double money)
        {
            for (PlayerListener listener : listeners) {
                listener.moneyChanged(money);
            }
        }
    }
}
