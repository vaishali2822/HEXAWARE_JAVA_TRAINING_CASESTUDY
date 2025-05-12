package com.hexaware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.model.Reservation;
import com.hexaware.exception.ReservationException;
import com.hexaware.util.DBConnUtil;
import com.hexaware.util.DBPropertyUtil;


public class ReservationServiceImpl implements IReservationService{
static Connection connection;
	
	private static void getConnection() throws SQLException{
		if(connection ==null)
			connection = DBConnUtil.getConnection(DBPropertyUtil.getConnectionString("db.properties"));
	}
	
	
	//-------------------- Get Reservation Details Using Reservation ID --------------------
	@Override
	public Reservation getReservationByID(int reservationID) throws SQLException, ReservationException {
	    getConnection();
	    Reservation r = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        pstmt = connection.prepareStatement("select * from Reservation where ReservationID =?");
	        pstmt.setInt(1,reservationID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            r = new Reservation(
	                rs.getInt("ReservationID"),
	                rs.getInt("CustomerID"),
	                rs.getInt("VehicleID"),
	                rs.getDate("StartDate"),
	                rs.getDate("EndDate"),
	                rs.getDouble("TotalCost"),
	                rs.getString("Status")
	            );
	        } else {
	            throw new ReservationException("Reservation with ID " + reservationID + " not found.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL error while finding Reservation: " + e.getMessage());
	        throw e;
	    } finally {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	    }

	    return r;
	}


	
	//-------------------- Get Reservation Details Using Customer ID --------------------
	@Override
	public List<Reservation> getReservationsByCustomerID(int customerID) throws SQLException, ReservationException {
		getConnection();
		List<Reservation> reservation =new ArrayList<Reservation>();
		Reservation r=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = connection.prepareStatement("Select * from Reservation where customerID=?");
			pstmt.setInt(1, customerID);
			ResultSet rs= pstmt.executeQuery();
			if(rs.next()) {
				r = new Reservation(rs.getInt("ReservationID"),
						rs.getInt("CustomerID"),
						rs.getInt("VehicleID"),
						rs.getDate("StartDate"),
						rs.getDate("EndDate"),
						rs.getDouble("TotalCost"),
						rs.getString("Status")
						);
				reservation.add(r);
			}
		}catch(Exception err) {
			err.printStackTrace();
			System.out.println("Error while finding Reservation details");
		}
		return reservation;
	}
	
	
	//-------------------- Add New Reservation --------------------

	public void createReservation(Reservation reservationData) throws SQLException, ReservationException {
	    getConnection();
	    connection.setAutoCommit(false);

	    PreparedStatement pstmt = null;

	    try {
	        if (reservationData.getStartDate() == null || reservationData.getEndDate() == null) {
	            throw new ReservationException("Start date or end date cannot be null.");
	        }

	        pstmt = connection.prepareStatement(
	            "INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)"
	        );

	        pstmt.setInt(1, reservationData.getCustomerID());
	        pstmt.setInt(2, reservationData.getVehicleID());
	        pstmt.setDate(3, new java.sql.Date(reservationData.getStartDate().getTime()));
	        pstmt.setDate(4, new java.sql.Date(reservationData.getEndDate().getTime()));
	        pstmt.setDouble(5, reservationData.getTotalCost());
	        pstmt.setString(6, reservationData.getStatus().toLowerCase());

	        int rowsInserted = pstmt.executeUpdate();

	        if (rowsInserted == 1) {
	            connection.commit();
	            System.out.println("✅ Reservation stored in DB.");
	        } else {
	            throw new ReservationException("Insert failed. No rows affected.");
	        }

	    } catch (SQLException e) {
	        connection.rollback();
	        System.out.println("⛔ SQL Error: " + e.getMessage());
	        throw e;
	    } finally {
	        if (pstmt != null) pstmt.close();
	        if (connection != null) connection.setAutoCommit(true);
	    }
	}



	//-------------------- Update Reservation Details --------------------
	@Override
	public void updateReservation(Reservation reservationData) throws SQLException {
	    getConnection();
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = connection.prepareStatement("UPDATE Reservation SET CustomerID=?, VehicleID=?, StartDate=?, EndDate=?, TotalCost=?, Status=? WHERE ReservationID=?");

	        // Set parameters in the correct order
	        pstmt.setInt(1, reservationData.getCustomerID());
	        pstmt.setInt(2, reservationData.getVehicleID());
	        pstmt.setDate(3, reservationData.getStartDate());
	        pstmt.setDate(4, reservationData.getEndDate());
	        pstmt.setDouble(5, reservationData.getTotalCost());

	        // Ensure Status is set as a valid string (ENUM)
	        pstmt.setString(6, reservationData.getStatus());

	        // Set the Reservation ID for the WHERE clause
	        pstmt.setInt(7, reservationData.getReservationID());

	        // Execute update and check result
	        int r = pstmt.executeUpdate();
	        if (r == 1) {
	            System.out.println("Reservation details successfully updated.");
	        }
	    } catch (SQLException err) {
	        err.printStackTrace();
	        System.out.println("Error while updating Reservation details.");
	    }
	}


	
	//-------------------- Cancel Reservation Details Using Reservation ID --------------------
	@Override
	public void cancelReservation(int reservationID) throws SQLException, ReservationException {
		getConnection();
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement("delete from Reservation where ReservationID=?");
			pstmt.setInt(1,reservationID);
			int r=pstmt.executeUpdate();
			if(r==1)
				System.out.println("Reservation Details Successfully Deleted");
			
		}catch(SQLException err)
		{
			err.printStackTrace();
			System.out.println("Error while deleting Reservation details.....");
		}
	}




}  