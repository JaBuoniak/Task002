package com.s4a.view;

import com.s4a.LoadDistribution;
import com.s4a.exceptions.FlightAlreadyExistsException;
import com.s4a.exceptions.JsonParseException;
import com.s4a.exceptions.NoSuchFlightException;
import com.s4a.utils.FileUtils;
import com.s4a.utils.ViewUtils;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ImportPanel extends JPanel {

    public static final String DEFAULT_FLIGHTS_FILE = "src/main/resources/com/s4a/data/flights.json";
    private static final String DEFAULT_LOADS_FILE = "src/main/resources/com/s4a/data/loads.json";
    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
    private final LoadDistribution distribution;
    private ExceptionsHandler exceptionsHandler;
    private final JButton importLoadButton;
    private final JButton importFlightsButton;
    private final JLabel importedFlightsLabel;
    private final JLabel importedLoadsLabel;

    public ImportPanel(LoadDistribution distribution, ExceptionsHandler exceptionsHandler) {
        super(new GridBagLayout());
        this.distribution = distribution;
        this.exceptionsHandler = exceptionsHandler;
    
        importFlightsButton = new JButton(BUNDLE.getString("Gui.Buttons.importFlights"));
        importFlightsButton.addActionListener(e -> importFlightsFromFile());

        importLoadButton = new JButton(BUNDLE.getString("Gui.Buttons.importLoad"));
        importLoadButton.addActionListener(e -> importLoadsFromFile());

        importedFlightsLabel = new JLabel("", SwingConstants.CENTER);
        importedLoadsLabel = new JLabel("", SwingConstants.CENTER);

        arrange();
    }

    private void arrange() {
        add(importFlightsButton, ViewUtils.createGridPlacement(0,0));
        add(importLoadButton, ViewUtils.createGridPlacement(0,1));

        add(importedFlightsLabel, ViewUtils.createGridPlacement(1,0));
        add(importedLoadsLabel, ViewUtils.createGridPlacement(1,1));
    }

    private void importFlightsFromFile() {
        try {
            File file = FileUtils.chooseFile(this, DEFAULT_FLIGHTS_FILE);
            if (file != null) {
                String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);

                importedFlightsLabel.setText("" +
                        distribution.importFlightsFromJson(fileContent));
                importedFlightsLabel.validate();
            }
        } catch (IOException | JSONException | JsonParseException | FlightAlreadyExistsException e) {
            exceptionsHandler.handle(e);
        }
    }

    private void importLoadsFromFile() {
        try {
            File file = FileUtils.chooseFile(this, DEFAULT_LOADS_FILE);
            if (file != null) {
                String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                importedLoadsLabel.setText("" +
                        distribution.importLoadsFromJson(fileContent));
                importedLoadsLabel.validate();
            }
        } catch (IOException | JSONException | NoSuchFlightException e) {
            exceptionsHandler.handle(e);
        }
    }
}
