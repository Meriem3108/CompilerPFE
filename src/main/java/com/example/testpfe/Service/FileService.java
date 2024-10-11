/*package com.example.testpfe.Service;

import com.example.testpfe.Entity.TranslatedFile;
import com.example.testpfe.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFileService {

    private final Path rootLocation = Paths.get("uploaded-files");

    @Autowired
    private FileRepository fileRepository;
    @Override
    public String saveFile(MultipartFile file) {
        try {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            file.transferTo(destinationFile);
            return destinationFile.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier.", e);
        }
    }
    @Override
    public void translateFile(String fileId) {
        // Chemin du fichier à traduire
        Path filePath = rootLocation.resolve(fileId).normalize().toAbsolutePath();

        try {
            // Appel du compilateur traducteur en C
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "C:\\Users\\merie\\OneDrive\\Bureau\\tests\\testSenarios\\Compilateurtraducteur\\TestComp15",
                    filePath.toString()
            );
            processBuilder.directory(new File("C:\\Users\\merie\\OneDrive\\Bureau\\tests\\testSenarios\\Compilateurtraducteur"));
            Process process = processBuilder.start();
            process.waitFor();

            // Lire le contenu traduit
            Path translatedFilePath = rootLocation.resolve("translated_" + fileId).normalize().toAbsolutePath();
            String translatedContent = new String(Files.readAllBytes(translatedFilePath));

            // Enregistrer le résultat dans la base de données
            TranslatedFile translatedFile = new TranslatedFile();
            translatedFile.setOriginalFileName(fileId);
            translatedFile.setTranslatedContent(translatedContent);
            fileRepository.save(translatedFile);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erreur lors de la traduction du fichier.", e);
        }
    }
}
*/