package cz.ophite.ew2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;

import cz.ophite.ew2.game.GameRenderer;
import cz.ophite.ew2.ui.FooterPane;
import cz.ophite.ew2.ui.NavigationPane;
import cz.ophite.ew2.ui.StatisticsPane;

@SuppressWarnings("serial")
public final class GameWindow extends WebFrame
{
    private JPanel contentPane;
    private NavigationPane navigationPane;
    private StatisticsPane statisticsPane;
    private FooterPane footerPane;
    private GameRenderer gameRenderer;

    public GameWindow()
    {
        super();
        initComponents();
    }

    private void initComponents()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 720, 480);
        setRound(6);
        setShadeWidth(20);
        setResizable(false);
        setLocationRelativeTo(this);
        setIconImages(WebLookAndFeel.getImages());
        setTitle(Game.GAME_TITLE);

        ComponentMoveAdapter.install(getRootPane(), GameWindow.this);

        contentPane = new JPanel();
        contentPane.setBackground(getBackground());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        navigationPane = new NavigationPane(this);
        navigationPane.setBackground(getBackground());
        navigationPane.setPreferredSize(new Dimension(100, 0));
        contentPane.add(navigationPane, BorderLayout.WEST);

        statisticsPane = new StatisticsPane(this);
        statisticsPane.setBackground(getBackground());
        statisticsPane.setPreferredSize(new Dimension(100, 0));
        contentPane.add(statisticsPane, BorderLayout.EAST);

        footerPane = new FooterPane(this);
        footerPane.setBackground(getBackground());
        contentPane.add(footerPane, BorderLayout.SOUTH);

        gameRenderer = new GameRenderer(this);
        contentPane.add(gameRenderer, BorderLayout.CENTER);
    }
}
