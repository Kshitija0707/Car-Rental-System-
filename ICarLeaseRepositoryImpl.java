package com.hexaware.dao;

import com.hexaware.entity.Vehicle;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Lease;
import com.hexaware.entity.Payment;
import com.hexaware.exception.CarNotFoundException;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.LeaseNotFoundException;
import com.hexaware.util.DBConnUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {

    private Connection conn;

    public ICarLeaseRepositoryImpl() throws SQLException, ClassNotFoundException, IOException{
        this.conn = DBConnUtil.getConnection(); 
    }
    // Car Management

    @Override
    public void addCar(Vehicle car) {
        try {
            String sql = "INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setInt(3, car.getYear());
            pstmt.setDouble(4, car.getDailyRate());
            pstmt.setString(5, car.getStatus());
            pstmt.setInt(6, car.getPassengerCapacity());
            pstmt.setDouble(7, car.getengineCapacity());
            pstmt.executeUpdate();
            System.out.println("Car added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(int carID) {
        try {
            String sql = "DELETE FROM Vehicle WHERE vehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, carID);
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new CarNotFoundException("Car with ID " + carID + " not found.");
            }
            System.out.println("Car removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CarNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> availableCars = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Vehicle WHERE status = 'available'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vehicle car = new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("dailyRate"),
                        rs.getString("status"),
                        rs.getInt("passengerCapacity"),
                        rs.getDouble("engineCapacity")
                );
                availableCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableCars;
    }

    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> rentedCars = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Vehicle WHERE status = 'rented'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vehicle car = new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("dailyRate"),
                        rs.getString("status"),
                        rs.getInt("passengerCapacity"),
                        rs.getDouble("engineCapacity")
                );
                rentedCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedCars;
    }

    
    @Override
    public Vehicle findCarById(int carID) throws CarNotFoundException {
        Vehicle car = null;
        try {
            String sql = "SELECT * FROM Vehicle WHERE vehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, carID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                car = new Vehicle(
                        rs.getInt("vehicleID"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("dailyRate"),
                        rs.getString("status"),
                        rs.getInt("passengerCapacity"),
                        rs.getDouble("engineCapacity")
                );
            } else {
                throw new CarNotFoundException("Car with ID " + carID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
    
    @Override
    public void addCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void removeCustomer(int customerID) {
        try {
            String sql = "DELETE FROM Customer WHERE customerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
            System.out.println("Customer removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Customer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer WHERE customerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                );
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Lease
    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) {
        Lease lease = null;
        try {
            // Insert new Lease
            String sql = "INSERT INTO Lease (vehicleID, customerID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carID);
            pstmt.setInt(2, customerID);
            pstmt.setDate(3, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(4, new java.sql.Date(endDate.getTime()));
            
            // Determine type based on dates (simple logic)
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long diffDays = diffInMillies / (1000 * 60 * 60 * 24);
            String leaseType = (diffDays > 30) ? "Monthly" : "Daily";
            
            pstmt.setString(5, leaseType);
            pstmt.executeUpdate();

            // Update vehicle status to notAvailable
            String updateStatus = "UPDATE Vehicle SET status = 'notAvailable' WHERE vehicleID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateStatus);
            updateStmt.setInt(1, carID);
            updateStmt.executeUpdate();

            // Get leaseID generated
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int leaseID = rs.getInt(1);
                lease = new Lease(leaseID, carID, customerID, startDate, endDate, leaseType);
            }
            System.out.println("Lease created successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lease;
    }

    @Override
    public void returnCar(int leaseID) throws LeaseNotFoundException {
        try {
            // Find the lease
            String sql = "SELECT vehicleID FROM Lease WHERE leaseID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, leaseID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int vehicleID = rs.getInt("vehicleID");

                // Update Vehicle status to 'available'
                String updateVehicle = "UPDATE Vehicle SET status = 'available' WHERE vehicleID = ?";
                PreparedStatement updateVehicleStmt = conn.prepareStatement(updateVehicle);
                updateVehicleStmt.setInt(1, vehicleID);
                updateVehicleStmt.executeUpdate();

                // Delete the Lease record
                String deleteLease = "DELETE FROM Lease WHERE leaseID = ?";
                PreparedStatement deleteLeaseStmt = conn.prepareStatement(deleteLease);
                deleteLeaseStmt.setInt(1, leaseID);
                deleteLeaseStmt.executeUpdate();

                System.out.println("Car returned and lease closed successfully.");
            } else {
                throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> activeLeases = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Lease WHERE endDate >= CURDATE()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Lease lease = new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("customerID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                );
                activeLeases.add(lease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeLeases;
    }
    
    @Override
    public List<Lease> listLeaseHistory() {
        List<Lease> allLeases = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Lease";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Lease lease = new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("customerID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                );
                allLeases.add(lease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allLeases;
    }
    
    // payment 
    @Override
    public void recordPayment(Lease lease, double amount) {
        try {
            String sql = "INSERT INTO Payment (leaseID, paymentDate, amount) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lease.getLeaseID());
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
            System.out.println("Payment recorded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
