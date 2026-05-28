package selambusreservationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminPanelController implements Initializable {

    @FXML
    private ComboBox<String> cmbOrigin;
    @FXML
    private ComboBox<String> cmbDestination;
    @FXML
    private ComboBox<String> cmbBus;
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
    private TableView<Trip> tripTable;
    @FXML
    private TableView<BookingDisplay> bookingTable;  // Changed from Booking to BookingDisplay
    @FXML
    private Label lblStatus;
    @FXML
    private TableColumn<Trip, Integer> colTripId;
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
    private TableColumn<BookingDisplay, Integer> colBookingId;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colPassenger;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colBusName;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colOriginB;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colDestinationB;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colDateB;  // Changed
    @FXML
    private TableColumn<BookingDisplay, Integer> colSeat;  // Changed
    @FXML
    private TableColumn<BookingDisplay, String> colStatus;  // Changed

    private final String[] locations = {
        "Addis Ababa", "Adama", "Hawassa", "Bahir Dar", "Mekelle",
        "Dire Dawa", "Gondar", "Jimma", "Harar", "Dessie",
        "Debre Birhan", "Debre Markos", "Shashamane", "Arba Minch",
        "Assosa", "Nekemte", "Wolaita Sodo", "Jijiga", "Gambela",
        "Axum", "Lalibela"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbOrigin.getItems().addAll(locations);
        cmbDestination.getItems().addAll(locations);

        setupTable();
        setupBookingTable();
        loadBuses();
        loadTrips();
        loadBookings();

        tripTable.setOnMouseClicked(e -> {
            Trip t = tripTable.getSelectionModel().getSelectedItem();
            if (t != null) {
                cmbOrigin.setValue(t.getOrigin());
                cmbDestination.setValue(t.getDestination());
                txtTime.setText(t.getTime());
                txtPrice.setText(String.valueOf(t.getPrice()));
            }
        });
    }

    private void setupTable() {
        colTripId.setCellValueFactory(new PropertyValueFactory<>("tripId"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBus.setCellValueFactory(new PropertyValueFactory<>("bus"));
    }

    private void setupBookingTable() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colPassenger.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        colBusName.setCellValueFactory(new PropertyValueFactory<>("busName"));
        colOriginB.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestinationB.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDateB.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSeat.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    public void addBus(ActionEvent event) {
        try {
            String busName = txtBusName.getText();
            String seats = txtTotalSeats.getText();

            if (busName.isEmpty() || seats.isEmpty()) {
                lblStatus.setText("Fill bus fields");
                return;
            }

            int totalSeats = Integer.parseInt(seats);
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO bus(bus_name, total_seats) VALUES(?, ?)";
            PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, busName);
            pst.setInt(2, totalSeats);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int busId = rs.next() ? rs.getInt(1) : 0;

            String seatSql = "INSERT INTO seat(bus_id, seat_number) VALUES(?, ?)";
            for (int i = 1; i <= totalSeats; i++) {
                PreparedStatement ps = con.prepareStatement(seatSql);
                ps.setInt(1, busId);
                ps.setInt(2, i);
                ps.executeUpdate();
            }

            txtBusName.clear();
            txtTotalSeats.clear();
            loadBuses();
            lblStatus.setText("Bus added successfully");
        } catch (Exception e) {
            lblStatus.setText("Error adding bus");
            e.printStackTrace();
        }
    }

    @FXML
    public void addTrip(ActionEvent event) {
        try {
            if (cmbOrigin.getValue() == null || cmbDestination.getValue() == null
                    || dateDeparture.getValue() == null || txtTime.getText().isEmpty()
                    || txtPrice.getText().isEmpty() || cmbBus.getValue() == null) {
                lblStatus.setText("Fill all fields");
                return;
            }

            Connection con = DatabaseConnection.getConnection();
            String getBus = "SELECT bus_id FROM bus WHERE bus_name=?";
            PreparedStatement psBus = con.prepareStatement(getBus);
            psBus.setString(1, cmbBus.getValue());
            ResultSet rs = psBus.executeQuery();
            int busId = rs.next() ? rs.getInt("bus_id") : 0;

            String sql = "INSERT INTO trip(bus_id, origin, destination, departure_date, departure_time, price) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, busId);
            pst.setString(2, cmbOrigin.getValue());
            pst.setString(3, cmbDestination.getValue());
            pst.setDate(4, Date.valueOf(dateDeparture.getValue()));
            pst.setString(5, txtTime.getText());
            pst.setDouble(6, Double.parseDouble(txtPrice.getText()));
            pst.executeUpdate();

            txtTime.clear();
            txtPrice.clear();
            dateDeparture.setValue(null);
            loadTrips();
            lblStatus.setText("Trip added");
        } catch (Exception e) {
            lblStatus.setText("Error adding trip");
            e.printStackTrace();
        }
    }

    @FXML
    public void updateTrip(ActionEvent event) {
        try {
            Trip t = tripTable.getSelectionModel().getSelectedItem();
            if (t == null) {
                lblStatus.setText("Select trip");
                return;
            }

            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE trip SET origin=?, destination=?, departure_date=?, departure_time=?, price=? WHERE trip_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, cmbOrigin.getValue());
            pst.setString(2, cmbDestination.getValue());
            pst.setDate(3, Date.valueOf(dateDeparture.getValue()));
            pst.setString(4, txtTime.getText());
            pst.setDouble(5, Double.parseDouble(txtPrice.getText()));
            pst.setInt(6, t.getTripId());
            pst.executeUpdate();

            loadTrips();
            lblStatus.setText("Trip updated");
        } catch (Exception e) {
            lblStatus.setText("Update error");
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteTrip(ActionEvent event) {
        try {
            Trip t = tripTable.getSelectionModel().getSelectedItem();
            if (t == null) {
                lblStatus.setText("Select trip");
                return;
            }

            Connection con = DatabaseConnection.getConnection();
            String sql = "DELETE FROM trip WHERE trip_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getTripId());
            pst.executeUpdate();

            loadTrips();
            lblStatus.setText("Trip deleted");
        } catch (Exception e) {
            lblStatus.setText("Delete error");
            e.printStackTrace();
        }
    }

    private void loadBuses() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT bus_name FROM bus";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            cmbBus.getItems().clear();
            while (rs.next()) {
                cmbBus.getItems().add(rs.getString("bus_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTrips() {
        try {
            ObservableList<Trip> list = FXCollections.observableArrayList();
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT t.*, b.bus_name FROM trip t JOIN bus b ON t.bus_id=b.bus_id";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
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
            tripTable.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        try {
            ObservableList<BookingDisplay> bookings = FXCollections.observableArrayList();
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT b.booking_id, b.passenger_name, bs.bus_name, t.origin, t.destination, t.departure_date, s.seat_number, b.status "
                    + "FROM booking b JOIN trip t ON b.trip_id = t.trip_id JOIN bus bs ON t.bus_id = bs.bus_id JOIN seat s ON b.seat_id = s.seat_id ORDER BY b.booking_id DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                bookings.add(new BookingDisplay(
                        rs.getInt("booking_id"),
                        rs.getString("passenger_name"),
                        rs.getString("bus_name"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getString("departure_date"),
                        rs.getInt("seat_number"),
                        rs.getString("status")
                ));
            }
            bookingTable.setItems(bookings);
            lblStatus.setText("Bookings loaded: " + bookings.size());
        } catch (Exception e) {
            e.printStackTrace();
            lblStatus.setText("Error loading bookings");
        }
    }

    @FXML
    public void refreshBookings(ActionEvent event) {
        loadBookings();
    }
}
