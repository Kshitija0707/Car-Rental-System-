package com.hexaware.main;

import com.hexaware.dao.ICarLeaseRepository;

import com.hexaware.dao.ICarLeaseRepositoryImpl;
import com.hexaware.entity.Vehicle;
import com.hexaware.entity.Customer;
import com.hexaware.entity.Lease;
import com.hexaware.exception.CarNotFoundException;
import com.hexaware.exception.CustomerNotFoundException;
import com.hexaware.exception.LeaseNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.sql.SQLException;


public class MainModule {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException  {
        Scanner sc = new Scanner(System.in);
        ICarLeaseRepository repo = new ICarLeaseRepositoryImpl();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n====== Car Rental System ======");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. List Available Cars");
            System.out.println("4. Find Car By ID");
            System.out.println("5. Add Customer");
            System.out.println("6. Remove Customer");
            System.out.println("7. List Customers");
            System.out.println("8. Find Customer By ID");
            System.out.println("9. Create Lease");
            System.out.println("10. Return Car");
            System.out.println("11. List Active Leases");
            System.out.println("12. List Lease History");
            System.out.println("13. Record Payment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Make: ");
                        String make = sc.next();
                        System.out.print("Enter Model: ");
                        String model = sc.next();
                        System.out.print("Enter Year: ");
                        int year = sc.nextInt();
                        System.out.print("Enter Daily Rate: ");
                        double rate = sc.nextDouble();
                        System.out.print("Enter Status (available/notAvailable): ");
                        String status = sc.next();
                        System.out.print("Enter Passenger Capacity: ");
                        int capacity = sc.nextInt();
                        System.out.print("Enter Engine Capacity: ");
                        double engine = sc.nextDouble();

                        Vehicle car = new Vehicle(0, make, model, year, rate, status, capacity, engine);
                        repo.addCar(car);
                        break;

                    case 2:
                        System.out.print("Enter Car ID to remove: ");
                        int carID = sc.nextInt();
                        repo.removeCar(carID);
                        break;

                    case 3:
                        List<Vehicle> availableCars = repo.listAvailableCars();
                        for (Vehicle v : availableCars) {
                            System.out.println(v.getVehicleID() + " " + v.getMake() + " " + v.getModel());
                        }
                        break;

                    case 4:
                        System.out.print("Enter Car ID to find: ");
                        int findCarId = sc.nextInt();
                        Vehicle foundCar = repo.findCarById(findCarId);
                        System.out.println(foundCar.getVehicleID() + " " + foundCar.getMake() + " " + foundCar.getModel());
                        break;

                    case 5:
                        System.out.print("Enter First Name: ");
                        String fname = sc.next();
                        System.out.print("Enter Last Name: ");
                        String lname = sc.next();
                        System.out.print("Enter Email: ");
                        String email = sc.next();
                        System.out.print("Enter Phone Number: ");
                        String phone = sc.next();

                        Customer customer = new Customer(0, fname, lname, email, phone);
                        repo.addCustomer(customer);
                        break;

                    case 6:
                        System.out.print("Enter Customer ID to remove: ");
                        int custID = sc.nextInt();
                        repo.removeCustomer(custID);
                        break;

                    case 7:
                        List<Customer> customers = repo.listCustomers();
                        for (Customer c : customers) {
                            System.out.println(c.getCustomerID() + " " + c.getFirstName() + " " + c.getLastName());
                        }
                        break;

                    case 8:
                        System.out.print("Enter Customer ID to find: ");
                        int findCustID = sc.nextInt();
                        Customer foundCustomer = repo.findCustomerById(findCustID);
                        System.out.println(foundCustomer.getCustomerID() + " " + foundCustomer.getFirstName() + " " + foundCustomer.getLastName());
                        break;

                    case 9:
                        System.out.print("Enter Customer ID: ");
                        int custIdForLease = sc.nextInt();
                        System.out.print("Enter Car ID: ");
                        int carIdForLease = sc.nextInt();
                        sc.nextLine(); 
                        System.out.print("Enter Start Date (yyyy-MM-dd): ");
                        String start = sc.nextLine();
                        System.out.print("Enter End Date (yyyy-MM-dd): ");
                        String end = sc.nextLine();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = sdf.parse(start);
                        Date endDate = sdf.parse(end);

                        Lease lease = repo.createLease(custIdForLease, carIdForLease, startDate, endDate);
                        System.out.println("Lease created with ID: " + lease.getLeaseID());
                        break;

                    case 10:
                        System.out.print("Enter Lease ID to return car: ");
                        int leaseId = sc.nextInt();
                        repo.returnCar(leaseId);
                        break;

                    case 11:
                        List<Lease> activeLeases = repo.listActiveLeases();
                        for (Lease l : activeLeases) {
                            System.out.println(l.getLeaseID() + " " + l.getVehicleID() + " " + l.getCustomerID());
                        }
                        break;

                    case 12:
                        List<Lease> allLeases = repo.listLeaseHistory();
                        for (Lease l : allLeases) {
                            System.out.println(l.getLeaseID() + " " + l.getVehicleID() + " " + l.getCustomerID());
                        }
                        break;

                    case 13:
                        System.out.print("Enter Lease ID for Payment: ");
                        int leaseIDForPayment = sc.nextInt();
                        Lease leaseForPayment = repo.listLeaseHistory().stream()
                                .filter(l -> l.getLeaseID() == leaseIDForPayment)
                                .findFirst()
                                .orElseThrow(() -> new LeaseNotFoundException("Lease ID not found"));
                        System.out.print("Enter Payment Amount: ");
                        double amount = sc.nextDouble();
                        repo.recordPayment(leaseForPayment, amount);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                        break;
                }
            } catch (CarNotFoundException | CustomerNotFoundException | LeaseNotFoundException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
