package cz.ophite.ew2.game;

import cz.ophite.ew2.game.json.ResourceProvider;

public final class GameBoard
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    private Player player;

    public GameBoard()
    {
        player = new Player();
    }

    public Player getPlayer()
    {
        return player;
    }
}
