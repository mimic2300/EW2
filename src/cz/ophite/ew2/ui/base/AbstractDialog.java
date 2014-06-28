package cz.ophite.ew2.ui.base;

import java.awt.Component;

import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;

import cz.ophite.ew2.util.SystemCheck;

@SuppressWarnings("serial")
public abstract class AbstractDialog extends WebDialog
{
    public AbstractDialog(Component owner, String title, int width, int height)
    {
        super(owner);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setModal(true);
        setRound(SystemCheck.isWindows() ? 6 : 0);
        setShadeWidth(SystemCheck.isWindows() ? 20 : 0);
        setIconImages(WebLookAndFeel.getImages());
        setTitle(title);
        setLocationRelativeTo(getOwner());

        initComponents();
    }

    protected abstract void initComponents();
}
