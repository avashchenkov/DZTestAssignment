package ru.doczilla.doc.plaintext.linker;

import ru.doczilla.doc.plaintext.filefinder.FileFinder;
import ru.doczilla.doc.plaintext.model.PlainTextFileModel;
import ru.doczilla.doc.plaintext.processor.PlainTextFileProcessor;

import java.nio.file.Path;
import java.util.List;

public class PlainTextFileLinker {
    private final Path rootPath;

    /**
     * Constructs a new instance of the processor for a given path.
     *
     * @param rootPath
     */
    public PlainTextFileLinker(Path rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Builds a sorted list of files.
     *
     * @return the sorted list of files
     */
    public List<PlainTextFileModel> buildSortedFileList() {
        FileFinder finder = new FileFinder();
        List<Path> txtFiles = finder.findTxtFilesRecursively(this.rootPath);
        SortedFiles sortedFiles = new SortedFiles();

        for (Path filePath : txtFiles) {
            PlainTextFileProcessor processor = new PlainTextFileProcessor(filePath);
            PlainTextFileModel fileModel = processor.buildPlainTextFileModel();

            sortedFiles.addFile(fileModel);
        }

        return sortedFiles.getSortedFiles();
    }

    /**
     * Builds a merged file from the sorted files.
     *
     * @return the merged file
     */
    public String buildMergedFile() {
        List<PlainTextFileModel> sortedFiles = this.buildSortedFileList();
        StringBuilder result = new StringBuilder();

        for (PlainTextFileModel file : sortedFiles) {
            result.append(file.getContent()).append("\n");
        }

        return result.toString();
    }
}
