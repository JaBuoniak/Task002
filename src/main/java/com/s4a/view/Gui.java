package com.s4a.view;

import com.s4a.LoadDistribution;
import com.s4a.model.AirportCode;
import com.s4a.model.Weight;
import com.s4a.utils.DateUtils;
import com.s4a.utils.FileUtils;
import com.s4a.utils.ViewUtils;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Properties;
import java.util.ResourceBundle;

public class Gui {
  
  public static final String DEFAULT_FLIGHTS_FILE = "com/s4a/data/flights.json";
  private static final String DEFAULT_LOADS_FILE = "com/s4a/data/loads.json";
  private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
  private LoadDistribution distribution;
  
  public Gui() throws IOException {
    distribution = new LoadDistribution();
    JPanel mainPanel = new JPanel(new GridBagLayout());
  
    mainPanel.add(importPanel(), ViewUtils.createGridPlacement(0,0));
    mainPanel.add(weightPanel(), ViewUtils.createGridPlacement(1,0));
    mainPanel.add(trafficPanel(), ViewUtils.createGridPlacement(2,0));
  
    JFrame frame = new JFrame(BUNDLE.getString("Gui.Window.Title"));
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setSize(new Dimension(400,300));
    frame.add(mainPanel);
    frame.pack();
    frame.setVisible(true);
  }
  
  private JPanel importPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    
    JButton importFlightsButton = new JButton(BUNDLE.getString("Gui.Buttons.importFlights"));
    importFlightsButton.addActionListener(e -> importFlightsFromFile(panel));
    
    JButton importLoadButton = new JButton(BUNDLE.getString("Gui.Buttons.importLoad"));
    importLoadButton.addActionListener(e -> importLoadsFromFile(panel));
    
    panel.add(importFlightsButton, ViewUtils.createGridPlacement(0,0));
    panel.add(importLoadButton, ViewUtils.createGridPlacement(0,1));
    
    return panel;
  }
  
  private void importFlightsFromFile(JPanel panel) {
    try {
      File file = FileUtils.chooseFile(panel, DEFAULT_FLIGHTS_FILE);
      if (file != null) {
        String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        distribution.importFlightsFromJson(fileContent);
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (JSONException jsonException) {
      //TODO
    }
  }
  
  private void importLoadsFromFile(JPanel panel) {
    try {
      File file = FileUtils.chooseFile(panel, DEFAULT_LOADS_FILE);
      if (file != null) {
        String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        distribution.importLoadsFromJson(fileContent);
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (JSONException jsonException) {
      //TODO
    }
  }
  
  private JPanel weightPanel() throws IOException {
    JPanel panel = new JPanel(new GridBagLayout());
    
    JDatePickerImpl datePicker = createDatePicker();
    panel.add(new JLabel(BUNDLE.getString("Gui.Weight.giveDate"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(0,0));
    panel.add(datePicker,  ViewUtils.createGridPlacement(0,1));
    
    JFormattedTextField flightNoField = getFlightNoField();
    panel.add(new JLabel(BUNDLE.getString("Gui.Weight.giveFlightNo"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(1,0));
    panel.add(flightNoField,  ViewUtils.createGridPlacement(1,1));
  
    JLabel resultLabel = new JLabel("");
    panel.add(resultLabel, ViewUtils.createGridPlacement(2,0));
    
    JButton calculateWeightButton = new JButton(BUNDLE.getString("Gui.Weight.calculateButton"));
    calculateWeightButton.addActionListener(e -> calculateWeigh(DateUtils.getInstant(datePicker), (Integer) flightNoField.getValue(), resultLabel));
    panel.add(calculateWeightButton, ViewUtils.createGridPlacement(2,1));
  
    
    return panel;
  }
  
  private void calculateWeigh(Instant date, Integer flightNo, JLabel resultLabel) {
    try {
      Weight baggageWeight = distribution.howMuchBaggageWeights(flightNo, date);
      Weight cargoWeight = distribution.howMuchCargoWeights(flightNo, date);
      Weight totalLoadWeight = distribution.howMuchTotalLoadWeights(flightNo, date);
      resultLabel.setText("<html>" +
            BUNDLE.getString("Result.Weight.Header") + flightNo + ":<br>"+
            BUNDLE.getString("Result.Weight.Baggage") + baggageWeight + "<br>"+
            BUNDLE.getString("Result.Weight.Cargo") + cargoWeight + "<br>"+
            BUNDLE.getString("Result.Weight.TotalLoad") + totalLoadWeight + "</html>");
      resultLabel.validate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  private JPanel trafficPanel() throws IOException {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.add(new JLabel(BUNDLE.getString("Gui.Traffic.giveDate"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(0,0));
    panel.add(createDatePicker(),  ViewUtils.createGridPlacement(0,1));
    
    panel.add(new JLabel(BUNDLE.getString("Gui.Traffic.giveAirportCode"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(1,0));
    JComboBox<AirportCode> airportCodeComboBox = new JComboBox<>(AirportCode.values());
    panel.add(airportCodeComboBox,  ViewUtils.createGridPlacement(1,1));
    
    JButton calculateWeightButton = new JButton(BUNDLE.getString("Gui.Traffic.calculateButton"));
    panel.add(calculateWeightButton, ViewUtils.createGridPlacement(2,1));
    
    return panel;
  }
  
  private JFormattedTextField getFlightNoField() {
    NumberFormat intFormat = NumberFormat.getIntegerInstance();
  
    NumberFormatter numberFormatter = new NumberFormatter(intFormat);
    numberFormatter.setValueClass(Integer.class);
    numberFormatter.setAllowsInvalid(false);
    numberFormatter.setMinimum(0);
    numberFormatter.setMaximum(9999);
  
    return new JFormattedTextField(numberFormatter);
  }
  
  private JDatePickerImpl createDatePicker() throws IOException {
    UtilDateModel model = new UtilDateModel();
    model.setDate(2020,1,1);
    Properties properties = new Properties();
    properties.load(getClass().getResourceAsStream("Bundle.properties"));
    JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
    return new JDatePickerImpl(datePanel, new DateComponentFormatter());
  }
  
  public static void main(String[] args) {
    LoadDistribution distribution = new LoadDistribution();
    try {
      Gui guiAppInstance = new Gui();
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
