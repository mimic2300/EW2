package cz.ophite.ew2.ui.game;

import cz.ophite.ew2.game.json.Resource;

public interface ShopModelListener
{
    void checkResource(Resource resource, int totalPrice, float totalIncome);
}
