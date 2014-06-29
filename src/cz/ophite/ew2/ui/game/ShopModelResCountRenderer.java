package cz.ophite.ew2.ui.game;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import cz.ophite.ew2.game.Player;
import cz.ophite.ew2.game.json.Resource;
import cz.ophite.ew2.game.json.ResourceProvider;

@SuppressWarnings("serial")
public class ShopModelResCountRenderer extends DefaultTableCellRenderer
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    private static final Color COLOR_LIMIT = new Color(220, 140, 140);
    private static final Color COLOR = new Color(230, 230, 230);

    private Player player;
    private final Set<String> maxLimitRows = new HashSet<String>();

    public ShopModelResCountRenderer(Player player)
    {
        this.player = player;

        for (Resource res : RP.getResources()) {
            if (res.getMaxLimit() == 0) {
                maxLimitRows.add(res.getCode());
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column)
    {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ShopModel model = (ShopModel) table.getModel();

        if (player.getResources().size() > 0) {
            for (Entry<String, Integer> entry : player.getResources().entrySet()) {
                Resource res = RP.getResourceByCode(entry.getKey());
                Resource rowRes = model.getResource(row);

                if (entry.getValue() >= res.getMaxLimit() && res.getCode().equals(rowRes.getCode())) {
                    maxLimitRows.add(res.getCode());
                } else {
                    maxLimitRows.remove(res.getCode());
                }
            }
        } else {
            maxLimitRows.clear();
        }
        if (!maxLimitRows.isEmpty()) {
            for (String resCode : maxLimitRows) {
                Resource rowRes = model.getResource(row);

                if (rowRes.getCode().equals(resCode)) {
                    com.setBackground(COLOR_LIMIT);
                } else {
                    com.setBackground(COLOR);
                }
            }
        } else {
            com.setBackground(COLOR);
        }
        return com;
    }
}
