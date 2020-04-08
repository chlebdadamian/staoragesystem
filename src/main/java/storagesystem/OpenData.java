package storagesystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

class OpenData {

    static String PATH = "src/main/java/resources";

    void openData() throws IOException {

        setPathToFile();
        Path dataPath = FileSystems.getDefault().getPath(getPATH());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataPath.toFile()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] splittedEntry = line.split(";");

                if (!splittedEntry[3].isEmpty()) {
                    for (int i = 3; i < splittedEntry.length; i++) {
                        String[] splittedProducts = splittedEntry[i].split(":");

                        String number = splittedProducts[1];
                        if (!number.matches("[0-9]+")) {
                            System.out.println("WARNING: Processed data contains non-number value at \"" + splittedProducts[0] + "\" quantity");
                            splittedProducts[1] = "0";
                        }
                        ProcessData.modifyState(splittedEntry[1], splittedProducts[0], Integer.parseInt(splittedProducts[1]));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file - please select proper file to import");
            openData();
        }
    }

    void setPathToFile() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("== Please enter absolute PATH to the file:");
        PATH = scanner.nextLine();

    }

    public static String getPATH() {
        return PATH;
    }
}
