package cz.ophite.ew2.game;

import java.util.EventListener;

public interface GameBoardListener extends EventListener
{
    void gameStateChanged(GameState newState);
}
