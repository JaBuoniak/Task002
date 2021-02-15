package com.s4a.utils;

import java.awt.*;

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
}
