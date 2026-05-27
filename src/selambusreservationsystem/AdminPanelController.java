package selambusreservationsystem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private TableView<?> tripTable;

    private String[] locations = {
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

        cmbOrigin.getItems().addAll(locations);
        cmbDestination.getItems().addAll(locations);

    }

    @FXML
    public void addTrip(ActionEvent event) {

        String origin = cmbOrigin.getValue();
        String destination = cmbDestination.getValue();
        String date = String.valueOf(dateDeparture.getValue());
        String time = txtTime.getText();
        String price = txtPrice.getText();

        System.out.println("Trip Added");
        System.out.println(origin);
        System.out.println(destination);
        System.out.println(date);
        System.out.println(time);
        System.out.println(price);

    }

    @FXML
    public void updateTrip(ActionEvent event) {

        System.out.println("Update Trip clicked");

    }

    @FXML
    public void deleteTrip(ActionEvent event) {

        System.out.println("Delete Trip clicked");

    }
}