package ru.doczilla.doc.plaintext.linker;

import ru.doczilla.doc.plaintext.model.PlainTextFileModel;
import ru.doczilla.doc.plaintext.processor.PlainTextFileProcessor;
import ru.doczilla.utils.Pair;

import java.nio.file.Path;
import java.util.*;

public class SortedFiles {
    private final Map<String, PlainTextFileModel> filesSortedByPath;

    /**
     * Constructs a new instance of the empty SortedFiles.
     */
    public SortedFiles() {
        this.filesSortedByPath = new TreeMap<>();
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
        Pair<Boolean, List<PlainTextFileModel>> cyclicDependencyReport = hasCyclicDependency();

        if (cyclicDependencyReport.getKey()) {
            throw new IllegalStateException("Cyclic dependency detected: " + cyclicDependencyReport.getValue());
        }

        List<PlainTextFileModel> filesSortedByPathAndReferences = new LinkedList<>();

        for (PlainTextFileModel file : this.filesSortedByPath.values()) {
            for (String ref : file.getReferences()) {
                if (ref.compareTo(file.getRelativePath()) > 0) {
                    Path pathToFile = Path.of(ref);
                    PlainTextFileProcessor processor = new PlainTextFileProcessor(pathToFile);
                    PlainTextFileModel fileModel = processor.buildPlainTextFileModel();

                    filesSortedByPathAndReferences.add(fileModel);
                    this.filesSortedByPath.remove(ref);
                }
            }

            filesSortedByPathAndReferences.add(file);
        }

        return filesSortedByPathAndReferences;
    }

    private Pair<Boolean, List<PlainTextFileModel>> hasCyclicDependency() {
        Map<String, PlainTextFileModel> checkedFiles = new HashMap<>(this.filesSortedByPath.size());

        for (String ref : this.filesSortedByPath.keySet()) {
            List<PlainTextFileModel> trace = new LinkedList<>();

            if (hasCyclicDependency(ref, checkedFiles, trace)) {
                return new Pair<>(true, trace);
            }
        }

        return new Pair<>(false, new LinkedList<>());
    }

    private boolean hasCyclicDependency(String ref,
                                        Map<String, PlainTextFileModel> checkedFiles,
                                        List<PlainTextFileModel> trace) {
        if (checkedFiles.containsKey(ref)) {
            return false;
        }

        PlainTextFileModel file = this.filesSortedByPath.get(ref);
        checkedFiles.put(ref, file);
        trace.add(file);

        for (String childRef : file.getReferences()) {
            return hasCyclicDependency(childRef, checkedFiles, trace);
        }

        trace.remove(file);

        return false;
    }
}
