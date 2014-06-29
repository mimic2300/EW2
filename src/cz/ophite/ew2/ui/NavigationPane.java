package cz.ophite.ew2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alee.laf.button.WebButton;

import cz.ophite.ew2.ImageConst;
import cz.ophite.ew2.game.GameBoard;
import cz.ophite.ew2.game.GameBoardListener;
import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.game.GameState;
import cz.ophite.ew2.ui.base.AbstractFrame;
import cz.ophite.ew2.ui.base.AbstractPane;
import cz.ophite.ew2.ui.game.NewGameDialog;
import cz.ophite.ew2.util.GuiUtil;

@SuppressWarnings("serial")
public class NavigationPane extends AbstractPane implements GameBoardListener
{
    private final Random rand = new Random();

    private GameBoard gameBoard;
    private GameRenderer gameRenderer;

    private WebButton btnNewGame;

    public NavigationPane(Window gameWindow, Component owner, GameBoard gameBoard, GameRenderer gameRenderer)
    {
        super(gameWindow, owner);
        this.gameBoard = gameBoard;
        this.gameRenderer = gameRenderer;
        gameBoard.gameBoardHandler.addListener(this);

        setLayout(new FlowLayout());

        btnNewGame = new WebButton("New Game");
        btnNewGame.setPreferredSize(new Dimension(100, 24));
        btnNewGame.addActionListener(e -> onNewGame());
        btnNewGame.setIcon(GuiUtil.getIcon(ImageConst.NEW_GAME));
        add(btnNewGame);

        WebButton btnScreen = new WebButton("Screenshot");
        btnScreen.setPreferredSize(new Dimension(100, 24));
        btnScreen.addActionListener(e -> onScreenshot());
        btnScreen.setIcon(GuiUtil.getIcon(ImageConst.SCREENSHOT));
        add(btnScreen);

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

    private void onScreenshot()
    {
        String format = new SimpleDateFormat("dd.mm.yyyy_HH-mm-ss.SSS").format(new Date(System.currentTimeMillis()));
        String fileName = String.format("ew2_%s.png", format);
        System.out.println("Screenshot created: " + fileName);
        gameRenderer.exportToPng(fileName);
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
