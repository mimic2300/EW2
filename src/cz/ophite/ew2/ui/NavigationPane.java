package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.ImageConst;
import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameBoardListener;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.ui.base.AbstractFrame;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.NewGameDialog;
import cz.ophite.ew2.util.GuiUtil;

@SuppressWarnings("serial")
public class NavigationPane extends AbstractPane implements GameBoardListener
{
    private GameBoard gameBoard;

    private WebButton btnNewGame;

    public NavigationPane(Window gameWindow, Component owner, GameBoard gameBoard)
    {
        super(gameWindow, owner);
        this.gameBoard = gameBoard;
        gameBoard.gameBoardHandler.addListener(this);

        setLayout(new FlowLayout());

        btnNewGame = new WebButton("New Game");
        btnNewGame.setPreferredSize(new Dimension(100, 24));
        btnNewGame.addActionListener(e -> onNewGame());
        btnNewGame.setIcon(GuiUtil.getIcon(ImageConst.NEW_GAME));
        add(btnNewGame);

        WebButton btnQuit = new WebButton("Quit");
        btnQuit.setPreferredSize(new Dimension(100, 24));
        btnQuit.addActionListener(e -> onQuit());
        btnQuit.setIcon(GuiUtil.getIcon(ImageConst.CLOSE));
        add(btnQuit);
    }

    private void onNewGame()
    {
        NewGameDialog dlg = new NewGameDialog(getGameWindow(), gameBoard);
        dlg.setVisible(true);
    }

    private void onQuit()
    {
        WindowEvent we = new WindowEvent(((AbstractFrame) getOwner()), WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(we);
    }

    @Override
    public void gameStateChanged(GameState newState)
    {
        switch (newState) {
            case MENU:
                btnNewGame.setVisible(true);
                break;

            case PLAY:
                btnNewGame.setVisible(false);
                break;
        }
    }
}
