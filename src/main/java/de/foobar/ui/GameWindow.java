package de.foobar.ui;

import de.foobar.mechanic.GameController;
import de.foobar.mechanic.ResultMap;
import de.foobar.mechanic.Round;
import de.foobar.mechanic.player.IPlayer;
import de.foobar.ui.actions.CloseAction;
import de.foobar.ui.actions.GameRuleAction;
import de.foobar.ui.actions.HelpAction;
import de.foobar.ui.actions.LoadPlayerAction;
import de.foobar.ui.actions.StartGameAction;
import de.foobar.ui.elements.PlayerList;
import de.foobar.ui.elements.PlayerResultTableModel;
import static de.foobar.ui.helper.ImageSizeHelper.getScaledImage;
import de.foobar.ui.layout.GridBagLayoutManager;
import java.awt.*;
import java.util.ArrayList;
import javax.accessibility.Accessible;
import javax.swing.*;

/**
 * Editor: van on 24.03.15.
 */
public class GameWindow extends JFrame implements WindowConstants, Accessible, RootPaneContainer
{
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

	private PlayerList leftPlayerList;

	private PlayerList rightPlayerList;

	private JPanel currentFightPanel;
	private JLabel currentPlayerLeft;
	private JLabel currentPlayerRight;

	private JPanel lastFightPanel;
	private JLabel lastPlayerLeft;
	private JLabel lastPlayerRight;

	private JProgressBar currentPlayerProgressBar;

	private JTable resultTable;

	private PlayerResultTableModel tableModel;

	private GridBagLayoutManager gblm;


	public GameWindow(GameController gameController) throws HeadlessException
	{
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

		this.leftPlayerList = new PlayerList(this.gameController.getPlayerList());
		this.rightPlayerList = new PlayerList(this.gameController.getPlayerList());
		gblm.addComponent(new JScrollPane(this.leftPlayerList),0,0,1,1);
		gblm.addComponent(new JScrollPane(this.rightPlayerList),2,0,1,1);

		JButton fightButton = new JButton("<html>vs.<br/> =- fight -=</html>");
		fightButton.setVerticalTextPosition(SwingConstants.CENTER);
		fightButton.setHorizontalTextPosition(SwingConstants.CENTER);
		fightButton.addActionListener(new StartGameAction(gameController, leftPlayerList, rightPlayerList));
		gblm.addComponent(fightButton, 1, 0, 1, 1);

		generateCurrentFightPanel(gblm);

		generateLastFightPanel(gblm);

		generateResultTable(gblm);

		// its generated Last because of redundant action setting
		generateMenuBar();

		this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		this.mainPanel.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		this.setVisible(true);
		this.update(this.getGraphics());
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
		loadPlayer.setAction(new LoadPlayerAction());
		gameMenu.add(loadPlayer);

		JMenuItem startGame = new JMenuItem();
		startGame.setAction(new StartGameAction(getGameController(),this.leftPlayerList, this.rightPlayerList));
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

	private void generateResultTable(GridBagLayoutManager gblm)
	{
		this.resultTable = new JTable();
		this.resultTable.setModel(new PlayerResultTableModel(new ResultMap(new ArrayList<IPlayer>())));
		this.resultTable.setFillsViewportHeight(true);
		this.resultTable.setPreferredScrollableViewportSize(new Dimension(this.resultTable.getPreferredScrollableViewportSize().width,DEFAULT_TABLE_HEIGHT));

		this.tableModel = new PlayerResultTableModel();
		this.resultTable.setModel(this.tableModel);

		this.resultTable.setRowHeight(RESULT_TABLE_ROW_HEIGHT);

		gblm.addComponent(new JScrollPane(this.resultTable),0,3,4,1,2,2);
	}

	private void generateCurrentFightPanel(GridBagLayoutManager gblm)
	{
		this.currentFightPanel = new JPanel();
		this.currentFightPanel.setLayout(new BorderLayout());

		this.initPlayers();

		currentFightPanel.add(currentPlayerLeft,BorderLayout.LINE_START);
		currentFightPanel.add(currentPlayerRight,BorderLayout.LINE_END);

		this.currentPlayerProgressBar = new JProgressBar();
		currentFightPanel.add(this.currentPlayerProgressBar,BorderLayout.SOUTH);

		currentFightPanel.setBorder(BorderFactory.createTitledBorder("Current Fight:"));
		gblm.addComponent(currentFightPanel,0,1,3,1);
	}

	private void generateLastFightPanel(GridBagLayoutManager gblm)
	{
		this.initLastRound();

	}

	private void initLastRound() {
		this.setLastRound(null);
	}

	public void initPlayers()
	{
		this.setPlayers(null, null);
	}

	public void setPlayers(IPlayer player1, IPlayer player2)
	{
		this.currentPlayerLeft = new JLabel();
		this.currentPlayerRight = new JLabel();

		if(player1 != null) {
			currentPlayerLeft.setIcon(new ImageIcon(player1.getPlayerImage()));
			currentPlayerLeft.setText(player1.getPlayerName());
		} else{
			currentPlayerLeft.setIcon(EMPTY_LEFT_PLAYER);
			currentPlayerLeft.setText(UNKNOWN_PLAYER);
		}

		if(player1 != null) {
			currentPlayerRight.setIcon(new ImageIcon(player2.getPlayerImage()));
			currentPlayerRight.setText(player2.getPlayerName());
		} else{
			currentPlayerRight.setIcon(EMPTY_RIGHT_PLAYER);
			currentPlayerRight.setText(UNKNOWN_PLAYER);
		}

		currentPlayerRight.setHorizontalTextPosition(SwingConstants.LEFT);

		currentPlayerLeft.repaint();
		currentPlayerRight.repaint();
		currentFightPanel.repaint();

		this.gblm.invalidateLayout(this.mainPanel);

		this.revalidate();
		this.repaint();
	}


	public void setLastRound(Round lastRound) {
		/*if(this.lastFightPanel != null){
			this.gblm.removeLayoutComponent(this.lastFightPanel);
		}*/

		if(this.lastFightPanel == null) {

			this.lastPlayerLeft= new JLabel();
			this.lastPlayerRight = new JLabel();

			this.lastFightPanel = new JPanel();
			this.lastFightPanel.setLayout(new BorderLayout());
			this.lastFightPanel.setBorder(BorderFactory.createTitledBorder("Last Fight:"));
			gblm.addComponent(lastFightPanel,0,2,3,1);

		}

		if(lastRound != null){
			lastPlayerLeft.setIcon(new ImageIcon(getScaledImage(lastRound.getPlayer1().getPlayerImage())));
			lastPlayerRight.setIcon(new ImageIcon(getScaledImage(lastRound.getPlayer2().getPlayerImage())));

			lastPlayerLeft.setText(lastRound.getPlayer1().getPlayerName() + "("+lastRound.getScorePlayer1()+")");
			lastPlayerRight.setText(lastRound.getPlayer2().getPlayerName() + "("+lastRound.getScorePlayer2()+")");

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
		if(setBold) {
			boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		}else {
			boldFont = new Font(font.getFontName(), Font.PLAIN, font.getSize());
		}
		label.setFont(boldFont);
	}

	public void setProgress(int roundsPlayed, int maxRounds)
	{
		this.currentPlayerProgressBar.setMaximum(maxRounds);
		this.currentPlayerProgressBar.setValue(roundsPlayed);
	}

	//===================================================
	//== Game running methods
	//===================================================


	public PlayerResultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(PlayerResultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void update(Graphics g) {
		paint(g);
		setVisibleRowCount(this.resultTable,VISIBLE_ROW_COUNT_RESULT_TABLE);
		this.pack();
	}


	public static void setVisibleRowCount(JTable table, int rows){
		int height = 0;
		for(int row=0; row<rows; row++) {
			height += table.getRowHeight(row);
		}
		table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredScrollableViewportSize().width,height));
	}


}
