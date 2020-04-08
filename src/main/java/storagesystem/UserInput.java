package storagesystem;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    Scanner scanner = new Scanner(System.in);
    ProcessData data = new ProcessData();

    void startProgram() {

        System.out.println("=== SIMPLE STORAGE SYSTEM ===");
        System.out.println("=== Select option:        ===");
        System.out.println("1 - Start mass-import from CSV file");
        System.out.println("2 - Import data from JSON backup");
        System.out.println("3 - Export storage status to JSON backup");
        System.out.println("4 - GET state of given product");
        System.out.println("5 - ADD given product");
        System.out.println("6 - REMOVE given product");
        System.out.println("7 - SET state of given product");
        System.out.println("8 - PRINT state of storage");
        System.out.println("9 - EXIT program");

        int selected = scanner.nextInt();
        scanner.nextLine();
        if (isBetween(selected, 1, 9)) {
            switch (selected) {
                case 1:
                    try {
                        new OpenData().openData();
                    } catch (IOException e) {
                        System.out.println("Unexpected error occurred");
                    } finally {
                        startProgram();
                    }
                    break;
                case 2:
                    System.out.println("Import from dataExport.json file");
                    data.importState();
                    startProgram();
                    break;
                case 3:
                    System.out.println("Export to the dataExport.json file");
                    data.exportState();
                    startProgram();
                    break;
                case 4:
                    System.out.println("Write name of the product");
                    data.getState(scanner.nextLine());
                    startProgram();
                    break;
                case 5:
                    System.out.println("Write product name and quantity");
                    ProcessData.modifyState("ADD", scanner.nextLine(), readIntInput());
                    startProgram();
                    break;
                case 6:
                    System.out.println("Write product name and quantity");
                    ProcessData.modifyState("REMOVE", scanner.nextLine(), readIntInput());
                    startProgram();
                    break;
                case 7:
                    System.out.println("Write product name and quantity");
                    ProcessData.modifyState("SET", scanner.nextLine(), readIntInput());
                    startProgram();
                    break;
                case 8:
                    data.printState();
                    startProgram();
                    break;
                case 9:
                    break;
            }

        }

    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    private int readIntInput() {
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please write proper integer value");
            input = 0;
        } catch (Exception e) {
        } finally {
            scanner.nextLine();
        }
        return input;
    }
}
