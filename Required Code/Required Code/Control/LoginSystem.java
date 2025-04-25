package Control;

import entity.*;
import java.io.*;
import java.util.*;

/**
 * Control system for the login
 */

public class LoginSystem {
    private Map<String, User> users = new HashMap<>();
    private Scanner sc = new Scanner(System.in);

    public LoginSystem() {
        for(HDBManager m : ManagerRepository.getAllManagers()){
            users.put(m.getNric(), m);
        }

        for(Officer o : OfficerRepository.getAllOfficers()){
            users.put(o.getNric(), o);
        }

        for(Applicant a : ApplicantRepository.getAllApplicants()){
            users.put(a.getNric(), a);
        }
    }

    public User start() {
        System.out.println("==== Login ====");

        while (true) {
            System.out.print("Enter NRIC: ");
            String NRIC = sc.nextLine().trim().toUpperCase();

            if (!users.containsKey(NRIC)) {
                System.out.println("NRIC not found. Try again.");
                continue;
            }

            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();

            User user = users.get(NRIC);

            if (!user.getPassword().equals(password)) {
                System.out.println("Incorrect password. Try again.");
                continue;
            }

            System.out.println("You have successfully logged in. Welcome, " + user.getName() + ".");
            return user;
        }
    }

    // private void loadApplicantsFromCSV(String filename) {
    //     try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
    //         String line = br.readLine(); 
    //         while ((line = br.readLine()) != null) {
    //             String[] data = line.split(",");
    //             if (data.length >= 5) {
    //                 String name = data[0].trim();
    //                 String nric = data[1].trim().toUpperCase();
    //                 int age = Integer.parseInt(data[2].trim());
    //                 String maritalStatus = data[3].trim();
    //                 String password = data[4].trim();
    //                 users.put(nric, new Applicant(name, nric, age, maritalStatus, password, "Applicant"));
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error loading applicants: " + e.getMessage());
    //     }
    // }

    // private void loadOfficersFromCSV(String filename) {
    //     try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
    //         String line = br.readLine(); 
    //         while ((line = br.readLine()) != null) {
    //             String[] data = line.split(",");
    //             if (data.length >= 5) {
    //                 String nric = data[0].trim().toUpperCase();
    //                 String name = data[1].trim();
    //                 int age = Integer.parseInt(data[2].trim());
    //                 String maritalStatus = data[3].trim();
    //                 String password = data[4].trim();
    //                 users.put(nric, new Officer(name, nric, age, maritalStatus, password, "Officer"));
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error loading officers: " + e.getMessage());
    //     }
    // }

    // private void loadManagersFromCSV(String filename) {
    //     try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
    //         String line = br.readLine(); 
    //         while ((line = br.readLine()) != null) {
    //             String[] data = line.split(",");
    //             if (data.length >= 5) {
    //                 String nric = data[0].trim().toUpperCase();
    //                 String name = data[1].trim();
    //                 int age = Integer.parseInt(data[2].trim());
    //                 String maritalStatus = data[3].trim();
    //                 String password = data[4].trim();
    //                 users.put(nric, new HDBManager(name, nric, age, maritalStatus, password, "Manager"));
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error loading managers: " + e.getMessage());
    //     }
    // }
}
