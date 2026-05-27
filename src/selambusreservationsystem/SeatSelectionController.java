package selambusreservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class SeatSelectionController {
    @FXML
    private AnchorPane seatPane;
    @FXML
    private TextField txtPassenger;

    private int tripId;

    private Button selectedButton;
    private int selectedSeatId;


    private Trip selectedTrip;

    public void setTrip(Trip trip) {

        this.selectedTrip = trip;
        this.tripId = trip.getTripId();

        loadSeats();
    }

    public void setTrip(int tripId) {
        this.tripId = tripId;
        loadSeats();
    }

    private void createSeat(int seatId, int seatNumber, double x, double y) {

        Button btn = new Button(String.valueOf(seatNumber));

        btn.setPrefWidth(35);
        btn.setPrefHeight(35);

        btn.setLayoutX(x);
        btn.setLayoutY(y);


        if (isBooked(seatId)) {

            btn.setStyle("""
                        -fx-background-color: red;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                    """);
            btn.setDisable(true);

        } else {
            btn.setStyle("""
                        -fx-background-color: green;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                    """);
        }

        btn.setOnAction(e -> {

            selectedSeatId = seatId;

            if (selectedButton != null) {
                selectedButton.setStyle("-fx-background-color: green;");
            }

            selectedButton = btn;

            btn.setStyle("""
                        -fx-background-color: yellow;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                    """);
        });

        seatPane.getChildren().add(btn);
    }

    private void loadSeats() {

        seatPane.getChildren().removeIf(node -> node instanceof Button);

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql =
                    "SELECT s.seat_id, s.seat_number " +
                            "FROM seat s " +
                            "JOIN trip t ON s.bus_id = t.bus_id " +
                            "WHERE t.trip_id = ? " +
                            "ORDER BY s.seat_number";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tripId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int seatId = rs.getInt("seat_id");
                int seatNumber = rs.getInt("seat_number");

                double x = 0;
                double y = 0;

                switch (seatNumber) {

                    case 1 -> {
                        x = 375;
                        y = 33;
                    }
                    case 2 -> {
                        x = 375;
                        y = 116;
                    }
                    case 3 -> {
                        x = 375;
                        y = 156;
                    }

                    case 4 -> {
                        x = 310;
                        y = 33;
                    }
                    case 5 -> {
                        x = 310;
                        y = 116;
                    }
                    case 6 -> {
                        x = 310;
                        y = 156;
                    }

                    case 7 -> {
                        x = 245;
                        y = 33;
                    }
                    case 8 -> {
                        x = 245;
                        y = 116;
                    }
                    case 9 -> {
                        x = 245;
                        y = 156;
                    }

                    case 10 -> {
                        x = 181;
                        y = 33;
                    }
                    case 11 -> {
                        x = 181;
                        y = 116;
                    }
                    case 12 -> {
                        x = 181;
                        y = 156;
                    }

                    case 13 -> {
                        x = 117;
                        y = 33;
                    }
                    case 14 -> {
                        x = 117;
                        y = 116;
                    }
                    case 15 -> {
                        x = 117;
                        y = 156;
                    }

                    case 16 -> {
                        x = 53;
                        y = 33;
                    }
                    case 17 -> {
                        x = 53;
                        y = 74;
                    }
                    case 18 -> {
                        x = 53;
                        y = 116;
                    }
                    case 19 -> {
                        x = 53;
                        y = 156;
                    }
                }

                createSeat(seatId, seatNumber, x, y);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isBooked(int seatId) {

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql =
                    "SELECT * FROM booking WHERE seat_id=? AND trip_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seatId);
            ps.setInt(2, tripId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void confirmBooking() {

        if (selectedSeatId == 0 || txtPassenger.getText().isEmpty()) {
            return;
        }

        try {

            Connection con = DatabaseConnection.getConnection();

            String sql =
                    "INSERT INTO booking(passenger_name, trip_id, seat_id) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtPassenger.getText());
            ps.setInt(2, tripId);
            ps.setInt(3, selectedSeatId);

            ps.executeUpdate();

            loadSeats(); // refresh UI

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
