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
    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("com/s4a/view/Bundle");
    private final JDatePickerImpl datePicker;
    private final JLabel resultLabel;
    private LoadDistribution distribution;
    private final JComboBox<AirportCode> airportCodeComboBox;
    private final JButton calculateTrafficButton;

    public TrafficPanel(LoadDistribution distribution) throws IOException {
        super(new GridBagLayout());
        this.distribution = distribution;

        datePicker = ViewUtils.createDatePicker();
        airportCodeComboBox = new JComboBox<>(AirportCode.values());
        resultLabel = new JLabel("<html>" +
                BUNDLE.getString("Result.Traffic.Header1") + BUNDLE.getString("Result.Traffic.Header2") + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedFlights") + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedFlights") + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedBaggage") + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedBaggage") + "</html>");
        calculateTrafficButton = new JButton(BUNDLE.getString("Gui.Traffic.calculateButton"));
        calculateTrafficButton.addActionListener(e -> calculateTraffic());
        arrange();
    }

    private void arrange() {
        add(new JLabel(BUNDLE.getString("Gui.Traffic.giveDate"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(0,0));
        add(datePicker,  ViewUtils.createGridPlacement(0,1));

        add(new JLabel(BUNDLE.getString("Gui.Traffic.giveAirportCode"), SwingConstants.RIGHT), ViewUtils.createGridPlacement(1,0));
        add(airportCodeComboBox,  ViewUtils.createGridPlacement(1,1));

        add(resultLabel, ViewUtils.createGridPlacement(2,0));
        add(calculateTrafficButton, ViewUtils.createGridPlacement(2,1));
    }

    private void calculateTraffic() {
        AirportCode selectedAirport = (AirportCode) airportCodeComboBox.getSelectedItem();
        Instant date = DateUtils.getInstant(datePicker);
        int flightsDepartedFrom = distribution.howManyFlightsDepartedFrom(selectedAirport, date);
        int flightsArrivedTo = distribution.howManyFlightsArrivedTo(selectedAirport, date);
        int piecesOfBaggageDepartedFrom = distribution.howManyPiecesOfBaggageDepartedFrom(selectedAirport, date);
        int piecesOfBaggageArrivedTo = distribution.howManyPiecesOfBaggageArrivedTo(selectedAirport, date);

        resultLabel.setText("<html>" +
                BUNDLE.getString("Result.Traffic.Header1") + selectedAirport +
                BUNDLE.getString("Result.Traffic.Header2") + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedFlights") + flightsDepartedFrom + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedFlights") + flightsArrivedTo + "<br>"+
                BUNDLE.getString("Result.Traffic.DepartedBaggage") + piecesOfBaggageDepartedFrom + "<br>"+
                BUNDLE.getString("Result.Traffic.ArrivedBaggage") + piecesOfBaggageArrivedTo + "</html>");
        validate();
    }
}
