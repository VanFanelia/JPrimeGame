package de.foobar.ui.elements;

import static de.foobar.ui.helper.ImageSizeHelper.getScaledImage;

import de.foobar.mechanic.ResultMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class PlayerResultTableModel extends DefaultTableModel {

  private ResultMap results;

  private String[] columnNames = {"Picture","Name","Points"};

  public PlayerResultTableModel() {
  }

  public PlayerResultTableModel(ResultMap results) {
    super(results.getPlayedPlayers().size(), 3);
    this.results = results;
  }

  public void setResults(ResultMap results) {
    this.results = results;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public int getRowCount() {
    if (results == null) {
      return 0;
    }
    return results.getSize();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int row, int col) {
    switch (col) {
      case 0:
        return new ImageIcon(getScaledImage(this.results.getPlayedPlayers().get(row).getPlayerImage()));
      case 1:
        return this.results.getPlayedPlayers().get(row).getPlayerName();
      case 2:
        return this.results.getPoints().get(results.getPlayedPlayers().get(row));
      default:
        return null;
    }
  }

  @Override
  public Class<?> getColumnClass(int column) {
    if (column == 0) {
      return Icon.class;
    }

    return super.getColumnClass(column);
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void setValueAt(Object value, int row, int col) {
    System.err.println("not supported!");
  }

}
