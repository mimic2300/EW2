package cz.ophite.ew2.ui.game;

import java.util.EventListener;

public interface ShopModelListener extends EventListener
{
    void resourceChecked(double price, double income);
}
