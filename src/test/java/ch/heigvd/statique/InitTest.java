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

    Path testFilesPath = Paths.get("src\\test\\java\\ch\\heigvd\\statique\\testFiles");

    @Test
    void testingTest(){
        assertTrue(true);
    }

    @Test
    void createFileShouldNotWorkIfFileExists() throws IOException {
        assertFalse(Init.createFile(new File(testFilesPath.toString() + "\\existingFile")));
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
        assertFalse(Init.createDirectory(new File(testFilesPath.toString())));
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
        assertTrue(Init.createConfigFiles(testFilesPath, false));
        new File(testFilesPath.toString() + "\\config.yaml").delete();
        new File(testFilesPath.toString() + "\\index.md").delete();
    }

    @Test
    void createConfigFilesShouldNotWorkIfConfigExists() throws IOException {
        assertFalse(Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), false));
    }

    @Test
    void createConfigFilesShouldWorkIfConfigExistsAndOptionOverwrite() throws IOException {
        assertTrue(Init.createConfigFiles(Paths.get(testFilesPath +"\\config"), true));
    }
}
