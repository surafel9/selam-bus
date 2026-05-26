package selambusreservationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class UIController {
    @FXML
    private Label lblSearch;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> from;
    @FXML
    private ComboBox<String> to;

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
            "Lalibela",
    };

    public void getDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            System.out.println("Selected Date: " + selectedDate.toString());
        }
    }

    public void searchButton(ActionEvent actionEvent) {
        String origin = from.getValue();
        String destination = to.getValue();
        if (origin == null) {
            from.setStyle(from.getStyle() + "; -fx-border-color: red;");
        } else if (destination == null) {
            to.setStyle(to.getStyle() + "; -fx-border-color: red;");
        } else if (origin.equals(destination)) {
            lblSearch.setText("Walk bro");
        } else {
            lblSearch.setText("Searching ...");
            LocalDate selectedDate = datePicker.getValue();
            // #TODO: Logic
        }
    }

    public void initialize() {
        from.getItems().addAll(locations);
        to.getItems().addAll(locations);
    }

}
