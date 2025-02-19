package net.flyfkue.base.util;

import javax.swing.table.AbstractTableModel;

/**
 * 表格模型实现，表格显示数据时将调用模型中的相应方法获取数据进行表格内容的显示
 */
public class MyTableModel extends AbstractTableModel {
    /**
     * 表头（列名）
     */
	private Object[]					columnNames				= { "编号", "筛选结果", "验奖结果" };

    /**
     * 表格所有行数据
     */
	private Object[][]					rowData					= new Object[0][0];

    public Object[][] getRowData() {
		return rowData;
	}

	public void setRowData(Object[][] rowData) {
		this.rowData = rowData;
	}

	/**
     * 返回总行数
     */
    @Override
    public int getRowCount() {
        return rowData.length;
    }

    /**
     * 返回总列数
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * 返回列名称（表头名称），AbstractTableModel 中对该方法的实现默认是以
     * 大写字母 A 开始作为列名显示，所以这里需要重写该方法返回我们需要的列名。
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column].toString();
    }

    /**
     * 返回指定单元格的显示的值
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }
}