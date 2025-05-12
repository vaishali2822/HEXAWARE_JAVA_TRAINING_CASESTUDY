package com.hexaware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.hexaware.util.*;


public class AuthenticationService {
	public static boolean authenticateCustomer(String username, String password){
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    try {
	        connection = DBConnUtil.getConnection(DBPropertyUtil.getConnectionString("db.properties"));
	        pstmt = connection.prepareStatement("select * from Customer where Username=?");
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String storedPassword = rs.getString("Password");
	            if (storedPassword.equals(password)) {
	                return true; 
	            } else {
	                System.out.println("Password does not match!");
	                return false;
	            }
	        } else {
	            System.out.println("Username not found!");
	            return false;
	        }
	    } catch (Exception err) {
	        err.printStackTrace();
	        return false;
	    }
	}
	
	public static boolean authenticateAdmin(String username, String password){
	    Connection connection = null;
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
	    try {
	        connection = DBConnUtil.getConnection(DBPropertyUtil.getConnectionString("db.properties"));
	        pstmt = connection.prepareStatement("SELECT * FROM Admin WHERE Username=?");
	        pstmt.setString(1, username);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String storedPassword = rs.getString("Password"); 
	            if (storedPassword.equals(password)) {
	                return true; 
	            } else {
	                System.out.println("Password does not match!");
	                return false; 
	            }
	        } else {
	            System.out.println("Username not found!");
	            return false; 
	        }
	    } catch (Exception err) {
	        err.printStackTrace();
	        return false; 
	    }
	}

}