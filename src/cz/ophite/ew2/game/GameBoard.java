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
    private long startTime;
    private long elapsedTime;

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

            switch (gameState) {
                case MENU:
                    player.clear();
                    break;

                case PLAY:
                    player.setIncome(difficulty.getInitialIncome());
                    player.setMoney(difficulty.getInitialMoney());
                    startTime = System.currentTimeMillis();
                    break;
            }
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

    public void update()
    {
        player.setMoney(player.getMoney() + getMoneyPerTick());
        elapsedTime = System.currentTimeMillis() - startTime;
    }

    public double getMoneyPerTick()
    {
        double baseIncome = player.getIncome() + difficulty.getInitialIncome();
        return (baseIncome * getDifficulty().getIncomeModifier()) * CONF.getEnergyRate();
    }

    public double getMoneyPerSecond()
    {
        return toMoneyToSecond(getMoneyPerTick());
    }

    public static double toMoneyToSecond(double moneyPerTick)
    {
        return (1000.0 / ScenePane.UPDATE_DELAY) * moneyPerTick;
    }

    public long getElapsedTime()
    {
        return elapsedTime;
    }

    public String getElapsedTimeFormat()
    {
        final long time = elapsedTime / 1000;
        String out = String.format("%02ds ", time % 60);

        if ((time % 3600) / 60 > 0) {
            out = String.format("%02dm ", (time % 3600) / 60) + out;
        }
        if (time / 3600 > 0) {
            out = String.format("%03dh ", time / 3600) + out;
        }
        return out;
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
