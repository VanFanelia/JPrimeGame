package de.foobar.mechanic;

import de.foobar.IPlayer;
import de.foobar.mechanic.runable.GameWorker;
import de.foobar.ui.GameWindow;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultBoundedRangeModel;

public class GameController {

  public static final int DEFAULT_GAME_RANGE = 1000;

  public GameWindow gameWindow;

  private List<IPlayer> players = new ArrayList<>();

  private List<Round> rounds = new ArrayList<>();

  private ResultMap results;

  private int roundsPlayed = 0;

  private Round currentRound;

  private Boolean gameRunning = false;

  private DefaultBoundedRangeModel progressBarRoundModel;
  private DefaultBoundedRangeModel progressBarGameModel;

  public int millisecondsDelayForPick = 500;
  private int numberPoolSize = DEFAULT_GAME_RANGE;

  public GameController() {
  }

  public void setGameWindow(GameWindow gameWindow) {
    this.gameWindow = gameWindow;
  }

  /**
   * start a game in background.
   */
  public void startGameInBackground() {
    this.roundsPlayed = 0;

    int currentGamePool = getNumberPoolSize();
    System.out.println("Game Pool is:" + currentGamePool);
    this.progressBarGameModel.setValue(this.roundsPlayed);
    this.progressBarGameModel.setMaximum(this.rounds.size());

    for (Round round : this.rounds) {
      this.startRound(round, currentGamePool);

      // finished:
      this.results.addResult(round);
      this.roundsPlayed++;
      this.progressBarGameModel.setValue(this.roundsPlayed);
      this.updateResultTable();
    }
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
    this.progressBarRoundModel = new DefaultBoundedRangeModel();
    this.progressBarGameModel = new DefaultBoundedRangeModel();
    this.gameWindow.initProgressBar(this.progressBarRoundModel, this.progressBarGameModel);

    this.rounds = new ArrayList<>();


    // Create Rounds
    for (IPlayer player1 : playerList) {
      for (IPlayer player2 : playerList) {
        if (player1 != player2) {
          this.rounds.add(new Round(player1, player2));
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

  private void updateResultTable() {
    this.gameWindow.getTableModel().setResults(results);
    this.gameWindow.update(gameWindow.getGraphics());
  }


  private void startRound(Round round, int currentGamePool) {

    Round lastRound = this.currentRound;
    this.currentRound = round;
    this.gameWindow.setCurrentPlayersPlayers(round.getPlayer1(), round.getPlayer2());
    this.gameWindow.setLastRound(lastRound);
    NumberComparator comparator = new NumberComparator();

    // init numbers
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i < currentGamePool; i++ ) {
      numbers.add(i);
    }

    // start playing against each other
    boolean playerOneTurn = true;
    this.progressBarRoundModel.setMaximum(currentGamePool);
    try {
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

        if (! numbers.contains(pickedNumber)) {
          throw new CheatDetectedException("Player picked a invalid number", currentPlayer);
        }

        // sleep to refresh the ui
        try {
          Thread.sleep(this.millisecondsDelayForPick);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        giveNumberToPlayer(pickedNumber, currentPlayer, round);
        List<Integer> divisors = giveDivisorsToPlayer(pickedNumber, numbers, round, opponent);
        numbers.remove(new Integer(pickedNumber)); //force Object to remove value
        numbers.removeAll(divisors);

        System.out.println(currentPlayer.getPlayerName() + " picked: " + pickedNumber + " enemy get: " + divisors);

        playerOneTurn = !playerOneTurn;

        // setProgressBar
        this.progressBarRoundModel.setValue(this.progressBarRoundModel.getMaximum() - numbers.size());

        // set points and picked:
        this.gameWindow.setCurrentPlayerPoints(pickedNumber, divisors, round, currentPlayer);
      }
    } catch (CheatDetectedException e) {
      System.out.println("Cheater detected: " + e.getCheater().getPlayerName() );
      if (this.currentRound.getPlayer1().equals(e.getCheater())) {
        this.currentRound.setScorePlayer1(0);
        giveAllNumbersToPlayer(this.currentRound, this.currentRound.getPlayer2(), numbers);
      } else {
        this.currentRound.setScorePlayer2(0);
        giveAllNumbersToPlayer(this.currentRound, this.currentRound.getPlayer1(), numbers);
      }

      // setProgressBar
      //this.progressBarRoundModel.setValue(this.progressBarRoundModel.getMaximum());
      //this.gameFinished();

    }
  }

  private void giveAllNumbersToPlayer(Round round, IPlayer player, List<Integer> numbers) {
    for (int number: numbers){
      round.addScore(player, number);
    }
  }

  private List<Integer> giveDivisorsToPlayer(int pickedNumber, List<Integer> numbers, Round round, IPlayer opponent) {
    List<Integer> primFactors = MathHelper.getDivisors(pickedNumber, numbers);
    for (Integer prime : primFactors) {
      round.addScore(opponent, prime);
    }
    return primFactors;
  }


  private void giveNumberToPlayer(int pickedNumber, IPlayer currentPlayer, Round round) {
    round.addScore(currentPlayer, pickedNumber);
  }

  /**
   * Called when a game is finshed. Reset progress bars.
   */
  public void gameFinished() {
    this.gameWindow.activateStartGame();
    this.progressBarGameModel.setMaximum(1);
    this.progressBarGameModel.setValue(1);
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

  /**
   * Add Players to possible player list.
   * @param newPlayerDirectory new players to add
   */
  public void addAdditionalPlayers(File newPlayerDirectory) {

    List<IPlayer> toAdd = new ArrayList<>();
    File[] files;
    try {
      // Convert File to a URL
      URL url = newPlayerDirectory.toURI().toURL();
      URL[] urls;
      if (newPlayerDirectory.isDirectory()) {
        files = newPlayerDirectory.listFiles();
        if (files == null) {
          return;
        }
        urls = new URL[ files.length ];
        for (int i = 0; i < files.length; i++) {
          urls[i] = files[i].toURI().toURL();
        }
      } else {
        files = new File[]{newPlayerDirectory};
        urls = new URL[]{url};
      }


      // Create a new class loader with the directory
      ClassLoader classLoader = new URLClassLoader(urls);

      ConfigurationBuilder builder = new ConfigurationBuilder().addClassLoader(classLoader);
      builder.setUrls(urls);
      builder.addScanners(new SubTypesScanner(false));
      Reflections reflections = new Reflections(builder);



      reflections = reflections.collect(files[0]);


      System.out.println(builder.getUrls());

      Set<Class<? extends IPlayer>> playerClasses = reflections.getSubTypesOf(IPlayer.class);

      for (Class playerClass : playerClasses) {
        try {
          toAdd.add((IPlayer) playerClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
          System.err.println("Cannot create instance of class from folder");
        }
      }

    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    //after Load
    for (IPlayer newPlayer: toAdd) {
      boolean exists = false;
      for (IPlayer oldPlayer: this.players) {
        if (oldPlayer.getClass().getName().equals(newPlayer.getClass().getName())) {
          exists = true;
          break;
        }
      }
      if (!exists) {
        this.players.add(newPlayer);
      }
    }
    this.gameWindow.updatePlayerSelectList(this.players);
  }

  public void setGameSpeed(int milliseconds) {
    this.millisecondsDelayForPick = milliseconds;
  }

  public void setNumberPoolSize(int numberPoolSize) {
    this.numberPoolSize = numberPoolSize;
  }

  public int getNumberPoolSize() {
    return numberPoolSize;
  }
}
