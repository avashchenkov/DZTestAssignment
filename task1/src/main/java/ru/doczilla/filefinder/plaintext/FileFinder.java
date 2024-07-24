package ru.doczilla.filefinder.plaintext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder {

    /**
     * Recursively finds all .txt files in the given root directory and its subdirectories.
     * @param rootPath the path to the root directory
     * @return a list of paths representing the .txt files
     */
    public List<Path> findTxtFilesRecursively(Path rootPath) {
        List<Path> txtFiles = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(rootPath)) {
            txtFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))  //TODO: Get rid of magic strings
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());  //TODO: Enhance error handling if needed
        }

        return txtFiles;
    }
}