package cz.ophite.ew2.game;

import java.util.Observable;

public final class GameBoard extends Observable
{
    private GameState gameState;
    private Player player;

    public GameBoard()
    {
        player = new Player();
    }

    public Player getPlayer()
    {
        return player;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        if (this.gameState != gameState) {
            this.gameState = gameState;

            setChanged();
            notifyObservers(this);
        }
    }
}
