package de.foobar.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class GridBagLayoutManager extends GridBagLayout{


  private Container container;

  public GridBagLayoutManager(Container container) {
    this.container = container;
    container.setLayout( this );
  }


  public void addComponent(Component component, int xpos, int ypos, int width, int height ) {
    addComponent(component, xpos, ypos,width,height, 1.0, 1.0);
  }

  /**
   * Add a component to the Grid back layout.
   * @param component the component to add
   * @param xpos x-Position in grid
   * @param ypos y-Position in grid
   * @param width width in grid size
   * @param height height in grid size
   * @param weightx weight in x in relation to other elements
   * @param weighty weight in y in relation to other elements
   */
  public void addComponent(Component component, int xpos, int ypos, int width, int height,
                           double weightx, double weighty ) {

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = xpos;
    gbc.gridy = ypos;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    this.setConstraints( component, gbc );
    this.container.add( component );
  }

}
