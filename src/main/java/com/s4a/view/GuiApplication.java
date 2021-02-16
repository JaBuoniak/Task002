package com.s4a.view;

import com.s4a.LoadDistribution;
import com.s4a.utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class GuiApplication {

  private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
  private LoadDistribution distribution;
  private final JPanel mainPanel;
  private final JFrame frame;

  public GuiApplication() throws IOException {
    distribution = new LoadDistribution();
    mainPanel = new JPanel(new GridBagLayout());
  
    mainPanel.add(new ImportPanel(distribution), ViewUtils.createGridPlacement(0,0));
    mainPanel.add(new WeightPanel(distribution), ViewUtils.createGridPlacement(1,0));
    mainPanel.add(new TrafficPanel(distribution), ViewUtils.createGridPlacement(2,0));

    frame = new JFrame(BUNDLE.getString("Gui.Window.Title"));
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setMinimumSize(new Dimension(800,500));
    frame.add(mainPanel);
  }

  private void show() {
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    try {
      new GuiApplication().show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
