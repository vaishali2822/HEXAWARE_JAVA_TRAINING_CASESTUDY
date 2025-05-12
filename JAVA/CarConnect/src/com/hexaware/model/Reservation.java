package com.hexaware.model;
import java.sql.Date;

import com.hexaware.exception.ReservationException;

public class Reservation {
	private int reservationID;
    private int customerID; 
    private int vehicleID;  
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private String status;
    
    
	
	
	//Default Constructor
	public Reservation() {
		// TODO Auto-generated constructor stub
	}

	//Parameterized Constructor
	public Reservation(int reservationID, int customerID, int vehicleID, Date startDate, Date endDate, double totalCost,
			String status) {
		super();
		this.reservationID = reservationID;
		this.customerID = customerID;
		this.vehicleID = vehicleID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalCost = totalCost;
		this.status = status;
	}


	//Getter and Setter
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	public Date getStartDate() {
		return (Date) startDate;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
	    this.totalCost = totalCost;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
	// Method to calculate the total cost of the reservation
	public double calculateTotalCost(double dailyRate, Date startDate, Date endDate) throws ReservationException {
	    if (startDate == null || endDate == null) {
	        throw new ReservationException("Start date or end date cannot be null.");
	    }

	    long diffMillis = endDate.getTime() - startDate.getTime();
	    long diffDays = Math.max(1, diffMillis / (1000 * 60 * 60 * 24));
	    return dailyRate * diffDays;
	}






}
