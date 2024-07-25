package ru.doczilla.doc.plaintext.linker;

import ru.doczilla.doc.plaintext.model.PlainTextFileModel;

import java.nio.file.Path;
import java.util.*;


/**
 * Represents a collection of files sorted by their paths and then by their internal references.
 */
public class SortedFiles {
    private final Map<String, PlainTextFileModel> filesSortedByPath;
    private final Path rootPath;

    /**
     * Constructs a new instance of the empty SortedFiles.
     */
    public SortedFiles(Path rootPath) {
        this.filesSortedByPath = new TreeMap<>();
        this.rootPath = rootPath;
    }

    /**
     * Sorted insertion of a file.
     *
     * @param file
     * @return void
     */
    public void addFile(PlainTextFileModel file) {
        this.filesSortedByPath.put(file.getRelativePath(), file);
    }

    public List<PlainTextFileModel> getSortedFiles() throws IllegalStateException {
        List<PlainTextFileModel> filesSortedByPathAndReferences = new LinkedList<>();
        HashSet<String> handledFilesAsReferences = new HashSet<>();

        for (PlainTextFileModel file : this.filesSortedByPath.values()) {
            if (handledFilesAsReferences.contains(file.getRelativePath())) {
                continue;
            }

            Set<PlainTextFileModel> recursivelyCheckedFiles = new HashSet<>();

            reorderDependenciesRecursively(file, filesSortedByPathAndReferences, handledFilesAsReferences, recursivelyCheckedFiles);
            filesSortedByPathAndReferences.add(file);
        }

        return filesSortedByPathAndReferences;
    }

    private void reorderDependenciesRecursively(PlainTextFileModel file,
                                                List<PlainTextFileModel> filesSortedByPathAndReferences,
                                                HashSet<String> handledFilesAsReferences,
                                                Set<PlainTextFileModel> recursivelyCheckedFiles) {
        recursivelyCheckedFiles.add(file);

        for (String ref : file.getReferences()) {
            PlainTextFileModel childFile = this.filesSortedByPath.get(ref);

            if (recursivelyCheckedFiles.contains(childFile)) {
                throw new IllegalStateException(
                        buildCallTraceErrorMessage(recursivelyCheckedFiles, childFile)
                );
            }

            if (ref.compareTo(file.getRelativePath()) > 0) {
                reorderDependenciesRecursively(childFile, filesSortedByPathAndReferences, handledFilesAsReferences, recursivelyCheckedFiles);

                filesSortedByPathAndReferences.add(childFile);
                handledFilesAsReferences.add(ref);
            }
        }
    }

    private String buildCallTraceErrorMessage(Set<PlainTextFileModel> recursivelyCheckedFiles, PlainTextFileModel cyclicallyHandledFile) {
        StringBuilder errorMessage = new StringBuilder("Cyclic dependency detected:\n");

        for (PlainTextFileModel file : recursivelyCheckedFiles) {
            errorMessage.append(file.getRelativePath()).append(" ->\n");
        }

        errorMessage.append(cyclicallyHandledFile.getRelativePath());

        return errorMessage.toString();
    }
}
