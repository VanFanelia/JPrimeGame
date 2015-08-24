package de.foobar.ui.listener;

import de.foobar.mechanic.GameController;
import de.foobar.ui.elements.IntTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StartNumberChangeListener implements DocumentListener {

  private GameController controller;

  private IntTextField inputField;

  public StartNumberChangeListener(GameController gameController, IntTextField intTextField) {
    this.controller = gameController;
    this.inputField = intTextField;
  }


  @Override
  public void insertUpdate(DocumentEvent event) {
    controller.setNumberPoolSize(this.inputField.getValue());
  }

  @Override
  public void removeUpdate(DocumentEvent event) {
    controller.setNumberPoolSize(this.inputField.getValue());
  }

  @Override
  public void changedUpdate(DocumentEvent event) {
    controller.setNumberPoolSize(this.inputField.getValue());
  }
}