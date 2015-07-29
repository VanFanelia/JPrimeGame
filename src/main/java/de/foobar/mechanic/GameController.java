package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import de.foobar.mechanic.runable.GameWorker;
import de.foobar.ui.GameWindow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultBoundedRangeModel;
import org.reflections.Reflections;

public class GameController {

  public static final int DEFAULT_GAME_RANGE = 1000;

  public GameWindow gameWindow;

  private List<IPlayer> players = new ArrayList<>();

  private List<Round> rounds = new ArrayList<>();

  private ResultMap results;

  private int roundsPlayed = 0;

  private Round currentRound;

  private Boolean gameRunning = false;

  private DefaultBoundedRangeModel progressBarModel;

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

    this.gameRunning = true;
    this.gameWindow.deactivateStartGame();

    final Set<IPlayer> playedPlayers = new HashSet<>();
    playedPlayers.addAll(playerList);

    // Init Result Table
    this.initResultTable(new ArrayList<>(playedPlayers));
    this.progressBarModel = new DefaultBoundedRangeModel();
    this.gameWindow.initProgressBar(this.progressBarModel);

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
    GameWorker worker = new GameWorker(this);
    worker.execute();


  }

  private void initResultTable(List<IPlayer> playedPlayers) {
    this.results = new ResultMap(playedPlayers);
    this.gameWindow.getTableModel().setResults(results);
    this.gameWindow.update(gameWindow.getGraphics());
  }

  /**
   * start a game.
   */
  public void startGame() {
    this.roundsPlayed = 0;
    for (Round round : this.rounds) {
      this.startRound(round);

      // finished:
      this.results.addResult(round);
      this.roundsPlayed++;
    }

  }

  private void startRound(Round round) {

    Round lastRound = this.currentRound;
    this.currentRound = round;
    this.gameWindow.setCurrentPlayersPlayers(round.getPlayer1(), round.getPlayer2());
    this.gameWindow.setLastRound(lastRound);
    NumberComparator comparator = new NumberComparator();

    // init numbers
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i < DEFAULT_GAME_RANGE; i++ ) {
      numbers.add(i);
    }

    // start playing against each other
    boolean playerOneTurn = true;
    this.progressBarModel.setMaximum(DEFAULT_GAME_RANGE);
    while (!numbers.isEmpty()) {
      IPlayer currentPlayer = playerOneTurn ? round.getPlayer1() : round.getPlayer2();
      IPlayer opponent = playerOneTurn ? round.getPlayer2() : round.getPlayer1();

      // start turn for player
      int[] transfer = new int[numbers.size()];
      numbers.sort(comparator);
      for (int i = 0; i < numbers.size(); i++) {
        transfer[i] = numbers.get(i);
      }
      // TODO set maximum time limit
      int pickedNumber = currentPlayer.pickNumber(transfer, round.getScoreOfPlayer(currentPlayer), round.getScoreOfPlayer(opponent));
      System.out.println(currentPlayer.getPlayerName() + " picked: " + pickedNumber);

      // sleep to refresh the ui
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      giveNumberToPlayer(pickedNumber, currentPlayer, round);
      List<Integer> primFactors = givePrimeToPlayer(pickedNumber, numbers, round, opponent);
      numbers.remove(new Integer(pickedNumber)); //force Object to remove value
      numbers.removeAll(primFactors);

      playerOneTurn = !playerOneTurn;

      // setProgressBar
      this.progressBarModel.setValue(this.progressBarModel.getMaximum() - numbers.size());
      System.out.println(this.progressBarModel.getValue() + " : " + this.progressBarModel.getMaximum());
    }

  }

  private List<Integer> givePrimeToPlayer(int pickedNumber, List<Integer> numbers, Round round, IPlayer opponent) {
    List<Integer> primFactors = MathHelper.getPrimeFactors(pickedNumber, numbers);
    for (Integer prime : primFactors) {
      round.addScore(opponent, prime);
    }
    return primFactors;
  }


  private void giveNumberToPlayer(int pickedNumber, IPlayer currentPlayer, Round round) {
    round.addScore(currentPlayer, pickedNumber);
  }


  public void gameFinished() {
    this.gameWindow.activateStartGame();
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
