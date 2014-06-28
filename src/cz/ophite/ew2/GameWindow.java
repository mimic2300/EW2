package cz.ophite.ew2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.alee.extended.window.ComponentMoveAdapter;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.FooterPane;
import cz.ophite.ew2.ui.NavigationPane;
import cz.ophite.ew2.ui.ShopPane;
import cz.ophite.ew2.ui.base.AbstractFrame;

@SuppressWarnings("serial")
public final class GameWindow extends AbstractFrame implements Observer
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private NavigationPane navigationPane;
    private ShopPane shopPane;
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
        gameBoard.addObserver(this);

        ComponentMoveAdapter.install(getRootPane(), GameWindow.this);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (gameBoard.getGameState() == GameState.PLAY) {
                    int result = JOptionPane.showConfirmDialog(getOwner(),
                            String.format("Are you sure you want to quit %s ?", CONF.getGameTitle()),
                            "Quit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });

        navigationPane = new NavigationPane(this, gameBoard);
        navigationPane.setBackground(getBackground());
        navigationPane.setPreferredSize(new Dimension(100, 0));
        getContent().add(navigationPane, BorderLayout.WEST);

        shopPane = new ShopPane(this);
        shopPane.setBackground(getBackground());
        shopPane.setPreferredSize(new Dimension(100, 0));
        getContent().add(shopPane, BorderLayout.EAST);

        footerPane = new FooterPane(this);
        footerPane.setBackground(getBackground());
        getContent().add(footerPane, BorderLayout.SOUTH);

        gameRenderer = new GameRenderer(this, gameBoard);
        getContent().add(gameRenderer, BorderLayout.CENTER);

        gameBoard.setGameState(GameState.MENU);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        GameBoard board = (GameBoard) arg;

        switch (board.getGameState()) {
            case MENU:
                shopPane.setVisible(false);
                break;

            case PLAY:
                shopPane.setVisible(true);
                break;
        }
    }
}
