package ch.heigvd.statique;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuildTest {

    @AfterEach
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(new File("./monStaticSite"));
    }

    @Test
    public void buildEmptyPath(){
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"init"};
        cmd.execute(args);

        args = new String[]{"build"};
        cmd.execute(args);

        File index = new File("./build/index.html");
        assertFalse(index.exists());
    }

    @Test
    public void buildCorrect() throws IOException {
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"init", "/myStaticSite"};
        cmd.execute(args);
        args = new String[]{"build", "/myStaticSite"};
        cmd.execute(args);

        File configYaml = new File(System.getProperty("user.dir") + "/myStaticSite/config.yaml");
        File indexMd = new File(System.getProperty("user.dir") + "/myStaticSite/build/index.html");
        File indexHtml = new File(System.getProperty("user.dir") +"/myStaticSite/index.md");

        assertTrue(configYaml.exists());
        assertTrue(indexHtml.exists());
        assertTrue(indexMd.exists());

   }
}