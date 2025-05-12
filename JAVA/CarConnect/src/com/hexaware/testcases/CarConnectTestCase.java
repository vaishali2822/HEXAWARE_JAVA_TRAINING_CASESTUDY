package com.hexaware.testcases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hexaware.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class CarConnectTestCase {

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(101, "Model S", "Tesla", 2023, "Red", "TS12345", true, 2500.0);
    }

    @Test
    void testAddNewVehicle() {
        assertEquals(101, vehicle.getVehicleID());
        assertEquals("Model S", vehicle.getModel());
        assertEquals("Tesla", vehicle.getMake());
        assertEquals(2023, vehicle.getYear());
        assertEquals("Red", vehicle.getColor());
        assertEquals("TS12345", vehicle.getRegistrationNumber());
        assertTrue(vehicle.isAvailability());
        assertEquals(2500.0, vehicle.getDailyRate());
    }

    @Test
    void testUpdateVehicleDetails() {
        vehicle.setColor("Blue");
        vehicle.setAvailability(false);
        vehicle.setDailyRate(3000.0);

        assertEquals("Blue", vehicle.getColor());
        assertFalse(vehicle.isAvailability());
        assertEquals(3000.0, vehicle.getDailyRate());
    }

    @Test
    void testGetAvailableVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        vehicleList.add(new Vehicle(102, "Civic", "Honda", 2022, "White", "MH56789", false, 1800.0));
        vehicleList.add(new Vehicle(103, "Mustang", "Ford", 2021, "Black", "DL99876", true, 2200.0));

        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.isAvailability()) {
                availableVehicles.add(v);
            }
        }

        assertEquals(2, availableVehicles.size());
    }

    @Test
    void testGetAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        vehicleList.add(new Vehicle(102, "Civic", "Honda", 2022, "White", "MH56789", false, 1800.0));

        assertEquals(2, vehicleList.size());
    }
}
