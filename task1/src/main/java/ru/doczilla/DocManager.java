package ru.doczilla;


import ru.doczilla.doc.plaintext.linker.PlainTextFileLinker;
import ru.doczilla.doc.plaintext.model.PlainTextFileModel;

import java.nio.file.Path;
import java.util.List;

public class DocManager {
    /**
     * Merges all text files in the given directory and its subdirectories.
     *
     * @param rootPath the root path to start the search from
     * @return the merged text files
     */
    public String mergeTextFiles(Path rootPath) {
        PlainTextFileLinker linker = new PlainTextFileLinker(rootPath);

        return linker.buildMergedFile();
    }

    /**
     * Returns a String as a list of files in the order they should be merged.
     *
     * @param rootPath the root path to start the search from
     * @return a list of files in the order they should be merged
     */
    public String showMergeOrder(Path rootPath) {
        PlainTextFileLinker linker = new PlainTextFileLinker(rootPath);
        List<PlainTextFileModel> sortedFiles = linker.buildSortedFileList();
        StringBuilder result = new StringBuilder();

        for (PlainTextFileModel file : sortedFiles) {
            result.append(file.getRelativePath()).append("\n");
        }

        return result.toString();
    }
}
