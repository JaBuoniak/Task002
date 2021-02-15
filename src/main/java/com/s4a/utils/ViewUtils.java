package com.s4a.utils;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Properties;

/**
 * Metody użytkowe dla widoku
 *
 * @author pjablonski
 */
public class ViewUtils {
  
  /**
   * Ułatwienie rozmieszczenia elementów panelu
   * @param rowNo nr wiersza od góry, począwszy od 0
   * @param colNo nr kolumny od lewej, począwszy od 0
   * @return rozmieszczenie dedykowane GridBagLayout
   */
  public static GridBagConstraints createGridPlacement(int rowNo, int colNo) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = colNo;
    gbc.gridy = rowNo;
    gbc.weightx = 0.5;
    gbc.weighty = 0.5;
    gbc.ipadx = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    return gbc;
  }


  public static JFormattedTextField createIntegerField(int min, int max) {
    NumberFormat intFormat = NumberFormat.getIntegerInstance();

    NumberFormatter numberFormatter = new NumberFormatter(intFormat);
    numberFormatter.setValueClass(Integer.class);
    numberFormatter.setAllowsInvalid(false);
    numberFormatter.setMinimum(min);
    numberFormatter.setMaximum(max);

    return new JFormattedTextField(numberFormatter);
  }

  public static JDatePickerImpl createDatePicker() throws IOException {
    UtilDateModel model = new UtilDateModel();
    model.setDate(2020,1,1);
    Properties properties = new Properties();
    properties.load(ViewUtils.class.getResourceAsStream("../view/Bundle.properties"));
    JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
    return new JDatePickerImpl(datePanel, new DateComponentFormatter());
  }
}
