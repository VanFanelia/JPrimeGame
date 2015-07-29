package de.foobar.ui.listener;

import de.foobar.mechanic.GameController;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameSpeedChangeListener implements ChangeListener {

  private GameController controller;

  public GameSpeedChangeListener(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    JSlider source = (JSlider) event.getSource();
    this.controller.setGameSpeed(source.getValue());
  }
}
