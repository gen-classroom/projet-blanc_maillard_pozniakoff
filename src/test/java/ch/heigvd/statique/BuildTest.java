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

class BuildTest {

    @Test
    public void buildEmptyPath(){
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"init"};
        cmd.execute(args);

        args = new String[]{"build"};
        cmd.execute(args);

        File index = new File("./build/index.html");
        assertTrue(index.exists());
    }

    @Test
    public void buildCorrect() throws IOException {
        Callable<Integer> callable = new Statique();
        CommandLine cmd = new CommandLine(callable);

        String[] args = new String[]{"init", "/myStaticSite"};
        cmd.execute(args);
        args = new String[]{"build", "/myStaticSite"};
        cmd.execute(args);

        File configYaml = new File("/myStaticSite/build/config.yaml");
        File indexMd = new File("/myStaticSite/build/index.md");
        File indexHtml = new File("/myStaticSite/build/index.html");
        File templateFolder = new File("/myStaticSite/build/templateFolder");

        assertTrue(!configYaml.exists());
        assertTrue(!indexHtml.exists());
        assertTrue(!indexMd.exists());
        assertTrue(templateFolder.exists());

        FileUtils.deleteDirectory(new File("./monStaticSite"));
   }
}