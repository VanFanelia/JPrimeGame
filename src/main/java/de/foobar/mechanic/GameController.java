package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import de.foobar.ui.GameWindow;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
   * Start a game by adding to groups of players fighting agains each other.
   * Every player from the first group will play against all persons from the 2. group exception against himself.
   * The player from the first group always start a game, so this implementation allows two rounds between each player.
   * @param group1 the first group
   * @param group2 the second group
   */
  public void startGame(List<IPlayer> group1, List<IPlayer> group2) {
    System.out.println("Game Start:");
    System.out.println(group1);
    System.out.println(group2);

    final Set<IPlayer> playedPlayers = new HashSet<>();
    playedPlayers.addAll(group1);
    playedPlayers.addAll(group2);

    // Init Result Table
    this.initResultTable(new ArrayList<>(playedPlayers));

    // Create Rounds
    for (IPlayer player1 : group1) {
      for (IPlayer player2 : group2) {
        if (player1 != player2) {
          this.rounds.add(new Round(player1, player2));
        }
      }
    }

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
