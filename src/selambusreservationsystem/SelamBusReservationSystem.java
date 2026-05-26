/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package selambusreservationsystem;

import java.sql.Connection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SelamBusReservationSystem extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("🚍 Selam Bus System Running!");

        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Selam Bus Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Database is working!");
            }
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }

        launch(args);
    }
}