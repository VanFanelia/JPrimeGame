package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Editor: van on 24.07.15.
 */
public class ResultMap {

	private List<IPlayer> playedPlayers = new ArrayList<>();

	private HashMap<IPlayer, Integer> points = new HashMap<>();

	public ResultMap(List<IPlayer> playedPlayers) {
		this.playedPlayers = playedPlayers;
		for(IPlayer player : playedPlayers)
		{
			points.put(player,0);
		}
	}

	public List<IPlayer> getPlayedPlayers() {
		return playedPlayers;
	}

	public void setPlayedPlayers(List<IPlayer> playedPlayers) {
		this.playedPlayers = playedPlayers;
	}

	public HashMap<IPlayer, Integer> getPoints() {
		return points;
	}

	public void setPoints(HashMap<IPlayer, Integer> points) {
		this.points = points;
	}

	public int getSize() {
		return playedPlayers.size();
	}

}
