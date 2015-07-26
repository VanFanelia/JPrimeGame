package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Editor: van on 22.07.15.
 */
public class LoadPlayerAction extends AbstractAction {

	final Icon smallIcon = new ImageIcon(LoadPlayerAction.class.getResource("/icon/folder_user.png") );

	public LoadPlayerAction() {
		putValue( Action.NAME, "Load Player" );
		putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 2 );
		putValue( Action.SMALL_ICON, smallIcon );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println("load player action");

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select directory with player");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}

	}
}

