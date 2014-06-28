package cz.ophite.ew2.ui.game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import cz.ophite.ew2.game.json.Resource;
import cz.ophite.ew2.game.json.ResourceProvider;

@SuppressWarnings("serial")
public class ShopModel extends AbstractTableModel
{
    private static final ResourceProvider RP = ResourceProvider.getInstance();

    private String[] columnNames = { "Name", "Price", "Income", "Buy" };
    private Object[][] data = prepareData();

    public final Object[] longestRowPattern = { getLongestRowByName(), 1000000, 1000, true };

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return data.length;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }

    @Override
    public Class<?> getColumnClass(int c)
    {
        return longestRowPattern[c].getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return col >= 3;
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    private Object[][] prepareData()
    {
        List<Object[]> rows = new ArrayList<Object[]>();

        for (Resource res : RP.getResources()) {
            List<Object> column = new ArrayList<Object>();
            column.add(res.getName());
            column.add(res.getPrice());
            column.add(res.getEnergyPerTick());
            column.add(false);

            rows.add(column.toArray());
        }
        return rows.toArray(new Object[][] {});
    }

    private String getLongestRowByName()
    {
        Resource longest = RP.getResources().get(0);

        for (Resource res : RP.getResources()) {
            if (res.getName().length() > longest.getName().length()) {
                longest = res;
            }
        }
        return longest.getName();
    }
}
