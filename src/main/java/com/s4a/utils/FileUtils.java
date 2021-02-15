package com.s4a.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

public class FileUtils {
  
  private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
  
  public static File chooseFile(Component parentComponent, String defaultPath) {
    JFileChooser fileChooser = new JFileChooser(defaultPath);
    fileChooser.setFileFilter(new FileNameExtensionFilter(BUNDLE.getString("FileUtils.fileTypeDescription"), BUNDLE.getString("FileUtils.fileExtension")));
    if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parentComponent)) {
      return fileChooser.getSelectedFile();
    }
    return null;
  }
}
