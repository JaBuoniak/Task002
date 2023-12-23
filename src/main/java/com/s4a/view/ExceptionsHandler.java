package com.s4a.view;

import javax.swing.*;
import java.awt.*;

public class ExceptionsHandler {
  
  private final Component parent;
  
  public ExceptionsHandler(Component parent) {
    this.parent = parent;
  }
  
  void handle (Throwable exception) {
    JOptionPane.showMessageDialog(parent, exception.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
  }
}
