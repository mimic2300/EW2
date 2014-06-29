package cz.ophite.ew2.ui.base;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;

import cz.ophite.ew2.util.SystemCheck;

@SuppressWarnings("serial")
public abstract class AbstractFrame extends WebFrame
{
    private JPanel contentPane;

    public AbstractFrame(String title, int width, int height, boolean resizable)
    {
        super(title);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, width, height);
        setResizable(resizable);
        setRound(SystemCheck.isWindows() ? 6 : 0);
        setShadeWidth(SystemCheck.isWindows() ? 20 : 0);
        setLocationRelativeTo(this);
        setIconImages(WebLookAndFeel.getImages());

        if (!resizable) {
            addWindowStateListener(e -> {
                if (e.getNewState() == Frame.MAXIMIZED_BOTH) {
                    setExtendedState(Frame.NORMAL);
                }
            });
        }
        contentPane = new JPanel();
        contentPane.setBackground(getBackground());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    protected JPanel getContent()
    {
        return contentPane;
    }
}
