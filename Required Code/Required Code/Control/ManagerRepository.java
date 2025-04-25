package Control;

import entity.HDBManager;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles all the data from the Manager data csv
 */
public final class ManagerRepository {

    private static final String FILENAME = "csv/ManagerList.csv";
    private static List<HDBManager> allManagers = new ArrayList<>();

    private ManagerRepository() {}

    public static void addManager(HDBManager manager) {
        allManagers.add(manager);
    }

    public static List<HDBManager> getAllManagers() {
        return new ArrayList<>(allManagers);
    }

    public static void clearAllManagers() {
        allManagers.clear();
    }

    public static void removeManager(HDBManager manager) {
        allManagers.remove(manager);
    }

    public static void loadManagersFromCSV() {
        clearAllManagers();

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

                HDBManager manager = new HDBManager(name, nric, age, maritalStatus, password, "Manager");
                addManager(manager);
            }
            System.out.println("Managers loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading managers: " + e.getMessage());
        }
    }

    public static void saveManagersToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("Name,NRIC,Age,MaritalStatus,Password");
            writer.newLine();

            for (HDBManager m : allManagers) {
                writer.write(String.join(",",
                    m.getName(),
                    m.getNric(),
                    String.valueOf(m.getAge()),
                    m.getMaritalStatus(),
                    m.getPassword()
                ));
                writer.newLine();
            }

            System.out.println("Managers saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving managers: " + e.getMessage());
        }
    }
}
