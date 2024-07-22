package ru.doczilla.doc.plaintext.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Builds a PlainTextFileModel from the file.
     *
     * @return the PlainTextFileModel
     */
    public PlainTextFileModel buildPlainTextFileModel() {
        String content = readContent();
        Set<String> references = this.extractReferences();

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
            e.printStackTrace();  // TODO: Enhance error handling if needed
        }

        return content.toString();
    }

    private Set<String> extractReferences(String content) {
        Set<String> directives = new HashSet<>();
        Pattern pattern = Pattern.compile("require\\s+‘([^’]+)’");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            directives.add(matcher.group(1));
        }

        return directives;
    }

    private Set<String> extractReferences() {
        String content = this.readContent();

        return this.extractReferences(content);
    }
}
