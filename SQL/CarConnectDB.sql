create database CarConnectDB;
use CarConnectDB;

-- Customer Table
CREATE TABLE Customer (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(15),
    Address VARCHAR(255),
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    RegistrationDate DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Vehicle Table
CREATE TABLE Vehicle (
    VehicleID INT AUTO_INCREMENT PRIMARY KEY,
    Model VARCHAR(50),
    Make VARCHAR(50),
    Year INT,
    Color VARCHAR(30),
    RegistrationNumber VARCHAR(50) UNIQUE NOT NULL,
    Availability BOOLEAN DEFAULT TRUE,
    DailyRate DECIMAL(10, 2)
);

-- Reservation Table
CREATE TABLE Reservation (
    ReservationID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    VehicleID INT,
    StartDate DATETIME,
    EndDate DATETIME,
    TotalCost DECIMAL(10, 2),
    Status ENUM('pending', 'confirmed', 'completed', 'cancelled') DEFAULT 'pending',
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID)
);

-- Admin Table
CREATE TABLE Admin (
    AdminID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(15),
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('super admin', 'fleet manager', 'support') DEFAULT 'fleet manager',
    JoinDate DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password)
VALUES
('Vaishali', 'Rao', 'vaishali.rao@example.com', '9876543210', '123 Main Street', 'vaishali', 'pass123'),
('Priya', 'Kumar', 'priya.k@example.com', '9123456789', '456 Oak Avenue', 'priyak', 'pass456'),
('Yuvashri', 'Devi', 'yuvashri.d@example.com', '9012345678', '78 Park Lane', 'yuvashri', 'pass789'),
('Ashly', 'Fernandez', 'ashly.f@example.com', '9955667788', '22 Rose Street', 'ashlyf', 'pass101'),
('Sharoon', 'Joseph', 'sharoon.j@example.com', '9800765432', '88 Lakeview Drive', 'sharoonj', 'pass202');

select * from customer;

INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate)
VALUES
('Swift', 'Honda', 2021, 'Black', 'TN01AB1234', TRUE, 1500.00),
('Model S', 'Tesla', 2023, 'White', 'TN02CD5678', TRUE, 3500.00),
('Fortuner', 'Toyota', 2020, 'Silver', 'TN03EF9101', FALSE, 2500.00),
('i20', 'Hyundai', 2022, 'Red', 'TN04GH2233', TRUE, 1200.00),
('E-Class', 'Mercedes', 2024, 'Blue', 'TN05IJ3344', TRUE, 4500.00);

select * from vehicle;

INSERT INTO Admin (FirstName, LastName, Email, PhoneNumber, Username, Password, Role)
VALUES
('Vaishali', 'Admin', 'vaishali.admin@example.com', '9000123456', 'vaishali_admin', 'admin123', 'super admin'),
('Priya', 'Admin', 'priya.admin@example.com', '9887766554', 'priya_admin', 'admin456', 'fleet manager');


select * from admin;

INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status)
VALUES
(1, 1, '2025-05-05 10:00:00', '2025-05-07 10:00:00', 3000.00, 'confirmed'),
(2, 2, '2025-05-06 12:00:00', '2025-05-08 12:00:00', 7000.00, 'pending'),
(3, 3, '2025-05-10 09:00:00', '2025-05-12 09:00:00', 5000.00, 'cancelled'),
(4, 4, '2025-05-04 08:00:00', '2025-05-05 08:00:00', 1200.00, 'completed'),
(5, 5, '2025-05-07 10:00:00', '2025-05-09 10:00:00', 9000.00, 'confirmed');


select * from Reservation;


-- Normalization

-- Customer Table
-- 1NF: All fields contain atomic values – follows 1NF.
-- 2NF: No partial dependency since the primary key is a single column (CustomerID) – follows 2NF.
-- 3NF: No transitive dependencies; all attributes depend only on the primary key – follows 3NF.
-- BCNF: All functional dependencies have superkeys on the left-hand side; Username is unique – follows BCNF.

-- Vehicle Table
-- 1NF: Each field stores indivisible values – follows 1NF.
-- 2NF: All non-key attributes depend on the full primary key (VehicleID) – follows 2NF.
-- 3NF: No transitive dependency between non-key attributes – follows 3NF.
-- BCNF: RegistrationNumber is unique and can be treated as a candidate key – follows BCNF.

-- Admin Table
-- 1NF: Atomic columns, no repeating groups – follows 1NF.
-- 2NF: Primary key is a single column (AdminID); no partial dependency – follows 2NF.
-- 3NF: Attributes like Role and JoinDate directly depend on AdminID – follows 3NF.
-- BCNF: Username is unique and acts as a candidate key; all FDs have superkeys – follows BCNF.

-- Reservation Table
-- 1NF: All fields have atomic values – follows 1NF.
-- 2NF: Primary key is ReservationID; no partial dependency – follows 2NF.
-- 3NF: Slight violation – TotalCost is a derived attribute (from dates and rate), implying transitive dependency.
-- BCNF: Despite the denormalization, all functional dependencies have ReservationID (a superkey) on the left – follows BCNF.
