package cz.ophite.ew2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import com.alee.extended.window.ComponentMoveAdapter;

import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameBoardListener;
import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.game.json.ConfigJson;
import cz.ophite.ew2.game.json.ConfigProvider;
import cz.ophite.ew2.ui.FooterPane;
import cz.ophite.ew2.ui.GameMenuPane;
import cz.ophite.ew2.ui.HeaderPane;
import cz.ophite.ew2.ui.NavigationPane;
import cz.ophite.ew2.ui.base.AbstractFrame;
import cz.ophite.ew2.util.GuiUtil;

@SuppressWarnings("serial")
public final class GameWindow extends AbstractFrame implements GameBoardListener
{
    private static final ConfigJson CONF = ConfigProvider.getInstance().getGameConfig();

    private NavigationPane navigationPane;
    private GameMenuPane gameMenuPane;
    private FooterPane footerPane;
    private HeaderPane headerPane;
    private GameRenderer gameRenderer;

    private GameBoard gameBoard;

    public GameWindow()
    {
        super(CONF.getGameTitle(), CONF.getWindowWidth(), CONF.getWindowHeight(), false);

        setIconImage(GuiUtil.getImage(ImageConst.GAME));

        gameBoard = new GameBoard();
        gameBoard.gameBoardHandler.addListener(this);

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
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });

        gameRenderer = new GameRenderer(this, gameBoard);
        getContent().add(gameRenderer, BorderLayout.CENTER);

        navigationPane = new NavigationPane(this, this, gameBoard, gameRenderer);
        navigationPane.setBackground(getBackground());
        navigationPane.setPreferredSize(new Dimension(123, 0));
        getContent().add(navigationPane, BorderLayout.WEST);

        gameMenuPane = new GameMenuPane(this, this, gameBoard);
        gameMenuPane.setBackground(getBackground());
        gameMenuPane.setPreferredSize(new Dimension(123, 0));
        getContent().add(gameMenuPane, BorderLayout.EAST);

        headerPane = new HeaderPane(this, this);
        headerPane.setBackground(getBackground());
        headerPane.setPreferredSize(new Dimension(0, 5));
        getContent().add(headerPane, BorderLayout.NORTH);

        footerPane = new FooterPane(this, this);
        footerPane.setBackground(getBackground());
        getContent().add(footerPane, BorderLayout.SOUTH);

        gameBoard.setGameState(GameState.MENU);
    }

    @Override
    public void gameStateChanged(GameState newState)
    {
        if (newState == GameState.MENU) {
            gameBoard.getPlayer().clear();
        }
    }
}
