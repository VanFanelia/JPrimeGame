package de.foobar.mechanic;

import de.foobar.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ResultMap {

  private List<IPlayer> playedPlayers = new ArrayList<>();

  private HashMap<IPlayer, Integer> points = new HashMap<>();

  /**
   * Constructor initalise the game result list.
   * @param playedPlayers the players which fight against each other.
   */
  public ResultMap(List<IPlayer> playedPlayers) {
    this.playedPlayers = playedPlayers;
    for (IPlayer player : playedPlayers) {
      points.put(player,0);
    }
  }

  public void addResult(Round round) {
    points.put(round.getPlayer1(),points.get(round.getPlayer1()) + round.getScorePlayer1());
    points.put(round.getPlayer2(),points.get(round.getPlayer2()) + round.getScorePlayer2());
  }

  public List<IPlayer> getPlayedPlayers() {
    return playedPlayers;
  }

  public HashMap<IPlayer, Integer> getPoints() {
    return points;
  }

  public int getSize() {
    return playedPlayers.size();
  }

}
