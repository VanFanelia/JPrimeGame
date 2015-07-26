package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Editor: van on 23.07.15.
 */
public class Round {

	private IPlayer player1;

	private IPlayer player2;

	private int scorePlayer1 = 0;

	private int scorePlayer2 = 0;

	public Round(IPlayer player1, IPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public IPlayer getPlayer1() {
		return player1;
	}

	public void setPlayer1(IPlayer player1) {
		this.player1 = player1;
	}

	public IPlayer getPlayer2() {
		return player2;
	}

	public void setPlayer2(IPlayer player2) {
		this.player2 = player2;
	}

	public List<IPlayer> getBoth() {
		List<IPlayer> result = new ArrayList<>();
		result.add(this.player1);
		result.add(this.player2);
		return result;
	}

	public int getScoreOfPlayer(IPlayer player) {
		if(player.equals(this.player1))
		{
			return this.scorePlayer1;
		}
		return scorePlayer2;
	}

	public int getScorePlayer1() {
		return scorePlayer1;
	}

	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	public int getScorePlayer2() {
		return scorePlayer2;
	}

	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("player1", player1)
				.append("player2", player2)
				.append("scorePlayer1", scorePlayer1)
				.append("scorePlayer2", scorePlayer2)
				.toString();
	}
}
