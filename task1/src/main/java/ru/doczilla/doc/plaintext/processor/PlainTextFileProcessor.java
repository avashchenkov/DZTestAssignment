package ru.doczilla.doc.plaintext.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import ru.doczilla.doc.plaintext.model.PlainTextFileModel;

public class PlainTextFileProcessor {
    private Path pathToFile;

    /**
     * Constructs a new PlainTextFileProcessor with the given path to the file.
     *
     * @param pathToFile
     */
    public PlainTextFileProcessor(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public PlainTextFileModel buildPlainTextFileModel() {
        String content = readContent();
        Set<PlainTextFileModel> references = this.extractReferences();

        return new PlainTextFileModel(this.pathToFile.toString(), content, references);
    }

    /**
     * Reads the content of the file.
     *
     * @return the content of the file
     */
    private String readContent() {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(this.pathToFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();  // TODO: Enchance error handling if needed
        }

        return content.toString();
    }

    private Set<PlainTextFileModel> loadReferences(String content) {

    }

    private Set<PlainTextFileModel> extractReferences() {
        String content = this.readContent();

        return this.loadReferences(content);
    }
}
