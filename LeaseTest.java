package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.dao.ICarLeaseRepositoryImpl;
import com.hexaware.entity.Lease;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;

class LeaseTest {
		
		//Lease Creation Test
	    @Test
	    public void testLeaseCreation() throws Exception {
	        // Setup
	        ICarLeaseRepositoryImpl repo = new ICarLeaseRepositoryImpl();
	        
	        Customer customer = new Customer(4, "Emily", "Davis", "emily.davis@example.com", "2233445566");
	        
	        Vehicle car = new Vehicle(2, "Honda", "Civic", 2019, 40.00, "available", 5, 2.0);
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date startDate = sdf.parse("2025-06-01");
	        Date endDate = sdf.parse("2025-06-10");

	        // Action
	        Lease lease = repo.createLease(customer.getCustomerID(), car.getVehicleID(), startDate, endDate);

	        // Assert
	        assertNotNull(lease);
	        assertEquals(customer.getCustomerID(), lease.getCustomerID());
	        assertEquals(car.getVehicleID(), lease.getVehicleID());
	        assertTrue(lease.getStartDate().before(lease.getEndDate()));
	    }
}
