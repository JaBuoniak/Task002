package com.s4a.view;

import com.s4a.LoadDistribution;
import com.s4a.exceptions.NoSuchFlightException;
import com.s4a.model.Weight;
import com.s4a.utils.DateUtils;
import com.s4a.utils.ViewUtils;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.ResourceBundle;

public class WeightPanel extends JPanel {
  
  private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
  private LoadDistribution distribution;
  private ExceptionsHandler exceptionsHandler;
  private JDatePickerImpl datePicker;
  private final JFormattedTextField flightNoField;
  private final JLabel resultValues;
  private final JButton calculateWeightButton;
  
  public WeightPanel(LoadDistribution distribution, ExceptionsHandler exceptionsHandler) {
    super(new GridBagLayout());
    this.distribution = distribution;
    this.exceptionsHandler = exceptionsHandler;
    
    try {
      datePicker = ViewUtils.createDatePicker();
    } catch (IOException e) {
      this.exceptionsHandler.handle(e);
    }
    flightNoField = ViewUtils.createIntegerField(0, 9999);
    calculateWeightButton = new JButton(BUNDLE.getString("Gui.Weight.calculateButton"));
    calculateWeightButton.addActionListener(e -> calculateWeigh());
    resultValues = new JLabel();
    
    arrange();
  }
  
  private void arrange() {
    add(new JLabel(BUNDLE.getString("Gui.Weight.giveDate"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(0, 0));
    add(datePicker, ViewUtils.createGridPlacement(0, 1));
    
    add(new JLabel(BUNDLE.getString("Gui.Weight.giveFlightNo"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(1, 0));
    add(flightNoField, ViewUtils.createGridPlacement(1, 1));
    
    JPanel resultPanel = new JPanel(new BorderLayout());
    resultPanel.add(new JLabel("<html>" +
                    BUNDLE.getString("Result.Weight.Header") + "&nbsp;<br>" +
                    BUNDLE.getString("Result.Weight.Baggage") + "<br>" +
                    BUNDLE.getString("Result.Weight.Cargo") + "<br>" +
                    BUNDLE.getString("Result.Weight.TotalLoad") + "</html>"),
            BorderLayout.WEST);
    resultPanel.add(resultValues, BorderLayout.CENTER);
    add(resultPanel, ViewUtils.createGridPlacement(2, 0));
    
    add(calculateWeightButton, ViewUtils.createGridPlacement(2, 1));
  }
  
  private void calculateWeigh() {
    Instant date = DateUtils.getInstant(datePicker);
    int flightNo = 0;
    try {
      flightNo = (Integer) flightNoField.getValue();
    } catch (NullPointerException e) {
      exceptionsHandler.handle(new NoSuchFlightException(0));
    }
    Weight baggageWeight = distribution.howMuchBaggageWeights(flightNo, date);
    Weight cargoWeight = distribution.howMuchCargoWeights(flightNo, date);
    Weight totalLoadWeight = distribution.howMuchTotalLoadWeights(flightNo, date);
    resultValues.setText("<html>" +
            flightNo + "<br>" +
            baggageWeight + "<br>" +
            cargoWeight + "<br>" +
            totalLoadWeight + "</html>");
  }
}
