CREATE TABLE bus (
    bus_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_name VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL
);

CREATE TABLE seat (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT,
    seat_number INT NOT NULL,

    FOREIGN KEY (bus_id) REFERENCES bus(bus_id)
);

CREATE TABLE trip (
    trip_id INT AUTO_INCREMENT PRIMARY KEY,
    bus_id INT,
    origin VARCHAR(100),
    destination VARCHAR(100),
    departure_date DATE,
    departure_time TIME,

    FOREIGN KEY (bus_id) REFERENCES bus(bus_id)
);

CREATE TABLE booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_name VARCHAR(100) NOT NULL,
    trip_id INT,
    seat_id INT,

    FOREIGN KEY (trip_id) REFERENCES trip(trip_id),
    FOREIGN KEY (seat_id) REFERENCES seat(seat_id)
);
