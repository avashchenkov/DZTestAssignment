package ru.doczilla;


import ru.doczilla.doc.plaintext.linker.PlainTextFileLinker;

import java.nio.file.Path;

public class DocManager {
    /**
     * Merges all text files in the given directory and its subdirectories.
     *
     * @param rootPath the root path to start the search from
     * @return the merged text files
     */
    public String mergeTextFiles(Path rootPath) {
        PlainTextFileLinker linker = new PlainTextFileLinker(rootPath);

        try {
            String mergedText = linker.buildMergedFile();

            return mergedText;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
