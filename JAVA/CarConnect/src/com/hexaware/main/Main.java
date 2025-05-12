
package com.hexaware.main;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import com.hexaware.dao.*;
import com.hexaware.model.*;
import com.hexaware.exception.ReservationException;
import com.hexaware.exception.VehicleNotFoundException;



public class Main{
	// creating static reference for taking input
		static Scanner scan= new Scanner(System.in);
		
		// creating static reference variable for all interfaces and report
		static ICustomerService customerservice= new CustomerServiceImpl();   
		static IVehicleService  vechicleservice= new VehicleServiceImpl();
		static IReservationService reservationservice= new ReservationServiceImpl();
		static IAdminService adminservice= new AdminServiceImpl();
		static ReportGenerator reportGenerator= new ReportGenerator();

		public static void main(String[] args) {
			System.out.println("Hello, Welcome to CarConnect!");
	        System.out.println("If you're new here, Sign Up"); 
	        System.out.println("Already have an account? Log In"); 

			while (true) {
					System.out.println("----------------------------------------------------------");
		            System.out.println(" -----Menu-----");
		            System.out.println("1. Customer Login");
		            System.out.println("2. Admin Login");
		            System.out.println("3. Sign Up as Customer");
		            System.out.println("4. Sign Up as Admin");
		            System.out.println("5. Exit");
		            System.out.print("Select an option: ");
		            int opt = scan.nextInt();
		            scan.nextLine();
		            switch (opt) {
		                case 1:
		                    customerLogin();
		                    break;
		                case 2:
		                    adminLogin();
		                    break;
		                case 3:
		                    customerSignUp();
		                    break;
		                case 4:
		                    adminSignUp();
		                    break;
		                case 5:
		                    System.out.println("Thank you for trusting CarConnect. Please visit again.");
		                    System.exit(0);
		                    break;
		                default:
		                    System.out.println("Invalid choice. Please try again.");
		            }
		        }
		    }

		
		//------------------------------- CUSTOMER SIGN UP ------------------------------
		private static void customerSignUp() {
	        System.out.print("Enter first name: ");
	        String firstName = scan.nextLine();
	        System.out.print("Enter last name: ");
	        String lastName = scan.nextLine();
	        System.out.print("Enter email: ");
	        String email = scan.nextLine();
	        System.out.print("Enter phone number: ");
	        String phoneNumber = scan.nextLine();
	        System.out.print("Enter address: ");
	        String address = scan.nextLine();
	        System.out.print("Enter username: ");
	        String username = scan.nextLine();
	        System.out.print("Enter password: ");
	        String password = scan.nextLine();
	        
	        Customer customerData = new Customer();
	        customerData.setFirstName(firstName);
	        customerData.setLastName(lastName);
	        customerData.setEmail(email);
	        customerData.setPhoneNumber(phoneNumber);
	        customerData.setAddress(address);
	        customerData.setUsername(username);
	        customerData.setPassword(password);

	        try {
	            customerservice.registerCustomer(customerData);
	            System.out.println("Customer registered successfully!");
	        } catch (Exception err) {
	            System.out.println("Error during registration: " + err.getMessage());
	        }
	    }
		
		
		
		//--------------------- ADMIN SIGN UP --------------------
		private static void adminSignUp() {
	        System.out.print("Enter first name: ");
	        String firstName = scan.nextLine();
	        System.out.print("Enter last name: ");
	        String lastName = scan.nextLine();
	        System.out.print("Enter email: ");
	        String email = scan.nextLine();
	        System.out.print("Enter phone number: ");
	        String phoneNumber = scan.nextLine();
	        System.out.print("Enter username: ");
	        String username = scan.nextLine();
	        System.out.print("Enter password: ");
	        String password = scan.nextLine();
	        System.out.print("Enter role: ");
	        String role = scan.nextLine();

	        Admin admin = new Admin();
	        admin.setFirstName(firstName);
	        admin.setLastName(lastName);
	        admin.setEmail(email);
	        admin.setPhoneNumber(phoneNumber);
	        admin.setUsername(username);
	        admin.setPassword(password);
	        admin.setRole(role);

	        try {
	            adminservice.registerAdmin(admin);
	            System.out.println("Admin registered successfully!");
	        } catch (Exception err) {
	            System.out.println("Error during registration: " + err.getMessage());
	        }
	    }

		//--------------------- CUSTOMER LOGIN --------------------
		private static void customerLogin() {
		    System.out.print("Enter username: ");
		    String username = scan.next();
		    System.out.print("Enter password: ");
		    String password = scan.next();

		    if (AuthenticationService.authenticateCustomer(username, password)) {
		        System.out.println("Authentication successful.");
		        try {
		            Customer customer = customerservice.getCustomerByUsername(username);

		            if (customer != null) {
		                // Debugging output
		                System.out.println("Retrieved Customer: " + customer.getFirstName() + " (" + customer.getUsername() + ")");
		                System.out.println("Login successful! Welcome, " + customer.getFirstName());
		                customerMenu(customer);
		            } else {
		                System.out.println("Customer not found for username: " + username);
		            }
		        } catch (Exception err) {
		            System.out.println("Error fetching customer: " + err.getMessage());
		        }
		    } else {
		        System.out.println("Authentication failed. Please try again.");
		    }
		}

		    private static void customerMenu(Customer customer) {
		        while (true) {
		        	System.out.println("----------------------------------------------------------");
		            System.out.println("***** Customer Menu *****");
		            System.out.println("1. View Available Vehicles");
		            System.out.println("2. Make a Reservation");
		            System.out.println("3. View Reservations");
		            System.out.println("4. Update Reservation");
		            System.out.println("5. Cancel Reservation");
		            System.out.println("6. Update Customer Profile");
		            System.out.println("7. Delete Customer Profile");
		            System.out.println("8. Logout");
		            System.out.print("Select an option: ");
		            int choice = scan.nextInt();

		            switch (choice) {
		            	//-------------------------------------- VIEW LIST OF AVAILABLE VEHCILES ---------------------------------------------------------------
		                case 1:
		                	try {
		                        List<Vehicle> availableVehicles = vechicleservice.getAvailableVehicles();
		                        
		                        if (availableVehicles.isEmpty()) {
		                            System.out.println("No vehicles available at the moment.");
		                        } else {
		                            System.out.println("Available Vehicles:");
		                            for (Vehicle vehicle : availableVehicles) {
		                                System.out.printf("ID: %d, Model: %s, Make: %s, Year: %d, Color: %s, Registration Number: %s, Daily Rate: %.2f\n",
		                                        vehicle.getVehicleID(), vehicle.getModel(), vehicle.getMake(),
		                                        vehicle.getYear(), vehicle.getColor(), vehicle.getRegistrationNumber(),
		                                        vehicle.getDailyRate());
		                            }
		                        }
		                    } catch (SQLException err) {
		                        System.out.println("Error fetching available vehicles: " + err.getMessage());
		                    }
		                    break;
		                //------------------------------------------ MAKE NEW RESERVATION ----------------------------------------------------------------------    
		                case 2:
		                    System.out.print("Enter Vehicle ID to reserve: ");
		                    int vehicleId = scan.nextInt();
		                    scan.nextLine(); // consume newline

		                    try {
		                        Vehicle vehicle = vechicleservice.getVehicleByID(vehicleId);
		                        if (vehicle == null) {
		                            System.out.println("Vehicle not found.");
		                            return;
		                        }
		                        if (!vehicle.isAvailability()) {
		                            System.out.println("The vehicle is not available for reservation.");
		                            return;
		                        }

		                        System.out.print("Enter reservation start date (YYYY-MM-DD): ");
		                        String startDateInput = scan.nextLine();
		                        System.out.print("Enter reservation end date (YYYY-MM-DD): ");
		                        String endDateInput = scan.nextLine();

		                        java.sql.Date startDate = java.sql.Date.valueOf(startDateInput);
		                        java.sql.Date endDate = java.sql.Date.valueOf(endDateInput);

		                        Reservation reservation = new Reservation();
		                        reservation.setCustomerID(customer.getCustomerID()); 
		                        reservation.setVehicleID(vehicleId);
		                        reservation.setStartDate(startDate);
		                        reservation.setEndDate(endDate);
		                        reservation.setStatus("confirmed"); // ✅ Must match ENUM

		                        double totalCost = reservation.calculateTotalCost(vehicle.getDailyRate(), startDate, endDate);
		                        reservation.setTotalCost(totalCost);

		                        reservationservice.createReservation(reservation); //Correct method called
		                        System.out.println("Reservation successful! Total Cost: " + reservation.getTotalCost());

		                    } catch (SQLException e) {
		                        System.out.println("Error making reservation: " + e.getMessage());
		                    } catch (ReservationException e) {
		                        System.out.println("Reservation error: " + e.getMessage());
		                    } catch (IllegalArgumentException e) {
		                        System.out.println("Invalid date format: " + e.getMessage());
		                    } catch (VehicleNotFoundException e) {
		                        System.out.println("Vehicle Not Found: " + e.getMessage());
		                    }
		                    break;


		                //------------------------------------------ VIEW RESERVATION(S) -------------------------------------------
		                case 3:
		                	System.out.println("Select an option to view reservation:");
		                    System.out.println("1. View by Reservation ID");
		                    System.out.println("2. View by Customer ID");
		                    int option = scan.nextInt();
		                    scan.nextLine(); 
		                    try {
		                        if (option == 1) {
		                            System.out.print("Enter Reservation ID: ");
		                            int reservationID = scan.nextInt();
		                            Reservation reservation = reservationservice.getReservationByID(reservationID);
		                            if (reservation != null) {
		                                System.out.println("Reservation Details:");
		                                System.out.println("Reservation ID: " + reservation.getReservationID());
		                                System.out.println("Customer ID: " + reservation.getCustomerID());
		                                System.out.println("Vehicle ID: " + reservation.getVehicleID());
		                                System.out.println("Start Date: " + reservation.getStartDate());
		                                System.out.println("End Date: " + reservation.getEndDate());
		                                System.out.println("Total Cost: " + reservation.calculateTotalCost(reservation.getTotalCost(), null, null));
		                                System.out.println("Status: " + reservation.getStatus());
		                            } else {
		                                System.out.println("Reservation not found.");
		                            }
		                        } else if (option == 2) {
		                            List<Reservation> reservations = reservationservice.getReservationsByCustomerID(customer.getCustomerID());
		                            if (reservations.isEmpty()) {
		                                System.out.println("No reservations found for Customer ID: " + customer.getCustomerID());
		                            } else {
		                                System.out.println("Reservations for Customer ID: " + customer.getCustomerID());
		                                for (Reservation reservation : reservations) {
		                                    System.out.println("Reservation ID: " + reservation.getReservationID());
		                                    System.out.println("Vehicle ID: " + reservation.getVehicleID());
		                                    System.out.println("Start Date: " + reservation.getStartDate());
		                                    System.out.println("End Date: " + reservation.getEndDate());
		                                    System.out.println("Total Cost: " + reservation.calculateTotalCost(reservation.getTotalCost(), null, null));
		                                    System.out.println("Status: " + reservation.getStatus());
		                                    System.out.println("-------------------------------");
		                                }
		                            }
		                        } else {
		                            System.out.println("Invalid option selected.");
		                        }
		                    } catch (SQLException e) {
		                        System.out.println("Error retrieving reservation: " + e.getMessage());
		                    } catch (ReservationException e) {
		                        System.out.println("Reservation error: " + e.getMessage());
		                    }
		                    break;
		                //--------------------------- UPDATE RESERVTAION ----------------------------------------------
		                case 4:
		                    System.out.print("Enter Reservation ID to update: ");
		                    int reservationID = scan.nextInt();
		                    scan.nextLine();
		                    try {
		                        Reservation existingReservation = reservationservice.getReservationByID(reservationID);
		                        if (existingReservation == null) {
		                            System.out.println("Reservation not found.");
		                            return;
		                        }
		                        System.out.println("Current Reservation Details:");
		                        System.out.println("Customer ID: " + existingReservation.getCustomerID());
		                        System.out.println("Vehicle ID: " + existingReservation.getVehicleID());
		                        System.out.println("Start Date: " + existingReservation.getStartDate());
		                        System.out.println("End Date: " + existingReservation.getEndDate());
		                        System.out.println("Total Cost: " + existingReservation.getTotalCost());
		                        System.out.println("Status: " + existingReservation.getStatus());

		                        // Update Vehicle ID
		                        System.out.println("Enter new Vehicle ID (leave blank to keep current): ");
		                        String vehicleIdInput = scan.nextLine();
		                        int vehicleID = vehicleIdInput.isEmpty() ? existingReservation.getVehicleID() : Integer.parseInt(vehicleIdInput);

		                        // Update Start Date
		                        System.out.println("Enter new Start Date (YYYY-MM-DD) (leave blank to keep current): ");
		                        String startDateInput = scan.nextLine();
		                        java.sql.Date startDate = existingReservation.getStartDate(); // Default to existing value if input is blank
		                        if (!startDateInput.isEmpty()) {
		                            try {
		                                startDate = java.sql.Date.valueOf(startDateInput); // Convert String to SQL Date
		                            } catch (IllegalArgumentException e) {
		                                System.out.println("Invalid Start Date format. Please use YYYY-MM-DD.");
		                                return;
		                            }
		                        }

		                        // Update End Date
		                        System.out.println("Enter new End Date (YYYY-MM-DD) (leave blank to keep current): ");
		                        String endDateInput = scan.nextLine();
		                        java.sql.Date endDate = existingReservation.getEndDate(); // Default to existing value if input is blank
		                        if (!endDateInput.isEmpty()) {
		                            try {
		                                endDate = java.sql.Date.valueOf(endDateInput); // Convert String to SQL Date
		                            } catch (IllegalArgumentException e) {
		                                System.out.println("Invalid End Date format. Please use YYYY-MM-DD.");
		                                return;
		                            }
		                        }

		                        // Prepare Updated Reservation
		                        Reservation updatedReservation = new Reservation();
		                        updatedReservation.setReservationID(reservationID);
		                        updatedReservation.setCustomerID(existingReservation.getCustomerID());
		                        updatedReservation.setVehicleID(vehicleID);
		                        updatedReservation.setStartDate(startDate);
		                        updatedReservation.setEndDate(endDate);
		                        updatedReservation.setStatus(existingReservation.getStatus()); // Assuming status remains the same

		                        // Calculate Total Cost
		                        Vehicle vehicle = vechicleservice.getVehicleByID(vehicleID);
		                        if (vehicle != null) {
		                            updatedReservation.setTotalCost(updatedReservation.calculateTotalCost(vehicle.getDailyRate(), startDate, endDate));
		                        } else {
		                            System.out.println("Vehicle not found.");
		                            return;
		                        }

		                        // Update Reservation in DB
		                        reservationservice.updateReservation(updatedReservation);
		                        System.out.println("Reservation updated successfully.");

		                    } catch (SQLException e) {
		                        System.out.println("Error updating reservation: " + e.getMessage());
		                    } catch (ReservationException e) {
		                        System.out.println("Reservation error: " + e.getMessage());
		                    } catch (IllegalArgumentException e) {
		                        System.out.println("Invalid date format: " + e.getMessage());
		                    } catch (VehicleNotFoundException e) {
		                        System.out.println("Vehicle Not Found error: " + e.getMessage());
		                    }
		                    break;

		                //------------------------------------CANCEL RESERVATION-----------------------------------------------
		                case 5:
		                	System.out.print("Enter Reservation ID to cancel: ");
		                    int rid = scan.nextInt();
		                    
		                    try {
		                        reservationservice.cancelReservation(rid);
		                    } catch (SQLException e) {
		                        System.out.println("Error canceling reservation: " + e.getMessage());
		                    } catch (ReservationException e) {
		                        System.out.println("Reservation error: " + e.getMessage());
		                    }
		                	break;
		                //----------------------------------UPDATE CUSTOMER PROFILE------------------------------
		                case 6:
		                	System.out.println("Updating profile for Customer ID: " + customer.getCustomerID());
		                    scan.nextLine();
		                    System.out.print("Enter new First Name (leave blank to keep current): ");
		                    String firstName = scan.nextLine();
		                    if (firstName.isEmpty()) {
		                        firstName = customer.getFirstName();
		                    }
		                    System.out.print("Enter new Last Name (leave blank to keep current): ");
		                    String lastName = scan.nextLine();
		                    if (lastName.isEmpty()) {
		                        lastName = customer.getLastName();
		                    }
		                    System.out.print("Enter new Email (leave blank to keep current): ");
		                    String email = scan.nextLine();
		                    if (email.isEmpty()) {
		                        email = customer.getEmail();
		                    }
		                    System.out.print("Enter new Phone Number (leave blank to keep current): ");
		                    String phoneNumber = scan.nextLine();
		                    if (phoneNumber.isEmpty()) {
		                        phoneNumber = customer.getPhoneNumber();
		                    }
		                    System.out.print("Enter new Address (leave blank to keep current): ");
		                    String address = scan.nextLine();
		                    if (address.isEmpty()) {
		                        address = customer.getAddress();
		                    }
		                    System.out.print("Enter new Username (leave blank to keep current): ");
		                    String username = scan.nextLine();
		                    if (username.isEmpty()) {
		                        username = customer.getUsername();
		                    }
		                    System.out.print("Enter new Password (leave blank to keep current): ");
		                    String password = scan.nextLine();
		                    if (password.isEmpty()) {
		                        password = customer.getPassword();
		                    }
		                    Customer updatedCustomer = new Customer();
		                    updatedCustomer.setCustomerID(customer.getCustomerID());
		                    updatedCustomer.setFirstName(firstName);
		                    updatedCustomer.setLastName(lastName);
		                    updatedCustomer.setEmail(email);
		                    updatedCustomer.setPhoneNumber(phoneNumber);
		                    updatedCustomer.setAddress(address);
		                    updatedCustomer.setUsername(username);
		                    updatedCustomer.setPassword(password);
		                    try {
		                        customerservice.updateCustomer(updatedCustomer);
		                    } catch (Exception e) {
		                        System.out.println("Error updating customer profile: " + e.getMessage());
		                    }
		                	break;
		                //------------------------------------DELETE CUSTOMER PROFILE------------------------------------
		                case 7:
		                	System.out.print("Are you sure you want to delete your profile? (yes/no):");
		                	scan.nextLine();
		                    String confirmation = scan.next();
		                    if (confirmation.equalsIgnoreCase("yes")) {
		                        try {
		                            customerservice.deleteCustomer(customer.getCustomerID());
		                            System.out.println("Your profile has been successfully deleted.");
		                        } catch (Exception e) {
		                            System.out.println("Error deleting customer profile: " + e.getMessage());
		                        }
		                    } else {
		                        System.out.println("Profile deletion cancelled.");
		                    }
		                	break;
		                case 8:
		                    System.out.println("Logging out...");
		                    return; 
		                default:
		                    System.out.println("Invalid choice. Please try again.");
		            }
		        }
		    }

		    
		    
		    
		    
		    
		  //--------------------- ADMIN LOGIN --------------------
		    private static void adminLogin() {
		        System.out.print("Enter Admin Username: ");
		        String username = scan.next(); 
		        System.out.print("Enter Admin Password: ");
		        String password = scan.next(); 
		        if (AuthenticationService.authenticateAdmin(username, password)) {
		            System.out.println("Authentication successful.");
		            try {
		                Admin admin = adminservice.getAdminByUsername(username);
		                System.out.println("Login successful! Welcome, " + admin.getFirstName());
		                adminDashboard(admin);
		            } catch (Exception err) {
		                System.out.println(err.getMessage());
		            }
		        } else {
		            System.out.println("Authentication failed. Please Try Again");
		        }
		    }
		    private static void adminDashboard(Admin admin) {
		        System.out.println("Welcome to the Admin Dashboard.");
		        adminMenu();
		    }


		    private static void adminMenu() {
		        while (true) {
		            System.out.println("***** Admin Menu *****");
		            System.out.println("1. View Reports");
		            System.out.println("2. Manage Vehicles");
		            System.out.println("3. View Customer Profile");
		            System.out.println("4. View Admin Profile");
		            System.out.println("5. Update Admin Profile");
		            System.out.println("6. Delete Admin Profile");
		            System.out.println("7. Exit");
		            System.out.print("Select an option: ");
		            int choice = scan.nextInt();
		            scan.nextLine();
		            switch (choice) {
		            	//-----------------------------VIEW REPORT----------------------------------------------------
		                case 1:
		                	Scanner scan = new Scanner(System.in);
		                    while (true) {
		                        System.out.println("Select a report to generate:");
		                        System.out.println("1. Generate Reservation Report");
		                        System.out.println("2. Generate Vehicle Reservation Report");
		                        System.out.println("3. Exit");
		                        int opt = scan.nextInt();
		                        switch (opt) {
		                            case 1:
		                                reportGenerator.generateReservationReport();
		                                break;
		                            case 2:
		                                reportGenerator.generateVehicleReport();
		                                break;
		                            
		                            case 3:
		                                System.out.println("Exiting report generation.");
		                                return; 
		                            default:
		                                System.out.println("Invalid choice. Please try again.");
		                        }
		                    }
		                //-------------------------------------------------------MANAGE VEHICLES--------------------------------------------------------------------
		                case 2:
		                	Scanner scanner = new Scanner(System.in);
		                    while (true) {
		                        System.out.println("Select an option to manage vehicles:");
		                        System.out.println("1. Add Vehicle");
		                        System.out.println("2. Update Vehicle");
		                        System.out.println("3. Remove Vehicle");
		                        System.out.println("4. Exit");
		                        int opt = scanner.nextInt();
		                        scanner.nextLine();
		                        switch (opt) {
		                            case 1:			// Add Vehicle
		                                Vehicle newVehicle = new Vehicle();
		                                System.out.print("Enter Vehicle ID: ");
		                                newVehicle.setVehicleID(scanner.nextInt());
		                                System.out.print("Enter Model: ");
		                                newVehicle.setModel(scanner.next());
		                                System.out.print("Enter Make: ");
		                                newVehicle.setMake(scanner.next());
		                                System.out.print("Enter Year: ");
		                                newVehicle.setYear(scanner.nextInt());
		                                System.out.print("Enter Color: ");
		                                newVehicle.setColor(scanner.next());
		                                System.out.print("Enter Registration Number: ");
		                                newVehicle.setRegistrationNumber(scanner.next());
		                                System.out.print("Enter Availability (true/false): ");
		                                newVehicle.setAvailability(scanner.nextBoolean());
		                                System.out.print("Enter Daily Rate: ");
		                                newVehicle.setDailyRate(scanner.nextDouble());
		                                try {
		                                    vechicleservice.addVehicle(newVehicle);
		                                } catch (SQLException e) {
		                                    System.out.println("Error adding vehicle: " + e.getMessage());
		                                }
		                                break;

		                            case 2:			// Update Vehicle
		                                System.out.print("Enter Vehicle ID to update: ");
		                                int updateVehicleID = scanner.nextInt();
		                                Vehicle updateVehicle = new Vehicle();
		                                updateVehicle.setVehicleID(updateVehicleID);
		                                System.out.print("Enter new Model: ");
		                                updateVehicle.setModel(scanner.next());
		                                System.out.print("Enter new Make: ");
		                                updateVehicle.setMake(scanner.next());
		                                System.out.print("Enter new Year: ");
		                                updateVehicle.setYear(scanner.nextInt());
		                                System.out.print("Enter new Color: ");
		                                updateVehicle.setColor(scanner.next());
		                                System.out.print("Enter new Registration Number: ");
		                                updateVehicle.setRegistrationNumber(scanner.next());
		                                System.out.print("Enter new Availability (true/false): ");
		                                updateVehicle.setAvailability(scanner.nextBoolean());
		                                System.out.print("Enter new Daily Rate: ");
		                                updateVehicle.setDailyRate(scanner.nextDouble());
		                                try {
		                                    vechicleservice.updateVehicle(updateVehicle);
		                                } catch (SQLException | VehicleNotFoundException e) {
		                                    System.out.println("Error updating vehicle: " + e.getMessage());
		                                }
		                                break;
		                            case 3:			// Remove Vehicle	                                
		                                System.out.print("Enter Vehicle ID to remove: ");
		                                int vehicleIDToRemove = scanner.nextInt();
		                                try {
		                                    vechicleservice.removeVehicle(vehicleIDToRemove);
		                                } catch (SQLException | VehicleNotFoundException e) {
		                                    System.out.println("Error removing vehicle: " + e.getMessage());
		                                }
		                                break;
		                            case 4:
		                                System.out.println("Exiting vehicle management.");
		                                scanner.close();
		                                return;
		                            default:
		                                System.out.println("Invalid choice. Please try again.");
		                        }
		                    }
		                //--------------------------------------GET CUSTOMER BY CUSTOMER ID-----------------------------------------------
		                case 3:
		                	Scanner s = new Scanner(System.in);
		                    System.out.print("Enter Customer ID: ");
		                    int customerID = s.nextInt();
		                    try {
		                        Customer customer = customerservice.getCustomerByID(customerID);
		                        if (customer != null) {
		                            System.out.println("Customer Details:");
		                            System.out.println("Customer ID: " + customer.getCustomerID());
		                            System.out.println("First Name: " + customer.getFirstName());
		                            System.out.println("Last Name: " + customer.getLastName());
		                            System.out.println("Email: " + customer.getEmail());
		                            System.out.println("Phone Number: " + customer.getPhoneNumber());
		                            System.out.println("Address: " + customer.getAddress());
		                            System.out.println("Username: " + customer.getUsername());
		                            System.out.println("Registration Date: " + customer.getRegistrationDate());
		                        } else {
		                            System.out.println("Customer not found with ID: " + customerID);
		                        }
		                    } catch (Exception e) {
		                        System.out.println("Error retrieving customer details: " + e.getMessage());
		                    }
		                    break;
		                //------------------------------------------------GET ADMIN BY ADMIN ID------------------------------------------------
		                case 4:
		                	Scanner s1 = new Scanner(System.in);
		                    System.out.print("Enter Admin ID: ");
		                    int adminID = s1.nextInt();
		                    AdminServiceImpl admin_service = new AdminServiceImpl();
		                    try {
		                        Admin admin = admin_service.getAdminByID(adminID);
		                        if (admin != null) {
		                            System.out.println("Admin Profile:");
		                            System.out.println("Admin ID: " + admin.getAdminID());
		                            System.out.println("First Name: " + admin.getFirstName());
		                            System.out.println("Last Name: " + admin.getLastName());
		                            System.out.println("Email: " + admin.getEmail());
		                            System.out.println("Phone Number: " + admin.getPhoneNumber());
		                            System.out.println("Username: " + admin.getUsername());
		                            System.out.println("Role: " + admin.getRole());
		                            System.out.println("Join Date: " + admin.getJoinDate());
		                        } else {
		                            System.out.println("Admin not found with ID: " + adminID);
		                        }
		                    } catch (Exception e) {
		                        System.out.println("Error retrieving admin details: " + e.getMessage());
		                    }
		                	break;
//---------------------------------------- UPDATE ADMIN PROFILE------------------------------------------------------
		                case 5:
		                	 Scanner scan1 = new Scanner(System.in);
		                	    System.out.print("Enter Admin ID to update: ");
		                	    int adminiD = scan1.nextInt();
		                	    scan1.nextLine(); // Consume newline

		                	    // Assuming you have an AdminService class
		                	    AdminServiceImpl adminService = new AdminServiceImpl();
		                	    
		                	    try {
		                	        Admin admin = adminService.getAdminByID(adminiD);
		                	        if (admin != null) {
		                	            System.out.println("Current Profile:");
		                	            System.out.println("First Name: " + admin.getFirstName());
		                	            System.out.println("Last Name: " + admin.getLastName());
		                	            System.out.println("Email: " + admin.getEmail());
		                	            System.out.println("Phone Number: " + admin.getPhoneNumber());
		                	            System.out.println("Username: " + admin.getUsername());
		                	            System.out.println("Role: " + admin.getRole());

		                	            // Update fields
		                	            System.out.print("Enter new First Name (or press Enter to keep current): ");
		                	            String firstName = scan1.nextLine();
		                	            if (!firstName.isEmpty()) {
		                	                admin.setFirstName(firstName);
		                	            }

		                	            System.out.print("Enter new Last Name (or press Enter to keep current): ");
		                	            String lastName = scan1.nextLine();
		                	            if (!lastName.isEmpty()) {
		                	                admin.setLastName(lastName);
		                	            }

		                	            System.out.print("Enter new Email (or press Enter to keep current): ");
		                	            String email = scan1.nextLine();
		                	            if (!email.isEmpty()) {
		                	                admin.setEmail(email);
		                	            }

		                	            System.out.print("Enter new Phone Number (or press Enter to keep current): ");
		                	            String phoneNumber = scan1.nextLine();
		                	            if (!phoneNumber.isEmpty()) {
		                	                admin.setPhoneNumber(phoneNumber);
		                	            }

		                	            System.out.print("Enter new Username (or press Enter to keep current): ");
		                	            String username = scan1.nextLine();
		                	            if (!username.isEmpty()) {
		                	                admin.setUsername(username);
		                	            }

		                	            System.out.print("Enter new Password (or press Enter to keep current): ");
		                	            String password = scan1.nextLine();
		                	            if (!password.isEmpty()) {
		                	                admin.setPassword(password);
		                	            }

		                	            System.out.print("Enter new Role (or press Enter to keep current): ");
		                	            String role = scan1.nextLine();
		                	            if (!role.isEmpty()) {
		                	                admin.setRole(role);
		                	            }

		                	            // Call updateAdmin method
		                	            adminService.updateAdmin(admin);
		                	            System.out.println("Admin Profile Updated");
		                	        } else {
		                	            System.out.println("Admin not found with ID: " + adminiD);
		                	        }
		                	    } catch (Exception e) {
		                	        System.out.println("Error updating admin: " + e.getMessage());
		                	    }
		                	break;
//------------------------------------------DELETE ADMIN PROFILE--------------------------------------------------
		                case 6:
		                	Scanner s2 = new Scanner(System.in);
		                	AdminServiceImpl adminservice = new AdminServiceImpl();
		                    System.out.print("Enter Admin ID to delete: ");
		                    int adminId = s2.nextInt();
		                    try {
		                        // Call deleteAdmin method
		                    	adminservice.deleteAdmin(adminId);
		                    } catch (Exception e) {
		                        System.out.println("Error deleting admin: " + e.getMessage());
		                    }
		                	break;
		                case 7:
		                    System.out.println("Logging out...");
		                    return;
		                default:
		                    System.out.println("Invalid choice. Please try again.");
		            }
		        }
		    }

	}