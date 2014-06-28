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
    private Object[] longestRowPattern = { getLongestRowByName(), 1000000, 1000, true, "" };
    private ShopModelListener modelListener;

    private int totalPrice = 0;
    private float totalIncome = 0;

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
        return getLongestRowPattern()[c].getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return col == 3;
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);

        String resCode = (String) data[row][getLongestRowPattern().length - 1];
        Resource res = RP.getResourceByCode(resCode);

        if (Boolean.TRUE.equals(value)) {
            totalPrice += res.getPrice();
            totalIncome += res.getIncome();
        } else {
            totalPrice -= res.getPrice();
            totalIncome -= res.getIncome();
        }
        totalIncome = Float.valueOf(String.format("%.3f", totalIncome));

        if (modelListener != null) {
            modelListener.checkResource(res, totalPrice, totalIncome);
        }
    }

    private Object[][] prepareData()
    {
        List<Object[]> rows = new ArrayList<Object[]>();

        for (Resource res : RP.getResources()) {
            List<Object> column = new ArrayList<Object>();
            column.add(res.getName());
            column.add(res.getPrice());
            column.add(res.getIncome());
            column.add(false);
            column.add(res.getCode());

            rows.add(column.toArray());
        }
        return rows.toArray(new Object[][] {});
    }

    private String getLongestRowByName()
    {
        if (RP.getResources().isEmpty()) {
            return "";
        }
        Resource longest = RP.getResources().get(0);

        for (Resource res : RP.getResources()) {
            if (res.getName().length() > longest.getName().length()) {
                longest = res;
            }
        }
        return longest.getName();
    }

    public void setModelListener(ShopModelListener modelListener)
    {
        this.modelListener = modelListener;
    }

    public Object[] getLongestRowPattern()
    {
        return longestRowPattern;
    }
}
