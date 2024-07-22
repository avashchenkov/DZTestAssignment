package ru.doczilla.doc.plaintext.model;

import ru.doczilla.doc.plaintext.linker.PlainTextFileLinker;

import java.util.Set;

/**
 * Represents a model of a plain text file.
 */
public class PlainTextFileModel {
    private String relativePath;
    private String content;
    private Set<String> references;

    /**
     * Constructs a new PlainTextFileModel with the given relative path, content, and references.
     *
     * @param relativePath
     * @param content
     * @param references
     */
    public PlainTextFileModel(String relativePath, String content, Set<String> references) {
        this.relativePath = relativePath;
        this.content = content;
        this.references = references;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getContent() {
        return content;
    }

    public Set<String> getReferences() {
        return references;
    }

    public void setReferences(Set<String> references) {
        this.references = references;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
