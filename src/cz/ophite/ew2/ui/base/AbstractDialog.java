package cz.ophite.ew2.ui.base;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;

import cz.ophite.ew2.util.SystemCheck;

@SuppressWarnings("serial")
public abstract class AbstractDialog extends WebDialog
{
    private Window gameWindow;
    private boolean parentVisible = false;

    public AbstractDialog(Window gameWindow, String title, int width, int height)
    {
        super(gameWindow);
        this.gameWindow = gameWindow;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setModal(true);
        setRound(SystemCheck.isWindows() ? 6 : 0);
        setShadeWidth(SystemCheck.isWindows() ? 20 : 0);
        setIconImages(WebLookAndFeel.getImages());
        setTitle(title);
        setLocationRelativeTo(getOwner());

        addGameWindowChangeState();
    }

    public Window getGameWindow()
    {
        return gameWindow;
    }

    public boolean getParentVisible()
    {
        return parentVisible;
    }

    public void setVisibleEx(boolean value)
    {
        super.setVisible(value);
        parentVisible = value;
    }

    private void addGameWindowChangeState()
    {
        gameWindow.addWindowStateListener(e -> {
            if (e.getNewState() == Frame.ICONIFIED && parentVisible && !isModal()) {
                setVisible(false);
            }
            if (e.getNewState() == Frame.NORMAL && parentVisible && !isModal()) {
                setVisible(true);
            }
        });
    }
}
