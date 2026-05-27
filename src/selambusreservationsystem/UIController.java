package selambusreservationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.*;
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
    @FXML
    private TableView<Trip> tripTable;

    @FXML
    private TableColumn<Trip, String> colOrigin;
    @FXML
    private TableColumn<Trip, String> colDestination;
    @FXML
    private TableColumn<Trip, String> colDate;
    @FXML
    private TableColumn<Trip, String> colTime;
    @FXML
    private TableColumn<Trip, Double> colPrice;
    @FXML
    private TableColumn<Trip, String> colBus;
    @FXML
    private TableColumn<Trip, Void> colAction;

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

    private Trip selectedTrip;

    public void getDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            System.out.println("Selected Date: " + selectedDate);
        }
    }

    public void searchButton(ActionEvent actionEvent) {

        String origin = from.getValue();
        String destination = to.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (origin == null || destination == null || selectedDate == null) {
            lblSearch.setText("Fill all fields");
            return;
        }

        try {

            String sql =
                    "SELECT t.*, b.bus_name FROM trip t " +
                            "JOIN bus b ON t.bus_id = b.bus_id " +
                            "WHERE t.origin=? AND t.destination=? AND t.departure_date=?";

            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setDate(3, Date.valueOf(selectedDate));

            ResultSet rs = ps.executeQuery();

            ObservableList<Trip> list = FXCollections.observableArrayList();

            while (rs.next()) {

                list.add(new Trip(
                        rs.getInt("trip_id"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getString("departure_date"),
                        rs.getString("departure_time"),
                        rs.getDouble("price"),
                        rs.getString("bus_name")
                ));
            }

            if (list.isEmpty()) {
                lblSearch.setText("No trips found");
                tripTable.setItems(null);
            } else {
                lblSearch.setText(list.size() + " trips found");
                tripTable.setItems(list);
            }

        } catch (Exception e) {
            lblSearch.setText("Database error");
            e.printStackTrace();
        }
    }

    public void initialize() {
        from.getItems().addAll(locations);
        to.getItems().addAll(locations);

        setupTable();

        tripTable.setOnMouseClicked(e -> {
            selectedTrip = tripTable.getSelectionModel().getSelectedItem();
        });
        addBookButtonToTable();
    }

    private void addBookButtonToTable() {

        colAction.setCellFactory(param -> new javafx.scene.control.TableCell<>() {

            private final javafx.scene.control.Button btn = new javafx.scene.control.Button("Book Seat");

            {
                btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                btn.setOnAction(event -> {
                    Trip trip = getTableView().getItems().get(getIndex());

                    openSeatWindow(trip); // pass selected trip
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private void setupTable() {

        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBus.setCellValueFactory(new PropertyValueFactory<>("bus"));
    }

    @FXML
    public void openSeatWindow(Trip trip) {
//        if (selectedTrip == null) {
//            lblSearch.setText("Select a trip first");
//            return;
//        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatSelection.fxml"));
            Parent root = loader.load();

            SeatSelectionController controller = loader.getController();
            controller.setTrip(trip); // IMPORTANT
//            controller.setTrip(trip.getTripId());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Seat Selection");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openAdminPanel(javafx.event.ActionEvent event) {

        try {

            javafx.fxml.FXMLLoader loader
                    = new javafx.fxml.FXMLLoader(getClass().getResource("AdminPanel.fxml"));

            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = new javafx.stage.Stage();

            javafx.scene.Scene scene
                    = new javafx.scene.Scene(root);

            stage.setTitle("Admin Panel");

            stage.setScene(scene);

            stage.setWidth(912);
            stage.setHeight(662);

            stage.setMinWidth(912);
            stage.setMinHeight(662);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
