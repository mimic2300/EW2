package cz.ophite.ew2.game;

import java.util.EventListener;

public interface PlayerListener extends EventListener
{
    void moneyChanged(double money);
}
