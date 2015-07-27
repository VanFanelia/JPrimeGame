package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import de.foobar.ui.GameWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class GameController {

  public GameWindow gameWindow;

  private List<IPlayer> players = new ArrayList<>();

  private List<Round> rounds = new ArrayList<>();

  private ResultMap results;

  private int roundsPlayed = 0;

  private Round currentRound;

  private Round lastRound;

  public GameController() {
  }

  public void setGameWindow(GameWindow gameWindow) {
    this.gameWindow = gameWindow;
  }

  /**
   * Start a game by adding a group of players fighting agains each other exception against himself.
   * @param playerList the list of players
   */
  public void startGame(List<IPlayer> playerList) {
    System.out.println("Game Start:");
    System.out.println(playerList);

    final Set<IPlayer> playedPlayers = new HashSet<>();
    playedPlayers.addAll(playerList);

    // Init Result Table
    this.initResultTable(new ArrayList<>(playedPlayers));

    // Create Rounds
    for (IPlayer player1 : playerList) {
      for (IPlayer player2 : playerList) {
        if (player1 != player2) {
          this.rounds.add(new Round(player1, player2));
          this.rounds.add(new Round(player2, player1));
        }
      }
    }

    // shuffle games
    Collections.shuffle(this.rounds);

    // start rounds:
    this.roundsPlayed = 0;
    for (Round round: this.rounds) {
      this.startRound(round);
      // finished:
      this.roundsPlayed++;
    }
  }

  private void initResultTable(List<IPlayer> playedPlayers) {
    this.results = new ResultMap(playedPlayers);
    this.gameWindow.getTableModel().setResults(results);
    this.gameWindow.update(gameWindow.getGraphics());
  }

  private void startRound(Round round) {
    this.lastRound = this.currentRound;
    this.currentRound = round;
    this.gameWindow.setCurrentPlayersPlayers(round.getPlayer1(), round.getPlayer2());
    this.gameWindow.setLastRound(this.lastRound);
    this.gameWindow.setProgress(roundsPlayed, this.getRoundCount());
  }

  public int getRoundCount() {
    return this.rounds.size();
  }

  /**
   * search for all Classes with IPlayer interface implemented.
   * @return a list of IPlayer
   */
  public List<IPlayer> searchClassPathForPlayerList() {
    if (this.players.isEmpty()) {
      Reflections reflections = new Reflections("");

      Set<Class<? extends IPlayer>> playerClasses = reflections.getSubTypesOf(IPlayer.class);

      for (Class playerClass : playerClasses) {
        try {
          players.add((IPlayer) playerClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
          System.err.println("Cannot create instance of class");
        }
      }
    }
    return this.players;
  }


}
