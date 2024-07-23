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

    public List<PlainTextFileModel> buildSortedFileMap() {
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
}
