package de.foobar.ui.elements;

import de.foobar.mechanic.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;


public class PlayerList extends JList<IPlayer> {

  public static final int VISIBLE_ROW_COUNT = 3;

  private List<IPlayer> playerList = new ArrayList<>();

  /**
   * Default constructor.
   * @param playerList a list of players
   */
  public PlayerList(List<IPlayer> playerList) {
    this.playerList = playerList;

    this.setCellRenderer(new PlayerListItemRenderer());

    this.refreshList();
    this.setVisibleRowCount(VISIBLE_ROW_COUNT);
    this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  }

  /**
   * Refresht the player list by converting the model "playerlist" to IPlayer array.
   */
  public void refreshList() {
    IPlayer[] playerListArray = new IPlayer[this.playerList.size()];
    for (int i = 0; i < this.playerList.size() ; i++) {
      playerListArray[i] = this.playerList.get(i);
    }
    this.setListData(playerListArray);
  }

  @Override
  public List<IPlayer> getSelectedValuesList() {
    List<IPlayer> result = new ArrayList<>();
    for (Object o : super.getSelectedValuesList()) {
      result.add((IPlayer) o);
    }
    return result;
  }
}
