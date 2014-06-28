package cz.ophite.ew2.game;

import java.util.Observable;

import cz.ophite.ew2.game.json.Difficulty;
import cz.ophite.ew2.game.json.DifficultyProvider;

public final class GameBoard extends Observable
{
    private static final DifficultyProvider DP = DifficultyProvider.getInstance();

    private GameState gameState;
    private Difficulty difficulty;
    private Player player;

    public GameBoard()
    {
        player = new Player();
        difficulty = DP.getDefaultDifficulty();
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

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
}
