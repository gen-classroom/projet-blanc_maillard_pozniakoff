package ch.heigvd.statique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ch.heigvd.statique.command.Init;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class InitTest {

    Path testFilesPath;
    @BeforeEach
    private void initPath() {
        testFilesPath = Paths.get(System.getProperty("user.dir") + "\\src\\test\\java\\ch\\heigvd\\statique\\testFiles");
        testFilesPath.toFile().mkdir();
    }

    @Test
    void createFileShouldNotWorkIfFileExists() throws IOException {
        File f = new File(testFilesPath.toString() + "\\existingFile");
        Init.createFile(f);
        assertFalse(Init.createFile(f));
        f.delete();
    }

    @Test
    void createFileShouldWorkIfFileDontExist() throws IOException {
        File f = new File(testFilesPath.toString() + "\\file");
        f.delete();
        assertTrue(Init.createFile(f));
        f.delete();
    }

    @Test
    void createDirectoryShouldNotWorkIfDirectoryExists() throws IOException {
        File f = new File(testFilesPath.toString());
        f.delete();
        Init.createDirectory(f);
        assertFalse(Init.createDirectory(f));
        f.delete();
    }

    @Test
    void createDirectoryShouldWorkIfDirectoryDontExist() throws IOException {
        File f = new File(testFilesPath.toString() + "\\directory");
        f.delete();
        assertTrue(Init.createDirectory(f));
        f.delete();
    }

    @Test
    void createConfigFileShouldWorkIfConfigDontExist() throws IOException {
        Path newPath = Paths.get(testFilesPath.toString()+"\\test");
        Init.createDirectory(newPath.toFile());
        new File(newPath.toString() + "\\config.yaml").delete();
        new File(newPath.toString() + "\\index.md").delete();
        assertTrue(Init.createConfigFiles(newPath, false));
        new File(newPath.toString() + "\\config.yaml").delete();
        new File(newPath.toString() + "\\index.md").delete();
        newPath.toFile().delete();

    }

    @Test
    void createConfigFilesShouldNotWorkIfConfigExists() throws IOException {
        Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), false);
        assertFalse(Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), false));
        new File(testFilesPath.toString() + "\\config\\config.yaml").delete();
        new File(testFilesPath.toString() + "\\config\\index.md").delete();
        new File(Paths.get(testFilesPath +"\\config").toString()).delete();
        testFilesPath.toFile().delete();
    }

    @Test
    void createConfigFilesShouldWorkIfConfigExistsAndOptionOverwrite() throws IOException {
        Init.createDirectory(new File(testFilesPath +"\\config"));
        Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), false);
        assertTrue(Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), true));
        new File(testFilesPath.toString() + "\\config.yaml").delete();
        new File(testFilesPath.toString() + "\\index.md").delete();
        new File(Paths.get(testFilesPath +"\\config").toString()).delete();
        testFilesPath.toFile().delete();
    }
}
