package ru.doczilla.doc.plaintext.linker;

import ru.doczilla.doc.plaintext.model.PlainTextFileModel;

import java.util.LinkedList;

public class SortedInsertListOfFiles extends LinkedList<PlainTextFileModel> {
    @Override
    public boolean add(PlainTextFileModel file) {
        if (this.isEmpty()) {
            return super.add(file);
        }

        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.get(i).getRelativePath().compareTo(file.getRelativePath()) < 0 ||
                    file.getReferences().contains(this.get(i).getRelativePath())) {
                super.add(i, file);

                return true;
            }
        }

        this.addFirst(file);

        return true;
    }
}
