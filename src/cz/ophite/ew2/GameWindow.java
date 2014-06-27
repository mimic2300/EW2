package cz.ophite.ew2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.alee.extended.window.ComponentMoveAdapter;

import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.ui.FooterPane;
import cz.ophite.ew2.ui.NavigationPane;
import cz.ophite.ew2.ui.StatisticsPane;
import cz.ophite.ew2.ui.base.AbstractFrame;

@SuppressWarnings("serial")
public final class GameWindow extends AbstractFrame
{
    private NavigationPane navigationPane;
    private StatisticsPane statisticsPane;
    private FooterPane footerPane;
    private GameRenderer gameRenderer;

    private Player player;

    public GameWindow()
    {
        super(Game.GAME_TITLE, 720, 480, false);

        player = new Player();
    }

    @Override
    protected void initComponents()
    {
        ComponentMoveAdapter.install(getRootPane(), GameWindow.this);

        navigationPane = new NavigationPane(this, player);
        navigationPane.setBackground(getBackground());
        navigationPane.setPreferredSize(new Dimension(100, 0));
        getContent().add(navigationPane, BorderLayout.WEST);

        statisticsPane = new StatisticsPane(this);
        statisticsPane.setBackground(getBackground());
        statisticsPane.setPreferredSize(new Dimension(100, 0));
        getContent().add(statisticsPane, BorderLayout.EAST);

        footerPane = new FooterPane(this);
        footerPane.setBackground(getBackground());
        getContent().add(footerPane, BorderLayout.SOUTH);

        gameRenderer = new GameRenderer(this, player);
        getContent().add(gameRenderer, BorderLayout.CENTER);
    }
}
