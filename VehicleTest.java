package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import com.hexaware.dao.ICarLeaseRepositoryImpl;
import com.hexaware.entity.Vehicle;
import com.hexaware.exception.CarNotFoundException;

class VehicleTest {

	@Test
	public void testCarCreation() throws ClassNotFoundException, SQLException, IOException, CarNotFoundException 
	{
		ICarLeaseRepositoryImpl repo = new ICarLeaseRepositoryImpl();
		
		// Create a car object
		Vehicle car = new Vehicle(8, "maruti", "suzuki", 2022, 50.0, "available", 5, 1.8);
		
		// Add car to the repository
		repo.addCar(car);
		
		// Fetch the car back 
		Vehicle fetchedCar = repo.findCarById(car.getVehicleID());
		
		
		assertNotNull(fetchedCar);
		assertEquals("maruti", fetchedCar.getMake());
		assertEquals("suzuki", fetchedCar.getModel());
		assertEquals(2022, fetchedCar.getYear());
	}
	

}
