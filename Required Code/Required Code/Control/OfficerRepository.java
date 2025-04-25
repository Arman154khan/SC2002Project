package Control;

import entity.Officer;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles all csv data for officers. 
 */

public final class OfficerRepository {

    private static final String FILENAME = "csv/OfficerList.csv";
    private static final List<Officer> allOfficers = new ArrayList<>();

    private OfficerRepository() {}

    public static void addOfficer(Officer officer) {
        allOfficers.add(officer);
    }

    public static List<Officer> getAllOfficers() {
        return new ArrayList<>(allOfficers);
    }

    public static void clearAllOfficers() {
        allOfficers.clear();
    }

    public static void removeOfficer(Officer officer) {
        allOfficers.remove(officer);
    }

    public static void loadOfficersFromCSV() {
        clearAllOfficers();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                if (fields.length < 5) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String name = fields[0].trim();
                String nric = fields[1].trim();
                int age = Integer.parseInt(fields[2].trim());
                String maritalStatus = fields[3].trim();
                String password = fields[4].trim();

                Officer officer = new Officer(name, nric, age, maritalStatus, password, "Officer");
                addOfficer(officer);
            }

            System.out.println("Officers loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading officers: " + e.getMessage());
        }
    }

    public static void saveOfficersToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("Name,NRIC,Age,MaritalStatus,Password");
            writer.newLine();

            for (Officer o : allOfficers) {
                writer.write(String.join(",",
                    o.getName(),
                    o.getNric(),
                    String.valueOf(o.getAge()),
                    o.getMaritalStatus(),
                    o.getPassword()
                ));
                writer.newLine();
            }

            System.out.println("Officers saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving officers: " + e.getMessage());
        }
    }
}
