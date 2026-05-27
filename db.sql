CREATE TABLE bus (
    bus_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_name VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL
);

CREATE TABLE seat (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT NOT NULL,
    seat_number INT NOT NULL,
    FOREIGN KEY (bus_id) REFERENCES bus(bus_id) ON DELETE CASCADE
);

CREATE TABLE trip (
    trip_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_date DATE NOT NULL,
    departure_time TIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (bus_id) REFERENCES bus(bus_id) ON DELETE CASCADE
);

CREATE TABLE booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_name VARCHAR(100) NOT NULL,
    trip_id INT NOT NULL,
    seat_id INT NOT NULL,
    FOREIGN KEY (trip_id) REFERENCES trip(trip_id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seat(seat_id) ON DELETE CASCADE
);
