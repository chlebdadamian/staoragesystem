package com.itacademy.hw_storage_new;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessDataTest {

    @Test
    void test_readFileWithExportedData() {
        File file = new File("src/main/resources/dataExport.json");
        assertTrue(file.exists());
    }

}
