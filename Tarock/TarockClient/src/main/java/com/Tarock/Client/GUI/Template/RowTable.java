package com.Tarock.Client.GUI.Template;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RowTable extends JTable
{
    private Map<Integer, Color> rowColor = new HashMap<>();
    public RowTable(TableModel model)
    {
        super(model);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
    {
        Component c = super.prepareRenderer(renderer, row, column);

        if (!isRowSelected(row))
        {
            Color color = rowColor.get( row );
            c.setBackground(color == null ? getBackground() : color);
        }

        return c;
    }

    public void setRowColor(int row, Color color)
    {
        rowColor.put(row, color);
    }
}

