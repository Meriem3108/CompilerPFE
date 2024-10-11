package com.example.testpfe.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;

@RestController
@RequestMapping("/api/compiler/template")
public class CompilerControllerTemplate {

    @PostMapping("/compile")
    public String compileCode(@RequestBody String code) {
        String output = "";
        try {
            // Write the code to a temporary file
            File sourceFile = new File(System.getProperty("java.io.tmpdir"), "TestCompTemplate.c");
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(code);
            writer.close();

            // Compile the C code
            Process compileProcess = new ProcessBuilder("gcc", sourceFile.getAbsolutePath(), "-o", sourceFile.getParent() + "/template.out").start();
            compileProcess.waitFor();

            // Check if the compilation was successful
            if (compileProcess.exitValue() != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorOutput.append(errorLine).append("\n");
                }
                return "Compilation failed:\n" + errorOutput.toString();
            }

            // Run the compiled code
            Process runProcess = new ProcessBuilder(sourceFile.getParent() + "/template.out").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }
            reader.close();

            // Check if the execution was successful
            if (runProcess.exitValue() != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorOutput.append(errorLine).append("\n");
                }
                return "Execution failed:\n" + errorOutput.toString();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Compilation or execution failed";
        }

        return output;
    }
}
