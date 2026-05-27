package selambusreservationsystem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminPanelController implements Initializable {

    
    @FXML
    private ComboBox<String> cmbOrigin;

    @FXML
    private ComboBox<String> cmbDestination;

    @FXML
    private DatePicker dateDeparture;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtBusName;

    @FXML
    private TextField txtTotalSeats;

    @FXML
    private TableView<?> tripTable;

    @FXML
    private Label lblStatus;

    
    private final String[] locations = {
        "Addis Ababa",
        "Adama",
        "Hawassa",
        "Bahir Dar",
        "Mekelle",
        "Dire Dawa",
        "Gondar",
        "Jimma",
        "Harar",
        "Dessie",
        "Debre Birhan",
        "Debre Markos",
        "Shashamane",
        "Arba Minch",
        "Assosa",
        "Nekemte",
        "Wolaita Sodo",
        "Jijiga",
        "Gambela",
        "Axum",
        "Lalibela"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (cmbOrigin != null) {
            cmbOrigin.getItems().addAll(locations);
        }

        if (cmbDestination != null) {
            cmbDestination.getItems().addAll(locations);
        }

        lblStatus.setText("");
    }

    
    @FXML
    public void addBus(ActionEvent event) {

        String busName = txtBusName.getText();
        String seats = txtTotalSeats.getText();

        if (busName == null || busName.isEmpty() || seats == null || seats.isEmpty()) {
            lblStatus.setText("Please fill all bus fields");
            return;
        }

        try {
            int totalSeats = Integer.parseInt(seats);

            // future DB insert here
            lblStatus.setText("Bus added successfully");

            txtBusName.clear();
            txtTotalSeats.clear();

        } catch (NumberFormatException e) {
            lblStatus.setText("Seats must be a number");
        }
    }

   
    @FXML
    public void addTrip(ActionEvent event) {

        if (cmbOrigin.getValue() == null ||
            cmbDestination.getValue() == null ||
            dateDeparture.getValue() == null ||
            txtTime.getText().isEmpty() ||
            txtPrice.getText().isEmpty()) {

            lblStatus.setText("Please fill all trip fields");
            return;
        }

        if (cmbOrigin.getValue().equals(cmbDestination.getValue())) {
            lblStatus.setText("Origin and destination cannot be same");
            return;
        }

        
        lblStatus.setText("Trip added successfully");

        txtTime.clear();
        txtPrice.clear();
        dateDeparture.setValue(null);
    }

    
    @FXML
    public void updateTrip(ActionEvent event) {
        lblStatus.setText("Trip updated successfully (TODO DB)");
    }

   
    @FXML
    public void deleteTrip(ActionEvent event) {
        lblStatus.setText("Trip deleted successfully (TODO DB)");
    }
}