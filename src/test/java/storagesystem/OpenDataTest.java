package com.itacademy.hw_storage_new;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenDataTest {

    @Test
    void test_readFileWithInputData() {
        File file = new File("src/main/resources/inputData.csv");
        assertTrue(file.exists());
    }

}


