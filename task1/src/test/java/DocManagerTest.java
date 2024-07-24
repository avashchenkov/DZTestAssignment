import org.junit.jupiter.api.Test;
import ru.doczilla.DocManager;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DocManagerTest {
    private static final String TEST_RESOURCES_PATH = "task1/src/test/resources";

    @Test
    void testExampleFromTask() {
        Path testFolderPath = null;

        try {
            testFolderPath = Paths.get(ClassLoader.getSystemResource("test_root_example").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        DocManager manager = new DocManager();
        String result = manager.mergeTextFiles(testFolderPath);

        String expected = """
Phasellus eget tellus ac risus iaculis feugiat nec in eros. Aenean in luctus ante. In lacinia lectus tempus, rutrum ipsum quis, gravida nunc. Fusce tempor eleifend libero at pharetra. Nulla lacinia ante ac felis malesuada auctor. Vestibulum eget congue sapien, ac euismod elit. Fusce nisl ante, consequat et imperdiet vel, semper et neque.

`Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse id enim euismod erat elementum cursus. In hac habitasse platea dictumst. Etiam vitae tortor ipsum. Morbi massa augue, lacinia sed nisl id, congue eleifend lorem.`

**`*require ‘Folder 2/File 2-1’*`**

`Praesent feugiat egestas sem, id luctus lectus dignissim ac. Donec elementum rhoncus quam, vitae viverra massa euismod a. Morbi dictum sapien sed porta tristique. Donec varius convallis quam in fringilla.`

**`*require ‘Folder 1/File 1-1’*`**

**`*require ‘Folder 2/File 2-1’*`**

`In pretium dictum lacinia. In rutrum, neque a dignissim maximus, dolor mi pretium ante, nec volutpat justo dolor non nulla. Vivamus nec suscipit nisl, ornare luctus erat. Aliquam eget est orci. Proin orci urna, elementum a nunc ac, fermentum varius eros. Mauris id massa elit.`

""";

        assertEquals(expected, result);
    }

    @Test
    void testShowMergeOrderFromRootFolder() {
        Path testFolderPath = null;

        try {
            testFolderPath = Paths.get(ClassLoader.getSystemResource("test_root_embedded_dependencies").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        DocManager manager = new DocManager();
        String result = manager.mergeTextFiles(testFolderPath);

        String expected = """
Content of sub-sub main file.

Start of sub main.
*require ‘Subfolder/Subfolder2/SubSubMain’*
End of sub main.

Start of the main file.
*require ‘Subfolder/SubMain’*
End of the main file.

""";

        assertEquals(expected, result);
    }

    @Test
    void testCyclicDependencyHandling() {
        Path testFolderPath = Paths.get(TEST_RESOURCES_PATH, "test_root_cyclic_dependencies");

        DocManager manager = new DocManager();
        Exception exception = assertThrows(RuntimeException.class, () -> manager.mergeTextFiles(testFolderPath));

        assertTrue(exception.getMessage().contains("Cyclic dependency detected"));
    }
}