package com.s4a.view;

import com.s4a.LoadDistribution;
import com.s4a.model.AirportCode;
import com.s4a.utils.DateUtils;
import com.s4a.utils.ViewUtils;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.ResourceBundle;

public class TrafficPanel extends JPanel {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
    private JDatePickerImpl datePicker;
    private final JLabel resultValues;
    private final transient LoadDistribution distribution;
    private final JComboBox<AirportCode> airportCodeComboBox;
    private final JButton calculateTrafficButton;

    public TrafficPanel(LoadDistribution distribution, ExceptionsHandler exceptionsHandler) {
        super(new GridBagLayout());
        this.distribution = distribution;

        try {
            datePicker = ViewUtils.createDatePicker();
        } catch (IOException e) {
            exceptionsHandler.handle(e);
        }
        airportCodeComboBox = new JComboBox<>(AirportCode.values());

        resultValues = new JLabel();
        calculateTrafficButton = new JButton(BUNDLE.getString("Gui.Traffic.calculateButton"));
        calculateTrafficButton.addActionListener(e -> calculateTraffic());
        arrange();
    }

    private void arrange() {
        add(new JLabel(BUNDLE.getString("Gui.Traffic.giveDate"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(0,0));
        add(datePicker,  ViewUtils.createGridPlacement(0,1));

        add(new JLabel(BUNDLE.getString("Gui.Traffic.giveAirportCode"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(1,0));
        add(airportCodeComboBox,  ViewUtils.createGridPlacement(1,1));

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("<html>" +
                BUNDLE.getString("Result.Traffic.Header") + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedFlights") + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedFlights") + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedBaggage") + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedBaggage") + "</html>"),
                BorderLayout.WEST);
        resultPanel.add(resultValues, BorderLayout.CENTER);
        add(resultPanel, ViewUtils.createGridPlacement(2,0));

        add(calculateTrafficButton, ViewUtils.createGridPlacement(2,1));
    }

    private void calculateTraffic() {
        AirportCode selectedAirport = (AirportCode) airportCodeComboBox.getSelectedItem();
        Instant date = DateUtils.getInstant(datePicker);
        int flightsDepartedFrom = distribution.howManyFlightsDepartedFrom(selectedAirport, date);
        int flightsArrivedTo = distribution.howManyFlightsArrivedTo(selectedAirport, date);
        int piecesOfBaggageDepartedFrom = distribution.howManyPiecesOfBaggageDepartedFrom(selectedAirport, date);
        int piecesOfBaggageArrivedTo = distribution.howManyPiecesOfBaggageArrivedTo(selectedAirport, date);

        resultValues.setText("<html><br>" +
                flightsDepartedFrom + "<br>" +
                flightsArrivedTo + "<br>" +
                piecesOfBaggageDepartedFrom + "<br>" +
                piecesOfBaggageArrivedTo + "</html>");
    }
}
