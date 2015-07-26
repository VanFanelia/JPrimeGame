package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

/**
 * Editor: van on 22.07.15.
 */
public class GameRuleAction extends AbstractAction {

	private JFrame parent;

	final Icon smallIcon = new ImageIcon(GameRuleAction.class.getResource("/icon/pi_math.png") );

	public GameRuleAction(JFrame frame) {
		this.parent = frame;
		putValue( Action.NAME, "Game Rules" );
		putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
		putValue( Action.SMALL_ICON, smallIcon );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		File file = new File(GameRuleAction.class.getResource("/rules.txt").getFile());
		String alert = "error";
		try {
			alert = FileUtils.readFileToString(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(this.parent, alert);
	}

}

