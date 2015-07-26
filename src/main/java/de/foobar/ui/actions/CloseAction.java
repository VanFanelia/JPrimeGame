package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Editor: van on 22.07.15.
 */
public class CloseAction extends AbstractAction {

	private JFrame frame;

	final Icon smallIcon = new ImageIcon(CloseAction.class.getResource("/icon/cross.png") );

	public CloseAction(JFrame frame) {
		this.frame = frame;
		putValue( Action.NAME, "Close" );
		putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
		putValue( Action.SMALL_ICON, smallIcon );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println("bla");
		frame.setVisible(false);
		System.exit(0);
	}
}

