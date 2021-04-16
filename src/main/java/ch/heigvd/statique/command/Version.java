package ch.heigvd.statique.command;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "version", description = "Print version of static site generator")
public class Version implements Callable<Integer> {

    @Override public Integer call() throws IOException {
        //Look for the version in the pom.xml file
        java.io.InputStream is = this.getClass().getResourceAsStream("my.properties");
        java.util.Properties p = new Properties();
        p.load(is);
        String version = p.getProperty("version");
        System.out.printf(version);
        return 1;
    }

}