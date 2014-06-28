package cz.ophite.ew2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import com.alee.extended.window.ComponentMoveAdapter;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.FooterPane;
import cz.ophite.ew2.ui.NavigationPane;
import cz.ophite.ew2.ui.StatisticsPane;
import cz.ophite.ew2.ui.base.AbstractFrame;

@SuppressWarnings("serial")
public final class GameWindow extends AbstractFrame
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private NavigationPane navigationPane;
    private StatisticsPane statisticsPane;
    private FooterPane footerPane;
    private GameRenderer gameRenderer;

    private GameBoard gameBoard;

    public GameWindow()
    {
        super(CONF.getGameTitle(), CONF.getWindowWidth(), CONF.getWindowHeight(), false);
    }

    @Override
    protected void initComponents()
    {
        gameBoard = new GameBoard();

        ComponentMoveAdapter.install(getRootPane(), GameWindow.this);

        navigationPane = new NavigationPane(this, gameBoard.getPlayer());
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

        gameRenderer = new GameRenderer(this, gameBoard);
        getContent().add(gameRenderer, BorderLayout.CENTER);
    }
}
