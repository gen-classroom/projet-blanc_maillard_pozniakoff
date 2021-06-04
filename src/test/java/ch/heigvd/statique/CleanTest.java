package ch.heigvd.statique;

import com.github.jknack.handlebars.internal.antlr.misc.Utils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

class CleanTest {

    String path = "/site";
    File site;
    File build;

    @BeforeEach
    void setUp() throws IOException {
        site = new File("." + path);
        build = new File("." + path + "/build");

        site.mkdirs();
        build.mkdirs();
    }

    @AfterEach
    void tearDown() throws IOException {
        FileUtils.deleteDirectory(site);
    }

    @Test
    public void nonValidPath(){
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"clean", "tagada"};

        cmd.execute(args);
    }

    @Test
    public void correctClean() throws IOException {
       assertTrue(Files.exists(site.toPath()));
       assertTrue(Files.exists(build.toPath()));

       Callable<Integer> callable = new Statique();
       CommandLine cmd = new CommandLine(callable);

       String[] args = new String[]{"clean", path};

       cmd.execute(args);

       assertFalse(Files.exists(build.toPath()));
       assertFalse(Files.exists(site.toPath()));
    }
}