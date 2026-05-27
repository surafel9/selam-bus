package selambusreservationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AdminPanelController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // later: load trips into table here
    }

    @FXML
    public void addTrip(ActionEvent event) {
        System.out.println("Add Trip clicked");
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