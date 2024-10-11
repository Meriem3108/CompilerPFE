package com.example.testpfe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final String TEMPLATE_COMPILER_PATH = "C:\\Users\\merie\\OneDrive\\Bureau\\tests\\testSenarios\\Compilateurtraducteur\\templatefinal2.exe";
    private static final String SCENARIO_COMPILER_PATH = "C:\\Users\\merie\\OneDrive\\Bureau\\tests\\testSenarios\\Compilateurtraducteur\\testFinal.exe";
    //private static final String TEMPLATE_COMPILER_PATH = "C:\\Users\\merie\\OneDrive\\Bureau\\youssef\\TestComp15.exe";
    @PostMapping("/uploadTemplate")
    public ResponseEntity<String> handleTemplateUpload(@RequestParam("file") MultipartFile file) {
        return handleFileUpload(file, TEMPLATE_COMPILER_PATH);
    }

    @PostMapping("/uploadScenario")
    public ResponseEntity<String> handleScenarioUpload(@RequestParam("file") MultipartFile file) {
        return handleFileUpload(file, SCENARIO_COMPILER_PATH);
    }

    private ResponseEntity<String> handleFileUpload(MultipartFile file, String compilerPath) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }

        try {
            // Write the uploaded file to a temporary location
            Path tempSourceFile = Files.createTempFile("uploaded-", ".txt");
            Files.write(tempSourceFile, file.getBytes());

            // Create a temporary destination file
            Path tempDestFile = Files.createTempFile("result-", ".txt");

            // Call the C compiler
            String result = executeCCompiler(tempSourceFile.toString(), tempDestFile.toString(), compilerPath);

            // Read the content of the destination file
            String translatedContent = Files.readString(tempDestFile);

            // Clean up the temporary files
            Files.delete(tempSourceFile);
            Files.delete(tempDestFile);

            return ResponseEntity.ok(translatedContent);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }

    private String executeCCompiler(String sourceFilePath, String destFilePath, String compilerPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(compilerPath, sourceFilePath, destFilePath);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Read output from the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "Compiler execution failed with exit code: " + exitCode;
            }

            return output.toString();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing compiler";
        }
    }
}
