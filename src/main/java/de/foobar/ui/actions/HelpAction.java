package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import org.apache.commons.io.FileUtils;

/**
 * Editor: van on 22.07.15.
 */
public class HelpAction extends AbstractAction {

	private JFrame parent;

	final Icon smallIcon = new ImageIcon(HelpAction.class.getResource("/icon/exclamation.png") );

	public HelpAction(JFrame frame) {
		this.parent = frame;
		putValue( Action.NAME, "About" );
		putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
		putValue( Action.SMALL_ICON, smallIcon );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		File file = new File(HelpAction.class.getResource("/about.txt").getFile());
		String alert = "error";
		try {
			alert = FileUtils.readFileToString(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(this.parent, alert);
	}

}

