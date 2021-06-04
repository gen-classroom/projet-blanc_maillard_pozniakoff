package ch.heigvd.statique;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CleanTest {

    private static final String path = "/test";

    @Test
    public void nonValidPath(){
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"clean", "tagada"};

        cmd.execute(args);
    }

    @Test
    public void correctClean() throws IOException {
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"clean", "/myStaticSite"};

        Path path = Paths.get("/myStaticSite/build");
        Files.createDirectories(path);

        cmd.execute(args);

        boolean result = false;
        File testFolder = new File("/myStaticSite/build");

        assertTrue(!testFolder.exists());

        FileUtils.deleteDirectory(new File("./myStaticSite"));
    }
}