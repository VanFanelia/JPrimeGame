package de.foobar.ui.layout;

import java.awt.*;

/**
 * Editor: van on 22.07.15.
 */
public class GridBagLayoutManager extends GridBagLayout{


	private Container container;

	public GridBagLayoutManager(Container container) {
		this.container = container;
		container.setLayout( this );
	}


	public void addComponent(Component c, int x, int y, int width, int height )
	{
		addComponent(c,x,y,width,height, 1.0, 1.0);
	}

	public void addComponent(Component c, int x, int y, int width, int height, double weightx, double weighty )
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		this.setConstraints( c, gbc );
		this.container.add( c );
	}

}
