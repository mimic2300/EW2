package cz.ophite.ew2.game;

import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.game.json.Difficulty;
import cz.ophite.ew2.game.json.DifficultyProvider;
import cz.ophite.ew2.ui.ScenePane;
import cz.ophite.ew2.util.EventHandler;

public final class GameBoard
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();
    private static final DifficultyProvider DP = DifficultyProvider.getInstance();

    public final GameBoardHandler gameBoardHandler = new GameBoardHandler();

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

            gameBoardHandler.fireGameStateChanged(gameState);
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

    public void updatePlayerMoney()
    {
        player.setMoney(player.getMoney() + getMoneyPerTick());
    }

    public double getMoneyPerTick()
    {
        return (player.getIncome() * getDifficulty().getIncomeModifier()) * CONF.getEnergyRate();
    }

    public double getMoneyPerSecond()
    {
        return (1000.0 / ScenePane.UPDATE_DELAY) * getMoneyPerTick();
    }

    public class GameBoardHandler extends EventHandler<GameBoardListener>
    {
        private void fireGameStateChanged(GameState newState)
        {
            for (GameBoardListener listener : listeners) {
                listener.gameStateChanged(newState);
            }
        }
    }
}
