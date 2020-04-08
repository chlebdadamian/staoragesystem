package storagesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class ProcessData {

    private static final String EXPORT_FILE_NAME = "dataExport.json";
    static Map<String, Integer> listOfProducts = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static int count = -1;

    void getState(String productName) {

        if (getListOfProducts().containsKey(productName)) {
            System.out.println("Storage state: " + productName + " quantity: " + getListOfProducts().get(productName));
        } else {
            System.out.println("Searched product: " + productName + " not found in database!");
        }

    }

    public static Map<String, Integer> getListOfProducts() {
        return listOfProducts;
    }

    static void modifyState(String type, String productName, Integer productQuantity) {

        LocalDateTime localDateTime = LocalDateTime.now();

        if (productName != null && productQuantity != null) {

            if (type.equalsIgnoreCase("ADD")) {
                Commands.ADD.perform(productName, productQuantity);
                updateCSV(++count + ";" + "ADD;" + localDateTime + ";" + productName + ":" + productQuantity + ";");
            } else if (type.equalsIgnoreCase("REMOVE")) {
                Commands.REMOVE.perform(productName, productQuantity);
                updateCSV(++count + ";" + "REMOVE;" + localDateTime + ";" + productName + ":" + productQuantity + ";");
            } else if (type.equalsIgnoreCase("SET")) {
                Commands.SET.perform(productName, productQuantity);
                updateCSV(++count + ";" + "SET;" + localDateTime + ";" + productName + ":" + productQuantity + ";");
            } else {
                System.out.println("Incorrect command - no changes applied.");
            }

        } else {
            System.out.println("Cannot modify the product state - improper input data");
        }

    }

    void exportState() {
        try {
            ObjectWriter writer = getMapper().writer(new DefaultPrettyPrinter());
            Path path = Paths.get(OpenData.getPATH());
            String directory = path.getParent().toString();
            writer.writeValue(new File(directory + File.separator + getExportFileName()), getListOfProducts());
            System.out.println("Export successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void importState() {

        try {
            TypeReference<HashMap<String, Integer>> typeReference = new TypeReference<>() {
            };
            Path path = Paths.get(OpenData.getPATH());
            String directory = path.getParent().toString();
            Map<String, Integer> tempMap = getMapper().readValue(new File(directory + File.separator + getExportFileName()), typeReference);
            getListOfProducts().clear();
            tempMap.forEach(Commands.SET::perform);
            System.out.println("Import successful");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void printState() {
        try {
            String print = getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(getListOfProducts());
            System.out.println("Warehouse state:");
            System.out.println(print);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static String getExportFileName() {
        return EXPORT_FILE_NAME;
    }

    private static void updateCSV(String updateLine) {
        Path path = Paths.get(OpenData.getPATH());
        String directory = path.getParent().toString();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory + File.separator + "commandHistory.csv", true))) {
            bufferedWriter.write("\n" + updateLine);
        } catch (IOException e) {
            System.out.println("Error: " + e + " while updating CSV file");
        }

    }
}
