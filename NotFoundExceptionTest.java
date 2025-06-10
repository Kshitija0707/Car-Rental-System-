package com.hexaware.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.hexaware.dao.ICarLeaseRepositoryImpl;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Vehicle;
import com.hexaware.exception.CarNotFoundException;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.LeaseNotFoundException;
import java.sql.*;

import java.io.IOException;

public class NotFoundExceptionTest {

    @Test
    public void testCarNotFoundException() throws ClassNotFoundException, IOException, SQLException{
        // Setup
        ICarLeaseRepositoryImpl repo = new ICarLeaseRepositoryImpl();
        
        // Assert + Act
        assertThrows(CarNotFoundException.class, () -> {
            repo.findCarById(999);
        });
    }
}
