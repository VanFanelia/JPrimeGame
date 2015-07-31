package de.foobar.ui;

import de.foobar.IPlayer;
import de.foobar.mechanic.GameController;
import de.foobar.mechanic.ResultMap;
import de.foobar.mechanic.Round;
import de.foobar.ui.actions.CloseAction;
import de.foobar.ui.actions.GameRuleAction;
import de.foobar.ui.actions.HelpAction;
import de.foobar.ui.actions.LoadPlayerAction;
import de.foobar.ui.actions.StartGameAction;
import de.foobar.ui.elements.PlayerList;
import de.foobar.ui.elements.PlayerResultTableModel;
import static de.foobar.ui.helper.ImageSizeHelper.getScaledImage;
import de.foobar.ui.layout.GridBagLayoutManager;
import de.foobar.ui.listener.GameSpeedChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.accessibility.Accessible;
import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


public class GameWindow extends JFrame implements WindowConstants, Accessible, RootPaneContainer {
  public static final int DEFAULT_WINDOW_HEIGHT = 600;
  public static final int DEFAULT_WINDOW_WIDTH = 800;

  public static final ImageIcon FILE_ICON = new ImageIcon(GameWindow.class.getResource("/icon/folder.png"));
  public static final ImageIcon GAME_ICON = new ImageIcon(GameWindow.class.getResource("/icon/dice.png"));
  public static final ImageIcon HELP_ICON = new ImageIcon(GameWindow.class.getResource("/icon/help.png"));

  public static final ImageIcon EMPTY_LEFT_PLAYER = new ImageIcon(GameWindow.class.getResource("/player/user_trooper_64.png"));
  public static final ImageIcon EMPTY_RIGHT_PLAYER = new ImageIcon(GameWindow.class.getResource("/player/user_wicket_64.png"));
  private static final int VISIBLE_ROW_COUNT_RESULT_TABLE = 5;
  private static final int DEFAULT_TABLE_HEIGHT = 200;
  private static final String UNKNOWN_PLAYER = "Unknown";
  private static final int RESULT_TABLE_ROW_HEIGHT = 64;


  private GameController gameController;

  private JPanel mainPanel;

  private PlayerList playerList;

  private JPanel currentFightPanel;
  private JLabel currentPlayerLeft;
  private JLabel currentPlayerRight;

  private JPanel lastFightPanel;
  private JLabel lastPlayerLeft;
  private JLabel lastPlayerRight;

  private JButton fightButton;

  private JProgressBar currentRoundProgressBar;
  private JProgressBar currentGameProgressBar;

  private JTable resultTable;

  private PlayerResultTableModel tableModel;

  private GridBagLayoutManager gblm;

  private GridBagLayoutManager currentFightPanelGBLM;

  /**
   * Default Constructor to init the game window.
   * @param gameController the controller
   * @throws HeadlessException JFrame exception
   */
  public GameWindow(GameController gameController) throws HeadlessException {
    super("Prime Game");
    this.setGameController(gameController);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    try {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException  e ) {
      e.printStackTrace();
    }

    this.setLayout(new FlowLayout());

    this.mainPanel = new JPanel();
    this.add(mainPanel);

    this.gblm = new GridBagLayoutManager(this.mainPanel);

    generatePlayerSelectList();

    generateCurrentFightPanel(gblm);

    generateLastFightPanel();

    generateResultTable(gblm);

    // its generated Last because of redundant action setting
    generateMenuBar();

    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.mainPanel.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setVisible(true);
    this.update(this.getGraphics());
  }

  private void generatePlayerSelectList() {

    this.playerList = new PlayerList(this.gameController.searchClassPathForPlayerList());
    gblm.addComponent(new JScrollPane(this.playerList),0,0,1,1);

    this.fightButton = new JButton("=- fight -=");
    fightButton.setVerticalTextPosition(SwingConstants.CENTER);
    fightButton.setHorizontalTextPosition(SwingConstants.CENTER);
    fightButton.addActionListener(new StartGameAction(this.gameController, playerList));

    gblm.addComponent(fightButton, 0, 1, 1, 1, 0.3, 0.3);
  }

  public void updatePlayerSelectList(List<IPlayer> players) {
    this.playerList.setPlayerList(players);
  }

  public GameController getGameController() {
    return gameController;
  }

  public void setGameController(GameController gameController) {
    this.gameController = gameController;
  }

  private void generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    generateFileMenu(menuBar);
    generateGameMenu(menuBar);
    generateHelpMenu(menuBar);

    this.setJMenuBar(menuBar);
  }

  private void generateHelpMenu(JMenuBar menuBar) {
    JMenu helpMenu = new JMenu( "Help" );
    helpMenu.setIcon(HELP_ICON);

    JMenuItem gameRules = new JMenuItem("");
    helpMenu.add( gameRules );
    gameRules.setAction(new GameRuleAction(this));

    JMenuItem about = new JMenuItem("");
    helpMenu.add( about );
    about.setAction(new HelpAction(this));

    menuBar.add( helpMenu );
  }

  private void generateGameMenu(JMenuBar menuBar) {
    JMenu gameMenu = new JMenu( "Game" );
    gameMenu.setIcon(GAME_ICON);

    JMenuItem loadPlayer = new JMenuItem();
    loadPlayer.setAction(new LoadPlayerAction(this.gameController));
    gameMenu.add(loadPlayer);

    JMenuItem startGame = new JMenuItem();
    startGame.setAction(new StartGameAction(getGameController(),this.playerList));
    gameMenu.add(startGame);

    JMenuItem stopResetGame = new JMenuItem("Stop / Reset Game");
    gameMenu.add(stopResetGame);

    menuBar.add( gameMenu );
  }

  private void generateFileMenu(JMenuBar menuBar) {
    JMenu fileMenu = new JMenu( "File" );
    fileMenu.setIcon(FILE_ICON);

    JMenuItem closeItem = new JMenuItem("Close");
    closeItem.setAction(new CloseAction(this));
    fileMenu.add(closeItem);

    menuBar.add( fileMenu );
  }

  private void generateResultTable(GridBagLayoutManager gblm) {
    this.resultTable = new JTable();
    this.resultTable.setModel(new PlayerResultTableModel(new ResultMap(new ArrayList<>())));
    this.resultTable.setFillsViewportHeight(true);
    this.resultTable.setPreferredScrollableViewportSize(new Dimension(
    this.resultTable.getPreferredScrollableViewportSize().width,DEFAULT_TABLE_HEIGHT));

    this.tableModel = new PlayerResultTableModel();
    this.resultTable.setModel(this.tableModel);

    this.resultTable.setRowHeight(RESULT_TABLE_ROW_HEIGHT);

    gblm.addComponent(new JScrollPane(this.resultTable),0,4,4,1,2,2);
  }

  private void generateCurrentFightPanel(GridBagLayoutManager gblm) {

    this.initPlayers();

    this.currentRoundProgressBar = new JProgressBar();
    this.currentGameProgressBar = new JProgressBar();
    currentFightPanelGBLM.addComponent(new JLabel("Fight / Round: "), 0, 1, 2, 1);
    currentFightPanelGBLM.addComponent(this.currentRoundProgressBar, 0, 2, 2, 1);
    currentFightPanelGBLM.addComponent(this.currentGameProgressBar, 0, 3, 2, 1);

    JSlider speedSlider = new JSlider( 0, 2000, 1000 );
    speedSlider.setPaintTicks( true );
    speedSlider.setMinorTickSpacing(50);
    speedSlider.setMajorTickSpacing(250);
    speedSlider.addChangeListener(new GameSpeedChangeListener(this.gameController));
    currentFightPanelGBLM.addComponent(speedSlider, 0, 4, 2, 1);

    currentFightPanel.setBorder(BorderFactory.createTitledBorder("Current Fight:"));
    gblm.addComponent(currentFightPanel,0,2,3,1);
  }

  private void generateLastFightPanel() {
    this.initLastRound();

  }

  private void initLastRound() {
    this.setLastRound(null);
  }

  /**
   * Default init player Method on program start.
   */
  public void initPlayers() {
    this.setCurrentPlayersPlayers(null, null);
  }

  /**
   * set the current fighting players.
   * @param player1 left player
   * @param player2 right player
   */
  public void setCurrentPlayersPlayers(IPlayer player1, IPlayer player2) {
    if (this.currentFightPanel == null) {
      this.currentFightPanel = new JPanel();
      this.currentFightPanelGBLM = new GridBagLayoutManager(this.currentFightPanel);
      this.currentFightPanel.setLayout(this.currentFightPanelGBLM);

      this.currentPlayerLeft = new JLabel(UNKNOWN_PLAYER, EMPTY_LEFT_PLAYER, SwingConstants.LEFT);
      this.currentPlayerRight = new JLabel(UNKNOWN_PLAYER, EMPTY_RIGHT_PLAYER, SwingConstants.RIGHT);

      this.currentFightPanelGBLM.addComponent(this.currentPlayerLeft, 0,0,1,1);
      this.currentFightPanelGBLM.addComponent(this.currentPlayerRight, 1, 0, 1, 1);

    } else {

      this.currentPlayerLeft.setIcon(new ImageIcon(getScaledImage(player1.getPlayerImage())));
      this.currentPlayerLeft.setText(player1.getPlayerName());

      this.currentPlayerRight.setIcon(new ImageIcon(getScaledImage(player2.getPlayerImage())));
      this.currentPlayerRight.setText(player2.getPlayerName());
    }

    currentPlayerRight.setHorizontalTextPosition(SwingConstants.LEFT);

    currentPlayerLeft.repaint();
    currentPlayerRight.repaint();
    currentFightPanel.repaint();

    this.gblm.invalidateLayout(this.mainPanel);

    this.revalidate();
    this.repaint();
  }

  /**
   * set the last Round to ui and replace/redraw images/textes.
   * @param lastRound the last played round
   */
  public void setLastRound(Round lastRound) {

    if (this.lastFightPanel == null) {

      this.lastPlayerLeft = new JLabel();
      this.lastPlayerRight = new JLabel();

      this.lastFightPanel = new JPanel();
      this.lastFightPanel.setLayout(new BorderLayout());
      this.lastFightPanel.setBorder(BorderFactory.createTitledBorder("Last Fight:"));
      gblm.addComponent(lastFightPanel,0,3,3,1);

    }

    if (lastRound != null) {
      lastPlayerLeft.setIcon(new ImageIcon(getScaledImage(lastRound.getPlayer1().getPlayerImage())));
      lastPlayerRight.setIcon(new ImageIcon(getScaledImage(lastRound.getPlayer2().getPlayerImage())));

      lastPlayerLeft.setText(lastRound.getPlayer1().getPlayerName() + "(" + lastRound.getScorePlayer1() + ")");
      lastPlayerRight.setText(lastRound.getPlayer2().getPlayerName() + "(" + lastRound.getScorePlayer2() + ")");

      JLabel setBold = (lastRound.getScorePlayer1() > lastRound.getScorePlayer2()) ? lastPlayerLeft : lastPlayerRight;
      JLabel removeBold = (lastRound.getScorePlayer1() < lastRound.getScorePlayer2()) ? lastPlayerLeft : lastPlayerRight;

      setLabelsBoldValue(setBold, true);
      setLabelsBoldValue(removeBold, false);

    } else {
      lastPlayerLeft.setIcon(EMPTY_LEFT_PLAYER);
      lastPlayerRight.setIcon(EMPTY_RIGHT_PLAYER);

      lastPlayerLeft.setText(UNKNOWN_PLAYER);
      lastPlayerRight.setText(UNKNOWN_PLAYER);
    }

    this.lastPlayerRight.setHorizontalTextPosition(SwingConstants.LEFT);

    this.lastFightPanel.add(this.lastPlayerLeft,BorderLayout.LINE_START);
    this.lastFightPanel.add(this.lastPlayerRight, BorderLayout.LINE_END);

  }

  private static void setLabelsBoldValue(JLabel label, boolean setBold) {
    Font font = label.getFont();
    Font boldFont;
    if (setBold) {
      boldFont = new Font(font.getFontName(), Font.BOLD, 12 );
    } else {
      boldFont = new Font(font.getFontName(), Font.PLAIN, 12 );
    }
    label.setFont(boldFont);
  }

  private static void setLabelToCurrentPlayerColor(JLabel label, boolean setCurrent) {

    if (setCurrent) {
      label.setForeground(new Color(0,90,0));
    } else {
      label.setForeground(Color.BLACK);
    }
  }

  public void deactivateStartGame() {
    this.fightButton.setEnabled(false);
  }

  public void activateStartGame() {
    this.fightButton.setEnabled(true);
  }

  //===================================================
  //== Game running methods
  //===================================================


  public PlayerResultTableModel getTableModel() {
    return tableModel;
  }

  /**
   * Overwrite ui update method to fix visible row count in table (default handling is strange...).
   * @param graphics graphic context to update
   */
  public void update(Graphics graphics) {
    paint(graphics);
    setVisibleRowCount(this.resultTable,VISIBLE_ROW_COUNT_RESULT_TABLE);
    this.pack();
  }

  /**
   * calculated the visible row count on cell heights of the table model.
   * @param table the table to fix
   * @param rows the number of rows to show
   */
  public static void setVisibleRowCount(JTable table, int rows) {
    int height = 0;
    for (int row = 0; row < rows; row++) {
      height += table.getRowHeight(row);
    }
    table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredScrollableViewportSize().width,height));
  }

  public void initProgressBar(DefaultBoundedRangeModel progressRound, DefaultBoundedRangeModel progressGame) {
    this.currentRoundProgressBar.setModel(progressRound);
    this.currentGameProgressBar.setModel(progressGame);
  }

  public void setCurrentPlayerPoints(int pickedNumber, List<Integer> divisors, Round round, IPlayer currentPlayer) {


    int sumOfDivisors = divisors.stream().mapToInt((x) -> x).sum();

    boolean currentPlayerIsPlayer1 = currentPlayer.equals(round.getPlayer1());

    setLabelsBoldValue(this.currentPlayerLeft, currentPlayerIsPlayer1);
    setLabelsBoldValue(this.currentPlayerRight, !currentPlayerIsPlayer1);

    String pickedTextLeft = currentPlayerIsPlayer1 ? " picked: " + pickedNumber : " get: " + sumOfDivisors;
    String pickedTextRight = !currentPlayerIsPlayer1 ? " picked: " + pickedNumber : " get: " + sumOfDivisors;

    setLabelToCurrentPlayerColor(this.currentPlayerLeft, currentPlayerIsPlayer1);
    setLabelToCurrentPlayerColor(this.currentPlayerRight, !currentPlayerIsPlayer1);


    String leftPlayerTextToSet = "<html>" + round.getPlayer1().getPlayerName() + "<br/>"
        + "points: " + round.getScorePlayer1() + "<br />" + pickedTextLeft + " </html>";
    this.currentPlayerLeft.setText(leftPlayerTextToSet);

    String rightPlayerTextToSet = "<html>" + round.getPlayer2().getPlayerName() + "<br/>"
        + "points: " + round.getScorePlayer2() + "<br />" + pickedTextRight + " </html>";
    this.currentPlayerRight.setText(rightPlayerTextToSet);


  }


}
