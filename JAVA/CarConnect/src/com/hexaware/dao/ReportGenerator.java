package com.hexaware.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;

public class ReportGenerator {

    // Reservation report with JOINs and a subquery
    public void generateReservationReport() {
    	String query = "SELECT r.ReservationID, CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, v.Model, v.Make, r.StartDate, r.EndDate, r.TotalCost, r.Status " +
                "FROM Reservation r " +
                "JOIN Customer c ON r.CustomerID = c.CustomerID " +
                "JOIN Vehicle v ON r.VehicleID = v.VehicleID " +
                "WHERE v.DailyRate < (SELECT AVG(DailyRate) FROM Vehicle)";


        try (Connection conn = DBConnUtil.getConnection(
                    DBPropertyUtil.getConnectionString("D:/Eclipse-workspace/Car-Connect-main/db.properties"));
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Reservation Report (Vehicles Below Average Daily Rate):");
            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("ReservationID"));
                System.out.println("Customer Name: " + rs.getString("CustomerName"));
                System.out.println("Vehicle: " + rs.getString("Make") + " " + rs.getString("Model"));
                System.out.println("Start Date: " + rs.getTimestamp("StartDate"));
                System.out.println("End Date: " + rs.getTimestamp("EndDate"));
                System.out.println("Total Cost: " + rs.getDouble("TotalCost"));
                System.out.println("Status: " + rs.getString("Status"));
                System.out.println("---------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vehicle report with JOINs and aggregation subquery
    public void generateVehicleReport() {
        String query = "SELECT v.VehicleID, v.Model, v.Make, v.Year, v.Color, v.RegistrationNumber, v.DailyRate, " +
                       "IFNULL(res.TotalBookings, 0) AS TotalBookings, IFNULL(res.TotalRevenue, 0) AS TotalRevenue " +
                       "FROM Vehicle v " +
                       "LEFT JOIN ( " +
                       "    SELECT VehicleID, COUNT(*) AS TotalBookings, SUM(TotalCost) AS TotalRevenue " +
                       "    FROM Reservation " +
                       "    GROUP BY VehicleID " +
                       ") res ON v.VehicleID = res.VehicleID";

        try (Connection conn = DBConnUtil.getConnection(
                    DBPropertyUtil.getConnectionString("D:/Eclipse-workspace/Car-Connect-main/db.properties"));
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Vehicle Report:");
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID"));
                System.out.println("Model: " + rs.getString("Model"));
                System.out.println("Make: " + rs.getString("Make"));
                System.out.println("Year: " + rs.getInt("Year"));
                System.out.println("Color: " + rs.getString("Color"));
                System.out.println("Registration Number: " + rs.getString("RegistrationNumber"));
                System.out.println("Daily Rate: " + rs.getDouble("DailyRate"));
                System.out.println("No. of Bookings Done: " + rs.getInt("TotalBookings"));
                System.out.println("Total Revenue Generated: " + rs.getDouble("TotalRevenue"));
                System.out.println("---------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
